package com.trodev.myethicnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.ViewModel.NotesViewModel;
import com.trodev.myethicnotes.adapter.NotesAdapter;
import com.trodev.myethicnotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    NotesViewModel notesViewModel;
    RecyclerView noteRecyclerView;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add toolbar name
        getSupportActionBar().setTitle("My Ethic");

        fab = findViewById(R.id.newNoteBtn);
        noteRecyclerView = findViewById(R.id.notesRecycler);
        //get model class to view
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });

        notesViewModel.getallNotes.observe(this, notes -> {
            noteRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new NotesAdapter(MainActivity.this, notes);
            noteRecyclerView.setAdapter(adapter);
        });
    }
}