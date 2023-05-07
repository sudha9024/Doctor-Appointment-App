package com.example.doctor.PatientHomePagePack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctor.R;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {
    Context context;
    ArrayList<DoctorListModel> arrdoctorList;
    DoctorListAdapter(Context context, ArrayList<DoctorListModel> arrdoctorList){
        this.context=context;
        this.arrdoctorList=arrdoctorList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.doctorlist,parent,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(arrdoctorList.get(position).img).fitCenter().into(holder.img);
//         holder.img.setImageResource(arrdoctorList.get(position).img);
        holder.name.setText(arrdoctorList.get(position).name);
        holder.spec.setText(arrdoctorList.get(position).spec);
        holder.exp.setText(arrdoctorList.get(position).exp);
        Glide.with(context).load(arrdoctorList.get(position).img)
                //.placeholder()
                .fitCenter()
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,arrdoctorList.get(position).name,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrdoctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,spec,exp;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.d1name);
            spec=itemView.findViewById(R.id.d1specialization);
            exp=itemView.findViewById(R.id.d1experience);
            img=itemView.findViewById(R.id.d1image);

        }
    }
}
