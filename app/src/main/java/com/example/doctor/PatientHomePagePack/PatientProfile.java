package com.example.doctor.PatientHomePagePack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.Period;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfile extends Fragment {
    private TextView pName, pAge,pBloodGroup,pWeight,pPhone, pEmail;
    private ImageView pImage;
    private Button editProfile;
    FragmentActivity context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfile newInstance(String param1, String param2) {
        PatientProfile fragment = new PatientProfile();
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
        return inflater.inflate(R.layout.fragment_patient_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        pAge=context.findViewById(R.id.patientProfileAge);
        pName=context.findViewById(R.id.patientProfileName);
        pBloodGroup=context.findViewById(R.id.patientProfileBloodGroup);
        pWeight=context.findViewById(R.id.patientProfileWeight);
        pPhone=context.findViewById(R.id.patientProfilePhoneNo);
        pEmail=context.findViewById(R.id.patientProfileEmail);
        pImage=context.findViewById(R.id.patientProfileImage);
        editProfile=context.findViewById(R.id.patientProfileEditBtn);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Users u=documentSnapshot.toObject(Users.class);
//                        String str= u.getDob(), nstr="";
//                        char ch;
//
//                        for (int i=0; i<str.length(); i++)
//                        {
//                            ch= str.charAt(i); //extracts each character
//                            nstr= ch+nstr; //adds each character in front of the existing string
//                        }
//                        LocalDate dob = LocalDate.parse(nstr.replace("/","-"));
//                        Log.d("MYTAGS",""+dob);
//                        LocalDate current=LocalDate.now();
//                        pAge.setText( Period.between(dob, current).getYears());
//                        Glide.with(getActivity()).load(u.getImageUrl())
//                                .placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.stat_notify_error).into(pImage);
                        Glide.with(getActivity()).load(u.getImageUrl())
                                //.placeholder()
                                .fitCenter()
                                .into(pImage);
                        pAge.setText(u.getDob());
                        pBloodGroup.setText(u.getBloodType());
                        pWeight.setText(u.getWeight());
                        pName.setText(u.getName());
                        pEmail.setText(u.getEmail());
                        pPhone.setText(u.getPhone());
                        editProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent in=new Intent(getActivity(), edit_profile_patient.class);
                                startActivity(in);
                            }
                        });
                    }
                });

    }
}