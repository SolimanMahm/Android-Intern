package com.example.to_do.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.R;
import com.example.to_do.intrtfaces.OnItemClickListenerTask;
import com.example.to_do.models.TaskModel;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<TaskModel> data = new ArrayList<>();
    private Context context;

    private OnItemClickListenerTask listener;

    public TaskAdapter(ArrayList<TaskModel> data, OnItemClickListenerTask listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        context = parent.getContext();
        return new TaskViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskModel model = data.get(position);
        holder.item.setBackgroundColor(model.getColor());
        holder.title.setText(model.getTitle());
        holder.note.setText(model.getNote());
        holder.date.setText(model.getStartTime() + " - " + model.getEndTime());
        holder.state.setText((model.getIsCompleted() == 1)
                ? context.getResources().getText(R.string.completed)
                : context.getResources().getText(R.string.toDo));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        LinearLayout item;
        TextView title, note, date, state;

        public TaskViewHolder(@NonNull View itemView, final OnItemClickListenerTask listener) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);
            date = itemView.findViewById(R.id.startEndDate);
            state = itemView.findViewById(R.id.state);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClickTask(position);
                        }
                    }
                }
            });

        }
    }
}
