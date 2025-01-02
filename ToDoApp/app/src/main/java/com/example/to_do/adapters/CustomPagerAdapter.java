package com.example.to_do.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.to_do.R;
import com.example.to_do.activities.HomeActivity;
import com.example.to_do.activities.SplashActivity;

public class CustomPagerAdapter extends PagerAdapter {

    private int[] layouts;
    private Activity activity;

    public CustomPagerAdapter(Activity activity, int[] layouts) {
        this.activity = activity;
        this.layouts = layouts;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SplashActivity.OPENED, Context.MODE_PRIVATE);

        View view = inflater.inflate(layouts[position],
                container, false);


        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((position + 1) < getCount()) {
                    ((ViewPager) container).setCurrentItem(position + 1, true);
                } else {
                    sharedPreferences.edit().putBoolean(SplashActivity.OPENED, true).apply();
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, 0);
                    }
                    activity.finish();
                }
            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((position - 1) >= 0) {
                    ((ViewPager) container).setCurrentItem(position - 1, true);
                }
            }
        });

        view.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean(SplashActivity.OPENED, true).apply();
                activity.startActivity(new Intent(activity, HomeActivity.class));
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{"android.permission.POST_NOTIFICATIONS"}, 0);
                }
                activity.finish();
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}
