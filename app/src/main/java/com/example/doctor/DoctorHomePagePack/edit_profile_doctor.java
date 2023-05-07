package com.example.doctor.DoctorHomePagePack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctor.PatientHomePagePack.PatientHomePage;
import com.example.doctor.PatientHomePagePack.edit_profile_patient;
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
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class edit_profile_doctor extends AppCompatActivity {
     private Button btn_save;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
   private FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();

    private ImageView Doctor_Image;
    private String IMAGEURL1;
    private final int PICK_IMAGE_RESULT=1;
    private Uri imageUri;
    StorageReference storageReference;
    DocumentReference documentReference=db.collection("Users").document(currentUser.getUid());
    CollectionReference collectionReference= db.collection("Users");
    FirebaseStorage storage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_doctor);

        Doctor_Image = findViewById(R.id.doctorEditProfile_image);
        btn_save = findViewById(R.id.save_doctor_detail);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        TextInputEditText Doctor_name = findViewById(R.id.Edi_name);
        TextInputEditText Doctor_Phone = findViewById(R.id.Edi_phone);
        TextInputEditText Doctor_exp = findViewById(R.id.Edi_exp);
        TextInputEditText Doctor_fee = findViewById(R.id.Edi_fees);
        TextInputEditText Doctor_city = findViewById(R.id.Edi_city);
        TextInputEditText Doctor_build = findViewById(R.id.Edi_build);
        TextInputEditText Doctor_country = findViewById(R.id.Edi_country);
        TextInputEditText Doctor_street = findViewById(R.id.Edi_street);
        TextInputEditText Doctor_pincode = findViewById(R.id.Edi_pincode);
        TextInputEditText Doctor_gender=findViewById(R.id.Edi_gender);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users v=documentSnapshot.toObject(Users.class);
                Doctor_name.setText(v.getName());
                Doctor_Phone.setText(v.getPhone());
                Doctor_build.setText(v.getBuildingNo());
                Doctor_city.setText(v.getCity());
                Doctor_country.setText(v.getCountry());
                Doctor_exp.setText(v.getExperience());
                Doctor_gender.setText(v.getGender());
                Doctor_fee.setText(v.getFees());
                Doctor_pincode.setText(v.getPinCode());
                Doctor_street.setText(v.getStreet());

            }
        });


        Doctor_Image.setOnClickListener(new View.OnClickListener() {
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
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        saveImage(Doctor_name.getText().toString().trim()
                                , Doctor_Phone.getText().toString().trim(),
                                Doctor_gender.getText().toString().trim()
                                ,Doctor_exp.getText().toString().trim(),
                                Doctor_fee.getText().toString().trim(),
                                Doctor_build.getText().toString().trim(),
                                Doctor_street.getText().toString().trim(),
                                Doctor_city.getText().toString().trim(),
                                Doctor_country.getText().toString().trim(),
                                Doctor_pincode.getText().toString().trim()
                                );

                    }
                });

            }
        });
    }

        private void saveImage(String name,String phone,String gender,String exp,String fee,String building,String street,String city,String country,String pincode) {
            StorageReference filepath = storageReference.child("Doctor/" + Timestamp.now().getSeconds());
            Log.d("Image",filepath.toString());
            // uploading the image
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            IMAGEURL1=uri.toString();
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Users data=documentSnapshot.toObject(Users.class);
                                    data.setImageUrl(uri.toString());
                                    data.setName(name);
                                    data.setPhone(phone);
                                    data.setExperience(exp);
                                    data.setFees(fee);
                                    data.setBuildingNo(building);
                                    data.setStreet(street);
                                    data.setCity(city);
                                    data.setCountry(country);
                                    data.setPinCode(pincode);
                                    data.setGender(gender);

                                    Log.d("Image", IMAGEURL1);

                                    documentReference.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Log.d("ImageURL",filepath.toString());
                                            Intent intent=new Intent(edit_profile_doctor.this, DoctorHomePage.class);
                                            startActivity(intent);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(edit_profile_doctor.this, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();

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