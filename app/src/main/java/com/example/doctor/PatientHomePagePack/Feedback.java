package com.example.doctor.PatientHomePagePack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    EditText Pname,text_f;
    TextView comment;
    RatingBar rating;
    Map<String,Object> data=new HashMap<>();
    String Patient_Name,Message;
    Float rate;

    Button btnSend;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent = getIntent();
        String str = intent.getStringExtra("DoctorId");
        FeedbackModel feed= new FeedbackModel();
        Pname=findViewById(R.id.Patientname);
        text_f=findViewById(R.id.feed_text);
        rating=findViewById(R.id.rbStars);
        comment=findViewById(R.id.tvFeedback);
        btnSend=findViewById(R.id.btnSend);
        Log.d("MYTAGS", "hiii");
        Log.d("MYTAGS",Pname.getText().toString());

        Patient_Name=Pname.getText().toString();
        Message=text_f.getText().toString();
        rate=(float)rating.getRating();
//        feed.setP_name(Patient_Name);
//
//        feed.setRatings(rate);
//        feed.setText(Message);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0)
                {
                    comment.setText("Very Dissatisfied");
                }
                else if(rating==1)
                {
                    comment.setText("Dissatisfied");
                }
                else if(rating==2)
                {
                    comment.setText("OK");
                }
                else if(rating==4)
                {
                    comment.setText("Satisfied");
                }
                else if(rating==5)
                {
                    comment.setText("Very Satisfied");
                }

            }
        });



          btnSend.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Patient_Name=Pname.getText().toString();
                  Message=text_f.getText().toString();
                  rate=(float)rating.getRating();
//                  feed.setP_name(Patient_Name);
//
//                  feed.setRatings(rate);
//                  feed.setText(text_f.getText().toString());
                  data.put("P_Name",Patient_Name);
                  data.put("Rating",rate);
                  data.put("Message",Message);
                  db.collection("FeedBack").document(str).collection("MyFeedbacks").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                              @Override
                              public void onComplete(@NonNull Task<DocumentReference> task) {
                                  Log.d("TAG", "Feedback Submitted");
                              }
                          })
                          .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Log.w("TAG", "Error Occured", e);
                              }
                          });
                  Toast.makeText(Feedback.this, "Feedback Sent", Toast.LENGTH_SHORT).show();
                  Intent intent=new Intent(Feedback.this,PatientHomePage.class);
                  startActivity(intent);
              }
          });
}
}