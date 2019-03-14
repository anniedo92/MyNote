package com.example.mynote;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {

    private int noteId;
    private Date date;
    private String priority;
    private String title;
    private String fullText;
//    private boolean fullDisplayed;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

    public Note() {
        noteId = -1;
        this.date = new Date();
    }


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(String date) {
        try {
            this.date = (Date) dateFormat.parse(date);
        } catch (ParseException p){
            p.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
//    public String getShortText() {
//        String temp = fullText.replaceAll("\n", " ");
//        if (temp.length() > 20) {
//            return temp.substring(0, 20) + "...";
//        } else {
//            return temp;
//        }
//    }

//    public boolean isFullDisplayed() {
//        return fullDisplayed;
//    }

//    public void setFullDisplayed(boolean fullDisplayed) {
//        this.fullDisplayed = fullDisplayed;
//    }

//    public DateFormat getDateFormat() {
//        return dateFormat;
//    }

}
