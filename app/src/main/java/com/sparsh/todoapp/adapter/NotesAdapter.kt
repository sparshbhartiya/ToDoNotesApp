package com.sparsh.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sparsh.todoapp.R
import com.sparsh.todoapp.clicklisteners.ItemClickListener
import com.sparsh.todoapp.db.Notes
import kotlinx.android.synthetic.main.add_notes_dialog_layout.view.*

class NotesAdapter(val list: List<Notes>, val itemClickListener:ItemClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        //whether to attach this layout to root or not
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = list[position] //Instead of getPosition() we can use this
        val title = notes.title
        val description = notes.description
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description
        holder.checkBoxMarkStatus.isChecked = notes.isTaskCompleted
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
        holder.itemView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                itemClickListener.onClick(notes)
            }

        })
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted = isChecked
                itemClickListener.onUpdate(notes)
            }


        })

    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textViewTitle : TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewDescription : TextView = itemView.findViewById(R.id.textViewDescription)
        var checkBoxMarkStatus : CheckBox = itemView.findViewById(R.id.checkboxMarkStatus)
        var imageView : ImageView = itemView.findViewById(R.id.imageView)
    }

}