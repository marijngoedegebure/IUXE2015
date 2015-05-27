package com.undefined.iuxe2015.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Event;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by marijngoedegebure on 18-05-15.
 */
public class EventSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {


    private MumoDataSource data;
    private LayoutInflater inflater;
    private Context c;
    private ArrayList<Event> events;

    public EventSpinnerAdapter(Activity a, MumoDataSource data) {
        this.data = data;
        inflater = a.getLayoutInflater();
        c = a;
        refresh();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        try {
            return events.get(position);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return getIntegerId(position);
    }

    public int getIntegerId(int position) {
        Event s = getItem(position);
        return s == null ? -1 : s.get_id();
    }

    class ViewHolder {
        @InjectView(R.id.event_name)
        TextView name;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    public void refresh() {
        events = data.getEvents(c);
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                if (lhs.getDate() > rhs.getDate()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_event_spinner, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder h = (ViewHolder) convertView.getTag();
        Event s = getItem(position);
        h.name.setText(s.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_event_spinner, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder h = (ViewHolder) convertView.getTag();
        Event s = getItem(position);
        h.name.setText(s.getName());
        return convertView;
    }

    public int getIndex(Event event) {
        return event == null ? -1 : getIndex(event.get_id());
    }

    public int getIndex(int eventId) {
        for (int i = 0; i < getCount(); i++) {
            int requestInt = getIntegerId(i);
            if (requestInt == eventId)
                return i;
        }
        return -1;
    }
}
