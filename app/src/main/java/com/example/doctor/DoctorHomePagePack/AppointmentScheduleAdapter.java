package com.example.doctor.DoctorHomePagePack;

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

public class AppointmentScheduleAdapter extends RecyclerView.Adapter<AppointmentScheduleAdapter.AppointmentScheduleViewHolder> {
    // 1- Data
    private Context context;
    private ArrayList<AppointmentScheduleModel> patientsList;

    // 2- Constructor


    public AppointmentScheduleAdapter(Context context, ArrayList<AppointmentScheduleModel> patientsList) {
        this.context = context;
        this.patientsList = patientsList;
    }

    @NonNull
    @Override
    public AppointmentScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Instantiate and Create View
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.appointmentschedule_list_item, parent, false);
        return new AppointmentScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentScheduleViewHolder holder, int position) {
        // Display things to their widgets
        AppointmentScheduleModel model=patientsList.get(position);
        holder.patientName.setText(model.getPatientName());
        Glide.with(context).load(model.getPatientImage())
                //.placeholder()
                .fitCenter()
                .into(holder.patientImage);
       // holder.patientImage.setImageResource(model.getPatientImage());
        holder.patientDate.setText(model.getPatientDate());
        holder.patientTime.setText(model.getPatientTime());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent in=new Intent();
               in.setAction(Intent.ACTION_DIAL);
               in.setData(Uri.parse("tel: "+model.getPatientPhone()));
               context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }

    // 3- creating view holder
    public class AppointmentScheduleViewHolder extends RecyclerView.ViewHolder{
        private ImageView patientImage;
        private TextView patientName, patientDate, patientTime;

        private Button call;

        public AppointmentScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            patientImage=itemView.findViewById(R.id.imageView);
            patientName=itemView.findViewById(R.id.textView_name);
            patientDate=itemView.findViewById(R.id.textView_date);
            patientTime=itemView.findViewById(R.id.textView_time);
            call=itemView.findViewById(R.id.button_call);
        }
    }
}

