package com.undefined.iuxe2015.tools;

import android.app.Activity;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

/**
 * Created by Jan-Willem on 9-4-2015.
 */
public class SpotifyTool {

    public static void startLogin(Activity a, String clientId, String redirectUri, int requestCode) {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(clientId,
                AuthenticationResponse.Type.TOKEN,
                redirectUri);
        builder.setScopes(new String[]{"user-read-private", "playlist-read-private", "playlist-modify-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(a, requestCode, request);
    }
}
