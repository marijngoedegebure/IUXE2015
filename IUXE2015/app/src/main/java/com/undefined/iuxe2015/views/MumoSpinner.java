package com.undefined.iuxe2015.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by Jan-Willem on 19-4-2015.
 * Spinner extension that calls onItemSelected even when the selection is the same as its previous value
 */
public class MumoSpinner extends Spinner {
    public MumoSpinner(Context context) {
        super(context);
    }

    public MumoSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MumoSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected && listener != null) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            listener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    OnItemSelectedListener listener;

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected && listener != null) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            listener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
        this.listener = listener;
    }
}
