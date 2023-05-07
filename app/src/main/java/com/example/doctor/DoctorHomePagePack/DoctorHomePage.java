package com.example.doctor.DoctorHomePagePack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorHomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorHomePage extends Fragment {
    public CardView mypatients,appointments,mycalender,patientrequest;
    private TextView docName, docSpec;
    private ImageView Doctor_Image;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    Activity context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorHomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorHomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorHomePage newInstance(String param1, String param2) {
        DoctorHomePage fragment = new DoctorHomePage();
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
        View view =inflater.inflate(R.layout.fragment_doctor_home_page, container, false);

        return view;
    }

    public void onStart(){
        super.onStart();
        Doctor_Image=context.findViewById(R.id.Doc_img);
        docSpec=context.findViewById(R.id.docspec);
        docName=context.findViewById(R.id.docname);
        mypatients=context.findViewById(R.id.mypatients);
        appointments= context.findViewById(R.id.appointments);
        mycalender= context.findViewById(R.id.mycalender);
        patientrequest= context.findViewById(R.id.patientrequest);
        db.collection("Users").whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                Users u=doc.toObject(Users.class);
                                Glide.with(context).load(u.getImageUrl())
                                        //.placeholder()
                                        .fitCenter()
                                        .into(Doctor_Image);
                                docName.setText(u.getName());
                                docSpec.setText(u.getSpecialisation());
                            }

                        }else{
                            Log.d("MYTAGS", "unable to fetch data");
                        }
                    }
                });
        mypatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MyPatients.class);
                startActivity(i);
            }
        });
        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, AppointmentsSchedule.class);
                startActivity(i);
            }
        });
        mycalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MyCalender.class);
                startActivity(i);
            }
        });
        patientrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MyFeedbacks.class);
                startActivity(i);
            }
        });
    }
}