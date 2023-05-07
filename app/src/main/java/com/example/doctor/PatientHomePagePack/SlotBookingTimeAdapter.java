
package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doctor.R;
import com.example.doctor.SlotModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SlotBookingTimeAdapter extends RecyclerView.Adapter<SlotBookingTimeAdapter.SlotBookingViewModel> {
    private Context context;
    private ArrayList<SlotModel> timeSlots;
    SlotBookingTimeAdapterListener listener;
    private FirebaseFirestore db;
    public interface SlotBookingTimeAdapterListener{
        void timeSelected(String time);
    }
    public SlotBookingTimeAdapter(Context context,ArrayList<SlotModel> timeSlots ){
        this.context=context;
        this.timeSlots=timeSlots;
        this.listener= (SlotBookingTimeAdapterListener) context;
    }
    @NonNull
    @Override
    public SlotBookingViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_item, parent, false);
        return new SlotBookingViewModel(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SlotBookingViewModel holder, int position) {
        db=FirebaseFirestore.getInstance();
        SlotModel model=timeSlots.get(position);


        holder.time.setText(model.getTime());
        holder.slot_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.slot_item.setCardBackgroundColor(Color.parseColor("#0997ED"));
                listener.timeSelected(model.getTime());

            }
        });





    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }
    public class SlotBookingViewModel extends RecyclerView.ViewHolder{
        TextView time;
        CardView slot_item;
        public SlotBookingViewModel(@NonNull View itemView) {
            super(itemView);
            time=itemView.findViewById(R.id.textView_time);

            slot_item=itemView.findViewById(R.id.time_slot_item);


        }
    }
}
