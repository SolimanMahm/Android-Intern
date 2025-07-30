package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.tictactoe.databinding.ActivityAddPlayersBinding;

public class AddPlayers extends AppCompatActivity {

    ActivityAddPlayersBinding binding;
    private String errorMessage = "Please enter player name";
    public final static String PLAYER_ONE = "player one", PLAYER_TWO = "player two";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityAddPlayersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.playerOne.getText().toString().isEmpty())
                    binding.playerOne.setError(errorMessage);
                else if (binding.playerTwo.getText().toString().isEmpty())
                    binding.playerTwo.setError(errorMessage);
                else {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra(PLAYER_ONE, binding.playerOne.getText().toString().trim());
                    intent.putExtra(PLAYER_TWO, binding.playerTwo.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}