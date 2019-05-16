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

public class CourseActivity extends AppCompatActivity {
    private ListView courseList;
    public static final String cour_id = "cour_id";
    public static final String course_title = "term_title";
    public static final String course_SD = "course_SD";
    public static final String course_ED = "course_ED";
    public static final String course_Status = "course_Status";
    public static final String course_Notes = "course_Notes";
    public static final String course_SDAlert = "course_SDAlert";
    public static final String course_EDAlert = "course_EDAlert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseActivity.this, AddCourse.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //create databdase
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
//populate
        // Course course =  new Course("Math 101", "10-10-18",
        //         "Wait listed", "11-10-18");
        //helper.createCourse(course);
        final List<Course> courses = helper.getAllCourses();
        CourseListAdapter adapter = new CourseListAdapter(this, R.layout.content_course, courses);
        courseList = findViewById(R.id.courses_list);
        courseList.setAdapter(adapter);

       courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(CourseActivity.this, EditCourse.class);

               Course course = courses.get(position);
               intent.putExtra(cour_id, course.getCourseId());
               intent.putExtra(course_title, course.getCourseTitle());
               intent.putExtra(course_SD, course.getCourseStartDate());
               intent.putExtra(course_ED, course.getCourseEndDate());
               intent.putExtra(course_Status, course.getCourseStatus());
               intent.putExtra(course_SDAlert, course.getCourseStartDateAlert());
               intent.putExtra(course_EDAlert, course.getCourseEndDateAlert());
               intent.putExtra(course_Notes, course.getCourseNotes());
               startActivity(intent);

           }
       });
    }

       }