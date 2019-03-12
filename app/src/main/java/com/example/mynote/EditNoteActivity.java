package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {
    private EditText editText;
    private Button btnSave;
    private Button btnCancel;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        this.editText = (EditText) findViewById(R.id.editTitle);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        this.editText = (EditText) findViewById(R.id.editFullText);

        note = new Note();
        initSaveBtn();

        Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                note = (Note) bundle.get("NOTE");
                if (note != null) {
                    this.editText.setText(note.getFullText());
                }
            }
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

        private void initCancelBtn() {
            Button btnCancel = (Button) findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

//        private void initSaveButton() {
//        Button saveButton = (Button) findViewById(R.id.buttonSave);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                hideKeyboard();
//                boolean wasSuccessful = false;
//                ContactDataSource ds = new ContactDataSource(ContactActivity.this);
//                try {
//                    ds.open();
//
//                    if (currentContact.getContactID() == -1) {
//                        wasSuccessful = ds.insertContact(currentContact);
//                        int newId = ds.getLastContactId();
//                        currentContact.setContactID(newId);
//                    } else {
//                        wasSuccessful = ds.updateContact(currentContact);
//                    }
//                    ds.close();
//                }
//                catch (Exception e) {
//                    wasSuccessful = false;
//                }
//
//                if (wasSuccessful) {
//                    ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
//                    editToggle.toggle();
//                    setForEditing(false);
//                }
//            }
//        });
//    }
//
//        this.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onCancelClicked();
//            }
//        });
//
//        puclic void onSaveClicked() {
//            NoteDataSource dbAccess = NoteDataSource.getInstance(this);
//            dbAccess.open();
//            if (note == null) {
//                //add new note
//                Note temp = new Note();
//                temp.setFullText(editText.getText().toString());
//                dbAccess.save(temp);
//            } else {
//                note.setFullText(editText.getText().toString());
//                dbAccess.updateNote(note);
//            }
//            dbAccess.close();
//            this.finish();
//        }
//
//        public void onCancelClicked() {
//            this.finish();
//        }

}
