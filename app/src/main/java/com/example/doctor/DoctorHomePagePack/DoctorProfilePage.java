package com.example.doctor.DoctorHomePagePack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.doctor.PatientHomePagePack.edit_profile_patient;
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
 * Use the {@link DoctorProfilePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorProfilePage extends Fragment {
    private TextView dName, dAge,dSpecialisation,dExperience,dPhone, dEmail,dAddress;
    private ImageView dcotor_Image;
    private Button editProfile;
    FragmentActivity context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorProfilePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorProfilePage.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorProfilePage newInstance(String param1, String param2) {
        DoctorProfilePage fragment = new DoctorProfilePage();
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
        return inflater.inflate(R.layout.fragment_doctor_profile_page, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();


        dAge=context.findViewById(R.id.doctorProfileAge);
        dName=context.findViewById(R.id.doctorProfileName);
        dExperience=context.findViewById(R.id.doctorProfileExperience);
        dSpecialisation=context.findViewById(R.id.doctorProfileSpecialisation);
        dPhone=context.findViewById(R.id.doctorProfilePhone);
        dEmail=context.findViewById(R.id.doctorProfileEmail);
        dcotor_Image=context.findViewById(R.id.doctorProfileImage);
        editProfile=context.findViewById(R.id.doctorProfileEditProfile);
        dAddress=context.findViewById(R.id.doctorProfileAddress);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Users u=documentSnapshot.toObject(Users.class);
                        String str= u.getDob(), nstr="";
//                        char ch;
//
//                        for (int i=0; i<str.length(); i++)
//                        {
//                            ch= str.charAt(i); //extracts each character
//                            nstr= ch+nstr; //adds each character in front of the existing string
//                        }
//                        LocalDate dob = LocalDate.parse(nstr.replace("/","-"));
//                        LocalDate current=LocalDate.now();
//                        dAge.setText( Period.between(dob, current).getYears());
                        Glide.with(context).load(u.getImageUrl())
                                //.placeholder()
                                .fitCenter()
                                .into(dcotor_Image);
                        dAge.setText(u.getDob());
                        dSpecialisation.setText(u.getSpecialisation());
                        dExperience.setText(u.getExperience());
                        dName.setText(u.getName());
                        dEmail.setText(u.getEmail());
                        dPhone.setText(u.getPhone());
                        dAddress.setText(u.getBuildingNo()+" "+u.getStreet()+" "+u.getCity()+" "+u.getCountry()+" "+u.getPinCode());
                        editProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent in=new Intent(context, edit_profile_doctor.class);
                                startActivity(in);
                            }
                        });
                    }
                });

    }
}