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
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LibraryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Song> songs;
    private Context c;
    private MumoDataSource data;

    public LibraryAdapter(Activity a, MumoDataSource _data, ArrayList<Song> initSongs) {
        inflater = a.getLayoutInflater();
        songs = new ArrayList<>();
        c = a;
        data = _data;
        refresh(initSongs);
    }

    public void refresh(ArrayList<Song> _songs) {
        if (_songs == null)
            _songs = new ArrayList<>();

        songs.clear();
        songs.addAll(_songs);
        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                Rating r1 = data.getRatingsForSong(c, lhs);
                Rating r2 = data.getRatingsForSong(c, rhs);
                if (r1.getRating() < r2.getRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

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
        TextView artist;
        ImageView rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_library, parent, false);
            ViewHolder h = new ViewHolder();
            h.name = (TextView) convertView.findViewById(R.id.library_song_name);
            h.artist = (TextView) convertView.findViewById(R.id.library_song_artist);
            h.rating = (ImageView) convertView.findViewById(R.id.library_song_rating);
            convertView.setTag(h);
        }

        ViewHolder h = (ViewHolder) convertView.getTag();
        Song s = getItem(position);
        Rating r = data.getRatingsForSong(c, s);
        h.name.setText(s.getName());
        h.artist.setText(s.getArtistsString(c));

        int image;
        switch (r.getRating()) {
            case 1:
                image = R.drawable.ic_likert1; break;
            case 2:
                image = R.drawable.ic_likert2; break;
            case 3:
                image = R.drawable.ic_likert3; break;
            case 4:
                image = R.drawable.ic_likert4; break;
            case 5:
                image = R.drawable.ic_likert5; break;
            case 6:
                image = R.drawable.ic_likert6; break;
            case 7:
                image = R.drawable.ic_likert7; break;
            default:
                image = R.drawable.ic_likert4;
        }
        h.rating.setImageResource(image);

        return convertView;
    }
}
