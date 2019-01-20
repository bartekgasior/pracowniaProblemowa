package com.example.dpiotr.projekt.Registry;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 28.11.2016.
 */

public class GetRegistryRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/get/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public GetRegistryRequest(int id, Response.Listener<String> listener, String token){
        super(Method.GET,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}