package com.example.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {
    private ArrayList<Note> items;
    private Context adapterContext;


    public NoteAdapter(Context context, ArrayList<Note> items) {
        super(context, R.layout.list_note, items);
        adapterContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        try {
            Note note = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_note, null);
            }

            TextView date = (TextView) v.findViewById(R.id.textDate);
            TextView priority = (TextView) v.findViewById(R.id.priorityLvl) ;
            TextView title = (TextView) v.findViewById(R.id.textTitle);
            Button b = (Button) v.findViewById(R.id.btnDeleteNote);

            date.setText(note.getDate());
            priority.setText("Priority: " + note.getPriority());
            title.setText(note.getTitle());
            b.setVisibility(View.INVISIBLE);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }

    //show delete button next to each note
    public void showDelete(final int position, final View convertView,final Context context, final Note note) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.btnDeleteNote);
        if (b.getVisibility() == View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(note);
                    deleteOption(note.getNoteId(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }

    //access the database to delete note
    private void deleteOption(int noteToDelete, Context context) {
        NoteDataSource db = new NoteDataSource(context);
        try {
            db.open();
            db.deleteNote(noteToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Note Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }

    //hide delete button
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.btnDeleteNote);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }
}
