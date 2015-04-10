package com.undefined.iuxe2015.tools;

import android.app.Activity;
import android.content.Context;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.undefined.iuxe2015.R;

/**
 * Created by Jan-Willem on 9-4-2015.
 */
public class SpotifyTool {

    public static void startLogin(Activity a, int requestCode) {
        String clientId = a.getString(R.string.client_id);
        String redirectUri = a.getString(R.string.redirect_uri);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(clientId,
                AuthenticationResponse.Type.TOKEN, redirectUri);
        builder.setScopes(new String[]{"user-read-private", "playlist-read-private", "playlist-modify-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(a, requestCode, request);
    }

    public static void logout(Context c) {
        AuthenticationClient.logout(c);
    }
}
