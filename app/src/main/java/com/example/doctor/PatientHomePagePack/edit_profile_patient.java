package com.example.doctor.PatientHomePagePack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class edit_profile_patient extends AppCompatActivity {

    ImageView Patient_Image;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    TextInputEditText Patient_name,Patient_Phone,Patient_Weight;
    Button btn_save;
    int PICK_IMAGE_RESULT = 1;
    private String IMAGEURL;
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    Uri imageUri,Profile_Image;
    StorageReference storageReference;
    DocumentReference documentReference=db.collection("Users").document(currentUser.getUid());
    CollectionReference collectionReference= db.collection("Users");
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_patient);
        Patient_Image = findViewById(R.id.Patient_image);
        btn_save = findViewById(R.id.saveButton);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Patient_name=findViewById(R.id.name);
        Patient_Phone=findViewById(R.id.phone);
        Patient_Weight=findViewById(R.id.weight);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users v=documentSnapshot.toObject(Users.class);
                Patient_name.setText(v.getName());
                Patient_Phone.setText(v.getPhone());
                Patient_Weight.setText(v.getWeight());
            }
        });
        FirebaseUser currenUser=FirebaseAuth.getInstance().getCurrentUser();

        Patient_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_RESULT);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveImage(Patient_name.getText().toString().trim()
                        ,Patient_Weight.getText().toString().trim()
                        ,Patient_Phone.getText().toString().trim());

            }
        });

    }

    private void saveImage(String name,String weight,String phone) {
        StorageReference filepath = storageReference.child("patient/" + Timestamp.now().getSeconds());
        Log.d("Image",filepath.toString());
        // uploading the image
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        IMAGEURL=uri.toString();
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Users data=documentSnapshot.toObject(Users.class);
                                data.setImageUrl(uri.toString());
                                data.setName(name);
                                data.setPhone(phone);
                                data.setWeight(weight);

                                Log.d("Image", IMAGEURL);

                                documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("ImageURL",filepath.toString());
                                        Intent intent=new Intent(edit_profile_patient.this,PatientHomePage.class);
                                        startActivity(intent);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(edit_profile_patient.this, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_RESULT && resultCode == RESULT_OK && data != null && data.getData() != null) {

            if(data!=null){
                imageUri=data.getData();
            }
        }
    }

}