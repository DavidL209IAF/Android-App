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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditTerm extends AppCompatActivity {
    private Button startDate;
    private Button endDate;
    private EditText titleInput;
    String endDateResult;
    String startDateResult;
    DBHelper helper;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder1;
    private AlertDialog.Builder builder2;

    long id;
    private int siseDeleteCheck;
    private String termID;
    private ListView termCourseList;
    private List<Course> courseDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleInput = (EditText) findViewById(R.id.termTitleInput);
        startDate = (Button) findViewById(R.id.termStartDateInput);
        endDate = (Button) findViewById(R.id.termEndDateInput);

        builder = new AlertDialog.Builder(EditTerm.this);
        builder.setMessage("Term Updated Successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EditTerm.this, TermActivityRev.class);
                        startActivity(intent);
                    }
                });

        termID = getIntent().getStringExtra(TermActivityRev.term_id);

        String termTitle = getIntent().getStringExtra(TermActivityRev.term_title);
        String termSD = getIntent().getStringExtra(TermActivityRev.term_SD);
        String termED = getIntent().getStringExtra(TermActivityRev.term_ED);

        titleInput.setText(termTitle);
        startDate.setText(termSD);
        endDate.setText(termED);

        final List<Course> courses = helper.getAllCourses();
        final List<String> courseTitles = new ArrayList<String>();
        final List<Course> courseFiltered = new ArrayList<Course>();
        courseDelete = new ArrayList<>();
        int size = courses.size();

        for (int i = 0; i < size; i++) {
            Course tempCourse = courses.get(i);
            if (tempCourse.getTermId() == null) {
                courseFiltered.add(tempCourse);
                courseTitles.add(tempCourse.getCourseTitle());
            } else if (tempCourse.getTermId().equals(termID)){
                courseFiltered.add(tempCourse);
                courseDelete.add(tempCourse);
                courseTitles.add(tempCourse.getCourseTitle());

            }
        }





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, courseTitles);
        termCourseList = findViewById(R.id.termsCourse_list);
        termCourseList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        termCourseList.setAdapter(adapter);

        ListView listV = termCourseList;

        for (int i = 0; i < termCourseList.getCount(); i++) {
            if (courseFiltered.get(i).getTermId() != null && courseFiltered.get(i).getTermId().equals(termID)) {
                listV.setItemChecked(i, true);
            }
        }


        Button fab = (Button) findViewById(R.id.updateButton);
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
                    term.setTermId(termID);
                    term.setTermTitle(titleInput.getText().toString());
                    term.setTermStartDate(startDate.getText().toString());
                    term.setTermEndDate(endDate.getText().toString());
                    id = helper.updateTerm(term);

                    SparseBooleanArray boolArray = termCourseList.getCheckedItemPositions();
                    int size = termCourseList.getCount();

                    if (termCourseList.getCheckedItemCount() != 0) {
                        for (int i = 0; i < size; i++) {

                            if (boolArray.get(i)) {
                                Course temp = courseFiltered.get(i);
                                temp.setTermId(termID);
                                helper.updateCourse(temp);
                            } else if (!boolArray.get(i)) {
                                Course temp = courseFiltered.get(i);
                                temp.setTermId(null);
                                helper.updateCourse(temp);
                            }
                        }


                    } else if (termCourseList.getCheckedItemCount() == 0){
                        for (int i = 0; i < size; i++) {
                            Course temp = courseFiltered.get(i);
                            System.out.println(temp.getCourseTitle());
                            temp.setTermId(null);
                            helper.updateCourse(temp);
                        }



                    }
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTerm.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, startDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditTerm.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, endDateSetListener, year, month, day);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


            builder1 = new AlertDialog.Builder(EditTerm.this);
            builder1.setMessage("Are you sure you want to delete this term?")
                    .setCancelable(true)
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            helper.deleteTerm(Long.parseLong(termID));
                            Intent intent = new Intent(EditTerm.this, TermActivityRev.class);
                            startActivity(intent);
                        }
                    });

        builder2 = new AlertDialog.Builder(EditTerm.this);
        builder2.setMessage("Error deleting term. Please remove all courses in this term before deleting.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_delete) {

                if (courseDelete.size() == 0) {
                    AlertDialog alert = builder1.create();
                    alert.show();
                } else if (courseDelete.size() != 0) {

                    AlertDialog alert = builder2.create();
                    alert.show();
                }

            }



        return super.onOptionsItemSelected(item);

    }
}