package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.doctor.Login;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientHomePage extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private TextView headerPatientName, headerPatientEmail;
    private ImageView headerPatientImage;
    private FirebaseFirestore db;
    private  Context context;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    ArrayList<DoctorListModel> arrdlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db=FirebaseFirestore.getInstance();
        //patient data

       // Log.d("MYTAGS",FirebaseAuth.getInstance().getCurrentUser().getUid());


        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_dehaze_24);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Drawer view
        mDrawer =findViewById(R.id.drawer_layout);

        // Drawer Navigation
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        View headerView = nvDrawer.getHeaderView(0);
        headerPatientEmail=headerView.findViewById(R.id.headerPatientEmail);
        headerPatientImage=headerView.findViewById(R.id.headerP_img);
        headerPatientName=headerView.findViewById(R.id.headerPatientName);
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot!=null){
                            Log.d("MYTAGS","here is  data");
                            Users u=documentSnapshot.toObject(Users.class);
                            Log.d("MYTAGS",u.getName());


                            Glide.with(getApplicationContext()).load(u.getImageUrl())
                                    //.placeholder()
                                    .fitCenter()
                                    .into(headerPatientImage);
                            headerPatientName.setText(u.getName());
                            headerPatientEmail.setText(u.getEmail());}
                        else{
                            Log.d("MYTAGS","no data");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MYTAGS", "failed"+ e.getMessage());
                    }
                });




//        RecyclerView recyclerView=findViewById(R.id.doctorrecyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        arrdlist.add(new DoctorListModel(R.drawable.img,"Arjun Pandey","Cardiologist","3 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Nikita Sinha","Dermatologist","5 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_2,"Ankit Dubey","Neurologist","2 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Aparna Jain","Pulmonogist","4 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Aparna Jain","Pulmonogist","4 Years"));
//        DoctorListAdapter adapter=new DoctorListAdapter(this,arrdlist);
//        recyclerView.setAdapter(adapter);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.patienthome,new Home()).commit();
//            nvDrawer.setCheckedItem(R.id.home);
        }
    }
    private void setupDrawerContent(NavigationView nvDrawer) {
        nvDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });


     showHome();

    }

    private void showHome() {
    }
    Fragment fragment = null;
    private void selectDrawerItem(MenuItem item)
    {

        Class fragmentClass;

        switch (item.getItemId()) {
            case R.id.home:{
                fragmentClass = Home.class;
                break;
            }
            case R.id.mydoctors:
            {
            fragmentClass = MyDoctorsFragment.class;



                break;}
            case R.id.profile:{
                fragmentClass = PatientProfile.class;
                break;
            }

            case R.id.myappointments:
            {
                fragmentClass = MyAppointmentsFragment.class;

                break;}

            case R.id.prescriptions:
            {fragmentClass = PrescriptionsFragment.class;

                break;}
            case R.id.settings:
            { fragmentClass = SettingsFragment.class;



                break;}
            case R.id.logout:
            {
                //fragmentClass = LogoutFragment.class;
                FirebaseAuth.getInstance().signOut();

                Intent intent=new Intent(PatientHomePage.this, Login.class);
                startActivity(intent);
                finish();
            }
            default:{
                fragmentClass = LogoutFragment.class;
            }



        }


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction().replace(R.id.patienthome, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        item.setChecked(true);

        setTitle(item.getTitle());

        mDrawer.closeDrawers();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
             }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }

        else{
            FragmentManager fragmentManager=getSupportFragmentManager();
            int backStackEntryCount=fragmentManager.getBackStackEntryCount();
            if(backStackEntryCount>0)
                fragmentManager.popBackStack();
            else
                super.onBackPressed();

        }
    }


}