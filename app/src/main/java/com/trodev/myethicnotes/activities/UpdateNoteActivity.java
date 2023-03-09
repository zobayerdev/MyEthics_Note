package com.trodev.myethicnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.ViewModel.NotesViewModel;
import com.trodev.myethicnotes.databinding.ActivityUpdateNoteBinding;

import java.util.Date;

public class UpdateNoteActivity extends AppCompatActivity {

    ActivityUpdateNoteBinding binding;
    public String priority = "1";
    String  stitle, ssubtitle, snotes, spriority;
    int sid ;

    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Update Ethic");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get data on model view
        sid = getIntent().getIntExtra("id",0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        snotes = getIntent().getStringExtra("notes");
        spriority = getIntent().getStringExtra("priority");

        //set data on edit text
        binding.updateTitile.setText(stitle);
        binding.updateSubtitile.setText(ssubtitle);
        binding.updateNote.setText(snotes);

        if(spriority.equals("1"))
        {
            binding.greenPriority.setImageResource(R.drawable.ic_done);
        }
        else if(spriority.equals("2"))
        {
            binding.yellowPriority.setImageResource(R.drawable.ic_done);
        }
        else if(spriority.equals("3"))
        {
            binding.redPriority.setImageResource(R.drawable.ic_done);
        }

        //get model class to view
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.greenPriority.setImageResource(R.drawable.ic_done);
                binding.yellowPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority = "1";
                Toast.makeText(UpdateNoteActivity.this, "You select a Low Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.yellowPriority.setImageResource(R.drawable.ic_done);
                binding.greenPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority = "2";
                Toast.makeText(UpdateNoteActivity.this, "You select a Medium Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.redPriority.setImageResource(R.drawable.ic_done);
                binding.greenPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(0);
                priority = "3";
                Toast.makeText(UpdateNoteActivity.this, "You select a High Priority Navigation", Toast.LENGTH_SHORT).show();
            }
        });

        binding.updateNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  title = binding.updateTitile.getText().toString();
              String  subtitle = binding.updateSubtitile.getText().toString();
              String   notes = binding.updateNote.getText().toString();

                updateNotes(title, subtitle, notes);
            }
        });

    }

    private void updateNotes(String title, String subtitle, String notes) {

        // date & format
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes updatenote = new Notes();
        updatenote.id = sid;
        updatenote.notesTitle = title;
        updatenote.notesSubTitle = subtitle;
        updatenote.notes = notes;
        updatenote.notesPriority = priority;
        updatenote.notesDate = sequence.toString();

        notesViewModel.updateNote(updatenote);


        Toast.makeText(this, "Notes updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete)
        {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNoteActivity.this, R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNoteActivity.this)
                    .inflate(R.layout.delete_bottom_sheet, (LinearLayout) findViewById(R.id.deleteSheet));

            sheetDialog.setContentView(view);



            TextView yes, no;

            yes = view.findViewById(R.id.delete_yes);
            no = view.findViewById(R.id.delete_no);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notesViewModel.deleteNote(sid);
                    finish();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetDialog.dismiss();
                }
            });


            sheetDialog.show();
        }
        return true;
    }
}