package com.example.dpiotr.projekt.Attributes;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import com.example.dpiotr.projekt.R;
/**
 * Created by Bartek on 02.01.2017.
 */

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.MyViewHolder> {
    private List<Attribute> attributes;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,type;

        public MyViewHolder(View view){
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            type = (TextView) view.findViewById(R.id.type);
        }
    }

    public AttributesAdapter(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attribute_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AttributesAdapter.MyViewHolder holder, int position) {
        Attribute attribute = attributes.get(position);
        holder.name.setText(attribute.getName());
        holder.type.setText(attribute.getType());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }
}
