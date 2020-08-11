package com.sparsh.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setUpSharedPreference();
        checkLoginStatus();
    }

    private void setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false);
        if(isLoggedIn){
            //if logged in show MyNotesActivity
            Intent intent = new Intent(SplashActivity.this,MyNotesActivity.class);
            startActivity(intent);
        }
        else{
            //else LoginActivity
            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }


        //No data is stored in database so use SharedPreference
    }
}