package com.example.dpiotr.projekt.Registry;

import android.content.Context;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dpiotr.projekt.Login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import static com.example.dpiotr.projekt.TokenSaver.getToken;

/**
 * Created by Bartek on 28.11.2016.
 */

public class AddRegistryRequest extends StringRequest {
    private static final String URL = "http://damian.dvtr.pl/ewidencja/public/api/registry/add?token=";
    private Map<String,String> params;

    public AddRegistryRequest(String name, String user_id, String room_id, String state, Response.Listener<String> listener, String token) {
        super(Request.Method.POST, URL + token, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("user_id",user_id);
        params.put("room_id", room_id);
        params.put("state", state);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }


}