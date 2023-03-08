package com.trodev.myethicnotes.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.trodev.myethicnotes.Model.Notes;
import com.trodev.myethicnotes.R;
import com.trodev.myethicnotes.activities.MainActivity;
import com.trodev.myethicnotes.activities.UpdateNoteActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewholder> {
    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;
    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesItem = new ArrayList<>(notes);
    }

    public void searchNotes(List<Notes> filterName){
        this.notes = filterName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewholder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewholder holder, int position) {

        Notes note = notes.get(position);

        if (note.notesPriority.equals("1")) {
            holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
        } else if (note.notesPriority.equals("2")) {
            holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
        } else if (note.notesPriority.equals("3")) {
            holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
        }

        holder.noteTitle.setText(note.notesTitle);
        holder.noteSubtitle.setText(note.notesSubTitle);
        holder.noteDate.setText(note.notesDate);

        // send data another activity....
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, UpdateNoteActivity.class);
                intent.putExtra("id", note.id);
                intent.putExtra("title", note.notesTitle);
                intent.putExtra("subtitle", note.notesSubTitle);
                intent.putExtra("notes", note.notes);
                intent.putExtra("priority", note.notesPriority);
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class notesViewholder extends RecyclerView.ViewHolder {

        View notesPriority;
        TextView noteTitle, noteSubtitle, noteDate;
        public notesViewholder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.notesTitle);
            noteSubtitle = itemView.findViewById(R.id.noteSubtitle);
            noteDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
