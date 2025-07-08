package com.example.stopwatch;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stopwatch.databinding.ItemListBinding;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TimeViewHolder> {

    ArrayList<LabsTime> data = new ArrayList<>();

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addTime(LabsTime time) {
        data.add(0, time);
        notifyItemInserted(0);
    }

    public void clear(){
        data = new ArrayList<>();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {

        ItemListBinding binding;

        public TimeViewHolder(@NonNull ItemListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(LabsTime time) {
            binding.numberTextView.setText(String.format("%02d", time.getId()));
            binding.diffTimeTextView.setText("+ " + time.getDiffTime());
            binding.timeTextView.setText(time.getTime());
        }
    }

}
