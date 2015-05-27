package com.undefined.iuxe2015.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.LibraryActivity;
import com.undefined.iuxe2015.activities.SearchActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.LibraryAdapter;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class LibraryFragment extends MumoFragment {

    LibraryAdapter adapter;

    @InjectView(R.id.library_rated_songs_listview)
    ListView listView;
    @InjectView(R.id.library_empty)
    TextView empty;

    public LibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LibraryAdapter(getActivity(), getData(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        ArrayList<Song> songs = getData().getRatedSongs(getActivity());
        ButterKnife.inject(this, rootView);

        listView.setAdapter(adapter);
        adapter.refresh(songs);

        empty.setVisibility(songs == null || songs.size() == 0 ? View.VISIBLE : View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Song item = adapter.getItem(position);
                getActivity().startActivityForResult(SongDetailActivity.getStartIntent(getActivity(), item), LibraryActivity.REQUEST_CODE);
            }

        });

        return rootView;
    }
}
