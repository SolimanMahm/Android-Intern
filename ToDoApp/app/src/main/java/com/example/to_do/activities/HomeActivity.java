package com.example.to_do.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.database.MyDatabase;
import com.example.to_do.adapters.TaskAdapter;
import com.example.to_do.databinding.ActivityHomeBinding;
import com.example.to_do.fragments.DataPickerFragment;
import com.example.to_do.intrtfaces.OnItemClickListenerTask;
import com.example.to_do.models.TaskModel;
import com.example.to_do.service.TimeService;

import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements OnItemClickListenerTask {

    private ActivityHomeBinding binding;
    private final int REQUEST_CODE = 100;
    private ArrayList<TaskModel> data = new ArrayList<>();
    final String[] months = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
    MyDatabase database;
    private TaskAdapter adapter;
    private Calendar calendar = Calendar.getInstance();
    private String dateNow = calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.fragmentContainerView.getId(), new DataPickerFragment());
        fragmentTransaction.commit();

        database = new MyDatabase(this);

        binding.currentDate.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE) + ", " + calendar.get(Calendar.YEAR));

        binding.tasks.setHasFixedSize(true);
        binding.tasks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        data = database.getAllTasks(dateNow);
        updateUI();

        TaskAdapter adapter = new TaskAdapter(data, this);
        binding.tasks.setAdapter(adapter);

        binding.addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddTaskActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Intent intent = new Intent(getBaseContext(), TimeService.class);
        ContextCompat.startForegroundService(getBaseContext(), intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            onChangeAdapter(dateNow);
        }
    }

    @Override
    public void onItemClickTask(int position) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                getBaseContext(),
                data.get(position).getId(),
                data.get(position).getIsCompleted(),
                this,
                data.get(position).getDate());
        bottomSheetDialog.show(getSupportFragmentManager(), "ModalBottomSheet");
    }

    @Override
    public void onChangeAdapter(String date) {
        data = database.getAllTasks(date);
        updateUI();
        adapter = new TaskAdapter(data, this);
        binding.tasks.setAdapter(adapter);
    }

    private void updateUI() {
        if (data.isEmpty()) binding.noTasks.setVisibility(View.VISIBLE);
        else binding.noTasks.setVisibility(View.GONE);
    }


}