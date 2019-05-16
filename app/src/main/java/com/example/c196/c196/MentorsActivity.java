package com.example.c196.c196;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MentorsActivity extends AppCompatActivity {
    private ListView mentorList;
    public static final String mentor_id = "mentor_id";
    public static final String mentor_name = "mentor_name";
    public static final String mentor_number = "mentor_number";
    public static final String mentor_email = "mentor_email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MentorsActivity.this, AddMentor.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create databdase
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

//populate list for mentor

        // Mentors mentor =  new Mentors("John Doe", "209-111-1111", "test@fake.com");
        // helper.createMentors(mentor);
        final List<Mentors> mentors = helper.getAllMentors();
        MentorListAdapter adapter = new MentorListAdapter(this, R.layout.content_mentors, mentors);
        mentorList = findViewById(R.id.mentors_list);
        mentorList.setAdapter(adapter);

        mentorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MentorsActivity.this, EditMentor.class);

                Mentors mentor = mentors.get(position);
                intent.putExtra(mentor_id, mentor.getMentorId());
                intent.putExtra(mentor_name, mentor.getMentorName());
                intent.putExtra(mentor_number, mentor.getMentorPhone());
                intent.putExtra(mentor_email, mentor.getMentorEmail());
                startActivity(intent);
            }
        });
    }

}
