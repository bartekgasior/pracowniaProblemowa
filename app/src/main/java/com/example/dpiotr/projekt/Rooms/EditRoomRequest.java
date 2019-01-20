package com.example.dpiotr.projekt.Rooms;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 15.12.2016.
 */

public class EditRoomRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/rooms/edit/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public EditRoomRequest(String name, String number, int id, Response.Listener<String> listener, String token){
        super(Method.POST,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("number", number);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}