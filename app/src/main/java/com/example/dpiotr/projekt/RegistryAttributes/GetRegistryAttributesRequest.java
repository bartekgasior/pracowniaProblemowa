package com.example.dpiotr.projekt.RegistryAttributes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 02.01.2017.
 */

public class GetRegistryAttributesRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registryAttributes/get/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public GetRegistryAttributesRequest(int id, Response.Listener<String> listener, String token){
        super(Method.GET,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}