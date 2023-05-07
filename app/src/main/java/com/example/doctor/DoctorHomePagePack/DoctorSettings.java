package com.example.doctor.DoctorHomePagePack;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorSettings extends Fragment {
    Activity context;
    ArrayList<DoctorSettingsModel> settingsList;
    RecyclerView recyclerViewSettings;
    DoctorSettingsAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorSettings newInstance(String param1, String param2) {
        DoctorSettings fragment = new DoctorSettings();
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
        return inflater.inflate(R.layout.fragment_doctor_settings, container, false);
    }
    public void onStart(){
        super.onStart();
        recyclerViewSettings=context.findViewById(R.id.recyclerViewSettings);
        settingsList=new ArrayList<>();
        settingsList.add(new DoctorSettingsModel(R.drawable.followandinvite,"Follow and Invite"));
        settingsList.add(new DoctorSettingsModel(R.drawable.notification,"Notifications"));
        settingsList.add(new DoctorSettingsModel(R.drawable.privacy,"Privacy"));
        settingsList.add(new DoctorSettingsModel(R.drawable.help,"Help"));
        settingsList.add(new DoctorSettingsModel(R.drawable.about,"About"));
        settingsList.add(new DoctorSettingsModel(R.drawable.theme,"Theme"));
        settingsList.add(new DoctorSettingsModel(R.drawable.addaccount,"Add Account"));
        settingsList.add(new DoctorSettingsModel(R.drawable.logout,"Log Out"));
        adapter=new DoctorSettingsAdapter(context, settingsList);
        RecyclerView.LayoutManager layoutManager=new
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerViewSettings.setLayoutManager(layoutManager);
        recyclerViewSettings.setAdapter(adapter);

    }
}