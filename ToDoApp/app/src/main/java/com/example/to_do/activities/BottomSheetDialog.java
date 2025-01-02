package com.example.to_do.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.to_do.database.MyDatabase;
import com.example.to_do.R;
import com.example.to_do.intrtfaces.OnItemClickListenerTask;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    private Button complete, delete, cancel;
    private CardView cancelCard, completeCard;
    private boolean isCompleted;
    private int id;
    private MyDatabase database;

    private OnItemClickListenerTask listener;
    private String date;

    public BottomSheetDialog(Context context, int id, int isCompleted, OnItemClickListenerTask listener, String date) {
        this.listener = listener;
        database = new MyDatabase(context);
        this.id = id;
        this.isCompleted = (isCompleted == 1) ? true : false;
        this.date = date;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);


        complete = view.findViewById(R.id.complete);
        completeCard = view.findViewById(R.id.completeCardView);
        delete = view.findViewById(R.id.delete);
        cancel = view.findViewById(R.id.cancel);
        cancelCard = view.findViewById(R.id.cancelCardView);

        if (isCompleted) { // isCompleted
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(0, 0, 0, 24);

            completeCard.setVisibility(View.GONE);
            cancelCard.setLayoutParams(params);
        }

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (database.updateTask(id)) {
                    listener.onChangeAdapter(date);
                    dismiss();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (database.deleteTask(id)) {
                    listener.onChangeAdapter(date);
                    dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}
