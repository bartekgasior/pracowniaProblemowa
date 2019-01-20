package com.example.dpiotr.projekt.RegistryAttributes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 13.01.2017.
 */

public class EditRegistryAttributesRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/rooms/edit/";
    private static final String URL_token = "?token=";
    private Map<String,String> params;

    public EditRegistryAttributesRequest(int registry_id, int attribute_id, String value, int id, Response.Listener<String> listener, String token){
        super(Method.POST,URL + Integer.toString(id) + URL_token + token,listener, null);
        params = new HashMap<>();
        params.put("registry_id", registry_id+"");
        params.put("attribute_id", attribute_id+ "");
        params.put("value",value);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}