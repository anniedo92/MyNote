package com.example.mynote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        this.editText = (EditText) findViewById(R.id.editText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            note = (Note) bundle.get("NOTE");
            if(note != null) {
                this.editText.setText(note.getFullText());
            }
        }

//        this.btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSaveClicked();
//
//            }
//        });
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
}
