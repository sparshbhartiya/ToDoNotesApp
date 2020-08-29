package com.sparsh.todoapp.clicklisteners

import com.sparsh.todoapp.db.Notes

interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}