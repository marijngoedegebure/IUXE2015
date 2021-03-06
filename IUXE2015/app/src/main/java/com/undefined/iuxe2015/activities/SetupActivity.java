package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SetupFragment;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.types.ScaleType;
import com.undefined.iuxe2015.tools.Convert;
import com.undefined.iuxe2015.tools.PreferenceTool;
import com.undefined.iuxe2015.tools.SpotifyTool;

public class SetupActivity extends MumoActivity {

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    public static final int REQUEST_CODE = 1337;

    //TODO remove
    //public static Player mPlayer;

    private SetupFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        SpotifyTool.startLogin(this, REQUEST_CODE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        fragment = (SetupFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_setup);
    }

    public void startHub(View button) {
        int buttonId = button.getId();
        ScaleType scale = ScaleType.getDefault();
        if (buttonId == R.id.setup_btn_scale_normal) {
            scale = ScaleType.NORMAL;
        } else if (buttonId == R.id.setup_btn_scale_large) {
            scale = ScaleType.LARGE;
        } else if (buttonId == R.id.setup_btn_scale_huge) {
            scale = ScaleType.HUGE;
        }

        int stakeholderId = PreferenceTool.getCurrentStakeholderId(this);
        Log.i("SetupActivity", "stakeholderid " + stakeholderId);
        Stakeholder s = getData().getStakeholderWithId(stakeholderId);
        if(s!=null) {
            s.setScaleType(scale);
            getData().updateStakeholder(s);
        }else{

        }

        startActivity(new Intent(this, HubActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    String accessToken = response.getAccessToken();
                    Config playerConfig = new Config(this, accessToken, getString(R.string.client_id));
                    MumoApplication.mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                        @Override
                        public void onInitialized(Player player) {
                            MumoApplication.mPlayer.addConnectionStateCallback(fragment);
                            MumoApplication.mPlayer.addPlayerNotificationCallback((MumoApplication)getApplication());
                            //mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
                            Log.d("SetupActivity", "initialized player");
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Log.e("SetupActivity", "Could not initialize player: " + throwable.getMessage());
                            fragment.onLoginFailed(throwable);
                        }
                    });
                    break;

                // Auth flow returned an error
                case ERROR:
                    fragment.onLoginFailed(null);
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
