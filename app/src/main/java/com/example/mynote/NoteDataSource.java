package com.example.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;


public class NoteDataSource {

    private SQLiteDatabase database;
    private NoteDBHelper dbHelper;
    //private static volatile NoteDataSource instance;

    public NoteDataSource(Context context) {
        dbHelper = new NoteDBHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.dbHelper.close();
        }
    }

    //insert note
    public boolean insertNote(Note nt) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("date", nt.getDate());
            initialValues.put("title", nt.getTitle());
            initialValues.put("fullText", nt.getFullText());

            didSucceed = database.insert("note", null, initialValues) > 0;
        } catch (Exception e) {
            //do nothing - will return false if there is an exception
        }
        return didSucceed;
    }


    //update note
    public boolean updateNote(Note nt) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) nt.getNoteId();
            ContentValues updateValues = new ContentValues();
            updateValues.put("date", nt.getDate());
            updateValues.put("title", nt.getTitle());
            updateValues.put("fullText", nt.getFullText());

            didSucceed = database.update("note", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e) {
            //do nothing - will return false if there is an exception
        }
        return didSucceed;
    }

    //get last note id
    public int getLastNoteId() {
        int lastId = -1;
        try {
            String query = "Select MAX(_id) from note";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    //get title
    public ArrayList<String> getTitle() {
        ArrayList<String> title = new ArrayList<>();
        try {
            String query = "Select title from note";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                title.add(cursor.getString(2));
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            title = new ArrayList<String>();
        }
        return title;
    }


    public ArrayList<Note> getNote(String orderBy, String sortBy) {
        ArrayList<Note> note = new ArrayList<>();
        try {
            String query = "SELECT * FROM note ORDER BY " + sortBy + " " + orderBy;
            Cursor cursor = database.rawQuery(query, null);

            Note newNote;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                newNote = new Note();                                          //1
                newNote.setNoteId(cursor.getInt(0));
                newNote.setTitle(cursor.getString(2));
                newNote.setFullText(cursor.getString(3));

                note.add(newNote);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            note = new ArrayList<Note>();
        }
        return note;
    }

    //delete note
    public boolean deleteNote(int noteId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("note", "_id=" + noteId, null) > 0;
        } catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }


    //get note
    public Note getSpecificNote(int noteId) {
        Note note = new Note();
        String query = "SELECT  * FROM note WHERE _id =" + noteId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            note.setNoteId(cursor.getInt(0));
            note.setTitle(cursor.getString(2));
            note.setFullText(cursor.getString(3));

            cursor.close();
        }
        return note;
    }

}