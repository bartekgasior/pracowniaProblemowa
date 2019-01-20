package com.example.dpiotr.projekt.Rooms;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 11.12.2016.
 */

public class AddRoomRequest extends StringRequest{
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/rooms/add?token=";
    private Map<String,String> params;

    public AddRoomRequest(String name, String number, Response.Listener<String> listener,String token){
        super(Request.Method.POST,URL+token,listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("number",number);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}