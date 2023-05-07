package com.example.doctor.DoctorHomePagePack;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.PatientHomePagePack.SlotBookingDateAdapter;
import com.example.doctor.PatientHomePagePack.SlotBookingTimeAdapter;
import com.example.doctor.R;
import com.example.doctor.SlotModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MyCalender extends AppCompatActivity implements MyCalendarDateAdapter.MyCalendarAdapterListener {
    private RecyclerView rvDate, rvTime;
    private MyCalendarDateAdapter adapterDate;
    private MyCalendarTimeAdapter adapterTime;
    private ArrayList<SlotModel> slotsOfCurrentChosenDate;
    private LocalDate dateSelected;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calender);
        rvDate=findViewById(R.id.myCalendarDateRecyclerView);
        rvTime=findViewById(R.id.myCalendarTimeRecyclerView);
        LocalDate currentDate = LocalDate.now();

        // Create a list to store the dates
        ArrayList<LocalDate> nextThreeDays = new ArrayList<>();

        // Add the current date to the list
        nextThreeDays.add(currentDate);

        // Loop to add the next three days to the list
        for (int i = 1; i <= 3; i++) {
            LocalDate nextDay = currentDate.plus(i, ChronoUnit.DAYS);
            nextThreeDays.add(nextDay);
        }

        adapterDate=new MyCalendarDateAdapter(this, nextThreeDays);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvDate.setLayoutManager(linearLayoutManager);
        rvDate.setAdapter(adapterDate);
    }

    @Override
    public void serveBookingDate(ArrayList<SlotModel> slots, LocalDate dateChosen) {
        slotsOfCurrentChosenDate=slots;
        this.dateSelected=dateSelected;
        showAvailableSlots();
    }

    private void showAvailableSlots() {


        adapterTime=new MyCalendarTimeAdapter(this, slotsOfCurrentChosenDate);
        GridLayoutManager glayoutManager=new GridLayoutManager(this, 3);
        rvTime.setLayoutManager(glayoutManager);
        rvTime.setAdapter(adapterTime);
    }
}