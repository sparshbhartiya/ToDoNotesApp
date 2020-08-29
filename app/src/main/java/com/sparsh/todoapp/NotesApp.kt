package com.sparsh.todoapp

import android.app.Application
import com.sparsh.todoapp.db.NotesDatabase

class NotesApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
    fun getNotesdb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }

}