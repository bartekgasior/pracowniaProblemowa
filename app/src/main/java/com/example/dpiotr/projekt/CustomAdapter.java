package com.example.dpiotr.projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dpiotr.projekt.Resource.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dpiotr on 10.11.16.
 */

public class CustomAdapter extends ArrayAdapter {


    private List list = new ArrayList();

    public CustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void add(Resource object){
        list.add(object);
        super.add(object);
    }

    static class  Holder{
        TextView NAME;
        TextView OWNER;
        TextView ROOM;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        Holder holder;
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.resource_view, parent, false);
            holder = new Holder();
            holder.NAME = (TextView) row.findViewById(R.id.name);
            //holder.OWNER = (TextView) row.findViewById(R.id.owner);
            holder.ROOM = (TextView) row.findViewById(R.id.room);
            row.setTag(holder);
        }else{
            holder = (Holder) row.getTag();
        }
        Resource RS = (Resource) getItem(position);
        holder.NAME.setText(RS.getName());
       // holder.OWNER.setText(RS.getOwner());
        holder.ROOM.setText(RS.getRoom());
        //holder.VALUE.setText(Integer.toString(RS.getValue()));*/
        return row;
    }



}
