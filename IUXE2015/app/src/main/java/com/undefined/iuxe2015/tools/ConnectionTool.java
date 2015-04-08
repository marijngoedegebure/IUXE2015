package com.undefined.iuxe2015.tools;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.async.future.ConvertFuture;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.json.QueryResult;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public class ConnectionTool {

    public interface ConnectionListener {
        public void onConnectionSuccess(QueryResult result);

        public void onConnectionFailed(Exception e);
    }

    public static void getSongsForQuery(Context c, String query, final ConnectionListener UiCallback) {
        String url = c.getString(R.string.query_url, query);

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
}
