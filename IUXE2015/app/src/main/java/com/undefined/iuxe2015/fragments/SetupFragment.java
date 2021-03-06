package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.adapters.StakeholderAdapter;
import com.undefined.iuxe2015.dialogs.StakeholderDialog;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;
import com.undefined.iuxe2015.tools.PreferenceTool;
import com.undefined.iuxe2015.tools.SpotifyTool;
import com.undefined.iuxe2015.views.MumoSpinner;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetupFragment extends MumoFragment implements ConnectionStateCallback {

    @InjectView(R.id.setup_list_btns)
    ScrollView listBtns;
    @InjectView(R.id.setup_error)
    TextView error;
    @InjectView(R.id.setup_btn_settings)
    TextView btnSettings;
    @InjectView(R.id.setup_box_settings)
    View boxSettings;
    @InjectView(R.id.setup_rating)
    RadioGroup radioRating;
    @InjectView(R.id.setup_login)
    Button spotifyLogin;
    @InjectView(R.id.setup_logout)
    Button spotifyLogout;
    @InjectView(R.id.setup_stakeholder_spinner)
    MumoSpinner stakeholderSpinner;
    @InjectView(R.id.setup_stakeholder_add)
    Button stakeholderBtnAdd;

    private StakeholderAdapter adapter;

    public SetupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        showSetup(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetup(boxSettings.getVisibility() != View.VISIBLE);
            }
        });

        switch (getRatingType()) {
            case BINARY:
                radioRating.check(R.id.setup_rating_binary);
                break;
            case LIKERT:
                radioRating.check(R.id.setup_rating_likert);
                break;
            default:
                toast("Unknown ratingtype in preferences: " + getRatingType());
                radioRating.check(R.id.setup_rating_binary);
                break;
        }

        radioRating.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.setup_rating_binary) {
                    PreferenceTool.setRatingType(getActivity(), RatingType.BINARY);
                } else if (checkedId == R.id.setup_rating_likert) {
                    PreferenceTool.setRatingType(getActivity(), RatingType.LIKERT);
                } else {
                    toast("Unknown radiobutton pressed");
                }
            }
        });

        spotifyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyTool.startLogin(getActivity(), SetupActivity.REQUEST_CODE);
            }
        });
        spotifyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyTool.logout(getActivity());
                setUiLoggedIn(false);
            }
        });

        adapter = new StakeholderAdapter(getActivity(), getData());
        Log.d("STARTUP", "Preference Stakeholder: " + PreferenceTool.getCurrentStakeholderId(getActivity()));
        stakeholderSpinner.setAdapter(adapter);
        int currentStakeholder = PreferenceTool.getCurrentStakeholderId(getActivity());
        if (currentStakeholder == PreferenceTool.DEFAULT_STAKEHOLDER_ID)
            stakeholderSpinner.setSelection(0, false);
        else {
            stakeholderSpinner.setSelection(adapter.getIndex(currentStakeholder), false);
        }
        stakeholderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PreferenceTool.setCurrentStakeholderId(getActivity(), adapter.getIntegerId(position));
                setUiStakeHolderSelected(adapter.getCount() > 0);

                if (preventDialogFromPoppingUpOnce) {
                    preventDialogFromPoppingUpOnce = false;
                } else {
                    StakeholderDialog newFragment = StakeholderDialog.getInstance(adapter.getIntegerId(position));
                    newFragment.show(getActivity().getSupportFragmentManager(), StakeholderDialog.TAG);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        stakeholderBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StakeholderDialog newFragment = StakeholderDialog.getInstance(-1);
                newFragment.show(getActivity().getSupportFragmentManager(), StakeholderDialog.TAG);
            }
        });
    }

    @Override
    public void onLoggedIn() {
        toast("onLoggedIn");
        Log.d("Setup", "onLoggedIn");
        setUiLoggedIn(true);
    }

    @Override
    public void onLoggedOut() {
        toast("onLoggedOut");
        Log.d("Setup", "onLoggedOut");
        setUiLoggedIn(false);
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        String message = throwable == null || throwable.getLocalizedMessage() == null ? "null" : throwable.getLocalizedMessage();
        toast("onLoginFailed: " + message);
        Log.d("Setup", "onLoginFailed: " + message);

        if (message.equals("Login to Spotify failed because of invalid credentials")) {
            Log.d("Setup", "- Of course those credentials don't match stupid -.-");
        } else if (message.equals("The operation requires a Spotify Premium account")) {
            Log.d("Setup", "- Hmm, no premium eh?");
        }

        //SpotifyTool.logout(getActivity()); //TODO re-enable and change below to false (DEV_BYPASS)
        setUiLoggedIn(true);
        //TODO let the ui know which error specifically has happened
    }

    @Override
    public void onTemporaryError() {
        toast("onTemporaryError");
        Log.d("Setup", "onTemporaryError");

        setUiLoggedIn(false);
    }

    @Override
    public void onConnectionMessage(String s) {
        toast("onConnectionMessage: " + s);
        Log.d("Setup", "onConnectionMessage: " + s);
        //TODO adjust UI?
    }

    private void setUiLoggedIn(boolean loggedIn) {
        if (loggedIn) {
            error.setText(R.string.setup_help_continue);
            listBtns.setVisibility(View.VISIBLE);
            spotifyLogin.setVisibility(View.GONE);
            spotifyLogout.setVisibility(View.VISIBLE);
        } else {
            error.setText(R.string.setup_message_error);
            error.setVisibility(View.VISIBLE);
            listBtns.setVisibility(View.GONE);
            spotifyLogin.setVisibility(View.VISIBLE);
            spotifyLogout.setVisibility(View.GONE);
        }
        setUiStakeHolderSelected(adapter.getCount() > 0);
    }

    private void setUiStakeHolderSelected(boolean stakeholderSelected) {
        if (stakeholderSelected) {
            error.setText(R.string.setup_help_continue);
            listBtns.setVisibility(View.VISIBLE);
        } else {
            error.setText(R.string.setup_message_error);
            error.setVisibility(View.VISIBLE);
            listBtns.setVisibility(View.GONE);
        }
    }

    private void showSetup(boolean show) {
        if (show) {
            boxSettings.setVisibility(View.VISIBLE);
            btnSettings.setText(R.string.setup_btn_settings);
        } else {
            boxSettings.setVisibility(View.GONE);
            btnSettings.setText("");
        }
    }

    private boolean preventDialogFromPoppingUpOnce = false;

    public void refreshStakeholderSpinnerAndSelectStakeholder(Stakeholder stakeholder) {
        adapter = new StakeholderAdapter(getActivity(), getData());
        stakeholderSpinner.setAdapter(adapter);
        preventDialogFromPoppingUpOnce = true;
        if (stakeholder == null) {
            PreferenceTool.setCurrentStakeholderId(getActivity(), PreferenceTool.DEFAULT_STAKEHOLDER_ID);
            stakeholderSpinner.setSelection(0, false);
        } else {
            int index = adapter.getIndex(stakeholder);
            if (index == -1) {
                PreferenceTool.setCurrentStakeholderId(getActivity(), PreferenceTool.DEFAULT_STAKEHOLDER_ID);
                stakeholderSpinner.setSelection(0, false);
            } else {
                PreferenceTool.setCurrentStakeholderId(getActivity(), stakeholder.get_id());
                stakeholderSpinner.setSelection(index, false);
            }
        }
    }
}
