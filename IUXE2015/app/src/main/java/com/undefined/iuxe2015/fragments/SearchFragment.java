package com.undefined.iuxe2015.fragments;

import android.content.Intent;
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

import com.spotify.sdk.android.player.Spotify;
import com.undefined.iuxe2015.MumoFragment;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.activities.MusicControllerActivity;
import com.undefined.iuxe2015.activities.SearchActivity;
import com.undefined.iuxe2015.activities.SetupActivity;
import com.undefined.iuxe2015.activities.SongDetailActivity;
import com.undefined.iuxe2015.adapters.SongSearchAdapter;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.QueryResult;
import com.undefined.iuxe2015.tools.ConnectionTool;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jan-Willem on 1-4-2015.
 */
public class SearchFragment extends MumoFragment {

    @InjectView(R.id.search_input)
    public EditText input;
    @InjectView(R.id.search_btn)
    public Button searchBtn;
    @InjectView(R.id.search_list_header)
    TextView searchHeader;
    @InjectView(R.id.search_list_empty)
    TextView searchEmpty;
    @InjectView(R.id.search_list)
    public ListView searchList;
    @InjectView(R.id.search_list_results)
    View searchResults;
    public SongSearchAdapter adapter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SongSearchAdapter(getActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, rootView);
        searchEmpty.setVisibility(View.VISIBLE);
        searchEmpty.setText(R.string.search_no_input);
        searchResults.setVisibility(View.INVISIBLE);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // On text change the app does a search query.
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                ConnectionTool.getSongsForQuery(getActivity(), s.toString(), new ConnectionTool.ConnectionQueryResultListener() {
                    @Override
                    public void onConnectionSuccess(QueryResult result) {
                        //TODO stop loading UI, if visible
                        if(result.hasTracks()){
                            searchEmpty.setVisibility(View.INVISIBLE);
                            searchHeader.setText(getResources().getQuantityText(R.plurals.search_results_label, result.getNumTracks()));
                            searchResults.setVisibility(View.VISIBLE);
                            Log.d("SearchFragment", "onConnectionSuccess:" + result.tracks.items.size());
                            adapter.refresh(result.tracks.items);
                        }else{
                            Log.d("SearchFragment", "onConnectionSuccess: but no results");
                            if(s.length() == 0){
                                searchEmpty.setText(R.string.search_no_input);
                            }else{
                                searchEmpty.setText(R.string.search_no_results);
                            }
                            searchEmpty.setVisibility(View.VISIBLE);
                            searchResults.setVisibility(View.INVISIBLE);

                            adapter.refresh(null);
                        }
                    }

                    @Override
                    public void onConnectionFailed(Exception e) {
                        Log.d("SearchFragment", "onConnectionFailed");
                        searchEmpty.setVisibility(View.VISIBLE);
                        searchHeader.setVisibility(View.INVISIBLE);
                        searchEmpty.setText(R.string.search_connection_failed);
                        adapter.refresh(null);
                    }
                });
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

                startActivity(SongDetailActivity.getStartIntent(getActivity(), item));
            }

        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO, only if query is different and non-empty?
                ConnectionTool.getSongsForQuery(getActivity(), input.getText().toString(), new ConnectionTool.ConnectionQueryResultListener() {
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

        return rootView;
    }

}
