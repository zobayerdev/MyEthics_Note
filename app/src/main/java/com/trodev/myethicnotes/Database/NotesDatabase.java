package com.trodev.myethicnotes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.trodev.myethicnotes.Dao.NotesDao;
import com.trodev.myethicnotes.Model.Notes;

@Database(entities = {Notes.class}, version = 1, exportSchema = false) //So if you don't need to check the schema and you want to get rid of the warning, just add
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class,
                    "Notes_Database").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

}
