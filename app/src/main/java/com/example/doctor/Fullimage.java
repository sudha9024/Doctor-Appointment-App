package com.example.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.doctor.PatientHomePagePack.PrescriptionModel;
import com.google.firebase.auth.FirebaseUser;

public class Fullimage extends AppCompatActivity {
    private ImageView i;
    FirebaseUser current;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullimage);
        Intent in=getIntent();
        String imgUrl=in.getStringExtra("imageUrl");
        i=findViewById(R.id.fullImage);
        PrescriptionModel pres=new PrescriptionModel();
        Glide.with(getApplicationContext()).load(imgUrl)
                //.placeholder()
                .fitCenter()
                .into(i);

    }
}