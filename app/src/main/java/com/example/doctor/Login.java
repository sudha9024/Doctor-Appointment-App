package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctor.DoctorHomePagePack.DoctorFrontPage;
import com.example.doctor.PatientHomePagePack.PatientHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail,editTextPassword;
    Button buttonLog;
    TextView textView;
    FirebaseAuth mAuth;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            FirebaseUser currentUser=mAuth.getCurrentUser();
            db.collection("Users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Users u=documentSnapshot.toObject(Users.class);
                    Log.d("Login","Data is successfully fetched from firestore");
                    if(u.getRole().equals("Doctor")){
                        Intent i = new Intent(Login.this, DoctorFrontPage.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Intent i = new Intent(Login.this, PatientHomePage.class);
                        startActivity(i);
                        finish();
                    }
                }
            });

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        buttonLog=findViewById(R.id.login);
        mAuth= FirebaseAuth.getInstance();
        textView=findViewById(R.id.register_btn);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Details.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email,password;
                email=String.valueOf(editTextEmail.getText());
                password=String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser=mAuth.getCurrentUser();
                                    db.collection("Users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Users u=documentSnapshot.toObject(Users.class);
                                            Log.d("Login"," user is not currently logged in Data is successfully fetched from firestore");
                                            if(u.getRole()=="Doctor"){
                                                Intent i = new Intent(Login.this, DoctorFrontPage.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                Intent i = new Intent(Login.this, PatientHomePage.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}