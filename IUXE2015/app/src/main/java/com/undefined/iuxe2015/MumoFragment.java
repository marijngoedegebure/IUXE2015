package com.undefined.iuxe2015;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.undefined.iuxe2015.model.persistent.MumoDataSource;

/**
 * Created by Jan-Willem on 8-4-2015.
 */
public abstract class MumoFragment extends Fragment {

    protected MumoDataSource getData() {
        if (getActivity() == null || isDetached()) {
            Log.w("MumoFragment", "Fragment wanted data after detachment!");
            return null;
        } else
            return ((MumoActivity) getActivity()).getData();
    }
}
