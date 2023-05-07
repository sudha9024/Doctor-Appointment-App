package com.example.doctor.PatientHomePagePack;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doctor.AppointmentListModel;
import com.example.doctor.R;
import com.example.doctor.SlotModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SlotBooking extends AppCompatActivity implements SlotBookingDateAdapter.SlotBookingDateAdapterListener
        ,  SlotBookingTimeAdapter.SlotBookingTimeAdapterListener{
    private FirebaseFirestore db;
    private RecyclerView rvTime, rvDate;
    //private ArrayList<SlotModelTime> timeSlots;
    private  ArrayList<SlotModelDate>  dates;
    ArrayList<SlotModel> availableSlots;
    private SlotBookingDateAdapter slotBookingDateAdapter;
    private SlotBookingTimeAdapter slotBookingTimeAdapter;
    private Button bookAppointmentTimeBTN;
    private LocalDate dateSelected;

    private String DoctorId;
    private ArrayList<SlotModel> slotsOfCurrentChosenDate;
    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_booking);

        rvTime = findViewById(R.id.recyclerViewSlotBooking);
        rvDate = findViewById(R.id.dateRecyclerView);
        bookAppointmentTimeBTN = findViewById(R.id.slot_book_btn);
        Intent in = getIntent();
        DoctorId = in.getStringExtra("DoctorId");

        Log.d("SlotBookingTag", DoctorId);
        //timeSlots = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        LocalDate currentDate = LocalDate.now();

        // Create a list to store the dates
        List<LocalDate> nextThreeDays = new ArrayList<>();

        // Add the current date to the list
        nextThreeDays.add(currentDate);

        // Loop to add the next three days to the list
        for (int i = 1; i <= 3; i++) {
            LocalDate nextDay = currentDate.plus(i, ChronoUnit.DAYS);
            nextThreeDays.add(nextDay);
        }
//        for(int i=0;i<nextThreeDays.size();i++){
//            db.collection("DoctorSlots").document(DoctorId)
//                    .collection("Dates").document(""+nextThreeDays.get(i))
//                    .collection("Slots")
//                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if(task.isSuccessful()){
//                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                    Long count=(Long)documentSnapshot.get("NumberOfPatients");
//                                    LocalTime t= (LocalTime) documentSnapshot.get("Time");
//                                    if(count==3){
//                                        timeSlots.add(new SlotModelTime(t, "Full",DoctorId));
//                                    }
//                                    else{
//                                        timeSlots.add(new SlotModelTime(t, "Empty",DoctorId));
//                                    }
//
//                                }
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("SlotBooking", "Failed to fetch slots");
//                        }
//                    });
//        }



        dates=new ArrayList<>();
        for(int i=0;i<nextThreeDays.size();i++){
            dates.add(new SlotModelDate(nextThreeDays.get(i), DoctorId));
        }
        slotBookingDateAdapter=new SlotBookingDateAdapter(this, dates);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvDate.setLayoutManager(linearLayoutManager);
        rvDate.setAdapter(slotBookingDateAdapter);


    }

    public void showAvailableSlots()
    {
        availableSlots=new ArrayList<>();

        for(SlotModel slot:slotsOfCurrentChosenDate)
        {
            if(slot.getNumberOfPatients()<3)
            {
                availableSlots.add(slot);
            }
        }




        slotBookingTimeAdapter=new SlotBookingTimeAdapter(this, availableSlots);
        GridLayoutManager glayoutManager=new GridLayoutManager(this, 3);
        rvTime.setLayoutManager(glayoutManager);
        rvTime.setAdapter(slotBookingTimeAdapter);
    }
    @Override
    public void serveBookingDate(ArrayList<SlotModel> slots, LocalDate dateSelected) {
        slotsOfCurrentChosenDate=slots;
        this.dateSelected=dateSelected;
        showAvailableSlots();
    }

    @Override
    public void timeSelected(String time) {
        AppointmentListModel appointmentData=new AppointmentListModel();
        appointmentData.setPatientId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        appointmentData.setDoctorId(DoctorId);

        appointmentData.setSlotTimeSelected(time);
        appointmentData.setDateSelected(""+dateSelected);

        bookAppointmentTimeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MYTAGS", "going to book an appointement");
                db.collection("PatientsAppointment").document(appointmentData.getPatientId()).
                        collection("Doctors").document(DoctorId+" "+dateSelected).
                        set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("MYTAGS", "Successfully added to patients appointment");
                                // add here
                                db.collection("DoctorAppointment").document(appointmentData.getDoctorId()).
                                        collection("Patients").document(appointmentData.getPatientId()+" "+dateSelected).
                                        set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d("MYTAGS", "Successfully added to patients appointment");
                                                // add here
                                                db.collection("DoctorSlots").document(appointmentData.getDoctorId())
                                                        .collection("Dates").document(""+dateSelected)
                                                        .collection("Slots").document(appointmentData.getSlotTimeSelected())
                                                        .update("numberOfPatients", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Log.d("MYTAGS", "number of patients successfully updated!");
                                                                // add here
                                                                // go to home fragment
                                                               Intent intent=new Intent(SlotBooking.this,PatientHomePage.class);
                                                               startActivity(intent);
                                                               finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.d("MYTAGS", "Could not update number of patients");
                                                            }
                                                        });

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MYTAGS", "Could not add to patients appointement "+e) ;
                                            }
                                        });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("MYTAGS", "Could not add to patients appointement "+e) ;
                            }
                        });
//
//                int count=0;
//                for(SlotModel toGetTime: availableSlots){
//                    if(toGetTime.getTime().equals(time)){
//                        count=toGetTime.getNumberOfPatients();
//                    }
//                }
//                count++;




            }
        });
    }
}