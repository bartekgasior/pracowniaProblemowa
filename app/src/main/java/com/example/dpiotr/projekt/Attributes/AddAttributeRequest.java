package com.example.dpiotr.projekt.Attributes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 02.01.2017.
 */

public class AddAttributeRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/attributes/add?token=";
    private Map<String,String> params;

    public AddAttributeRequest(String name, Response.Listener<String> listener, String token){
        super(Request.Method.POST,URL + token,listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("type"," ");
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}