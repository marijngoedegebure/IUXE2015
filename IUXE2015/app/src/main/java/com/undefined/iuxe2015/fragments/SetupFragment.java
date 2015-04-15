package com.undefined.iuxe2015.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.HubActivity;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.adapters.StakeholderAdapter;
import com.undefined.iuxe2015.dialogs.StakeholderDialog;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;
import com.undefined.iuxe2015.tools.PreferenceTool;
import com.undefined.iuxe2015.tools.SpotifyTool;

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
    @InjectView(R.id.setup_scale)
    RadioGroup radioScale;
    @InjectView(R.id.setup_rating)
    RadioGroup radioRating;
    @InjectView(R.id.setup_login)
    Button spotifyLogin;
    @InjectView(R.id.setup_logout)
    Button spotifyLogout;
    @InjectView(R.id.setup_stakeholder_spinner)
    Spinner stakeholderSpinner;
    @InjectView(R.id.setup_stakeholder_btn_delete)
    Button stakeholderDelete;

    private StakeholderAdapter adapter;

    public SetupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new StakeholderAdapter(getActivity(), getData());
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

        switch (getScaleType()) {
            case BIG:
                radioScale.check(R.id.setup_scale_big);
                break;
            case COMPACT:
                radioScale.check(R.id.setup_scale_compact);
                break;
            default:
                toast("Unknown scaleType in preferences: " + getScaleType());
                radioScale.check(R.id.setup_scale_big);
                break;
        }

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

        radioScale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.setup_scale_big) {
                    PreferenceTool.setScaleType(getActivity(), ScaleType.BIG);
                } else if (checkedId == R.id.setup_scale_compact) {
                    PreferenceTool.setScaleType(getActivity(), ScaleType.COMPACT);
                } else {
                    toast("Unknown radiobutton pressed");
                }
            }
        });
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
                SpotifyTool.startLogin(getActivity(), ((SetupActivity)getActivity()).REQUEST_CODE);
            }
        });
        spotifyLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotifyTool.logout(getActivity());
                setUiLoggedIn(false);
            }
        });

        stakeholderSpinner.setAdapter(adapter);
        stakeholderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == adapter.getCount()-1){
                    //TODO add stakeholder dialog
                }else{
                    StakeholderDialog newFragment = new StakeholderDialog(); //TODO open correct stakeholder dialog
                    newFragment.show(getActivity().getSupportFragmentManager(), "stakeholder");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        stakeholderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCount() > 1){ //1 because of the bottom part
                    //TODO show warning and delete everything stakeholder-related
                }
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

        //SpotifyTool.logout(getActivity()); //TODO re-enable and change to false DEV_BYPASS
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

    private void setUiLoggedIn(boolean loggedIn){
        if(loggedIn){
            error.setText(R.string.setup_help_continue);
            listBtns.setVisibility(View.VISIBLE);
            spotifyLogin.setVisibility(View.GONE);
            spotifyLogout.setVisibility(View.VISIBLE);
        }else{
            error.setText(R.string.setup_message_error);
            error.setVisibility(View.VISIBLE);
            listBtns.setVisibility(View.GONE);
            spotifyLogin.setVisibility(View.VISIBLE);
            spotifyLogout.setVisibility(View.GONE);
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
}
