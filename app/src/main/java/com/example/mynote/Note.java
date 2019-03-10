package com.example.mynote;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {

    private int noteId;
    private Date date;
    private String title;
    private String fullText;
    private boolean fullDisplayed;
    //private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY 'at' HH:mm aaa");

    public Note() {
        noteId = -1;
        date = new Date();

    }


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getDate() {
        return date + "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getShortText() {
        String temp = fullText.replaceAll("\n", " ");
        if (temp.length() > 20) {
            return temp.substring(0, 20) + "...";
        } else {
            return temp;
        }
    }

    public boolean isFullDisplayed() {
        return fullDisplayed;
    }

    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed = fullDisplayed;
    }

//    public DateFormat getDateFormat() {
//        return dateFormat;
//    }

    @Override
    public String toString() {
        return this.fullText;
    }
}
