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
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoDialog;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.fragments.SetupFragment;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.tools.ConnectionTool;
import com.undefined.iuxe2015.tools.PreferenceTool;

/**
 * Created by marijngoedegebure on 30-04-15.
 */
public class MusicFinishedDialog extends MumoDialog {

    public static final String TAG = "MusicFinishedDialog";
    public static final String SONG_ID = "song_id";

    TextView name;
    TextView album;
    TextView artist;
    RadioButton rating_rate_value1;
    RadioButton rating_rate_value2;
    RadioButton rating_rate_value3;
    RadioButton rating_rate_value4;
    RadioButton rating_rate_value5;
    Button rateSong;

    private Song song;

    public static MusicFinishedDialog getInstance(String songId) {
        MusicFinishedDialog d = new MusicFinishedDialog();
        Bundle args = new Bundle();
        args.putString(SONG_ID, songId);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String songId = getArguments().getString(SONG_ID);
        if(songId == ""){
            Log.e("MusicFinishedDialog", "NO SONGID!!!!!!");
            getActivity().finish();
        }else{

            song = getData().getSongById(songId);
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

        name = (TextView) v.findViewById(R.id.rating_name);
        album = (TextView) v.findViewById(R.id.rating_album);
        artist = (TextView) v.findViewById(R.id.rating_artist);
        rating_rate_value1 = (RadioButton) v.findViewById(R.id.rating_rate_value1);
        rating_rate_value2 = (RadioButton) v.findViewById(R.id.rating_rate_value2);
        rating_rate_value3 = (RadioButton) v.findViewById(R.id.rating_rate_value3);
        rating_rate_value4 = (RadioButton) v.findViewById(R.id.rating_rate_value4);
        rating_rate_value5 = (RadioButton) v.findViewById(R.id.rating_rate_value5);
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
                    if(rating_rate_value1.isChecked()) {
                        rate(stakeHolderId, song, 1);
                    }
                    else if(rating_rate_value2.isChecked()) {
                        rate(stakeHolderId, song, 2);
                    }
                    else if(rating_rate_value3.isChecked()) {
                        rate(stakeHolderId, song, 3);
                    }
                    else if(rating_rate_value4.isChecked()) {
                        rate(stakeHolderId, song, 4);
                    }
                    else if(rating_rate_value5.isChecked()) {
                        rate(stakeHolderId, song, 5);
                    }
                }
            });
        }
    }

    private void rate(int stakeHolderId, Song song, int rate) {
        Log.d("MusicFinishedDialog", "Add rating: " + stakeHolderId + ", " + song.getId() + ", " + rate + ", " + "");
        song = getData().addSong(stakeHolderId, song);
        getData().addRating(stakeHolderId, song.get_id(), rate, "");
        dismiss();
    }
}
