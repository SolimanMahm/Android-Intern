package com.example.to_do.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_do.R;
import com.example.to_do.databinding.ActivityPagerBinding;
import com.example.to_do.adapters.CustomPagerAdapter;

public class PagerActivity extends AppCompatActivity {

    ActivityPagerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPager.setAdapter(new CustomPagerAdapter(this,
                new int[]{R.layout.on_boarding_one,
                        R.layout.on_boarding_two, R.layout.on_boarding_three}));

    }
}