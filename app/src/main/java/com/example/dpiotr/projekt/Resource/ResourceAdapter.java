package com.example.dpiotr.projekt.Resource;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dpiotr.projekt.R;

import java.util.List;

/**
 * Created by dpiotr on 11.11.16.
 */

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyViewHolder> {

    private List<Resource> resourcesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, room, state;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            //owner = (TextView) view.findViewById(R.id.owner);
            room = (TextView) view.findViewById(R.id.room);
            state = (TextView) view.findViewById(R.id.state);
        }
    }


    public ResourceAdapter(List<Resource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Resource res = resourcesList.get(position);
        holder.name.setText(res.getName());
        //holder.owner.setText(res.getOwner());
        holder.room.setText(res.getRoom());
        holder.state.setText(res.getState());

    }

    @Override
    public int getItemCount() {
        return resourcesList.size();
    }
}
