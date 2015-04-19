package com.undefined.iuxe2015;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.undefined.iuxe2015.model.persistent.MumoDataSource;

/**
 * Created by Jan-Willem on 15-4-2015.
 */
public class MumoDialog extends DialogFragment {

    protected MumoDataSource getData() {
        if (getActivity() == null || isDetached()) {
            Log.w("MumoFragment", "Fragment wanted data after detachment!");
            return null;
        } else
            return ((MumoActivity) getActivity()).getData();
    }

    protected void toast(String message) {
        if (!(getActivity() == null || isDetached()))
            ((MumoActivity) getActivity()).toast(message);
    }
}
