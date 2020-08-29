package com.sparsh.todoapp.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.sparsh.todoapp.R

class DetailActivity:AppCompatActivity() {
    val TAG = "DetailActivity"
    lateinit var  textViewTitle:TextView
    lateinit var textViewDescription:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentData()
    }

    private fun bindViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)


    }

    private fun setIntentData() {
        val intent = intent
        var title = intent.getStringExtra("title")
        var description = intent.getStringExtra("description")
        textViewTitle.text = title
        textViewDescription.text = description
    }

}