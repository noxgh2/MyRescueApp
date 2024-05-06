package com.myapp.examples.loginregister;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context _context;

    int Privat_mode = 0;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String username_key = "username";

    public static final String IS_login = "isloggedin";
    public static final String has_healthreport = "has_healthreport";
    public static final String has_econtact = "has_econtact";

    public SessionManager (Context context)
    {
        this._context = context;
        sharedPreferences = _context.getSharedPreferences(SHARED_PREFS,Privat_mode);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username)
    {
        editor.putBoolean(IS_login,true);
        editor.putString(username_key,username);
        editor.commit();
    }

    public boolean isLoggedIn(){

        return  sharedPreferences.getBoolean(IS_login,false);
    }
    public void sethashreport(boolean value){

        editor.putBoolean(has_healthreport,value);
        editor.commit();
    }
    public void sethasecontact(boolean value){

        editor.putBoolean(has_econtact,value);
        editor.commit();
    }
    public boolean gethashreport(){

        return  sharedPreferences.getBoolean(has_healthreport,false);
    }
    public boolean gethasecontact(){

        return  sharedPreferences.getBoolean(has_econtact,false);
    }
   /* public boolean gethashreport(Context context){

        sharedPreferences= context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
       boolean value = sharedPreferences.getBoolean(has_healthreport,false);
       return  value;
    }
    public boolean gethasecontact(Context context){

        sharedPreferences= context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(has_econtact,false);
        return  value;
    }*/


    public void  chekLogin(){
        if(!this.isLoggedIn())
        {
            Intent i = new Intent(_context, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
        }
    }


    public HashMap<String, String> getUserData(){

        HashMap<String, String> user = new HashMap<String, String>();
        user.put(username_key, sharedPreferences.getString(username_key,null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
}
