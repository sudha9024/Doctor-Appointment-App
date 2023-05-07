package com.example.doctor.PatientHomePagePack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindYourDoctor extends AppCompatActivity {
    // 1-data
    ArrayList<FindYourDoctorModel> doctorsList;

    // 2- Adapter View
    RecyclerView recyclerView;

    //3- Adapter
    FindYourDoctorAdapter adapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    SearchView sv;
    FirebaseUser fbuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_doctor);
        fbuser= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=findViewById(R.id.recyclerView);
        doctorsList=new ArrayList<>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("cardSelected");
        Log.d("cardSelected", str);
        db.collection("Users")
                .whereEqualTo("specialisation", str)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("FindYourDoctor", document
                                        .toString());
                                Users ud=document.toObject(Users.class);
                                Log.d("FindYourDoctor",ud.getName());
                                doctorsList.add(new FindYourDoctorModel(ud.getUserId(), ud.getName(), ud.getPhone(), ud.getImageUrl()));
                            }
                                 adapter.notifyDataSetChanged();

//                            Log.d("FindYourDoctor", doctorsList.get(0).getDoctorName());
                        } else {
                            Log.d("FindYourDoctor", "Error getting documents: ", task.getException());
                        }
                    }
                });



        adapter=new FindYourDoctorAdapter(this, doctorsList);
        RecyclerView.LayoutManager layoutManager=new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        sv=findViewById(R.id.searchView);
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<FindYourDoctorModel> filteredList=new ArrayList<>();

        for(FindYourDoctorModel item: doctorsList){
            if(item.getDoctorName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "Nothing Found",Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.setFilteredList(filteredList);
        }

    }
}