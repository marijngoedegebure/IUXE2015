package com.undefined.iuxe2015.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.tools.Convert;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jan-Willem on 2-4-2015.
 */
public class SongSearchAdapter extends BaseAdapter {

    private Context c;
    private LayoutInflater inflater;
    private ArrayList<Song> songs;

    public SongSearchAdapter(Activity a, ArrayList<Song> initSongs) {
        c = a;
        inflater = a.getLayoutInflater();
        songs = new ArrayList<>();
        refresh(initSongs);
    }

    public void refresh(ArrayList<Song> _songs) {
        if (_songs == null)
            _songs = new ArrayList<>();

        songs.clear();
        songs.addAll(_songs);

        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        @InjectView(R.id.search_song_duration)
        TextView duration;
        @InjectView(R.id.search_song_name)
        TextView name;
        @InjectView(R.id.search_song_artist)
        TextView artists;
        @InjectView(R.id.search_song_rating)
        ImageView rating;

        ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_search, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        ViewHolder h = (ViewHolder) convertView.getTag();
        Song s = getItem(position);
        h.name.setText(s.getName());
        h.duration.setText(Convert.durationToString(s.getDurationMs()));
        h.artists.setText(s.getArtistsString(c));

        return convertView;
    }
}
