package com.undefined.iuxe2015.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Rating;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Jan-Willem on 14-5-2015.
 */
public class EventAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    Context c;
    MumoDataSource data;
    ArrayList<Rating> sortedRatings;
    private LayoutInflater inflater;

    public EventAdapter(MumoActivity activity) {
        c = activity;
        inflater = LayoutInflater.from(activity);
        data = activity.getData();
        sortedRatings = data.getRatings(c);
        Collections.sort(sortedRatings, new Comparator<Rating>() {
            @Override
            public int compare(Rating lhs, Rating rhs) {
                return 0;
            }
        });
    }

    @Override
    public int getCount() {
        return sortedRatings.size();
    }

    @Override
    public Rating getItem(int position) {
        return sortedRatings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_event_song, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Rating r = getItem(position);
        Song s = data.getSong(c, r.get_id_song());

        holder.name.setText(s.getName());
        if (s.hasArtists())
            holder.artist.setText(s.getArtists().get(0).getName());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_event, parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Rating r = getItem(position);
        Event e = data.getEventWithId(c, r.get_id_event());

        holder.name.setText(e.getName());
        holder.date.setText("Date:" + e.getDate());

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return getItem(position).get_id_event();
    }

    class HeaderViewHolder {
        @InjectView(R.id.event_name)
        TextView name;
        @InjectView(R.id.event_date)
        TextView date;

        public HeaderViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    class ViewHolder {
        @InjectView(R.id.song_name)
        TextView name;
        @InjectView(R.id.song_artist)
        TextView artist;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

}
