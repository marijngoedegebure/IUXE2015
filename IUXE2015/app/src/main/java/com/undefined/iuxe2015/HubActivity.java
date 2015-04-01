package com.undefined.iuxe2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;


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
            case R.id.hub_btn_library:
                Intent intent = new Intent(this, LibraryActivity.class);
                startActivity(intent);
                break;
            case R.id.hub_btn_search:
                toast("TODO: Start Contacts Activity");
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                //TODO start SearchActivity Activity
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
