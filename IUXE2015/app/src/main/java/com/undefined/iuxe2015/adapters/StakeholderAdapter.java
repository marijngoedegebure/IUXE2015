package com.undefined.iuxe2015.adapters;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.undefined.iuxe2015.R;
import com.undefined.iuxe2015.model.Stakeholder;
import com.undefined.iuxe2015.model.persistent.MumoDataSource;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jan-Willem on 15-4-2015.
 */
public class StakeholderAdapter implements SpinnerAdapter {

    private MumoDataSource data;
    private LayoutInflater inflater;

    public StakeholderAdapter(Activity a, MumoDataSource data) {
        this.data = data;
        inflater = a.getLayoutInflater();
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
        return data.getStakeholders().size();
    }

    @Override
    public Stakeholder getItem(int position) {
        try {
            return data.getStakeholders().get(position);
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return getIntegerId(position);
    }

    public int getIntegerId(int position) {
        Stakeholder s = getItem(position);
        return s == null ? -1 : s.get_id();
    }

    class ViewHolder {
        @InjectView(R.id.setup_stakeholder_name)
        TextView name;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_stakeholder, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder h = (ViewHolder) convertView.getTag();
        Stakeholder s = getItem(position);
        h.name.setText(s.getName() + " (" + s.getAge() + ")");
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_stakeholder, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder h = (ViewHolder) convertView.getTag();
        Stakeholder s = getItem(position);
        h.name.setText(s.getName() + " (" + s.getAge() + ")");
        return convertView;
    }

    public int getIndex(Stakeholder stakeholder) {
        return stakeholder == null ? -1 : getIndex(stakeholder.get_id());
    }

    public int getIndex(int stakeholderId) {
        for (int i = 0; i < getCount(); i++) {
            if (getIntegerId(i) == stakeholderId)
                return i;
        }
        return -1;
    }
}
