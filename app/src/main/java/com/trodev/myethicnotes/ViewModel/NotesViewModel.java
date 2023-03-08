package com.trodev.myethicnotes.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getallNotes;
    public LiveData<List<Notes>> highToLow;
    public LiveData<List<Notes>> lowToHigh;

    public NotesViewModel(Application application) {
        super(application);
        repository = new NotesRepository (application);
        getallNotes = repository.getallNotes;
        highToLow = repository.highToLow;
        lowToHigh = repository.lowToHigh;
    }

    public  void insertNote(Notes notes){
        repository.insertNotes(notes);
    }

    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNotes(id);
    }


}
