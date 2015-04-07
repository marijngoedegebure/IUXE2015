package com.undefined.iuxe2015;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.content.Intent;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.undefined.iuxe2015.fragments.SearchFragment;


public class SearchActivity extends ActionBarActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    // Spotify client id
    private static final String CLIENT_ID = "b62cce1d0d804fbfa1db66540a8018e4";
    // Spotify call back url
    private static final String REDIRECT_URI = "yourcustomprotocol://callback";

    // Spotify static Access Token
    String SPOTIFY_ACCESS_TOKEN = "BQB0cVMHxoWSDZrFoXfxgnRlTpMEQJJFw2f-OqATyPH4uQfKbc9tFRvqD6MwFqeF316qpWTEqlWos0WsSvQ5R2O_QTQhmw1_OXPbaCMy8tyVHrAaw7LjnjkIH-M1V1pbKx-jkW9DNJmdFOq3rowSvDYIGDpD-vKrpgA9YYU";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SearchFragment())
                    .commit();
        }

        Config playerConfig = new Config(this, SPOTIFY_ACCESS_TOKEN, CLIENT_ID);
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(SearchActivity.this);
                mPlayer.addPlayerNotificationCallback(SearchActivity.this);
                mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("SearchActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onLoggedIn() {
        Log.d("SearchActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("SearchActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("SearchActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("SearchActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("SearchActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("SearchActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("SearchActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
