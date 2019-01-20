package com.example.dpiotr.projekt.Registry;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 28.11.2016.
 */

public class EditRegistryRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/edit/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public EditRegistryRequest(String name, String user_id,String room_id, String state, int id, Response.Listener<String> listener,String token){
        super(Method.POST,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("user_id",user_id);
        params.put("room_id",room_id);
        params.put("state",state);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
