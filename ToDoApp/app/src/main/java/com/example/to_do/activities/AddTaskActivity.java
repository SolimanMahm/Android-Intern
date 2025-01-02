package com.example.to_do.activities;

import static java.lang.Thread.sleep;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.to_do.database.MyDatabase;
import com.example.to_do.R;
import com.example.to_do.databinding.ActivityAddTaskBinding;
import com.example.to_do.models.TaskModel;
import com.example.to_do.utils.DateUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    ActivityAddTaskBinding binding;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    private final String START = "start";
    private final String END = "end";
    private int color;

    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.GONE);

        database = new MyDatabase(this);

        setActionBar(binding.toolBar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        binding.edtDate.setText(day + "/" + (month + 1) + "/" + year);
        binding.edtSTime.setText(DateUtils.convertStringToTime12(hour + ":" + minute));
        binding.edtETime.setText(DateUtils.add30M(hour + ":" + minute));
        color = binding.color1.getCardBackgroundColor().getDefaultColor();

        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        binding.edtSTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(START);
            }
        });

        binding.edtETime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(END);
            }
        });

        setColorOnClick(binding.color1, binding.colorImg1,
                new ImageView[]{binding.colorImg2, binding.colorImg3,
                        binding.colorImg4, binding.colorImg5, binding.colorImg6});
        setColorOnClick(binding.color2, binding.colorImg2,
                new ImageView[]{binding.colorImg1, binding.colorImg3,
                        binding.colorImg4, binding.colorImg5, binding.colorImg6});
        setColorOnClick(binding.color3, binding.colorImg3,
                new ImageView[]{binding.colorImg1, binding.colorImg2,
                        binding.colorImg4, binding.colorImg5, binding.colorImg6});
        setColorOnClick(binding.color4, binding.colorImg4,
                new ImageView[]{binding.colorImg1, binding.colorImg2,
                        binding.colorImg3, binding.colorImg5, binding.colorImg6});
        setColorOnClick(binding.color5, binding.colorImg5,
                new ImageView[]{binding.colorImg1, binding.colorImg2,
                        binding.colorImg3, binding.colorImg4, binding.colorImg6});
        setColorOnClick(binding.color6, binding.colorImg6,
                new ImageView[]{binding.colorImg1, binding.colorImg2,
                        binding.colorImg3, binding.colorImg4, binding.colorImg5});

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = binding.edtTitle.getText().toString();
                String note = binding.edtNote.getText().toString();

                if (title.isEmpty()) binding.edtTitle.setError("It cannot be empty ...!");
                if (note.isEmpty()) binding.edtNote.setError("It cannot be empty ...!");
                if (title.isEmpty() || note.isEmpty()) return;

                binding.progressBar.setVisibility(View.VISIBLE);
                binding.btn.setVisibility(View.GONE);
                TaskModel task = new TaskModel(title, note,
                        binding.edtSTime.getText().toString(),
                        binding.edtETime.getText().toString(),
                        binding.edtDate.getText().toString(),
                        color, 0);

                if (database.insertTask(task)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sleep(1000);
                                setResult(RESULT_OK);
                                finish();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btn.setVisibility(View.VISIBLE);
                    Toast.makeText(AddTaskActivity.this, "Failed ...!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setColorOnClick(MaterialCardView clickedColor, final ImageView clickedImage, final ImageView[] otherImage) {
        clickedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = clickedColor.getCardBackgroundColor().getDefaultColor();
                clickedImage.setImageResource(R.drawable.correct_signal_svgrepo_com);
                for (ImageView imageView :
                        otherImage) {
                    imageView.setImageResource(0);
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                binding.edtDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePickerDialog(String type) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = String.format("%02d:%02d", i, i1);
                if (type.equals(START)) {
                    binding.edtSTime.setText(DateUtils.convertStringToTime12(time));
                    binding.edtETime.setText(DateUtils.add30M(time));
                } else if (type.equals(END)) {
                    binding.edtETime.setText(DateUtils.convertStringToTime12(time));
                }
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

}