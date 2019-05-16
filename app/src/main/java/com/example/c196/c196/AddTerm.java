package com.example.c196.c196;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTerm extends AppCompatActivity {
private Button startDate;
private Button endDate;
private EditText titleInput;
String endDateResult;
String startDateResult;
DBHelper helper;
private DatePickerDialog.OnDateSetListener startDateSetListener;
private DatePickerDialog.OnDateSetListener endDateSetListener;
private AlertDialog.Builder builder;
    long id;
    private ListView termCourseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleInput = (EditText) findViewById(R.id.termTitleInput);

        final List<Course> courses = helper.getAllCourses();
        final List<String> courseTitles = new ArrayList<String>();
        final List<Course> courseFiltered = new ArrayList<Course>();
        int size=courses.size();

        for(int i=0; i<size ;i++) {
            Course tempCourse = courses.get(i);
            if (tempCourse.getTermId() == null) {
                courseFiltered.add(tempCourse);
                courseTitles.add(tempCourse.getCourseTitle());
                System.out.println(tempCourse.getCourseId() + tempCourse.getCourseTitle());
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, courseTitles);
        termCourseList = findViewById(R.id.termsCourse_list);
        termCourseList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        termCourseList.setAdapter(adapter);

        builder = new AlertDialog.Builder(AddTerm.this);
        builder.setMessage("Term Added Successfully."  )
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddTerm.this, TermActivityRev.class);
                        startActivity(intent);
                    }
                });

        Button fab = (Button) findViewById(R.id.saveButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titleInput.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter a term title.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (startDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select a start date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (endDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select an end date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    Term term = new Term();
                    term.setTermTitle(titleInput.getText().toString());
                    term.setTermStartDate(startDate.getText().toString());
                    term.setTermEndDate(endDate.getText().toString());
                    id = helper.createTerm(term);

                   SparseBooleanArray boolArray=termCourseList.getCheckedItemPositions();
                    int size=termCourseList.getCount();
                    System.out.println(size);
                    if (termCourseList.getCheckedItemCount() != 0) {
                        for (int i = 0; i < size; i++) {

                            if (boolArray.get(i)) {
                                Course temp = courseFiltered.get(i);
                                System.out.println(temp.getCourseId() + temp.getCourseTitle());
                                temp.setTermId(Long.toString(id));
                                helper.updateCourse(temp);
                            }
                        }


                    }  AlertDialog alert = builder.create();
                    alert.show();
                }

                }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startDate = (Button) findViewById(R.id.termStartDateInput);
        startDate.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTerm.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, startDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDate = (Button) findViewById(R.id.termEndDateInput);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTerm.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, endDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                startDateResult = month + "/" + day + "/" + year;
                startDate.setText(startDateResult);
            }
        };


        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                endDateResult = month + "/" + day + "/" + year;
                endDate.setText(endDateResult);
            }
        };





    }



}
