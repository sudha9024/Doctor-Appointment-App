package com.example.doctor.DoctorHomePagePack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorFrontPage extends AppCompatActivity {
    BottomNavigationView bnviewdoctor;
    //    public CardView mypatients,appointments,mycalender,patientrequest;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_front_page);
//        mypatients= findViewById(R.id.mypatients);
//        appointments= findViewById(R.id.appointments);
//        mycalender= findViewById(R.id.mycalender);
//        patientrequest= findViewById(R.id.patientrequest);
        bnviewdoctor=findViewById(R.id.bottom_navigation);

        bnviewdoctor.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    loadFragment(new DoctorHomePage());}
                else if (id == R.id.nav_profile) {
                    loadFragment(new DoctorProfilePage());
                }
//                else if (id == R.id.nav_feedback) {
//                    loadFragment(new FeedbackPage());
//                }
                else {
                    loadFragment(new DoctorSettings());
                }
                return true;
            }
        });

        bnviewdoctor.setSelectedItemId(R.id.nav_home);
    }
    public void loadFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        ft.replace(R.id.container,fragment);

        ft.commit();
    }
}
