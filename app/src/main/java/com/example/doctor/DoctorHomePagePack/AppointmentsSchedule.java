package com.example.doctor.DoctorHomePagePack;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.AppointmentListModel;
import com.example.doctor.PatientHomePagePack.MyAppointmentAdapter;
import com.example.doctor.PatientHomePagePack.MyAppointmentModel;
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

public class AppointmentsSchedule extends AppCompatActivity {
    //1- Data
    private ArrayList<AppointmentScheduleModel> patientsList;

    // 2- Adapter View
    private RecyclerView recyclerViewAppointmentSchedule;

    //3- Adapter
    private AppointmentScheduleAdapter adapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_appointments_schedule);

        recyclerViewAppointmentSchedule=findViewById(R.id.recyclerViewAppointmentSchedule);
        patientsList=new ArrayList<>();
        ArrayList<String[] > patientsIdsAndDate=new ArrayList<>();
        Log.d("MYTAGS", FirebaseAuth.getInstance().getCurrentUser().getUid());
        db.collection("DoctorAppointment").document(""+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Patients").
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d("MYTAGS", "I am inside");
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                AppointmentListModel alm = doc.toObject(AppointmentListModel.class);
                                patientsIdsAndDate.add(new String[]{alm.getPatientId(), alm.getDateSelected(), alm.getSlotTimeSelected()});
                            }
                            Log.d("MYTAGS", "My appointment list created");
//                                 add here
                            for(int i=0;i<patientsIdsAndDate.size();i++){
                                String dateS=patientsIdsAndDate.get(i)[1];
                                String timeS=patientsIdsAndDate.get(i)[2];
                                db.collection("Users").whereEqualTo("userId", patientsIdsAndDate.get(i)[0]).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if(task.isSuccessful()){
                                                    for(QueryDocumentSnapshot doc: task.getResult()){
                                                        Users mal=doc.toObject(Users.class);
                                                        patientsList.add(new AppointmentScheduleModel(mal.getName()
                                                                ,""+dateS,timeS,mal.getPhone(),mal.getImageUrl()));
                                                    }
//                                                               adapter.notifyDataSetChanged();
                                                    adapter=new AppointmentScheduleAdapter(AppointmentsSchedule.this, patientsList);
                                                    RecyclerView.LayoutManager layoutManager=new
                                                            LinearLayoutManager(AppointmentsSchedule.this, LinearLayoutManager.VERTICAL, false);
                                                    recyclerViewAppointmentSchedule.setLayoutManager(layoutManager);
                                                    recyclerViewAppointmentSchedule.setAdapter(adapter);
                                                    Log.d("MYTAGS", "appointment schedule list modified");
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MYTAGS", "Failure"+ e.getMessage());
                                            }
                                        });
                            }

                        }else {
                            Log.d("MYTAGS", "appointment schedule is not  created");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MyAppointment", "failed"+e.getMessage());
                    }
                });


      patientsList=new ArrayList<>();
//        patientsList.add(new AppointmentScheduleModel("Rekha Gupta","Date:25-03-2023","Time: 11:30 AM","9834567823",R.drawable.profile));
//        patientsList.add(new AppointmentScheduleModel("Ravi Srivastava","Date:24-03-2023","Time: 11:30 AM","8834567823",R.drawable.profile));
//        patientsList.add(new AppointmentScheduleModel("Aniket Singh","Date:25-03-2023","Time: 06:30 PM","7834567823",R.drawable.profile));


    }
}