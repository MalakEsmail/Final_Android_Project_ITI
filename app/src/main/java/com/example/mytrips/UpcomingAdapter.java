package com.example.mytrips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    UpcomingData[] upcomingData;

    public UpcomingAdapter(UpcomingData[] upcomingData) {
         this.upcomingData = upcomingData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView dateTxt, timeTxt, tripNameTxt, stateTxt, startTxt, endTxt;
        ImageButton tripMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.upcoming_date);
            timeTxt = itemView.findViewById(R.id.upcoming_time);
            tripNameTxt = itemView.findViewById(R.id.upcoming_name);
            stateTxt = itemView.findViewById(R.id.upcoming_state);
            startTxt = itemView.findViewById(R.id.upcoming_start);
            endTxt = itemView.findViewById(R.id.upcoming_end);
            tripMenu = itemView.findViewById(R.id.trip_menu);
            tripMenu.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            showPopupMenu(view);
        }

        private void showPopupMenu(View view) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.inflate(R.menu.trip_menu);
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {

                case R.id.notes:
                    Toast.makeText(itemView.getContext(), "notes", Toast.LENGTH_LONG).show();
                    break;
                case R.id.edit_trip:
                    Toast.makeText(itemView.getContext(), "edit", Toast.LENGTH_LONG).show();
                    break;
                case R.id.delete_trip:
                    Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_LONG).show();
                    break;
                case R.id.cancel_trip:
                    Toast.makeText(itemView.getContext(), "cancel", Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }
    }


}
