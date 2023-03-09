package com.trodev.myethicnotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.ViewModel.NotesViewModel;
import com.trodev.myethicnotes.databinding.ActivityAddNoteBinding;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    public String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // add toolbar name
        getSupportActionBar().setTitle("Create Ethic");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get model class to view
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriority.setImageResource(R.drawable.ic_done);
                binding.yellowPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority = "1";
                Toast.makeText(AddNoteActivity.this, "You select a Low Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.yellowPriority.setImageResource(R.drawable.ic_done);
                binding.greenPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority = "2";
                Toast.makeText(AddNoteActivity.this, "You select a Medium Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.redPriority.setImageResource(R.drawable.ic_done);
                binding.greenPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(0);
                priority = "3";
                Toast.makeText(AddNoteActivity.this, "You select a High Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        //init button
        binding.doneNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = binding.notesTitle.getText().toString();
                subtitle = binding.noteSubtitle.getText().toString();
                notes = binding.notesData.getText().toString();

                createNotes(title, subtitle, notes);
            }
        });
    }

    private void createNotes(String title, String subtitle, String notes) {

        // date & format
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        // create model variable
        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubTitle = subtitle;
        notes1.notes = notes;
        notes1.notesPriority = priority;
        notes1.notesDate = sequence.toString();

        // insert data on notes....
        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}