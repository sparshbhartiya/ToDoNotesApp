package com.sparsh.todoapp;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sparsh.todoapp.adapter.NotesAdapter;
import com.sparsh.todoapp.model.Notes;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {
    String fullName;
    FloatingActionButton fabAddNotes;
    RecyclerView recyclerViewNotes;
    SharedPreferences sharedPreferences;
    ArrayList<Notes> notesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        bindViews();
        setupSharedPreferences();
        getIntentData();
        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDialogBox();
            }
        });
        getSupportActionBar().setTitle(fullName);

    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME,MODE_PRIVATE);

    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.FULL_NAME);
        if(TextUtils.isEmpty(fullName)){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"");
        }
    }

    private void bindViews(){
        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

    }
    private void setupDialogBox(){
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        //Dialog(pop up is called dialog in android)
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view).setCancelable(false).create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                Notes notes = new Notes();
                notes.setTitle(title);
                notes.setDescription(description);
                notesList.add(notes);
                setUpRecyclerView();
                Log.d("MyNotesActivity", String.valueOf(notesList.size()));
                dialog.hide();
            }
        });
        dialog.show();
    }

    private void setUpRecyclerView() {
        NotesAdapter notes = new NotesAdapter(notesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notes);
    }
}