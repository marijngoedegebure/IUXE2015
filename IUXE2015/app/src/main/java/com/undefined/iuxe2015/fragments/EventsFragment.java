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

import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
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

    @InjectView(R.id.events_list)
    public StickyListHeadersListView eventsList;
   // public SongSearchAdapter adapter;

    public EventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter = new SongSearchAdapter(getActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.inject(this, rootView);

//        eventsList.setAdapter(adapter);
//
//        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final Song item = adapter.getItem(position);
//
//                startActivity(SongDetailActivity.getStartIntent(getActivity(), item));
//            }
//
//        });

        return rootView;
    }

}
