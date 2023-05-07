package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctor.R;
import com.example.doctor.SlotModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.MyAppointmentViewHolder> {
    // 1- Data
    private Context context;
    private ArrayList<MyAppointmentModel> doctorsList;

    // 2- Constructor


    public MyAppointmentAdapter(Context context, ArrayList<MyAppointmentModel> doctorsList) {
        this.context = context;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public MyAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Instantiate and Create View
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.myappointment_list_item, parent, false);
        return new MyAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppointmentViewHolder holder, int position) {
        // Display things to their widgets
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        MyAppointmentModel model= doctorsList.get(position);
        String docId=model.getDoctorId();
        holder.doctorName.setText(model.getDoctorName());
        Glide.with(context).load(model.getDoctorImage())
                //.placeholder()
                .fitCenter()
                .into(holder.doctorImage);
      //  holder.doctorImage.setImageResource(model.getDoctorImage());


        holder.appointmentTime.setText(model.getAppointmentTime());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  dateS=model.getAppointmentTime().substring(0,10);
                String   timeS=model.getAppointmentTime().substring(11,16);
                Log.d("MYTAGS",dateS+" "+ timeS);

                db.collection("DoctorSlots").document(docId)
                        .collection("Dates").document(dateS).
                        collection("Slots").document(timeS).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                  DocumentSnapshot doc=task.getResult();
                                    SlotModel s=doc.toObject(SlotModel.class);
                                    int count=s.getNumberOfPatients();


                                    db.collection("DoctorSlots").document(docId)
                                            .collection("Dates").document(dateS).
                                            collection("Slots").document(timeS)
                                            .update("numberOfPatients", count-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("MYTAGS", "count of patients successfully updated");
                                                    //  add here

                                                    db.collection("DoctorAppointment").document(docId)
                                                            .collection("Patients").document(FirebaseAuth.getInstance().getCurrentUser().getUid()+" "+dateS)
                                                            .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Log.d("MYTAGS", "doctor appointment se successfully deleted");
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d("MYTAGS", "doctor appointment se Unsuccessfully deletion");
                                                                }
                                                            });
                                                    db.collection("PatientsAppointment").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                            .collection("Doctors").document(docId+" "+dateS).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Log.d("MYTAGS", "patient appointment se successfully deleted");
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d("MYTAGS", "patient  appointment se Unsuccessfully deletion");
                                                                }
                                                            });

                                                    notifyDataSetChanged();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("MYTAGS", "count of patients not updated");
                                                }
                                            });
                                }else{
                                    // failed
                                    Log.d("MYTAGS", "updation failed");
                                }
                            }
                        });

                Toast.makeText(context.getApplicationContext(), "Appointment Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    // 3- creating view holder
    public class MyAppointmentViewHolder extends RecyclerView.ViewHolder{
        private ImageView doctorImage;
        private TextView doctorName, appointmentTime;

        private Button cancel;

        public MyAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage =itemView.findViewById(R.id.imageView);
            doctorName =itemView.findViewById(R.id.textView_name);
            appointmentTime =itemView.findViewById(R.id.textView_appointmentTime);
            cancel =itemView.findViewById(R.id.button_cancel);
        }
    }
}

