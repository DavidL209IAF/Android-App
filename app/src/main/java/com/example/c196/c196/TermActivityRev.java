package com.example.c196.c196;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class TermActivityRev extends AppCompatActivity {
    private ListView termList;
    public static final String term_id = "term_id";
    public static final String term_title = "term_title";
    public static final String term_SD = "term_SD";
    public static final String term_ED = "term_ED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_rev);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TermActivityRev.this, AddTerm.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create databdase
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

//populate list for term

        // Term term =  new Term("Fall Term", "10-10-18", "11-10-18");
        // helper.createTerm(term);
       final List<Term> terms = helper.getAllTerms();
        TermListAdapter adapter = new TermListAdapter(this, R.layout.content_term_activity_rev, terms);
        termList = findViewById(R.id.terms_list);
        termList.setAdapter(adapter);

        termList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TermActivityRev.this, EditTerm.class);

                Term term = terms.get(position);
                intent.putExtra(term_id, term.getTermId());
                intent.putExtra(term_title, term.getTermTitle());
                intent.putExtra(term_SD, term.getTermStartDate());
                intent.putExtra(term_ED, term.getTermEndDate());
                startActivity(intent);
            }
        });
    }

}
