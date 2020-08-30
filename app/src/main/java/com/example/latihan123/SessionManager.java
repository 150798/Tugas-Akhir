package com.example.latihan123;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SmartHome";

    // All Shared Preferences Keys
    private static final String TAG_USERNAME = "USERNAME";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String username){

        editor.putString(TAG_USERNAME, username);
        // commit changes
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    // Untuk ngambil isi dari session apa aja/keseluruhan
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user
        user.put(TAG_USERNAME, pref.getString(TAG_USERNAME, null));
        // return user
        return user;
    }

    public String getUsername(){
        return pref.getString(TAG_USERNAME, "");
    }

    /**
     * Clear session details
     * */
    public void logoutUser(Intent logoutIntent){
        // Clearing all data from Shared Preferences
        try {
            editor.clear();
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(logoutIntent);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    // Fungsinya kalau dia nggak kosong dia udah masuk login. --> krn usernamenya udh kesimpen
    public boolean isLoggedIn(){
        if(!getUsername().equals("")){
            return true;
        }else{
            return false;
        }
        /*return pref.getBoolean(IS_LOGIN, false);*/
    }
}