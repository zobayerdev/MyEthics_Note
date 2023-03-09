package com.trodev.myethicnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.ViewModel.NotesViewModel;
import com.trodev.myethicnotes.adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    NotesViewModel notesViewModel;
    RecyclerView noteRecyclerView;
    NotesAdapter adapter;

    TextView noFilter, highToLow, lowToHigh;

    ImageView filterBtn;

    List<Notes> filterNotesAllList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add toolbar name
        getSupportActionBar().setTitle("My Ethic");

        // init all views
        noFilter = findViewById(R.id.noFilter);
        highToLow = findViewById(R.id.highTolow);
        lowToHigh = findViewById(R.id.lowToHigh);
        filterBtn = findViewById(R.id.filterBtn);

        // no filter background setup
        noFilter.setBackgroundResource(R.drawable.filter_sl_shape);
        // filterBtn.setImageResource(R.drawable.filter_btn_shape);

        // ###########################################
        //set click
        noFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(0);
                highToLow.setBackgroundResource(R.drawable.filter_un_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
                noFilter.setBackgroundResource(R.drawable.filter_sl_shape);

            }
        });

        highToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(1);
                noFilter.setBackgroundResource(R.drawable.filter_un_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_un_shape);
                highToLow.setBackgroundResource(R.drawable.filter_sl_shape);

            }
        });

        lowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData(2);
                noFilter.setBackgroundResource(R.drawable.filter_un_shape);
                highToLow.setBackgroundResource(R.drawable.filter_un_shape);
                lowToHigh.setBackgroundResource(R.drawable.filter_sl_shape);

            }
        });


        // #########################################################


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


        notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNotesAllList = notes;
            }
        });


    }

    private void loadData(int i) {

        if (i == 0) {
            notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filterNotesAllList = notes;
                }
            });
        }
    }

    public void setAdapter(List<Notes> notes) {
        // size as per note sizes
        noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        noteRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Ethic here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                notesFilter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void notesFilter(String s) {
        ArrayList<Notes> FilterNames = new ArrayList<>();
        for (Notes notes : this.filterNotesAllList) {
            if (notes.notesTitle.contains(s) || notes.notesSubTitle.contains(s)) {
                FilterNames.add(notes);
            }
            this.adapter.searchNotes(FilterNames);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.dev)
        {
            startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
            Toast.makeText(this, "Developer Activity", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}