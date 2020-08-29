package com.sparsh.todoapp.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sparsh.todoapp.NotesApp
import com.sparsh.todoapp.utils.AppConstant
import com.sparsh.todoapp.utils.PrefConstant
import com.sparsh.todoapp.R
import com.sparsh.todoapp.adapter.NotesAdapter
import com.sparsh.todoapp.clicklisteners.ItemClickListener
import com.sparsh.todoapp.db.Notes

class MyNotesActivity:AppCompatActivity() {
    lateinit var fullName : String
    lateinit var fabAddNotes : FloatingActionButton
    lateinit var recyclerViewNotes : RecyclerView
    lateinit var sharedPreferences: SharedPreferences
    var notesList = ArrayList<Notes>()
    val TAG = "MyNotesActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindViews()
        setSharedPreferences()
        getIntentData()
        getDataFromDb()
        setUpToolbarText()
        clickListeners()
        setUpRecyclerView()



    }

    private fun clickListeners() {
        fabAddNotes.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                setupDialogBox()
            }

        })
    }

    private fun setUpToolbarText() {
        if(supportActionBar != null) {
            supportActionBar?.title = fullName
        }
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout,null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog  =  AlertDialog.Builder(this).setView(view).
        setCancelable(false).create()
        buttonSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val title = editTextTitle.getText().toString()
                val description = editTextDescription.getText().toString()
                val notes = Notes(title =title,description =  description)
                notesList.add(notes)
                addNotesToDb(notes)
                dialog.hide()
            }
        })
        dialog.show()
    }

    private fun getDataFromDb() {
        //select data from database
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesdb().NotesDao()
        notesList.addAll(notesDao.getAll())
    }

    private fun addNotesToDb(notes:Notes) {
        //Insert Data to database
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesdb().NotesDao()
        notesDao.insert(notes)
    }

    private fun setUpRecyclerView() {
        val itemClickListener = object : ItemClickListener{
            override fun onClick(notes: Notes) {
                var intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra("title",notes.title)
                intent.putExtra("description",notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesdb().NotesDao()
                notesDao.update(notes)
            }
        }
        var notes = NotesAdapter(notesList,itemClickListener)
        var linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notes

    }

    private fun bindViews() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    }

    private fun getIntentData(){
        var intent = intent
        fullName = intent.getStringExtra(AppConstant.FULL_NAME).toString()
        if(fullName.isEmpty()){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"").toString()
        }
    }

}


