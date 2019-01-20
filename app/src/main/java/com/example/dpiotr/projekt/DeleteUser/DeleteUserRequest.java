package com.example.dpiotr.projekt.DeleteUser;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bartek on 11.11.2016.
 */

public class DeleteUserRequest extends StringRequest{
    private static final String DELETE_REQUEST_URL = "http://damian.dvtr.pl/ewidencja/public/api/users/delete/"; //<<<<<<<<<<<<<<
    private Map<String,String> params;

    public DeleteUserRequest(int id, Response.Listener<String> listener){
        super(Method.POST, DELETE_REQUEST_URL + Integer.toString(id), listener, null);
        params = new HashMap<>();
        params.put("id",id + "");
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
