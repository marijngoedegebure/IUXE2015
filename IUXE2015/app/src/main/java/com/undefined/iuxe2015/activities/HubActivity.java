package com.undefined.iuxe2015.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.undefined.iuxe2015.MumoActivity;
import com.undefined.iuxe2015.R;


public class HubActivity extends MumoActivity {

    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void onButtonClick(View button) {
        Intent intent;
        switch (button.getId()) {
            case R.id.hub_btn_library:
                intent = new Intent(this, LibraryActivity.class);
                startActivity(intent);
                break;
            case R.id.hub_btn_search:
                intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.hub_btn_help:
                toast("TODO: Start Help Overlay");
                //TODO start Help Overlay
                break;
        }
    }

    private void toast(String message) {
        if (t == null)
            t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.setText(message);
        t.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hub, container, false);
            return rootView;
        }
    }
}