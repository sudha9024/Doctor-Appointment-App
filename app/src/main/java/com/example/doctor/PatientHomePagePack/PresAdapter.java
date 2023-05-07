package com.example.doctor.PatientHomePagePack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctor.Fullimage;
import com.example.doctor.R;

import java.text.BreakIterator;
import java.util.ArrayList;

public class PresAdapter extends RecyclerView.Adapter<PresAdapter.PrescriptionViewHolder>{
    ArrayList<PrescriptionModel> presList;
    private Context context;
    boolean isImageFitToScreen;
    public PresAdapter(Context context,ArrayList<PrescriptionModel> presList){
        this.context=context;
        this.presList=presList;
    }
    @NonNull
    @Override
    public  PresAdapter.PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.presitem,parent,false);
        return new PresAdapter.PrescriptionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PrescriptionModel model = presList.get(position);
        holder.doctorName.setText(model.getDoctorName());
        holder.date.setText(model.getDate());

        Glide.with(context).load(model.getImageUrl())
                //.placeholder()
                .fitCenter()
                .into(holder.image);
//
//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    holder.image.setLayoutParams(new CardView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    holder.image.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    holder.image.setLayoutParams(new CardView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });


//
//
//
//
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Fullimage.class);
                intent.putExtra("imageUrl", model.getImageUrl());
               context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return presList.size();
    }
    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, date;
        ImageView image;
        RelativeLayout item;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.textView_name);
            date = itemView.findViewById(R.id.textView_Date);
            image=itemView.findViewById(R.id.imageView);
            item = itemView.findViewById(R.id.item);

        }
        }
    }