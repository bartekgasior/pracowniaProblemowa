package com.example.dpiotr.projekt.Registry;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 28.11.2016.
 */

public class GetAllRegistryRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/getAll?token=";
    private Map<String,String> params;

    public GetAllRegistryRequest(Response.Listener<String> listener,String token){
        super(Method.GET,URL + token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
