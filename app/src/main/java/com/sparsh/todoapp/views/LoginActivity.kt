package com.sparsh.todoapp.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.sparsh.todoapp.utils.AppConstant
import com.sparsh.todoapp.utils.PrefConstant
import com.sparsh.todoapp.R

class LoginActivity:AppCompatActivity() {
    lateinit var editTextFullName:EditText
    lateinit var editTextUserName:EditText
    lateinit var buttonLogin: Button
    lateinit var  sharedPreferences : SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViews()
        setSharedPreferences()
    }

    private fun bindViews() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        val clickAction = object : View.OnClickListener{
            override fun onClick(v : View?){
                var fullName = editTextFullName.text.toString()
                var userName = editTextUserName.text.toString()
                if(fullName.isNotEmpty() && userName.isNotEmpty()){
                    var intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                    intent.putExtra(AppConstant.FULL_NAME,fullName)
                    startActivity(intent)
                    saveLoginStatus()
                    saveFullName(fullName)


                }


            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN,true)
        editor.apply()
    }

    private fun saveFullName(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(PrefConstant.FULL_NAME,"")
        editor.apply()

    }

    private fun setSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }
}