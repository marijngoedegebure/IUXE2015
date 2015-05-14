package com.undefined.iuxe2015.activities;

import android.os.Bundle;
import android.util.Log;
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
import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.MusicControllerFragment;
import com.undefined.iuxe2015.fragments.SearchFragment;


public class SearchActivity extends MumoActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .commit();
        }
    }
}
