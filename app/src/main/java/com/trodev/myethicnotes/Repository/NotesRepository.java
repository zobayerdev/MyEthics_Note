package com.trodev.myethicnotes.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.trodev.myethicnotes.Dao.NotesDao;
import com.trodev.myethicnotes.Database.NotesDatabase;
import com.trodev.myethicnotes.Model.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>> getallNotes;

    public NotesRepository(Application application) {
        NotesDatabase notesDatabase = NotesDatabase.getDatabaseInstance(application);
        notesDao = notesDatabase.notesDao();
        getallNotes = notesDao.getallNotes();
    }

    public void insertNotes(Notes notes) {
        notesDao.insertNotes(notes);
    }

    public void deleteNotes(int id) {
        notesDao.deleteNotes(id);
    }

    public void updateNotes(Notes notes) {
        notesDao.updateNotes(notes);
    }

}
