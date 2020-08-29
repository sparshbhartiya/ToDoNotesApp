package com.sparsh.todoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Notes::class],version=1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun NotesDao():NotesDao
    companion object{
        lateinit var INSTANCE:NotesDatabase
        fun getInstance(context: Context):NotesDatabase {
            synchronized(NotesDatabase::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, "my-notes.db")
                        .allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}