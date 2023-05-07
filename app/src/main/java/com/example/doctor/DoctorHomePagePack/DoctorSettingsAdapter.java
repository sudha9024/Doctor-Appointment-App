package com.example.doctor.DoctorHomePagePack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.Login;
import com.example.doctor.PatientHomePagePack.PatientHomePage;
import com.example.doctor.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DoctorSettingsAdapter extends RecyclerView.Adapter<DoctorSettingsAdapter.DoctorSettingsViewHolder> {

    Context context;
    ArrayList<DoctorSettingsModel> settings;
    public DoctorSettingsAdapter(Context context, ArrayList<DoctorSettingsModel> settings){
        this.context=context;
        this.settings=settings;
    }

    public DoctorSettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.settings_list_item, parent, false);
        return new DoctorSettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorSettingsViewHolder holder, int position) {
          DoctorSettingsModel model=settings.get(position);
          holder.icon.setImageResource(model.getIcon());
          holder.settingsName.setText(model.getHeading());
          holder.item.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(model.getHeading().equals("Log Out")){
                      FirebaseAuth.getInstance().signOut();
                      Intent intent=new Intent(context, Login.class);
                      context.startActivity(intent);
                      ((Activity)context).finish();
                  }
                  else
                  Toast.makeText(context, "You Clicked" + model.getHeading(), Toast.LENGTH_SHORT).show();
              }
          });
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }
    public class DoctorSettingsViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView settingsName;
        private RelativeLayout item;
        public DoctorSettingsViewHolder(@NonNull View itemView) {
            super(itemView);
              icon=itemView.findViewById(R.id.imageView);
              settingsName=itemView.findViewById(R.id.textView_settingname);
              item=itemView.findViewById(R.id.settings_item);
        }
    }
}
