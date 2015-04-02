package com.undefined.iuxe2015.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Song;

import java.util.ArrayList;

/**
 * Created by Jan-Willem on 2-4-2015.
 */
public class SongSearchAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Song> songs;

    public SongSearchAdapter(Activity a, ArrayList<Song> initSongs) {
        inflater = a.getLayoutInflater();
        songs = new ArrayList<Song>();
        refresh(initSongs);
    }

    public void refresh(ArrayList<Song> _songs) {
        if (_songs == null)
            _songs = new ArrayList<Song>();

        songs.clear();
        songs.addAll(_songs);

        //TODO sort?

        notifyDataSetChanged();
        //Or, when the list position should be set to 0:
        //notifyDataSetInvalidated();
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
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_search, parent, false);
            ViewHolder h = new ViewHolder();
            h.name = (TextView) convertView.findViewById(R.id.search_song_name);
            convertView.setTag(h);
        }

        ViewHolder h = (ViewHolder) convertView.getTag();
        Song s = getItem(position);
        h.name.setText(s.getTitle());

        return convertView;
    }
}
