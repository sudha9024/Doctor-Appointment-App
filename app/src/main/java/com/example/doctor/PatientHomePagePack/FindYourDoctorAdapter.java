package com.example.doctor.PatientHomePagePack;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class FindYourDoctorAdapter extends RecyclerView.Adapter<FindYourDoctorAdapter.FindYourDoctorViewHolder> {
    // 1- Data
    private Context context;
    private ArrayList<FindYourDoctorModel> doctorsList;

    // 2- Constructor

  public void setFilteredList(ArrayList<FindYourDoctorModel> filteredList){
      this.doctorsList=filteredList;
      notifyDataSetChanged();
  }

    public FindYourDoctorAdapter(Context context, ArrayList<FindYourDoctorModel> doctorsList) {
        this.context = context;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public FindYourDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Instantiate and Create View
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.findyourdoctor_list_item, parent, false);
        return new FindYourDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindYourDoctorViewHolder holder, int position) {
        // Display things to their widgets
        FindYourDoctorModel model= doctorsList.get(position);

        holder.doctorName.setText(model.getDoctorName());
        Glide.with(context).load(model.getDoctorImage())
                //.placeholder()
                .fitCenter()
                .into(holder.doctorImage);
        //holder.doctorImage.setImageResource(model.getDoctorImage());
        holder.doctorPhone.setText(model.getDoctorPhone());
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent in=new Intent(context, SlotBooking.class);
              in.putExtra("DoctorId",model.getDoctorId());
              context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    // 3- creating view holder
    public class FindYourDoctorViewHolder extends RecyclerView.ViewHolder{
        private ImageView doctorImage;
        private TextView doctorName, doctorPhone;

        private Button book;

        public FindYourDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage =itemView.findViewById(R.id.imageView);
            doctorName =itemView.findViewById(R.id.textView_name);
            doctorPhone =itemView.findViewById(R.id.textView_phone);
            book=itemView.findViewById(R.id.button_book);
        }
    }
}

