package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.EventAdapter;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.dialogs.EventDialog;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.ConnectionTool;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class EventsFragment extends MumoFragment {

    public static final String TAG = "EventsFragment";

    @InjectView(R.id.events_list_header)
    TextView eventsHeader;
    @InjectView(R.id.events_list)
    public ListView eventsList;
    @InjectView(R.id.events_empty)
    public TextView empty;
    public EventAdapter adapter;

    public EventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new EventAdapter((MumoActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, rootView);

        Button btn_new = (Button) rootView.findViewById(R.id.event_btn_new);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEventDialog("");
            }
        });

        eventsList.setAdapter(adapter);
        empty.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        eventsHeader.setText(getResources().getQuantityText(R.plurals.events_label, adapter.getCount()));
        eventsHeader.setVisibility(adapter.getCount() == 0 ? View.GONE : View.VISIBLE);
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                showEventDialog(adapter.getItem(position).get_id() + "");
            }
        });

        return rootView;
    }

    private void showEventDialog(String eventId) {
        EventDialog newFragment = EventDialog.getInstance(eventId);
        newFragment.show(getActivity().getSupportFragmentManager(), EventDialog.TAG);
    }

    public void onEventDialogClose() {
        Log.d(TAG, "onEventDialogClose");
        if (adapter != null) {
            adapter.refresh();
            empty.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }
}
