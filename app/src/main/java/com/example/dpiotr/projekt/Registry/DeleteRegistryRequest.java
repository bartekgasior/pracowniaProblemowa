package com.example.dpiotr.projekt.Registry;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 23.01.2017.
 */

public class DeleteRegistryRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/delete/";
    private static final String URL_token = "?token=";

    public DeleteRegistryRequest(int id, Response.Listener<String> listener, String token){
        super(Method.DELETE,URL + Integer.toString(id) + URL_token + token,listener, null);
    }

}