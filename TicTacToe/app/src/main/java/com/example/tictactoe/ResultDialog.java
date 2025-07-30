package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tictactoe.databinding.ActivityResultDialogBinding;

public class ResultDialog extends Dialog {

    ActivityResultDialogBinding binding;
    private final String message;
    private final MainActivity mainActivity;

    public ResultDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.message.setText(message);

        binding.startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.restart();
                dismiss();
            }
        });

    }
}