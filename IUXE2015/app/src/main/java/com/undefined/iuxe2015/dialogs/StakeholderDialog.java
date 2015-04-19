package com.undefined.iuxe2015.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoDialog;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.fragments.SetupFragment;
import com.undefined.iuxe2015.model.Stakeholder;

/**
 * Created by Jan-Willem on 15-4-2015.
 */
public class StakeholderDialog extends MumoDialog {

    public static final String TAG = "StakeholderDialog";
    private static final String EXTRA_STAKEHOLDER_ID = "stakeholder_id";

    public static StakeholderDialog getInstance(int stakeholderId) {
        StakeholderDialog d = new StakeholderDialog();
        Bundle args = new Bundle();
        args.putInt(EXTRA_STAKEHOLDER_ID, stakeholderId);
        d.setArguments(args);
        return d;
    }

    private SetupFragment getSetupFragment() {
        SetupFragment s = (SetupFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_setup);
        if (s == null)
            Log.e(TAG, "ERROR:::: COULD NOT FIND PARENT FRAGMENT!!!!");
        return s;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final Stakeholder stakeholder = getData().getStakeholderWithId(args.getInt(EXTRA_STAKEHOLDER_ID));

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_stakeholder, null, false);
        final TextView header = (TextView) v.findViewById(R.id.stakeholder_header);
        final EditText name = (EditText) v.findViewById(R.id.stakeholder_name);
        final EditText age = (EditText) v.findViewById(R.id.stakeholder_age);
        final Button delete1 = (Button) v.findViewById(R.id.stakeholder_btn_delete1);
        final View delete2Wrapper = v.findViewById(R.id.stakeholder_delete2);
        final Button delete2 = (Button) v.findViewById(R.id.stakeholder_btn_delete2);

        header.setText(stakeholder == null ? R.string.setup_stakeholder_header_new : R.string.setup_stakeholder_header_edit);
        if (stakeholder != null) {
            name.setText(stakeholder.getName());
            name.setSelection(stakeholder.getName().length());
            String ageString = "" + stakeholder.getAge();
            age.setText(ageString);
            age.setSelection(ageString.length());
        }

        builder.setView(v)
                .setPositiveButton(stakeholder == null ? R.string.create : R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newName = name.getText().toString();
                        int newAge = -1;
                        try {
                            newAge = Integer.parseInt(age.getText().toString());
                        } catch (NumberFormatException e) {
                            Log.w("Stakeholder", "Age parse error: " + e.getLocalizedMessage());
                            toast("Warning: No stakeholder age!");
                        }

                        if (newName.length() == 0) {
                            Log.w("Stakeholder", "Warning: Empty name");
                            toast("Warning: Empty stakeholder name!");
                        }
                        Stakeholder newStakeholder;
                        if (stakeholder == null) {
                            newStakeholder = getData().newStakeholder(newName, newAge);
                        } else {
                            stakeholder.setAge(newAge);
                            stakeholder.setName(newName);
                            newStakeholder = getData().updateStakeholder(stakeholder);
                        }

                        getSetupFragment().refreshStakeholderSpinnerAndSelectStakeholder(newStakeholder);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        final AlertDialog d = builder.create();

        delete2Wrapper.setVisibility(View.GONE);
        delete1.setVisibility(stakeholder == null ? View.GONE : View.VISIBLE);

        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete1.setVisibility(View.GONE);
                delete2Wrapper.setVisibility(View.VISIBLE);
            }
        });
        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData().deleteStakeholder(stakeholder);
                getSetupFragment().refreshStakeholderSpinnerAndSelectStakeholder(null);
                d.cancel();
            }
        });

        // Create the AlertDialog object and return it
        return d;
    }
}
