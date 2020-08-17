package com.jasbuber.allpolls;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainTabAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[];

    private Context context;

    public MainTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        tabTitles = new String[]{context.getString(R.string.my_polls),
                context.getString(R.string.find_polls)};

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MyPollsFragment();
            case 1:
                return PollsListFragment.newInstance();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
