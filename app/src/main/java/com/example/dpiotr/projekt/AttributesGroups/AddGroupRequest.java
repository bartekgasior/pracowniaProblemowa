package com.example.dpiotr.projekt.AttributesGroups;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 02.01.2017.
 */

public class AddGroupRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/attributesGroups/add?token=";
    private Map<String,String> params;

    public AddGroupRequest(String name, Response.Listener<String> listener, String token){
        super(Request.Method.POST,URL + token,listener, null);
        params = new HashMap<>();
        params.put("name",name);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}