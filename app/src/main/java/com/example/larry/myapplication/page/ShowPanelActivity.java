package com.example.larry.myapplication.page;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.larry.myapplication.PlaybackControlsFragment;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.listener.OnListFragmentInteractionListener;
import com.example.larry.myapplication.page.dummy.DummyContent;
import com.example.larry.myapplication.utils.LogHelper;
import com.example.larry.myapplication.utils.MyActivity;

public class ShowPanelActivity extends MyActivity implements OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_panel);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        mControlsFragment = (PlaybackControlsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_playback_controls);
        if (mControlsFragment == null) {
            throw new IllegalStateException("Mising fragment with id 'controls'. Cannot continue.");
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.show_list, KindOfParentFragment.newInstance(1)).commit();
        hidePlaybackControls();


    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            ShowPanelActivity.this.finish();
            overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
//            navigateUpTo(new Intent(this, MainActivity.class));
            ShowPanelActivity.this.finish();
            overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        LogHelper.i(this.getLocalClassName(), item.content);
    }
}
