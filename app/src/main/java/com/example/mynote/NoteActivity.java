package com.example.mynote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class NoteActivity extends Activity {
    private ListView listView;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnBack;
    ImageButton btnSort;
    private NoteDataSource dbAccess;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSort = findViewById(R.id.btnSort);

        initAddBtn();
        initSortBtn();
        initSortPrefs();
        savePrefs();
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

    public void initAddBtn() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initBackBtn() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            RelativeLayout layoutNoteList = findViewById(R.id.layoutNoteList);
            RelativeLayout layoutSort = findViewById(R.id.layoutSort);

            @Override
            public void onClick(View v) {
                savePrefs();

                layoutSort.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);

                layoutNoteList.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initDeleteBtn() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initSortBtn() {

        btnSort.setOnClickListener(new View.OnClickListener() {
            RelativeLayout layoutNoteList = findViewById(R.id.layoutNoteList);
            RelativeLayout layoutSort = findViewById(R.id.layoutSort);

            @Override
            public void onClick(View v) {
                if(layoutNoteList.getVisibility() == View.GONE) {
                    layoutSort.setVisibility(View.GONE);
                    btnBack.setVisibility(View.GONE);

                    layoutNoteList.setVisibility(View.VISIBLE);
                    btnAdd.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.VISIBLE);
                } else {
                    layoutNoteList.setVisibility(View.GONE);
                    btnAdd.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.GONE);

                    layoutSort.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);

                    initBackBtn();
                }
            }
        });
    }

    private void initSortPrefs() {
        String orderBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("orderby", "ASC");
        String sortBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("sortby", "title");

        RadioButton rbASC = findViewById(R.id.radioASC);
        RadioButton rbDESC = findViewById(R.id.radioDESC);

        if(orderBy.equalsIgnoreCase("ASC")) {
            rbASC.setChecked(true);
        } else {
            rbDESC.setChecked(true);
        }

        RadioButton rbTitle = findViewById(R.id.radioByTitle);
        RadioButton rbDate = findViewById(R.id.radioByDate);

        if(sortBy.equalsIgnoreCase("title")) {
            rbTitle.setChecked(true);
        } else {
            rbDate.setChecked(true);
        }
    }

    private void savePrefs() {
        RadioGroup rgOrderBy = findViewById(R.id.rgOrderBy);

        rgOrderBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbASC = findViewById(R.id.radioASC);
                RadioButton rbDESC = findViewById(R.id.radioDESC);

                if(rbASC.isChecked()) {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("orderby", "ASC")
                            .commit();
                } else {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("orderby", "DESC")
                            .commit();
                }
            }
        });

        RadioGroup rgSortBy = findViewById(R.id.rgSortBy);

        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbTitle = findViewById(R.id.radioByTitle);
                RadioButton rbDate = findViewById(R.id.radioByDate);

                if(rbTitle.isChecked()) {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("sortby", "title")
                            .commit();
                } else {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("sortby", "date")
                            .commit();
                }
            }
        });
    }

}
