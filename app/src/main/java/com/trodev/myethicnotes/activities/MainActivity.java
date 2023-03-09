package com.trodev.myethicnotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.trodev.myethicnotes.BuildConfig;
import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.ViewModel.NotesViewModel;
import com.trodev.myethicnotes.adapter.NotesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    NotesViewModel notesViewModel;
    RecyclerView noteRecyclerView;
    NotesAdapter adapter;
    TextView noFilter, highToLow, lowToHigh;
    ImageView filterBtn;
    List<Notes> filterNotesAllList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

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


        // ############################# Drawer Layout Activity ##########################
        // init drawerLayout
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // eikhane eituku hocche amader navigation layout er kaj korar jonno.
        navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // ###########################################
        // no filter background setup
        noFilter.setBackgroundResource(R.drawable.filter_sl_shape);

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

    // #################################
    // load all data
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

    // ##########################################
    // set adapter to get data from adapter
    public void setAdapter(List<Notes> notes) {
        // size as per note sizes
        noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        noteRecyclerView.setAdapter(adapter);
    }

    // ##########################################
    //search here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Ethics here...");
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

    //#############################
    // no filter system
    private void notesFilter(String s) {
        ArrayList<Notes> FilterNames = new ArrayList<>();
        for (Notes notes : this.filterNotesAllList) {
            if (notes.notesTitle.contains(s) || notes.notesSubTitle.contains(s)) {
                FilterNames.add(notes);
            }
            this.adapter.searchNotes(FilterNames);
        }
    }

    // ##################
    // all navigation activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.nav_notification_notice:
                Toast.makeText(this, "Coming Soon....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_req_dev:
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@trodev"));
                try {
                    startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }
                Toast.makeText(this, "How to use our application....", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dev:
                Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, DeveloperActivity.class));
                break;
            case R.id.nav_policy:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.app-privacy-policy.com/live.php?token=FArIUG005G3apHaSpqknIJnug6bK6RtI")));
                Toast.makeText(this, "Privacy policy", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MyEthic Notes");
                    String shareMessage = "\nMyEthic Notes download now\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                    Toast.makeText(this, "Share our apps", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;

            case R.id.nav_apps:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6580660399707616800")));
                    Toast.makeText(this, "Our all application", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}