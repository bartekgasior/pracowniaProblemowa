package com.example.dpiotr.projekt.Rooms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dpiotr.projekt.R;


import java.util.List;

/**
 * Created by dpiotr on 12.12.16.
 */

public class RoomsAdapter extends RecyclerView.Adapter<com.example.dpiotr.projekt.Rooms.RoomsAdapter.MyViewHolder> {

    private List<Room> roomsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.room_name);
            //owner = (TextView) view.findViewById(R.id.owner);
            number = (TextView) view.findViewById(R.id.room_number);
        }
    }


    public RoomsAdapter(List<Room> roomsList) {
        this.roomsList = roomsList;
    }

    @Override
    public com.example.dpiotr.projekt.Rooms.RoomsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_view, parent, false);

        return new com.example.dpiotr.projekt.Rooms.RoomsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.dpiotr.projekt.Rooms.RoomsAdapter.MyViewHolder holder, int position) {
        Room room = roomsList.get(position);
        holder.name.setText(room.getName());
        //holder.owner.setText(res.getOwner());
        holder.number.setText(room.getNumber());
        // holder.value.setText(Integer.toString(res.getValue()));*/

    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }
}
