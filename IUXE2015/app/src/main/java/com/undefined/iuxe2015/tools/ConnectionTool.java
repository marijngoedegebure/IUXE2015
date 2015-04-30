package com.undefined.iuxe2015.tools;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.model.Song;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class ConnectionTool {

    public interface ConnectionQueryResultListener {
        public void onConnectionSuccess(QueryResult result);

        public void onConnectionFailed(Exception e);
    }

    public interface ConnectionSongListener {
        public void onConnectionSuccess(Song result);

        public void onConnectionFailed(Exception e);
    }

    public static void getSongsForQuery(Context c, String query, final ConnectionQueryResultListener UiCallback) {

        String url;
        try {
            url = c.getString(R.string.query_url, URLEncoder.encode(query, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            url = "";
        }
        Log.d("ConnectionTool", url);

        Ion.with(c).load(url).as(QueryResult.class).setCallback(new FutureCallback<QueryResult>() {
            @Override
            public void onCompleted(Exception e, QueryResult result) {
                if (e != null) {
                    Log.e("ConnectionTool", "ERROR: " + e.getLocalizedMessage());
                    if (UiCallback != null)
                        UiCallback.onConnectionFailed(e);
                } else {
                    if (UiCallback != null)
                        UiCallback.onConnectionSuccess(result);
                }
            }
        });
    }

    public static void getSong(Context c, String query, final ConnectionSongListener UiCallback) {
        String url = c.getString(R.string.query_song_url, query);
        Log.d("ConnectionTool", url);

        Ion.with(c).load(url).as(Song.class).setCallback(new FutureCallback<Song>() {
            @Override
            public void onCompleted(Exception e, Song result) {
                if (e != null) {
                    Log.e("ConnectionTool", "ERROR: " + e.getLocalizedMessage());
                    if (UiCallback != null)
                        UiCallback.onConnectionFailed(e);
                } else {
                    if (UiCallback != null)
                        UiCallback.onConnectionSuccess(result);
                }
            }
        });
    }
}
