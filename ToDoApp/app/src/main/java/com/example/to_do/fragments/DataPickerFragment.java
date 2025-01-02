package com.example.to_do.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.to_do.adapters.DateAdapter;
import com.example.to_do.intrtfaces.OnItemClickListenerDate;
import com.example.to_do.intrtfaces.OnItemClickListenerTask;
import com.example.to_do.models.DateModel;
import com.example.to_do.utils.DateUtils;
import com.example.to_do.R;

import java.util.ArrayList;
import java.util.Date;

public class DataPickerFragment extends Fragment implements OnItemClickListenerDate {

    private OnItemClickListenerTask listener;

    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<DateModel> list = new ArrayList<>();

    public DataPickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListenerTask)
            listener = (OnItemClickListenerTask) context;
        else
            throw new ClassCastException("Your activity does not implements OnItemClickListenerTask");
    }

    public static DataPickerFragment newInstance() {
        DataPickerFragment fragment = new DataPickerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_picker, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.date_picker_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        dates = DateUtils.getAllDatesFromNowToNextFiveMonths();
        String[] filterDate;

        for (Date date : dates) {
            filterDate = date.toString().split(" ");
            list.add(new DateModel(filterDate[0].toUpperCase(), filterDate[1].toUpperCase(),
                    filterDate[2], filterDate[filterDate.length - 1]));
        }

        DateAdapter adapter = new DateAdapter(list, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onItemClickDate(int position) {
        listener.onChangeAdapter(list.get(position).getNumberDay() + "/" + list.get(position).getNumberMonth() + "/" + list.get(position).getYear());
    }
}