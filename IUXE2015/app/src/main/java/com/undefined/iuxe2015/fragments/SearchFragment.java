package com.undefined.iuxe2015.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.json.QueryResult;
import com.undefined.iuxe2015.tools.ConnectionTool;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class SearchFragment extends MumoFragment {

    private EditText input;
    private Button searchBtn;

    private ListView searchList;
    private SongSearchAdapter adapter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO get actual Songs?

        ArrayList<Song> tempSongs = new ArrayList<Song>();
        tempSongs.add(new Song("Song1"));
        tempSongs.add(new Song("Song2"));
        tempSongs.add(new Song("Song3"));
        tempSongs.add(new Song("Song4"));
        tempSongs.add(new Song("Song5"));
        tempSongs.add(new Song("Song6"));

        adapter = new SongSearchAdapter(getActivity(), tempSongs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        input = (EditText) rootView.findViewById(R.id.search_input);
        searchBtn = (Button) rootView.findViewById(R.id.search_btn);
        searchList = (ListView) rootView.findViewById(R.id.search_list);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO maybe use this?
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchList.setAdapter(adapter);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Song item = adapter.getItem(position);
                SetupActivity.mPlayer.play(item.getUri());
            }

        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO, only if query is different and non-empty?
                ConnectionTool.getSongsForQuery(getActivity(), input.getText().toString(), new ConnectionTool.ConnectionListener() {
                    @Override
                    public void onConnectionSuccess(QueryResult result) {
                        //TODO stop loading UI, if visible
                        if(result.hasTracks()){
                            Log.d("SearchFragment", "onConnectionSuccess:" + result.tracks.items.size());
                            adapter.refresh(result.tracks.items);
                        }else{
                            Log.d("SearchFragment", "onConnectionSuccess: but no results");
                        }
                    }

                    @Override
                    public void onConnectionFailed(Exception e) {
                        //TODO give ui feedback about what went wrong
                        Log.d("SearchFragment", "onConnectionFailed");
                    }
                });
                //TODO search, also maybe listen to text changes in input? And maybe a loading spinner?
            }
        });

        searchList.setAdapter(adapter);

        return rootView;
    }

}
