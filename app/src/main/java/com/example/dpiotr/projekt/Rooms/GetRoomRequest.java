package com.example.dpiotr.projekt.Rooms;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 15.12.2016.
 */

public class GetRoomRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/rooms/get/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public GetRoomRequest(int id, Response.Listener<String> listener, String token){
        super(Method.GET,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
