package com.example.dpiotr.projekt.AttributesGroups;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dpiotr.projekt.R;

import java.util.List;

/**
 * Created by Bartek on 02.01.2017.
 */

public class AttributesGroupAdapter extends RecyclerView.Adapter<AttributesGroupAdapter.MyViewHolder> {

    private List<AttributesGroup> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public AttributesGroupAdapter(List<AttributesGroup> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attributes_group_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AttributesGroup adapter = list.get(position);
        holder.name.setText(adapter.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
