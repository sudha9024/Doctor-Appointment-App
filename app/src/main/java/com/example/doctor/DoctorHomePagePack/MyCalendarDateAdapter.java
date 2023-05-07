package com.example.doctor.DoctorHomePagePack;

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

import com.example.doctor.PatientHomePagePack.SlotBookingDateAdapter;
import com.example.doctor.SlotModel;
import com.example.doctor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;

public class MyCalendarDateAdapter extends RecyclerView.Adapter<MyCalendarDateAdapter.ViewHolder> {
    Context context;
    ArrayList<LocalDate> dates;
    MyCalendarDateAdapter.MyCalendarAdapterListener listener;
    public interface MyCalendarAdapterListener{
        void serveBookingDate(ArrayList<SlotModel> slots, LocalDate dateChosen);
    }
    public MyCalendarDateAdapter(Context context, ArrayList<LocalDate> dates){
        this.context=context;
        this.dates=dates;
        this.listener= (MyCalendarAdapterListener) context;
    }
    @NonNull
    @Override
    public MyCalendarDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.date_slot_item, parent, false);
        return new MyCalendarDateAdapter.ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyCalendarDateAdapter.ViewHolder holder, int position) {
        LocalDate model=dates.get(position);
        holder.week.setText(""+model.getDayOfWeek().name().toUpperCase().substring(0,3));
        holder.day.setText(""+model.getDayOfMonth());
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.date.setCardBackgroundColor(Color.parseColor("#0997ED"));

                // bring slots here
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("DoctorSlots").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("Dates").document(""+model.toString())
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
                                    listener.serveBookingDate(slots,model);

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
