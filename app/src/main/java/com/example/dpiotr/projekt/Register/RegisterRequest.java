package com.example.dpiotr.projekt.Register;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://damian.dvtr.pl/ewidencja/public/api/users/add";
    private Map<String,String> params;

    public RegisterRequest(String name, String surname, String login, String email, String password,  Response.Listener<String> listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("surname",surname);
        params.put("login", login);
        params.put("email",email);
        params.put("password",password);
    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
