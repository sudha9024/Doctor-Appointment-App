package com.example.doctor.PatientHomePagePack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAppointmentsFragment extends Fragment {
     private ArrayList<MyAppointmentModel> doctorsList;

    // 2- Adapter View
    private RecyclerView recyclerViewMyAppointment;

    //3- Adapter
    private MyAppointmentAdapter adapter;
    FragmentActivity context;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAppointmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAppointmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAppointmentsFragment newInstance(String param1, String param2) {
        MyAppointmentsFragment fragment = new MyAppointmentsFragment();
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
        return inflater.inflate(R.layout.fragment_my_appointments, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewMyAppointment=context.findViewById(R.id.recyclerViewMyAppointment);

        doctorsList=new ArrayList<>();
        ArrayList<String[] > doctorIdsAndDate=new ArrayList<>();
        db.collection("PatientsAppointment").document(""+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("Doctors").
                get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                AppointmentListModel alm = doc.toObject(AppointmentListModel.class);
                                doctorIdsAndDate.add(new String[]{alm.getDoctorId(), alm.getDateSelected(), alm.getSlotTimeSelected()});
                            }
                            Log.d("MYTAGS", "My appointment list created");
//                                 add here
                                for(int i=0;i<doctorIdsAndDate.size();i++){
                                            String docId=doctorIdsAndDate.get(i)[0];
                                            String dateS=doctorIdsAndDate.get(i)[1];
                                            String timeS=doctorIdsAndDate.get(i)[2];
                                            db.collection("Users").whereEqualTo("userId", doctorIdsAndDate.get(i)[0]).get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()){
                                                                for(QueryDocumentSnapshot doc: task.getResult()){
                                                                    Users mal=doc.toObject(Users.class);
                                                                    doctorsList.add(new MyAppointmentModel(docId,mal.getName()
                                                                            ,dateS+"("+timeS+")",mal.getImageUrl()));
                                                                }
//                                                               adapter.notifyDataSetChanged();
                                                                adapter=new MyAppointmentAdapter(context, doctorsList);
                                                                RecyclerView.LayoutManager layoutManager=new
                                                                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                                                recyclerViewMyAppointment.setLayoutManager(layoutManager);
                                                                recyclerViewMyAppointment.setAdapter(adapter);
                                                                Log.d("MYTAGS", "My appointment list modified");
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
                            Log.d("MYTAGS", "My appointment list is not  created");
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MyAppointment", "failed"+e.getMessage());
                    }
                });


//        doctorsList.add(new MyAppointmentModel("Akash Singh", "24-03-2023(10:30 AM)",R.drawable.profile));
//        doctorsList.add(new MyAppointmentModel("Nikita Singh", "25-03-2023(11:30 AM)",R.drawable.profile));


    }
}