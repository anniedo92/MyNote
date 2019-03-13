package com.example.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
}
