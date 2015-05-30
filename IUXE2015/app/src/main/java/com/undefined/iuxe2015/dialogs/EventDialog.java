package com.undefined.iuxe2015.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoDialog;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.EventsActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.LibraryAdapter;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.PreferenceTool;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Ralf Nieuwenhuizen on 14-05-15.
 */
public class EventDialog extends MumoDialog {

    public static final String TAG = "EventDialog";
    public static final String ARG_EVENT_ID = "event_id";

    private Event event;

    private LibraryAdapter songsAdapter;

    private eventDialogListener defaultListener = new eventDialogListener() {
        @Override
        public void onEventDialogClosed(int event_id) {
        }
    };
    private eventDialogListener listener;

    public interface eventDialogListener {
        public void onEventDialogClosed(int event_id);
    }


    public static EventDialog getInstance(String eventId) {
        EventDialog d = new EventDialog();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        d.setArguments(args);
        return d;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (eventDialogListener) activity;
        } catch (Exception e) {
            Log.e(TAG, "The parent activity should implement eventDialogListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = defaultListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String eventId = getArguments().getString(ARG_EVENT_ID);
        if (eventId == null || eventId.length() == 0) {
            Log.i("EventDialog", "Creating new Event");
        } else if (event == null) {
            Log.i("EventDialog", "Searching event: " + eventId);
            event = getData().getEventWithId(getActivity(), Integer.parseInt(eventId));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = null;
        if (event != null) {
            v = getActivity().getLayoutInflater().inflate(R.layout.dialog_event, null, false);
            ButterKnife.inject(this, v);

            TextView name = (TextView) v.findViewById(R.id.event_name);
            TextView year = (TextView) v.findViewById(R.id.event_year);
            name.setText(event.getName());
            year.setText(event.getYear() + "");


            ArrayList<Song> songs = getData().getSongsByEvent(getActivity(), event.get_id());
            if (!songs.isEmpty()) {
                songsAdapter = new LibraryAdapter(getActivity(), getData(), null);
                ListView songsList = (ListView) v.findViewById(R.id.event_rated_songs_listview);
                songsList.setAdapter(songsAdapter);
                TextView empty = (TextView) v.findViewById(R.id.empty);
                //songsList.setEmptyView(empty);
                songsAdapter.refresh(songs);

                songsList.setVisibility(songs.isEmpty() ? View.GONE : View.VISIBLE);
                empty.setVisibility(songs.isEmpty() ? View.VISIBLE : View.GONE);

                songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final Song item = songsAdapter.getItem(position);
                        getActivity().startActivityForResult(SongDetailActivity.getStartIntent(getActivity(), item), EventsActivity.REQUEST_CODE);
                    }
                });
            }

            Button close = (Button) v.findViewById(R.id.event_btn_close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            v = getActivity().getLayoutInflater().inflate(R.layout.dialog_event_new, null, false);
            ButterKnife.inject(this, v);
            final EditText name = (EditText) v.findViewById(R.id.event_name);
            final EditText year = (EditText) v.findViewById(R.id.event_year);

            Button saveEvent = (Button) v.findViewById(R.id.event_btn_save);
            saveEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int stakeHolderId = PreferenceTool.getCurrentStakeholderId(getActivity());

                    save(stakeHolderId, event, name.getText().toString(), Integer.parseInt(year.getText().toString()));

                }
            });
        }
        builder.setView(v);
        final AlertDialog d = builder.create();

        // Create the AlertDialog object and return it
        return d;
    }

    public void update() {
        ArrayList<Song> songs = getData().getSongsByEvent(getActivity(), event.get_id());
        songsAdapter.refresh(songs);
    }

    private void save(int stakeHolderId, Event event, String name, int year) {
        if (event == null) {
            if (name == null || name.length() == 0) {
                toast(getString(R.string.event_name_empty));
                return;
            }
            event = getData().newEvent(getActivity(), name, year);
        } else {
            getData().updateEvent(getActivity(), event);
        }
        Log.d("EventDialog", "Save: " + stakeHolderId + ", " + event.get_id());
        listener.onEventDialogClosed(event.get_id());
        dismiss();
    }
}
