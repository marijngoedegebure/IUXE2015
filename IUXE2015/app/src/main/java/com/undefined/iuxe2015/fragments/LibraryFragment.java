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

import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.LibraryAdapter;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class LibraryFragment extends MumoFragment {

    LibraryAdapter adapter;

    ListView listView;

    public LibraryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LibraryAdapter(getActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        ArrayList<Song> songs = getData().getRatedSongs();

        listView = (ListView) rootView.findViewById(R.id.library_rated_songs_listview);

        listView.setAdapter(adapter);

        adapter.refresh(songs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Song item = adapter.getItem(position);
                Intent intent = new Intent(parent.getContext(), SongDetailActivity.class);
                startActivity(intent);
            }

        });

        return rootView;
    }
}
