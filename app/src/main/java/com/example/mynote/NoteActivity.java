package com.example.mynote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {

    private Button btnAdd;
    private Button btnDelete;
    private Button btnBack;
    ImageButton btnSort;
    private NoteDataSource dbAccess;
    NoteAdapter adapter;
    ArrayList<Note> note;
    boolean isDeleting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSort = findViewById(R.id.btnSort);

        initAddBtn();
        initSortBtn();
        initDeleteBtn();
        initSortPrefs();
        saveOrderBy();
        saveSortBy();
        savePriorityBy();
        initItemClick();

        isDeleting = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNoteList();
    }

    //add button will go from main page to add/edit page
    public void initAddBtn() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    //back button will go back to main page after setting
    public void initBackBtn() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            RelativeLayout layoutNoteList = findViewById(R.id.layoutNoteList);
            RelativeLayout layoutSort = findViewById(R.id.layoutSort);

            @Override
            public void onClick(View v) {
                layoutSort.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);

                layoutNoteList.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);

                loadNoteList();
            }
        });
    }

    //delete button at bottom to select show or hide delete button next to each note
    public void initDeleteBtn() {
        final Button deleteBtn = (Button) findViewById(R.id.btnDelete);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeleting) {
                    deleteBtn.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();
                }
                else {
                    deleteBtn.setText("Done!");
                    isDeleting = true;
                }

            }
        });
    }

    //click each note to edit
    private void initItemClick(){
       ListView listView = (ListView)findViewById(R.id.listViewNotes);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView <?> parent, View itemClicked, int position,long id){
               Note selectedNote = note.get(position)  ;

               if(isDeleting){
                   adapter.showDelete(position,itemClicked,NoteActivity.this, selectedNote);
                 
               }else{
                   Intent intent = new Intent(NoteActivity.this, EditNoteActivity.class);
                   intent.putExtra("noteid",selectedNote.getNoteId());
                   startActivity(intent);
               }
           }
        });
    }

    //sort button to sort
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

                    loadNoteList();
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

    //select sort by date or title and asc or desc
    private void initSortPrefs() {
        String orderBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("orderby", "ASC");
        String sortBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("sortby", "title");
        String priorityBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("priorityby", "low");

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

        RadioButton rbLow = findViewById(R.id.rbLow);
        RadioButton rbMed = findViewById(R.id.rbMed);
        RadioButton rbHigh = findViewById(R.id.rbHigh);

        if(priorityBy.equalsIgnoreCase("low")) {
            rbLow.setChecked(true);
        } else if(priorityBy.equalsIgnoreCase("med")) {
            rbMed.setChecked(true);
        } else {
            rbHigh.setChecked(true);
        }
    }

    //save after selecting sort
    private void saveOrderBy() {
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
    }

    private void saveSortBy() {
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

    private void savePriorityBy() {
        RadioGroup rgPriorityBy = findViewById(R.id.rgPriorityBy);

        rgPriorityBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbLow = findViewById(R.id.rbLow);
                RadioButton rbMed = findViewById(R.id.rbMed);
                RadioButton rbHigh = findViewById(R.id.rbHigh);

                if(rbLow.isChecked()) {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("priorityby", "low")
                            .commit();
                } else if(rbMed.isChecked()) {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("priorityby", "med")
                            .commit();
                } else {
                    getSharedPreferences("SortingPreferences", Context.MODE_PRIVATE)
                            .edit()
                            .putString("priorityby", "high")
                            .commit();
                }
            }
        });
    }

    private void loadNoteList() {
        String orderBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("orderby", "ASC");
        String sortBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("sortby", "title");
        String priorityBy = getSharedPreferences("SortingPreferences",
                Context.MODE_PRIVATE).getString("priorityby", "low");

        NoteDataSource ds = new NoteDataSource(this);

        try {
            ds.open();
            note = ds.getNote(priorityBy, orderBy, sortBy);
            ds.close();

            ListView listView = (ListView) findViewById(R.id.listViewNotes);
            adapter = new NoteAdapter(this, note);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving note", Toast.LENGTH_LONG).show();
        }
    }
}
