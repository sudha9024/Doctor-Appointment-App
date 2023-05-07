package com.example.doctor.PatientHomePagePack;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.Details;
import com.example.doctor.R;
import com.example.doctor.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrescriptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentActivity context;


    ArrayList<PrescriptionModel> presList;
    int PICK_IMAGE_REQUEST = 1;
    // 2- Adapter View
    RecyclerView recyclerView;
    private ImageView addPhotoButton,imageView;

    //3- Adapter
    PresAdapter adapter;
    FloatingActionButton btndialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageReference;
    private ActivityResultLauncher<Intent> mGetImageLauncher;
    FirebaseUser current=FirebaseAuth.getInstance().getCurrentUser();

    Bitmap bitmap;
    private Uri imageUri;
    CollectionReference collectionReference=db.collection("Prescription").document(current.getUid()).collection("PatientPrescriptions");
    ArrayList<Uri> mArrayUri;
    int position = 0;

    public PrescriptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrescriptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrescriptionsFragment newInstance(String param1, String param2) {
        PrescriptionsFragment fragment = new PrescriptionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    ActivityResultLauncher<Intent> mPickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    // Handle the image data here
                    Intent data = result.getData();
                    imageUri = data.getData();
                    imageView.setImageURI(imageUri);
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();

        View view = inflater.inflate(R.layout.fragment_prescriptions, container, false);

        // Initialize the ActivityResultLauncher for image selection
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        recyclerView = context.findViewById(R.id.recyclerView);
        btndialog = context.findViewById(R.id.btn_dialogbox);
        Log.d("PatientId", "helloooooo");


        btndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialogboxprescription);
                EditText edtName = dialog.findViewById(R.id.name);
                EditText edtDate = dialog.findViewById(R.id.date);
                addPhotoButton = (ImageView) dialog.findViewById(R.id.postCameraButton);
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                imageView = dialog.findViewById(R.id.post_imageView);
                Button btnsave = dialog.findViewById(R.id.btnSave);
                mArrayUri = new ArrayList<Uri>();

                Log.d("PatientId", "HELLO");

                edtDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // on below line we are creating a variable for date picker dialog.
                        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        },

                                year, month, day);
                        datePickerDialog.show();
                    }
                });



                addPhotoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // initialising intent
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        mPickImageLauncher.launch(intent);


                    }

                });

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = "", date = "";
                        if (!edtName.getText().toString().equals("")) {
                            name = edtName.getText().toString();
                        } else {
                            Toast.makeText(context, "please enter the name of the doctor", Toast.LENGTH_SHORT).show();
                        }

                        if (!edtDate.getText().toString().equals("")) {
                            date = edtDate.getText().toString();
                        } else {
                            Toast.makeText(context, "please enter date", Toast.LENGTH_SHORT).show();
                        }


                        StorageReference filepath = storageReference.child("images/" + Timestamp.now().getSeconds());
                        Log.d("Image",filepath.toString());
                        // uploading the image
                        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        PrescriptionModel data=new PrescriptionModel();
                                        data.setImageUrl(imageUrl);
                                        data.setDoctorName(edtName.getText().toString().trim());
                                        data.setDate(edtDate.getText().toString().trim());
                                        Log.d("Image", String.valueOf(imageUri));

                                        collectionReference.add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("ImageURL",filepath.toString());
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                        Log.d("MYTAG","NOt FirebaseStore");




                        adapter.notifyItemInserted(presList.size() - 1);
                        recyclerView.scrollToPosition(presList.size() - 1);
                        dialog.dismiss();

                    }



                });
                dialog.show();

            }

        });


        presList = new ArrayList<>();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        PrescriptionModel pres=doc.toObject(PrescriptionModel.class);
                        presList.add(new PrescriptionModel(pres.getImageUrl(), pres.getDoctorName(), pres.getDate()));
                    }
                    adapter = new PresAdapter(context, presList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                }
            }
        });



}


}