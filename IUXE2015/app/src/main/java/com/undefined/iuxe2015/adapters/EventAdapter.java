package com.undefined.iuxe2015.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Song;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jan-Willem on 14-5-2015.
 */
public class EventAdapter extends BaseAdapter implements ListAdapter {

    Context c;
    MumoDataSource data;
    ArrayList<Event> events;
    private LayoutInflater inflater;

    public EventAdapter(MumoActivity activity) {
        c = activity;
        inflater = LayoutInflater.from(activity);
        data = activity.getData();
        refresh();
    }

    public void refresh() {
        events = data.getEvents(c);
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                if (lhs.getYear() > rhs.getYear()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_event, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event e = getItem(position);

        holder.name.setText(e.getName());
        holder.year.setText("Jaar: " + e.getYear());
        ArrayList<Song> songs = data.getSongsByEvent(c, e.get_id());
        holder.songs.setText(songs.size() + " verbonden nummers");

        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.event_name)
        TextView name;
        @InjectView(R.id.event_year)
        TextView year;
        @InjectView(R.id.event_songs)
        TextView songs;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

}
