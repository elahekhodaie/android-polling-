package com.jasbuber.allpolls;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jasbuber.allpolls.models.Poll;
import com.jasbuber.allpolls.models.orm.PollORM;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener, OnListMyPollsInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.client_toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.client_view_pager);

        viewPager.setAdapter(new MainTabAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Poll poll) {
        Intent intent = new Intent(this, PollActivity.class);

        intent.putExtra("poll", poll);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(PollORM poll) {
        Intent intent = new Intent(this, PollActivity.class);
        intent.putExtra("pollId", poll.getId());
        startActivity(intent);
    }
}
