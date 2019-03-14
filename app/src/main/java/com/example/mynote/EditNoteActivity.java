package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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

        editTitle = (EditText) findViewById(R.id.editTitle);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        editFullText = (EditText) findViewById(R.id.editFullText);
        btnSave = (Button) findViewById(R.id.btnSave);
        textDate = findViewById(R.id.textDate);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            initNote(bundle.getInt("noteid"));
        } else {
            note = new Note();
            textDate.setText("Created on: " + note.getDate());
        }


        setEditing(true);
        initChangedText();
        initChangedPriority();
        initSaveBtn();
        initCancelBtn();
    }

    //save after done adding or editing note
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

    //cancel if does not want to save
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

    //enable edit title and date
    private void setEditing(boolean enabled) {
        EditText editTitle = findViewById(R.id.editTitle);
        EditText editBody = findViewById(R.id.editFullText);

        editTitle.setEnabled(enabled);
        editBody.setEnabled(enabled);
    }

    //able to change text
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

    public void initNote(int id) {
        NoteDataSource ds = new NoteDataSource(EditNoteActivity.this);

        try {
            ds.open();
            note = ds.getSpecificNote(id);
            ds.close();
        } catch(Exception e) {
            Toast.makeText(this, "Load FAILED", Toast.LENGTH_LONG).show();
        }

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editBody = findViewById(R.id.editFullText);
        TextView textDate = findViewById(R.id.textDate);

        setPriority(note.getPriority());
        editTitle.setText(note.getTitle());
        editBody.setText(note.getFullText());
        textDate.setText("Created on: " + note.getDate());
    }

    private void initChangedPriority() {
        RadioGroup rgPriority = findViewById(R.id.rgPriority);

        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbLow = findViewById(R.id.radioLow);
                RadioButton rbMed = findViewById(R.id.radioMed);
                RadioButton rbHigh = findViewById(R.id.radioHigh);

                if(rbLow.isChecked()) {
                    note.setPriority("low");
                } else if(rbMed.isChecked()) {
                    note.setPriority("med");
                } else {
                    note.setPriority("high");
                }
            }
        });
    }

    private void setPriority(String p) {
        RadioButton rbLow = findViewById(R.id.radioLow);
        RadioButton rbMed = findViewById(R.id.radioMed);
        RadioButton rbHigh = findViewById(R.id.radioHigh);

        if(p.equalsIgnoreCase("low")) {
            rbLow.setChecked(true);
        } else if(p.equalsIgnoreCase("med")) {
            rbMed.setChecked(true);
        } else {
            rbHigh.setChecked(true);
        }
    }
}
