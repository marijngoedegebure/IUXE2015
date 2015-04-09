package com.undefined.iuxe2015.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.HubActivity;
import com.undefined.iuxe2015.model.types.RatingType;
import com.undefined.iuxe2015.model.types.ScaleType;
import com.undefined.iuxe2015.tools.PreferenceTool;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class SetupFragment extends MumoFragment {

    @InjectView(R.id.setup_btn_continue)
    Button btnContinue;
    @InjectView(R.id.setup_btn_settings)
    TextView btnSettings;
    @InjectView(R.id.setup_box_settings)
    View boxSettings;
    @InjectView(R.id.setup_scale)
    RadioGroup radioScale;
    @InjectView(R.id.setup_rating)
    RadioGroup radioRating;

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

        //TODO let the enablement of the continue button be dependant on Spotify being enabled, logged in and ready to go.

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHub();
            }
        });

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

    private void startHub() {
        startActivity(new Intent(getActivity(), HubActivity.class));
    }
}
