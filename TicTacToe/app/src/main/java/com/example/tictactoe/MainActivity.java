package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tictactoe.databinding.ActivityMainBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private boolean xIsPlay = true;
    private String regex =
            "1[0-9]*2[0-9]*3|1[0-9]*3[0-9]*2|2[0-9]*1[0-9]*3|2[0-9]*3[0-9]*1|3[0-9]*1[0-9]*2|3[0-9]*2[0-9]*1|" +
                    "4[0-9]*5[0-9]*6|4[0-9]*6[0-9]*5|5[0-9]*4[0-9]*6|5[0-9]*6[0-9]*4|6[0-9]*4[0-9]*5|6[0-9]*5[0-9]*4|" +
                    "7[0-9]*8[0-9]*9|7[0-9]*9[0-9]*8|8[0-9]*7[0-9]*9|8[0-9]*9[0-9]*7|9[0-9]*7[0-9]*8|9[0-9]*8[0-9]*7|" +
                    "1[0-9]*4[0-9]*7|1[0-9]*7[0-9]*4|4[0-9]*1[0-9]*7|4[0-9]*7[0-9]*1|7[0-9]*1[0-9]*4|7[0-9]*4[0-9]*1|" +
                    "2[0-9]*5[0-9]*8|2[0-9]*8[0-9]*5|5[0-9]*2[0-9]*8|5[0-9]*8[0-9]*2|8[0-9]*2[0-9]*5|8[0-9]*5[0-9]*2|" +
                    "3[0-9]*6[0-9]*9|3[0-9]*9[0-9]*6|6[0-9]*3[0-9]*9|6[0-9]*9[0-9]*3|9[0-9]*3[0-9]*6|9[0-9]*6[0-9]*3|" +
                    "1[0-9]*5[0-9]*9|1[0-9]*9[0-9]*5|5[0-9]*1[0-9]*9|5[0-9]*9[0-9]*1|9[0-9]*1[0-9]*5|9[0-9]*5[0-9]*1|" +
                    "3[0-9]*5[0-9]*7|3[0-9]*7[0-9]*5|5[0-9]*3[0-9]*7|5[0-9]*7[0-9]*3|7[0-9]*3[0-9]*5|7[0-9]*5[0-9]*3";

    private Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    private Matcher matcher;
    private String postionX = "", postionO = "";

    private int postionClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cardPlayerOne.setBackgroundResource(R.drawable.black_border);
        binding.cardPlayerTwo.setBackgroundResource(R.drawable.white_box);

        binding.textViewPlayerOne.setText(getIntent().getStringExtra(AddPlayers.PLAYER_ONE));
        binding.textViewPlayerTwo.setText(getIntent().getStringExtra(AddPlayers.PLAYER_TWO));

        binding.card00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card00.setImageResource(R.drawable.ximage);
                    postionX += "1";
                } else {
                    binding.card00.setImageResource(R.drawable.oimage);
                    postionO += "1";
                }
                changeStatePlayer();
                binding.card00.setEnabled(false);
            }
        });

        binding.card01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card01.setImageResource(R.drawable.ximage);
                    postionX += "2";
                } else {
                    binding.card01.setImageResource(R.drawable.oimage);
                    postionO += "2";
                }
                changeStatePlayer();
                binding.card01.setEnabled(false);
            }
        });

        binding.card02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card02.setImageResource(R.drawable.ximage);
                    postionX += "3";
                } else {
                    binding.card02.setImageResource(R.drawable.oimage);
                    postionO += "3";
                }
                changeStatePlayer();
                binding.card02.setEnabled(false);
            }
        });

        binding.card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card10.setImageResource(R.drawable.ximage);
                    postionX += "4";
                } else {
                    binding.card10.setImageResource(R.drawable.oimage);
                    postionO += "4";
                }
                changeStatePlayer();
                binding.card10.setEnabled(false);
            }
        });

        binding.card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card11.setImageResource(R.drawable.ximage);
                    postionX += "5";
                } else {
                    binding.card11.setImageResource(R.drawable.oimage);
                    postionO += "5";
                }
                changeStatePlayer();
                binding.card11.setEnabled(false);
            }
        });

        binding.card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card12.setImageResource(R.drawable.ximage);
                    postionX += "6";
                } else {
                    binding.card12.setImageResource(R.drawable.oimage);
                    postionO += "6";
                }
                changeStatePlayer();
                binding.card12.setEnabled(false);
            }
        });

        binding.card20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card20.setImageResource(R.drawable.ximage);
                    postionX += "7";
                } else {
                    binding.card20.setImageResource(R.drawable.oimage);
                    postionO += "7";
                }
                changeStatePlayer();
                binding.card20.setEnabled(false);
            }
        });

        binding.card21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card21.setImageResource(R.drawable.ximage);
                    postionX += "8";
                } else {
                    binding.card21.setImageResource(R.drawable.oimage);
                    postionO += "8";
                }
                changeStatePlayer();
                binding.card21.setEnabled(false);
            }
        });

        binding.card22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (xIsPlay) {
                    binding.card22.setImageResource(R.drawable.ximage);
                    postionX += "9";
                } else {
                    binding.card22.setImageResource(R.drawable.oimage);
                    postionO += "9";
                }
                changeStatePlayer();
                binding.card22.setEnabled(false);
            }
        });

    }

    void changeStatePlayer() {
        postionClicked++;
        if (checkWin()) {
            ResultDialog dialog;
            if (xIsPlay)
                dialog = new ResultDialog(MainActivity.this, binding.textViewPlayerOne.getText().toString() +
                        " is a Winner!", MainActivity.this);
            else
                dialog = new ResultDialog(MainActivity.this, binding.textViewPlayerTwo.getText().toString() +
                        " is a Winner!", MainActivity.this);
            dialog.setCancelable(false);
            dialog.show();
        }
        else if (postionClicked == 9) {
            ResultDialog dialog = new ResultDialog(MainActivity.this, "Match Draw", MainActivity.this);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            xIsPlay = !xIsPlay;
            if (xIsPlay) {
                binding.cardPlayerOne.setBackgroundResource(R.drawable.black_border);
                binding.cardPlayerTwo.setBackgroundResource(R.drawable.white_box);
            } else {
                binding.cardPlayerTwo.setBackgroundResource(R.drawable.black_border);
                binding.cardPlayerOne.setBackgroundResource(R.drawable.white_box);
            }
        }
    }

    boolean checkWin() {
        return pattern.matcher(postionX).find() || pattern.matcher(postionO).find();
    }

    public void restart() {
        postionClicked = 0;
        xIsPlay = true;
        postionX = "";
        postionO = "";
        binding.cardPlayerOne.setBackgroundResource(R.drawable.black_border);
        binding.cardPlayerTwo.setBackgroundResource(R.drawable.white_box);
        binding.card00.setImageResource(R.drawable.white_box);
        binding.card00.setEnabled(true);
        binding.card01.setImageResource(R.drawable.white_box);
        binding.card01.setEnabled(true);
        binding.card02.setImageResource(R.drawable.white_box);
        binding.card02.setEnabled(true);
        binding.card10.setImageResource(R.drawable.white_box);
        binding.card10.setEnabled(true);
        binding.card11.setImageResource(R.drawable.white_box);
        binding.card11.setEnabled(true);
        binding.card12.setImageResource(R.drawable.white_box);
        binding.card12.setEnabled(true);
        binding.card20.setImageResource(R.drawable.white_box);
        binding.card20.setEnabled(true);
        binding.card21.setImageResource(R.drawable.white_box);
        binding.card21.setEnabled(true);
        binding.card22.setImageResource(R.drawable.white_box);
        binding.card22.setEnabled(true);
    }
}