package com.undefined.iuxe2015.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoApplication;
import com.undefined.iuxe2015.MumoDialog;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.adapters.EventAdapter;
import com.undefined.iuxe2015.adapters.EventSpinnerAdapter;
import com.undefined.iuxe2015.adapters.StakeholderAdapter;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.ConnectionTool;
import com.undefined.iuxe2015.tools.PreferenceTool;
import com.undefined.iuxe2015.views.MumoSpinner;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by marijngoedegebure on 30-04-15.
 */
public class RateSongDialog extends MumoDialog {

    public static final String TAG = "RateSongDialog";
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
    @InjectView(R.id.rating_add_event)
    Button addEventBtn;
    @InjectView(R.id.rating_event_spinner)
    MumoSpinner eventSpinner;

    private Song song;
    private int currentRatingSelection;

    private EventSpinnerAdapter adapter;

    public static RateSongDialog getInstance(String songId) {
        RateSongDialog d = new RateSongDialog();
        Bundle args = new Bundle();
        args.putString(ARG_SONG_ID, songId);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String songId = getArguments().getString(ARG_SONG_ID);
        if (songId == "") {
            Log.e("RateSongDialog", "NO SONGID!!!!!!");
            getActivity().finish();
        } else {
            if (song == null) {
                Log.e("RateSongDialog", "NO SONG FOR ID " + songId + " !!!!!!");

                ConnectionTool.getSong(getActivity(), songId, new ConnectionTool.ConnectionSongListener() {
                    @Override
                    public void onConnectionSuccess(Song result) {
                        //TODO stop loading UI, if visible
                        if (result != null) {
                            Log.d("RateSongDialog", "onConnectionSuccess:" + result);
                            song = result;
                            Rating r = getData().getRatingsForSong(getActivity(), song);
                            setRatingSelection(r == null ? 4 : r.getRating());
                            setDetails();
                        } else {
                            Log.d("RateSongDialog", "onConnectionSuccess: but no results");
                        }
                    }

                    @Override
                    public void onConnectionFailed(Exception e) {
                        //TODO give ui feedback about what went wrong
                        Log.d("RateSongDialog", "onConnectionFailed");
                    }
                });
            }
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_rate_song, null, false);
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

        adapter = new EventSpinnerAdapter(getActivity(), getData());
        eventSpinner.setAdapter(adapter);
        eventSpinner.setSelection(0, false);

        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDialog newFragment = EventDialog.getInstance("");
                newFragment.show(getActivity().getSupportFragmentManager(), EventDialog.TAG);
            }
        });

        rateSong = (Button) v.findViewById(R.id.rating_btn_rate);
        setDetails();
        builder.setView(v);
        final AlertDialog d = builder.create();

        // Create the AlertDialog object and return it
        return d;
    }

    private void setDetails() {
        if (song != null) {
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
                    rate(stakeHolderId, song, currentRatingSelection, (Event) eventSpinner.getSelectedItem());
                }
            });
        }
    }

    private void setRatingSelection(int selection) {
        currentRatingSelection = selection;
        ratingButton1.setImageResource(selection == 1 ? R.drawable.ic_likert1_selected : R.drawable.ic_likert1);
        ratingButton2.setImageResource(selection == 2 ? R.drawable.ic_likert2_selected : R.drawable.ic_likert2);
        ratingButton3.setImageResource(selection == 3 ? R.drawable.ic_likert3_selected : R.drawable.ic_likert3);
        ratingButton4.setImageResource(selection == 4 ? R.drawable.ic_likert4_selected : R.drawable.ic_likert4);
        ratingButton5.setImageResource(selection == 5 ? R.drawable.ic_likert5_selected : R.drawable.ic_likert5);
        ratingButton6.setImageResource(selection == 6 ? R.drawable.ic_likert6_selected : R.drawable.ic_likert6);
        ratingButton7.setImageResource(selection == 7 ? R.drawable.ic_likert7_selected : R.drawable.ic_likert7);
    }

    private void rate(int stakeHolderId, Song song, int rate, Event event) {
        Log.d("RateSongDialog", "Add rating: " + stakeHolderId + ", " + song.getId() + ", rate: " + rate + ", Event: " + event.getName());
        song = getData().addSong(stakeHolderId, song);

        song = getData().getSongById(getActivity(), song.getId());
        if (song == null)
            song = getData().addSong(stakeHolderId, song);

        Rating rating = getData().getRatingsForSong(getActivity(), song);
        if (rating == null) {
            getData().addRating(stakeHolderId, song.get_id(), event.get_id(), rate, "");
        } else {
            rating.setRating(rate);
            getData().updateRating(stakeHolderId, event.get_id(), rating);
        }
        dismiss();
    }

    public void onEventDialogClose(int event_id) {
        Log.d(TAG, "onEventDialogClose");
        if (adapter != null) {
            adapter.refresh();
            eventSpinner.setSelection(adapter.getIndex(event_id), true);
        }
    }
}
