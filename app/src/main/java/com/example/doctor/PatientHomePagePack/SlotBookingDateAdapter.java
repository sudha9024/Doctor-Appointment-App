package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;

public class SlotBookingDateAdapter extends RecyclerView.Adapter<SlotBookingDateAdapter.ViewHolder> {
    Context context;
    ArrayList<SlotModelDate> dates;

    SlotBookingDateAdapterListener listener;

    public interface SlotBookingDateAdapterListener{
        void serveBookingDate(ArrayList<SlotModel> slots, LocalDate dateChosen);
    }
    public SlotBookingDateAdapter(Context context, ArrayList<SlotModelDate> dates){
        this.context=context;
        this.dates=dates;
        this.listener= (SlotBookingDateAdapterListener) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.date_slot_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SlotModelDate model=dates.get(position);
        holder.week.setText(""+model.getSlotDate().getDayOfWeek().name().toUpperCase().substring(0,3));
        holder.day.setText(""+model.getSlotDate().getDayOfMonth());
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ChosenDateAndTimeSlot.chosenDate=model.getSlotDate();
                holder.date.setCardBackgroundColor(Color.parseColor("#0997ED"));

                // bring slots here
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("DoctorSlots").document(model.getDoctorId())
                        .collection("Dates").document(""+model.getSlotDate().toString())
                        .collection("Slots")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    ArrayList<SlotModel> slots=new ArrayList<>();
                                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){

                                        SlotModel slot=documentSnapshot.toObject(SlotModel.class);
                                        slots.add(slot);

                                    }

                                    // now that we have the slots, lets send it to host activity
                                    listener.serveBookingDate(slots,model.getSlotDate());

                                }
                                else{
                                    Log.d("SLOTBOOKING", "Could not fetch slot details");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("SlotBooking", "Failed to fetch slots");
                            }
                        });



            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView week, day;
        CardView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            week=itemView.findViewById(R.id.textView_date_weekday);
            day=itemView.findViewById(R.id.textView_date_date);
            date=itemView.findViewById(R.id.date_slot_item);

        }
    }

}
