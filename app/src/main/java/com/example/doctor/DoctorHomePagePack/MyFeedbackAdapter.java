package com.example.doctor.DoctorHomePagePack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor.R;

import java.util.ArrayList;

public class MyFeedbackAdapter extends RecyclerView.Adapter<MyFeedbackAdapter.MyFeedbackViewHolder>{

    private Context context;
    private ArrayList<MyFeedbackModel> myFeedbacksList;

    public MyFeedbackAdapter(Context context, ArrayList<MyFeedbackModel> myFeedbacksList) {
        this.context = context;
        this.myFeedbacksList = myFeedbacksList;
    }

    @NonNull
    @Override
    public MyFeedbackAdapter.MyFeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.myfeedback_viewlist, parent, false);
        return new MyFeedbackAdapter.MyFeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFeedbackViewHolder holder, int position) {

      MyFeedbackModel model=myFeedbacksList.get(position);
//        Log.d("MyNotee",model.getPatientName());
        holder.PatientName.setText(model.getPatientName());

        holder.FeedText.setText(model.getFeedText());


    }


    @Override
    public int getItemCount() {
        return myFeedbacksList.size();
    }

    public class MyFeedbackViewHolder extends RecyclerView.ViewHolder{

        private TextView PatientName,FeedText;

//        private Button call, feedbutton;

        public MyFeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            PatientName =itemView.findViewById(R.id.textView_name);
            FeedText =itemView.findViewById(R.id.feed_text);


        }
    }
}
