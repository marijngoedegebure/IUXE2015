package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SetupFragment;
import com.undefined.iuxe2015.tools.SpotifyTool;

public class SetupActivity extends MumoActivity implements
        PlayerNotificationCallback {

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    //TODO remove
    private Player mPlayer;

    private SetupFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        //TODO setup Spotify ONLY if needed.
        SpotifyTool.startLogin(this, getString(R.string.client_id), getString(R.string.redirect_uri), REQUEST_CODE);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        fragment = (SetupFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_setup);
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
                    mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                        @Override
                        public void onInitialized(Player player) {
                            mPlayer.addConnectionStateCallback(fragment);
                            mPlayer.addPlayerNotificationCallback(SetupActivity.this);
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
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("SetupActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("SetupActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
