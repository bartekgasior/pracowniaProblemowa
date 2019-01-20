package com.example.dpiotr.projekt.RegistryAttributes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 02.01.2017.
 */

public class GetAllRegistryAttributesRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registryAttributes/getAll?token=";
    private Map<String,String> params;

    public GetAllRegistryAttributesRequest(Response.Listener<String> listener, String token){
        super(Request.Method.GET,URL+token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
