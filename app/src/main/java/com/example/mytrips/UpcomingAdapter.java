package com.example.mytrips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    UpcomingData[] upcomingData;
    public UpcomingAdapter(UpcomingData[] upcomingData ){
        this.upcomingData=upcomingData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row ,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateTxt.setText(upcomingData[position].getDate());
        holder.timeTxt.setText(upcomingData[position].getTime());
        holder.tripNameTxt.setText(upcomingData[position].getTripName());
        holder.stateTxt.setText(upcomingData[position].getState());
        holder.startTxt.setText(upcomingData[position].getStartPoint());
        holder.endTxt.setText(upcomingData[position].getEndPoint());
    }

    @Override
    public int getItemCount() {
        return upcomingData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateTxt,timeTxt,tripNameTxt,stateTxt,startTxt,endTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt=itemView.findViewById(R.id.upcoming_date);
            timeTxt=itemView.findViewById(R.id.upcoming_time);
            tripNameTxt=itemView.findViewById(R.id.upcoming_name);
            stateTxt=itemView.findViewById(R.id.upcoming_state);
            startTxt=itemView.findViewById(R.id.upcoming_start);
            endTxt=itemView.findViewById(R.id.upcoming_end);
        }
    }

}
