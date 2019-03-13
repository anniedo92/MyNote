package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class EditNoteActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editFullText;
    private Button btnSave;
    private Button btnCancel;
    private Note note;
    private TextView textDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        this.editTitle = (EditText) findViewById(R.id.editTitle);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        this.editFullText = (EditText) findViewById(R.id.editFullText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        textDate = findViewById(R.id.textDate);

        note = new Note();

        initSaveBtn();
        initCancelBtn();

        Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                note = (Note) bundle.get("NOTE");
                if (note != null) {
                    this.editTitle.setText(note.getTitle());
                    this.editFullText.setText(note.getFullText());
                    textDate.setText("Created on: " + note.getDate());
                }
            }


        setEditing(true);
        initChangedText();
    }

    private void initSaveBtn() {

            Button btnSave = (Button) findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean success = false;
                NoteDataSource ds = new NoteDataSource(EditNoteActivity.this);
                try {
                    ds.open();

                    if (note.getNoteId()== -1) {
                        success = ds.insertNote(note);
                        int newId = ds.getLastNoteId();
                        note.setNoteId(newId);
                    } else{
                        success = ds.updateNote(note);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    success = false;
                }
                if (success) {
                    Intent intent = new Intent(EditNoteActivity.this, NoteActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    //CANCEL BUTTON ACTION
    private void initCancelBtn() {
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            RelativeLayout layoutNoteList = findViewById(R.id.layoutNoteList);
            RelativeLayout layoutEdit = findViewById(R.id.activity_edit_note);
            @Override
            public void onClick(View v) {
                //EditNoteActivity.super.onBackPressed();
                //layoutEdit.setVisibility(View.GONE);
                //Intent intent = new Intent(EditNoteActivity.this, NoteActivity.class);
                //startActivity(intent);
                finish();
                //layoutNoteList.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initNote(int noteId) {

    }

    private void setEditing(boolean enabled) {
        EditText editTitle = findViewById(R.id.editTitle);
        EditText editBody = findViewById(R.id.editFullText);

        editTitle.setEnabled(enabled);
        editBody.setEnabled(enabled);
    }

    private void initChangedText() {
        final EditText editTitle = findViewById(R.id.editTitle);
        final EditText editBody = findViewById(R.id.editFullText);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setTitle(editTitle.getText().toString());
            }
        });

        editBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setFullText(editBody.getText().toString());
            }
        });
    }
}
