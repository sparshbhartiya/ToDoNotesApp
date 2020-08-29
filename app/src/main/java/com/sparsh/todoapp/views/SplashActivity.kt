package com.sparsh.todoapp.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sparsh.todoapp.utils.PrefConstant
import com.sparsh.todoapp.R

class SplashActivity:AppCompatActivity() {
    //No data is stored in database so use SharedPreference
    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreference()
        checkLoginStatus()
    }


    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        var isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN,false)
        //if logged in show MyNotesActivity

        if(isLoggedIn){
            var intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        }
        //else LoginActivity
        else{
            var intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}