package com.example.doctor.DoctorHomePagePack;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.PatientHomePagePack.SlotBookingTimeAdapter;
import com.example.doctor.R;
import com.example.doctor.SlotModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyCalendarTimeAdapter extends RecyclerView.Adapter<MyCalendarTimeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SlotModel> timeSlots;

    private FirebaseFirestore db;

    public MyCalendarTimeAdapter(Context context,ArrayList<SlotModel> timeSlots ){
        this.context=context;
        this.timeSlots=timeSlots;

    }
    @NonNull
    @Override
    public MyCalendarTimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_calendar_time_slot_item, parent, false);
        return new MyCalendarTimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCalendarTimeAdapter.ViewHolder holder, int position) {

        SlotModel model=timeSlots.get(position);


        holder.time.setText(model.getTime());
        holder.count.setText(String.valueOf(model.getNumberOfPatients()));

    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView time, count;
        CardView slot_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.textView_time);
            count=itemView.findViewById(R.id.myCalendarPatientCount);
            slot_item=itemView.findViewById(R.id.time_slot_item);


        }
    }
}
