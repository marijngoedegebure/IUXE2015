package com.undefined.iuxe2015.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;

public class LibraryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Song> songs;
    private Context c;
    private MumoDataSource data;

    public LibraryAdapter(Activity a, MumoDataSource _data, ArrayList<Song> initSongs) {
        inflater = a.getLayoutInflater();
        songs = new ArrayList<>();
        refresh(initSongs);
        c = a;
        data = _data;
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
        TextView name;
        TextView rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_library, parent, false);
            ViewHolder h = new ViewHolder();
            h.name = (TextView) convertView.findViewById(R.id.library_song_name);
            h.rating = (TextView) convertView.findViewById(R.id.library_song_rating);
            convertView.setTag(h);
        }

        ViewHolder h = (ViewHolder) convertView.getTag();
        Song s = getItem(position);
        Rating r = data.getRatingsForSong(c, s);
        h.name.setText(s.getName());
        h.rating.setText("Rating: " + r.getRating());

        return convertView;
    }
}
