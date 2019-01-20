package com.example.dpiotr.projekt;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bartek on 11.12.2016.
 */

public class TokenSaver {
    private final static String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    private final static String TOKEN_KEY = "token";
    private final static String ID_KEY = "id";
    private final static String LOGIN_KEY = "id";

    public static String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static void setID(Context c, int id){
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ID_KEY,id);
        editor.apply();
    }

    public static int getID(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(ID_KEY, -1);
    }

    public static void setLogin(Context c, String login){
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LOGIN_KEY, login);
        editor.apply();
    }

    public static String getLogin(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(LOGIN_KEY, "");
    }
}
