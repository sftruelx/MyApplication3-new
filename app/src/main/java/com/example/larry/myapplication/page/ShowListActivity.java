package com.example.larry.myapplication.page;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.example.larry.myapplication.PlaybackControlsFragment;
import com.example.larry.myapplication.R;
import com.example.larry.myapplication.songList.SongDetailFragment;
import com.example.larry.myapplication.utils.MyActivity;

public class ShowListActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        int classifyId = getIntent().getIntExtra("classifyId",0);
        String title = getIntent().getStringExtra("title");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
//        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
        }
        mControlsFragment = (PlaybackControlsFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_playback_controls);
        if (mControlsFragment == null) {
            throw new IllegalStateException("Mising fragment with id 'controls'. Cannot continue.");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.show_list, TabTwoFragment.newInstance(classifyId)).commit();
        hidePlaybackControls();

/*         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            ShowListActivity.this.finish();
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
            ShowListActivity.this.finish();
            overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
