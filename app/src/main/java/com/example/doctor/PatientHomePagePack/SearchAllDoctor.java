package com.example.doctor.PatientHomePagePack;

import android.annotation.SuppressLint;
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

public class SearchAllDoctor extends AppCompatActivity {
    ArrayList<SearchAllDoctorModel> doctorsList;

    // 2- Adapter View
    RecyclerView recyclerView;

    //3- Adapter
    SearchAllDoctorAdapter adapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    SearchView sv;
    FirebaseUser fbuser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all_doctor);

        fbuser= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=findViewById(R.id.recyclerView);
        doctorsList=new ArrayList<>();
//        Intent intent = getIntent();
//        String str = intent.getStringExtra("cardSelected");
//        Log.d("cardSelected", str);
        db.collection("Users")
                .whereEqualTo("role", "Doctor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("SearchAllDoctor", document
                                        .toString());
                                Users ud=document.toObject(Users.class);
                                Log.d("SearchAllDoctor",ud.getName());
                                doctorsList.add(new SearchAllDoctorModel(ud.getName(), ud.getPhone(), ud.getImageUrl(),ud.getUserId()));
                            }
                            adapter.notifyDataSetChanged();

                            Log.d("SearchAllDoctor", doctorsList.get(0).getDoctorName());
                        } else {
                            Log.d("SearchAllDoctor", "Error getting documents: ", task.getException());
                        }
                    }
                });



        adapter=new SearchAllDoctorAdapter(this, doctorsList);
        RecyclerView.LayoutManager layoutManager=new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        sv=findViewById(R.id.doctorsearch);
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
        ArrayList<SearchAllDoctorModel> filteredList=new ArrayList<>();

        for(SearchAllDoctorModel item: doctorsList){
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