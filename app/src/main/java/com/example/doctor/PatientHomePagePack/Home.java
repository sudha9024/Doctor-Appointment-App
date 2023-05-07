package com.example.doctor.PatientHomePagePack;

//import static com.example.doctor.DoctorHomePagePack.MyFeedbacks.topdoctorslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.AverageRating;
import com.example.doctor.DoctorHomePagePack.MyFeedbacks;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home   extends Fragment implements MyFeedbacks.topDoctors{
    private CardView cardiologist, dentist, ophthalmologist, psychologist, orthopedic, dermatologist, neurologist;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private SearchView searchView;
    ArrayList<DoctorListModel> arrdlist=new ArrayList<>();
    ArrayList<String> docId=new ArrayList<>();
    FragmentActivity context;
    //Map for Topdoctors
    Map<String,Double>TopDoctorsList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    public void onStart(){
        super.onStart();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        DoctorListAdapter adapter;
        cardiologist=context.findViewById(R.id.card_cardiologist);
        psychologist=context.findViewById(R.id.card_psychologist);
        dentist=context.findViewById(R.id.card_dentist);
        dermatologist=context.findViewById(R.id.card_dermatologist);
        orthopedic=context.findViewById(R.id.card_orthopedic);
        ophthalmologist=context.findViewById(R.id.card_opthalmologist);
        neurologist=context.findViewById(R.id.card_neurologist);
        searchView=context.findViewById(R.id.doctorssearch);

        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Cardiologist");
                startActivity(i);
            }
        });
        psychologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Psychologist");
                startActivity(i);
            }
        });
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Dentist");
                startActivity(i);
            }
        });
        dermatologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Dermatologist");
                startActivity(i);
            }
        });
        neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Neurologist");
                startActivity(i);
            }
        });
        ophthalmologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Ophthalmologist");
                startActivity(i);
            }
        });
        orthopedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),FindYourDoctor.class);
                i.putExtra("cardSelected", "Orthopedic");
                startActivity(i);
            }
        });
        adapter=new DoctorListAdapter(context,arrdlist);
//        Log.d("Check", ""+topdoctorslist.size());
//        Log.d("Check", String.valueOf(topdoctorslist.get("agPxLEgsh1bSy1uuQImNhICb0Uz2")));
        db.collection("AverageRating").orderBy("avgRating", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc:task.getResult()){
                    AverageRating avg=doc.toObject(AverageRating.class);
                    docId.add(avg.getDocId());

                }
                Log.d("Num", String.valueOf(docId.size()));
                for(int i=0;i<docId.size();i++){


                    db.collection("Users").whereEqualTo("userId", docId.get(i)).
                            get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {


                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot doc: task.getResult()){
                                Users uid= doc.toObject(Users.class);
                                arrdlist.add(new DoctorListModel(uid.getImageUrl(),uid.getName(),uid.getSpecialisation(),uid.getExperience()));
                            }

                            adapter.notifyDataSetChanged();
                        }
                    });
                }


            }
        });

        RecyclerView recyclerView=context.findViewById(R.id.doctorrecyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        arrdlist.add(new DoctorListModel(R.drawable.img,"Arjun Pandey","Cardiologist","3 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Nikita Sinha","Dermatologist","5 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_2,"Ankit Dubey","Neurologist","2 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Aparna Jain","Pulmonogist","4 Years"));
//        arrdlist.add(new DoctorListModel(R.drawable.img_1,"Aparna Jain","Pulmonogist","4 Years"));
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,SearchAllDoctor.class);
                startActivity(i);
            }
        });

//        topdocfunc(TopDoctorsList);
    }

    @Override
    public void topdocfunc(Map<String, Double> topdoctorslist) {
        TopDoctorsList=topdoctorslist;
//        TopDoctorsList=sortByValue(TopDoctorsList);
//        for (Map.Entry<String, Double> en : TopDoctorsList.entrySet()) {
//            System.out.println("Key = " + en.getKey() +
//                    ", Value = " + en.getValue());


    }
//    public static Map<String, Double> sortByValue(Map<String, Double> hm)
//    {
//        // Create a list from elements of HashMap
//        List<Map.Entry<String, Double> > list =
//                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());
//
//        // Sort the list
//        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
////            @Override
////            public int compare(Map.Entry<String, Double> o1,
////                               Map.Entry<String, Double> o2) {
////                return (o1.getValue()).compareTo(o2.getValue());
////            }
//
//            @Override
//            public int compare(Map.Entry<String, Double> stringDoubleEntry, Map.Entry<String, Double> t1) {
//                return (stringDoubleEntry.getValue()).compareTo(t1.getValue());
//            }
//
//            @Override
//            public Comparator<Map.Entry<String, Double>> reversed() {
//                return Comparator.super.reversed();
//            }
//
//
//        });
//
//        // put data from sorted list to hashmap
//        Map<String, Double> temp = new LinkedHashMap<String, Double>();
//        for (Map.Entry<String, Double> aa : list) {
//            temp.put(aa.getKey(), aa.getValue());
//        }
//        return temp;
//    }

}