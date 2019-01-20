package com.example.dpiotr.projekt.ChooseOption;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 12.12.2016.
 */

public class GetAllStatesRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/getAllState?token=";
    private Map<String,String> params;

    public GetAllStatesRequest(Response.Listener<String> listener, String token){
        super(Request.Method.GET,URL+token,listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}