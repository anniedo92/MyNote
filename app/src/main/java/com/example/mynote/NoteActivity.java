package com.example.mynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NoteActivity extends Activity {
    private ListView listView;
    private Button btnAdd;
    private Button btnDelete;
    private NoteDataSource dbAccess;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });


    }


    private void initTextChangedEvents(){
        final EditText editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //  Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                currentNote.setTitle(editTitle.getText().toString());
            }
        });

        final EditText editFullText = (EditText) findViewById(R.id.editFullText);
        editFullText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentNote.setFullText(editFullText.getText().toString());
            }
        });
    }

    public void onAddClicked() {
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivity(intent);
    }

}
