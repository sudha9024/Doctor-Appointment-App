package com.example.doctor.DoctorHomePagePack;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.AppointmentListModel;
import com.example.doctor.PatientHomePagePack.MyDoctorsAdapter;
import com.example.doctor.PatientHomePagePack.MyDoctorsModel;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MyPatients extends AppCompatActivity {
    private ArrayList<MyPatientsModel> patientsList;

    // 2- Adapter View
    private RecyclerView recyclerViewMyPatients;

    //3- Adapter
    private MyPatientsAdapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);
        recyclerViewMyPatients=findViewById(R.id.recyclerViewMyPatients);
        ArrayList<String> patientIds=new ArrayList<>();
        db.collection("DoctorAppointment").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                AppointmentListModel alm = doc.toObject(AppointmentListModel.class);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate d = LocalDate.parse(alm.getDateSelected(), formatter);
                                int result = d.compareTo(LocalDate.now());

                                if (result <=0) {
                                    patientIds.add(alm.getPatientId());
                                }

                            }

                            patientsList = new ArrayList<>();
                            for (int i = 0; i < patientIds.size(); i++) {

                                db.collection("Users").whereEqualTo("userId", patientIds.get(i)).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        Users mal = doc.toObject(Users.class);
                                                        patientsList.add(new MyPatientsModel(mal.getName(),mal.getPhone(), mal.getImageUrl()));
                                                    }
//                                                    //adapter.notifyDataSetChanged();
                                                    adapter=new MyPatientsAdapter(MyPatients.this, patientsList);
                                                    RecyclerView.LayoutManager layoutManager=new
                                                            LinearLayoutManager(MyPatients.this, LinearLayoutManager.VERTICAL, false);
                                                    recyclerViewMyPatients.setLayoutManager(layoutManager);
                                                    recyclerViewMyPatients.setAdapter(adapter);
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MYTAGS", "failed" + e.getMessage());
                                            }
                                        });
                            }
                        }
                        else{
                            Log.d("MYTAGS", "UnSuccessful  to add to doctor appointment");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MYTAGS", "UnSuccessful  to add to doctor appointment"+e.getMessage());
                    }
                });







    }
}