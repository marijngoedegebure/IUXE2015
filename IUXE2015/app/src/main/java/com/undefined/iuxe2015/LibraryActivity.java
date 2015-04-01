package com.undefined.iuxe2015;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.undefined.iuxe2015.fragments.LibraryFragment;


public class LibraryActivity extends ActionBarActivity {

    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LibraryFragment())
                    .commit();
        }
    }

    public void onButtonClick(View button) {
        switch (button.getId()) {
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
}

