package com.undefined.iuxe2015;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.undefined.iuxe2015.dialogs.RateSongDialog;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;

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

    protected void toast(String message) {
        if (!(getActivity() == null || isDetached()))
            ((MumoActivity) getActivity()).toast(message);
    }

    protected RatingType getRatingType() {
        if (getActivity() == null || isDetached()) {
            Log.w("MumoFragment", "Fragment wanted ratingType after detachment!");
            return RatingType.getDefault();
        } else
            return ((MumoActivity) getActivity()).getRatingType();
    }

    public void showRatingDialog(Song song) {
        if (getActivity() == null || isDetached()) {
            Log.w("MumoFragment", "Fragment wanted to show ratingdialog after detachment!");
        } else {
            ((MumoActivity) getActivity()).showRatingDialog(song);
        }
    }
}
