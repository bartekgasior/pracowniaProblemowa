package com.example.dpiotr.projekt.RegistryAttributes;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 02.01.2017.
 */

public class AddRegistryAttributesRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registryAttributes/add?token=";
    private Map<String,String> params;

    public AddRegistryAttributesRequest(int reg_id, int att_id, String value, Response.Listener<String> listener, String token){
        super(Request.Method.POST,URL + token,listener, null);
        params = new HashMap<>();
        params.put("registry_id",reg_id + "");
        params.put("attribute_id",att_id + "");
        params.put("value",value);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
