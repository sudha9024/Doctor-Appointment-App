package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctor.R;

import java.util.ArrayList;

public class MyDoctorsAdapter extends RecyclerView.Adapter<MyDoctorsAdapter.MyDoctorsViewHolder> {
    // 1- Data
    private Context context;
    private ArrayList<MyDoctorsModel> myDoctorsList;

    // 2- Constructor


    public MyDoctorsAdapter(Context context, ArrayList<MyDoctorsModel> myDoctorsList) {
        this.context = context;
        this.myDoctorsList = myDoctorsList;
    }

    @NonNull
    @Override
    public MyDoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Instantiate and Create View
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.mydoctors_list_item, parent, false);
        return new MyDoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDoctorsViewHolder holder, int position) {
        // Display things to their widgets
        MyDoctorsModel model= myDoctorsList.get(position);
        holder.doctorName.setText(model.getDoctorName());
        Glide.with(context).load(model.getDoctorImage())
                //.placeholder()
                .fitCenter()
                .into(holder.doctorImage);
       // holder.doctorImage.setImageResource(model.getDoctorImage());
        holder.doctorSpecialization.setText(model.getDoctorSpecialization());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri u = Uri.parse("tel:" + model.getDoctorPhoneNumber());
                Intent in=new Intent(Intent.ACTION_DIAL,u);
            }
        });
        holder.feedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context,Feedback.class);
                intent.putExtra("DoctorId",model.getDoctorId());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return myDoctorsList.size();
    }

    // 3- creating view holder
    public class MyDoctorsViewHolder extends RecyclerView.ViewHolder{
        private ImageView doctorImage;
        private TextView doctorName,doctorSpecialization ,doctorPhoneNumber;

        private Button call, feedbutton;

        public MyDoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage =itemView.findViewById(R.id.imageView);
            doctorName =itemView.findViewById(R.id.textView_name);
            doctorSpecialization=itemView.findViewById(R.id.textView_specialization);
            call=itemView.findViewById(R.id.button_call);
            feedbutton=itemView.findViewById(R.id.feedback_button);
        }
    }
}

