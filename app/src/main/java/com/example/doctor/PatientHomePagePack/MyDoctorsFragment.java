package com.example.doctor.PatientHomePagePack;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.AppointmentListModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDoctorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDoctorsFragment extends Fragment {
    private ArrayList<MyDoctorsModel> doctorsList;

    // 2- Adapter View
    private RecyclerView recyclerViewMyDoctors;

    //3- Adapter
    private MyDoctorsAdapter adapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    FragmentActivity context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyDoctorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDoctorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDoctorsFragment newInstance(String param1, String param2) {
        MyDoctorsFragment fragment = new MyDoctorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        return inflater.inflate(R.layout.fragment_my_doctors, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewMyDoctors=context.findViewById(R.id.recyclerViewMyDoctors);
        ArrayList<String> doctorIds=new ArrayList<>();
        db.collection("PatientsAppointment").document(""+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Doctors").
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                AppointmentListModel alm = doc.toObject(AppointmentListModel.class);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate d = LocalDate.parse(alm.getDateSelected(), formatter);
                                int result = d.compareTo(LocalDate.now());

                                if (result <=0) {
                                    doctorIds.add(alm.getDoctorId());
                                }

                            }
                            // add here
                            doctorsList = new ArrayList<>();
                            for (int i = 0; i < doctorIds.size(); i++) {

                                db.collection("Users").whereEqualTo("userId", doctorIds.get(i)).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                                        Users mal = doc.toObject(Users.class);
                                                        doctorsList.add(new MyDoctorsModel(mal.getUserId(), mal.getName(), mal.getSpecialisation(), mal.getPhone(), mal.getImageUrl()));
                                                    }
//                                                    adapter.notifyDataSetChanged();
                                                    adapter = new MyDoctorsAdapter(context, doctorsList);
                                                    RecyclerView.LayoutManager layoutManager = new
                                                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                                    recyclerViewMyDoctors.setLayoutManager(layoutManager);
                                                    recyclerViewMyDoctors.setAdapter(adapter);
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MYTAGS", "failed" + e.getMessage());
                                            }
                                        });
                            }

                        } else {
                            Log.d("MYTAGS", "failed .. task unsuccessful");
                        }

                    }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MYTAGS", "failed .. task unsuccessful"+ e.getMessage());
                    }
                });


//        doctorsList.add(new MyDoctorsModel("Ravi Yadav","Dermatologist","9812341398",R.drawable.profile));
//        doctorsList.add(new MyDoctorsModel("Sunita Singh","ENT","8912341398",R.drawable.profile));


    }
}