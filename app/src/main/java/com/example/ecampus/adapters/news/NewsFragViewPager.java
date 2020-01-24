package com.example.ecampus.adapters.news;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecampus.activities.NewsfeedActivity;
import com.example.ecampus.fragments.LastWeekFragment;
import com.example.ecampus.fragments.LatestFragment;
import com.example.ecampus.fragments.OldestFragment;
import com.example.ecampus.fragments.YesterdayFragment;

public class NewsFragViewPager extends FragmentPagerAdapter {
    public NewsFragViewPager(Context context, @NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LatestFragment();
            case 1:
                return new YesterdayFragment();
            case 2:
                return new LastWeekFragment();
            case 3:
                return new OldestFragment();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Latest";
            case 1:
                return "Yesterday";
            case 2:
                return "Last Week";
            case 3:
                return "Oldest";
            default:
                return "News Feed";
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
