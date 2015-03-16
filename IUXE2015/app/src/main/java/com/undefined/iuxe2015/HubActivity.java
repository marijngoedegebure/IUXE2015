package com.undefined.iuxe2015;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class HubActivity extends ActionBarActivity {

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
        switch (button.getId()) {
            case R.id.hub_btn_music:
                toast("TODO: Start Music Activity");
                //TODO start Music Activity
                break;
            case R.id.hub_btn_contacts:
                toast("TODO: Start Contacts Activity");
                //TODO start Contacts Activity
                break;
            case R.id.hub_btn_messaging:
                toast("TODO: Start Messaging Activity");
                //TODO start Messaging Activity
                break;
            case R.id.hub_btn_help:
                toast("TODO: Start Help Overlay");
                //TODO start Help Overlay
                break;
        }
    }

    private void toast(String message) {
        if (t == null) {
            t = new Toast(this);
            t.setDuration(Toast.LENGTH_SHORT);
        }
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
