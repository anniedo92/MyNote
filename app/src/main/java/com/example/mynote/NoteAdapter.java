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

import javax.sql.DataSource;

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

            TextView title = (TextView) v.findViewById(R.id.textTitle);
            TextView date = (TextView) v.findViewById(R.id.textDate);
            title.setText(note.getTitle());
            date.setText(note.getDate());
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return v;
    }


    public void showDelete(final int position, final View noteView,final Context notetext, final Note note) {
        View v = noteView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteNote);
        if (b.getVisibility()==View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, noteView, notetext);
                    items.remove(note);
                    deleteOption(note.getNoteId(), notetext);
                }
            });
        }
        else {
            hideDelete(position, noteView, notetext);
        }
    }

    //access the database
    private void deleteOption(int contactToDelete, Context notetext) {
        NoteDataSource db = new NoteDataSource(notetext);
        try {
            db.open();
            db.deleteContact(contactToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Contact Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }

    //Hide delete button
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteNote);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }
}
