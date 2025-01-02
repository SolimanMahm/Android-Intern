package com.example.to_do.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.intrtfaces.OnItemClickListenerDate;
import com.example.to_do.models.DateModel;
import com.example.to_do.R;

import java.util.ArrayList;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    ArrayList<DateModel> data = new ArrayList<>();
    private Context context;

    private OnItemClickListenerDate listener;

    private int selectedPosition = 0;

    public DateAdapter(ArrayList<DateModel> data, OnItemClickListenerDate listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        context = parent.getContext();
        return new DateViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {

        if (position == selectedPosition) {
            holder.card.setBackgroundColor(context.getResources().getColor(R.color.selectDay));
        } else holder.card.setBackgroundColor(Color.parseColor("#00000000"));

        DateModel model = data.get(position);
        holder.month.setText(model.getMonth());
        holder.numberDay.setText(model.getNumberDay() + "");
        holder.day.setText(model.getDay());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        TextView month, numberDay, day;
        LinearLayout card;

        public DateViewHolder(@NonNull View itemView, final OnItemClickListenerDate listener) {
            super(itemView);
            card = itemView.findViewById(R.id.cardItem);
            month = itemView.findViewById(R.id.month);
            numberDay = itemView.findViewById(R.id.numberDay);
            day = itemView.findViewById(R.id.day);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        selectedPosition = getAdapterPosition();
                        if (selectedPosition != RecyclerView.NO_POSITION)
                            listener.onItemClickDate(selectedPosition);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
