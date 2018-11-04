package com.olczyk.android.androidarchitectureapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import java.util.List;

import static com.olczyk.android.androidarchitectureapp.AddNoteActivity.EXTRA_DESCRIPTION;
import static com.olczyk.android.androidarchitectureapp.AddNoteActivity.EXTRA_PRIORITY;
import static com.olczyk.android.androidarchitectureapp.AddNoteActivity.EXTRA_TITLE;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;

    private NoteViewModel noteViewModel;

    @ViewById
    FloatingActionButton buttonAddNote;

    @AfterViews
    public void aVoid() {

        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity_.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(EXTRA_PRIORITY, 1);

            Log.i("LOG", "MA New note is: " + title);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);
            Log.i("LOG", "New note is: " + note.getPriority());

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
