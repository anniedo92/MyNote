package com.example.mynote;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {
    private ListView listView;
    private Button btnAdd;
    private NoteDataSource dbAccess;
    private List<Note> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listView = (ListView) findViewById(R.id.listView);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);


    }
}