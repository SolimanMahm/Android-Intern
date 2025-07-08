package com.example.stopwatch;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.stopwatch.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private int minutes, seconds, milliseconds;
    private int startMinutes, startSeconds, startMilliseconds;
    private boolean isRunning = false;

    private Handler handler = new Handler();
    private Runnable runnable;

    private final String START_STATE = "start";
    private final String PAUSE_STATE = "pause";
    private final String RESET_STATE = "reset";
    private final String LABS_STATE = "labs";
    private boolean labsClicked = false;
    private String time;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
                LabsTime.id = 0;
                adapter = new MyAdapter();
                binding.recyclerView.setAdapter(adapter);
                refactorUI(START_STATE);
            }
        });

        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning) {
                    pauseTimer();
                    refactorUI(PAUSE_STATE);
                } else {
                    startTimer();
                    refactorUI(START_STATE);
                }
            }
        });

        binding.labsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning && !labsClicked) {
                    refactorUI(LABS_STATE);
                    labsClicked = true;
                    adapter.addTime(new LabsTime(getFormattedTimeDiff(startMinutes, startSeconds, startMilliseconds, minutes, seconds, milliseconds), time));
                    startMinutes = minutes;
                    startSeconds = seconds;
                    startMilliseconds = milliseconds;
                } else if (isRunning) {
                    adapter.addTime(new LabsTime(getFormattedTimeDiff(startMinutes, startSeconds, startMilliseconds, minutes, seconds, milliseconds), time));
                    binding.recyclerView.scrollToPosition(0);
                    startMinutes = minutes;
                    startSeconds = seconds;
                    startMilliseconds = milliseconds;
                } else {
                    resetTimer();
                    refactorUI(RESET_STATE);
                }
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    milliseconds++;
                    if (milliseconds == 100) {
                        seconds++;
                        milliseconds = 0;
                    }
                    if (seconds == 60) {
                        minutes++;
                        seconds = 0;
                    }
                    time = String.format("%02d:%02d.%02d", minutes, seconds, milliseconds);
                    binding.displayTime.setText(time);
                    handler.postDelayed(this, 1);
                }
            }
        };

    }

    private void startTimer() {
        if (!isRunning) {
            isRunning = true;
            handler.post(runnable);
        }
    }

    private void pauseTimer() {
        isRunning = false;
    }

    private void resetTimer() {
        isRunning = false;
        labsClicked = false;
        milliseconds = seconds = minutes = 0;
        startMilliseconds = startSeconds = startMinutes = 0;
        binding.displayTime.setText("00:00.00");
    }

    private void refactorUI(String state) {
        switch (state) {
            case START_STATE:
                binding.playButton.setVisibility(View.INVISIBLE);
                if (!labsClicked) binding.recyclerView.setVisibility(View.INVISIBLE);
                binding.pauseButton.setVisibility(View.VISIBLE);
                binding.labsButton.setVisibility(View.VISIBLE);
                binding.pauseButton.setImageResource(R.drawable.pause_svgrepo_com);
                binding.labsButton.setImageResource(R.drawable.clock_schedule_time_timer_watch_svgrepo_com__1_);
                break;
            case PAUSE_STATE:
                binding.pauseButton.setImageResource(R.drawable.play_1000_svgrepo_com);
                binding.labsButton.setImageResource(R.drawable.player_stop_svgrepo_com);
                break;
            case RESET_STATE:
                binding.playButton.setVisibility(View.VISIBLE);
                binding.pauseButton.setVisibility(View.INVISIBLE);
                binding.labsButton.setVisibility(View.INVISIBLE);
                binding.recyclerView.setVisibility(View.INVISIBLE);
                binding.displayTime.animate()
                        .translationY(0)
                        .setDuration(500)
                        .start();
                break;
            case LABS_STATE:
                binding.displayTime.animate()
                        .translationY(-700)
                        .setDuration(500)
                        .start();
                binding.recyclerView.setAlpha(0f);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.recyclerView.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .start();
                break;
        }
    }

    public String getFormattedTimeDiff(int startMinutes, int startSeconds, int startMillis,
                                       int endMinutes, int endSeconds, int endMillis) {
        int startTotal = (startMinutes * 60 * 1000) + (startSeconds * 1000) + (startMillis * 10);
        int endTotal = (endMinutes * 60 * 1000) + (endSeconds * 1000) + (endMillis * 10);

        int diff = endTotal - startTotal;

        if (diff < 0) diff = 0;

        int diffMinutes = (diff / 1000) / 60;
        int diffSeconds = (diff / 1000) % 60;
        int diffMillis = (diff % 1000) / 10;

        return String.format("%02d:%02d.%02d", diffMinutes, diffSeconds, diffMillis);
    }


}