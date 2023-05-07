package com.example.doctor.DoctorHomePagePack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.AverageRating;
import com.example.doctor.PatientHomePagePack.FindYourDoctorAdapter;
import com.example.doctor.PatientHomePagePack.FindYourDoctorModel;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyFeedbacks extends AppCompatActivity {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    ArrayList<MyFeedbackModel> myfeedbacksList;
    RecyclerView recyclerView;
    //HashMap of TopDoctor
//    public  static Map<String,Double> topdoctorslist=new HashMap<>();
    Double sum=0.0;
    Double numoffeedback;
    //3- Adapter
    private MyFeedbackAdapter adapter;

    public interface topDoctors{
        void topdocfunc(Map<String,Double> topdoctorslist);
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feedbacks);
        AverageRating averageRating=new AverageRating();
        averageRating.setDocId(FirebaseAuth.getInstance().getCurrentUser().getUid());

        recyclerView=findViewById(R.id.recyclerView);
        myfeedbacksList=new ArrayList<>();
        Log.d("MYTAGS", "inside");
        Intent intent = getIntent();
        String str = intent.getStringExtra("DoctorId");
        db.collection("FeedBack").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MyFeedbacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    sum=0.0;
                    Log.d("MyTags  inside", "inside");
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        Map<String,Object> data=doc.getData();
                        Log.d("MyTags  inside", String.valueOf(doc.getData()));
                        if(data!=null){
//                            Map<String,Object> mapfeild=(Map<String,Object>)data.get("data");

                           // Log.d("MyTags",data.get("Rating").getClass().getSimpleName());
                            myfeedbacksList.add(new MyFeedbackModel((String) data.get("P_Name"), (String) data.get("Message"),(Double) data.get("Rating")));
                            sum+=(Double) data.get("Rating");
                            Log.d("MyNote", String.valueOf(sum));

                        }

                    }
                    numoffeedback=(double) myfeedbacksList.size();
                    averageRating.setAvgRating(sum/numoffeedback);

//                    Log.d("MyNote", String.valueOf(sum/numoffeedback));
                    adapter.notifyDataSetChanged();
//                    topdoctorslist.put(str,(sum/numoffeedback));
//                    Log.d("Chee", String.valueOf(topdoctorslist.get(str)));


                    db.collection("AverageRating").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .set(averageRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d("MyNote", "Successful");
                                    }
                                }
                            });


                }

            }
        });

        adapter=new MyFeedbackAdapter(MyFeedbacks.this,myfeedbacksList);
        RecyclerView.LayoutManager layoutManager=new
                LinearLayoutManager(MyFeedbacks.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}