package com.undefined.iuxe2015.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoDialog;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.fragments.SetupFragment;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.tools.ConnectionTool;
import com.undefined.iuxe2015.tools.PreferenceTool;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by marijngoedegebure on 30-04-15.
 */
public class MusicFinishedDialog extends MumoDialog {

    public static final String TAG = "MusicFinishedDialog";
    public static final String ARG_SONG_ID = "song_id";

    @InjectView(R.id.rating_name)
    TextView name;
    @InjectView(R.id.rating_album)
    TextView album;
    @InjectView(R.id.rating_artist)
    TextView artist;
    @InjectView(R.id.rating_scale_likert_1)
    ImageButton ratingButton1;
    @InjectView(R.id.rating_scale_likert_2)
    ImageButton ratingButton2;
    @InjectView(R.id.rating_scale_likert_3)
    ImageButton ratingButton3;
    @InjectView(R.id.rating_scale_likert_4)
    ImageButton ratingButton4;
    @InjectView(R.id.rating_scale_likert_5)
    ImageButton ratingButton5;
    @InjectView(R.id.rating_scale_likert_6)
    ImageButton ratingButton6;
    @InjectView(R.id.rating_scale_likert_7)
    ImageButton ratingButton7;
    @InjectView(R.id.rating_btn_rate)
    Button rateSong;

    private Song song;

    public static MusicFinishedDialog getInstance(String songId) {
        MusicFinishedDialog d = new MusicFinishedDialog();
        Bundle args = new Bundle();
        args.putString(ARG_SONG_ID, songId);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String songId = getArguments().getString(ARG_SONG_ID);
        if(songId == ""){
            Log.e("MusicFinishedDialog", "NO SONGID!!!!!!");
            getActivity().finish();
        }else{
            if(song == null){
                Log.e("MusicFinishedDialog", "NO SONG FOR ID " + songId + " !!!!!!");

                ConnectionTool.getSong(getActivity(), songId, new ConnectionTool.ConnectionSongListener() {
                    @Override
                    public void onConnectionSuccess(Song result) {
                        //TODO stop loading UI, if visible
                        if (result != null) {
                            Log.d("MusicFinishedDialog", "onConnectionSuccess:" + result);
                            song = result;
                            setDetails();
                        } else {
                            Log.d("MusicFinishedDialog", "onConnectionSuccess: but no results");
                        }
                    }

                    @Override
                    public void onConnectionFailed(Exception e) {
                        //TODO give ui feedback about what went wrong
                        Log.d("MusicFinishedDialog", "onConnectionFailed");
                    }
                });
            }
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_music_finished, null, false);
        ButterKnife.inject(this, v);

        ratingButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(1);
            }
        });
        ratingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(2);
            }
        });
        ratingButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(3);
            }
        });
        ratingButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(4);
            }
        });
        ratingButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(5);
            }
        });
        ratingButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(6);
            }
        });
        ratingButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRatingSelection(7);
            }
        });

        rateSong = (Button) v.findViewById(R.id.rating_rate);
        setDetails();
        builder.setView(v);
        final AlertDialog d = builder.create();

        // Create the AlertDialog object and return it
        return d;
    }

    private void setDetails() {
        if(song != null) {
            name.setText(song.getName());
            album.setText(song.getAlbum().getName());
            if (song.getArtists().size() > 0) {
                String artistText = song.getArtists().get(0).getName();
                for (int i = 1; i < song.getArtists().size(); i++) {
                    artistText += ", " + song.getArtists().get(i).getName();
                }
                artist.setText(artistText);
            } else {
                artist.setText("No artists found");
            }

            rateSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stakeHolderId = PreferenceTool.getCurrentStakeholderId(getActivity());
                    if (!ratingButton1.isEnabled())
                        rate(stakeHolderId, song, 1);
                    else if (!ratingButton2.isEnabled())
                        rate(stakeHolderId, song, 2);
                    else if (!ratingButton3.isEnabled())
                        rate(stakeHolderId, song, 3);
                    else if (!ratingButton4.isEnabled())
                        rate(stakeHolderId, song, 4);
                    else if (!ratingButton5.isEnabled())
                        rate(stakeHolderId, song, 5);
                    else if (!ratingButton6.isEnabled())
                        rate(stakeHolderId, song, 6);
                    else if (!ratingButton7.isEnabled())
                        rate(stakeHolderId, song, 7);
                }
            });
        }
    }

    private void rate(int stakeHolderId, Song song, int rate) {
        Log.d("MusicFinishedDialog", "Add rating: " + stakeHolderId + ", " + song.getId() + ", " + rate + ", " + "");
        song = getData().addSong(stakeHolderId, song);

        song = getData().getSongById(getActivity(), song.getId());
        if(song == null) {
            song = getData().addSong(stakeHolderId, song);
        }
        Rating rating = getData().getRatingsForSong(getActivity(), song);
        if(rating == null) {
            //TODO add eventId
            getData().addRating(stakeHolderId, song.get_id(), 0, rate, "");
        }
        else {
            rating.setRating(rate);
            getData().updateRating(stakeHolderId, rating);
        }
        dismiss();
    }

    private void setRatingSelection(int selection){
        SetupActivity.mPlayer.pause();
        ratingButton1.setEnabled(selection == 1);
        ratingButton2.setEnabled(selection == 2);
        ratingButton3.setEnabled(selection == 3);
        ratingButton4.setEnabled(selection == 4);
        ratingButton5.setEnabled(selection == 5);
        ratingButton6.setEnabled(selection == 6);
        ratingButton7.setEnabled(selection == 7);
    }
}
