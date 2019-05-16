package com.example.c196.c196;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditCourse extends AppCompatActivity {

    private Button startDate;
    private Button endDate;
    private EditText titleInput;
    private EditText statusInput;
    private EditText notesInput;
    String endDateResult;
    String startDateResult;
    DBHelper helper;
    long id;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder1;
    private String courseID;
    private ListView courseAssessmentList;
    private ListView courseMentorList;
    public static final String notificationTitle5 = "notificationTitle5";
    public static final String notificationID5 = "notificationID5";
    public static final String notificationMSG5 = "notificationMSG5";
    public static final String notificationTitle6 = "notificationTitle6";
    public static final String notificationID6 = "notificationID6";
    public static final String notificationMSG6 = "notificationMSG6";

    private CheckBox SDAlert;
    private CheckBox EDAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DBHelper(this);
        setContentView(R.layout.activity_edit_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleInput = (EditText) findViewById(R.id.courseTitleInput);
        statusInput = (EditText) findViewById(R.id.statusInput);
        notesInput = (EditText) findViewById(R.id.notesInput);
        startDate = (Button) findViewById(R.id.courseStartDateInput);
        endDate = (Button) findViewById(R.id.courseEndDateInput);
        SDAlert = (CheckBox) findViewById(R.id.startDateAlert);
        EDAlert = (CheckBox) findViewById(R.id.endDateAlert);

        builder = new AlertDialog.Builder(EditCourse.this);
        builder.setMessage("Course Updated Successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EditCourse.this, CourseActivity.class);
                        startActivity(intent);
                    }
                });


        courseID = getIntent().getStringExtra(CourseActivity.cour_id);
        System.out.println("Course ID: " + " " + courseID);
        String courseTitle = getIntent().getStringExtra(CourseActivity.course_title);
        String courseSD = getIntent().getStringExtra(CourseActivity.course_SD);
        String courseED = getIntent().getStringExtra(CourseActivity.course_ED);
        String courseStatus = getIntent().getStringExtra(CourseActivity.course_Status);
        final String courseNotes = getIntent().getStringExtra(CourseActivity.course_Notes);
        String courseSDAlert = getIntent().getStringExtra(CourseActivity.course_SDAlert);
        String courseEDAlert = getIntent().getStringExtra(CourseActivity.course_EDAlert);



        titleInput.setText(courseTitle);
        startDate.setText(courseSD);
        endDate.setText(courseED);
        statusInput.setText(courseStatus);
        notesInput.setText(courseNotes);

        final List<Assessments> assessments = helper.getAllAssessments();
        final List<String> assessmentTitles = new ArrayList<String>();
        final List<Assessments> assessmentsFiltered = new ArrayList<Assessments>();
        int size=assessments.size();

        for(int i=0; i<size ;i++) {
            Assessments tempAssessment = assessments.get(i);
            if (tempAssessment.getCourseId() == null ||  tempAssessment.getCourseId().equals(courseID)) {
                assessmentsFiltered.add(tempAssessment);
                assessmentTitles.add(tempAssessment.getAssessmentTitle());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, assessmentTitles);
        courseAssessmentList = findViewById(R.id.coursesAssessment_list);
        courseAssessmentList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        courseAssessmentList.setAdapter(adapter);
        courseAssessmentList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        ListView  listA = courseAssessmentList;

        for (int i = 0; i<courseAssessmentList.getCount(); i++){
            if (assessmentsFiltered.get(i).getCourseId() != null && assessmentsFiltered.get(i).getCourseId().equals(courseID)) {
                listA.setItemChecked(i, true);
            }
        }



        final List<Mentors> mentors = helper.getAllMentors();
        final List<String> mentorNames = new ArrayList<String>();
        final List<Mentors> mentorsFiltered = new ArrayList<Mentors>();
        int size1=mentors.size();

        for(int i=0; i<size1 ;i++) {
            Mentors tempMentor = mentors.get(i);
            if (tempMentor.getCourseId() == null) {
                mentorsFiltered.add(tempMentor);
                mentorNames.add(tempMentor.getMentorName());
            } else if (tempMentor.getCourseId().equals(courseID)){
                mentorsFiltered.add(tempMentor);
                mentorNames.add(tempMentor.getMentorName());
            }
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mentorNames);
        courseMentorList = findViewById(R.id.coursesMentors_list);
        courseMentorList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        courseMentorList.setAdapter(adapter1);

        courseMentorList.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        ListView  listM = courseMentorList;

        for (int i = 0; i<courseMentorList.getCount(); i++){
            if (mentorsFiltered.get(i).getCourseId() != null && mentorsFiltered.get(i).getCourseId().equals(courseID)) {
                listM.setItemChecked(i, true);
            }
        }
        if (courseSDAlert != null) {
            SDAlert.setChecked(true);
        }

        if (courseEDAlert != null) {
            EDAlert.setChecked(true);
        }
Button shareNotes = (Button) findViewById(R.id.shareNotesbutton);
        shareNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.putExtra("sms_body", courseNotes);
                smsIntent.setData(Uri.parse("sms:"));

                startActivity(smsIntent);



            } });






        Button fab = (Button) findViewById(R.id.updateButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titleInput.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter a course title.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (startDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select a start date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (endDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select an end date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (statusInput.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter a course status.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if (SDAlert.isChecked() && EDAlert.isChecked()) {
                        Course course = new Course();
                        course.setCourseId(courseID);
                        course.setCourseTitle(titleInput.getText().toString());
                        course.setCourseStartDate(startDate.getText().toString());
                        course.setCourseEndDate(endDate.getText().toString());
                        course.setCourseStatus(statusInput.getText().toString());
                        course.setCourseNotes(notesInput.getText().toString());
                        course.setCourseStartDateAlert(courseID);
                        course.setCourseEndDateAlert(courseID + 1);
                        helper.updateCourse(course);

                        String notfiID = String.valueOf(courseID);
                        String notfi1 = String.valueOf(courseID + 1);
                        String SD = startDate.getText().toString();
                        int finalMonth = (Integer.parseInt(SD.substring(0, 2))) - 1;
                        int finalDay = Integer.parseInt(SD.substring(3, 5));
                        int finalYear = Integer.parseInt(SD.substring(6, 10));

                        Calendar cal = Calendar.getInstance();

                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(finalYear,  finalMonth, finalDay);


                        Intent intent=new Intent(EditCourse.this, NotificationPub.class);
                        intent.putExtra(notificationTitle5, "Course Notification");
                        intent.putExtra(notificationID5, notfiID);
                        intent.putExtra(notificationMSG5, "Your course: " + titleInput.getText().toString() + " " + "start date is today.");

                        intent.putExtra(notificationTitle6, "Course Notification");
                        intent.putExtra(notificationID6, notfi1);
                        intent.putExtra(notificationMSG6, "Your course: " + titleInput.getText().toString() + " " + "end date is today.");

                        PendingIntent sender= PendingIntent.getBroadcast(EditCourse.this,Integer.parseInt(notfiID),intent,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);

                        String ED = endDate.getText().toString();
                        int finalMonth1 = (Integer.parseInt(ED.substring(0, 2))) - 1;
                        int finalDay1 = Integer.parseInt(ED.substring(3, 5));
                        int finalYear1 = Integer.parseInt(ED.substring(6, 10));

                        Calendar cal1 = Calendar.getInstance();

                        cal1.setTimeInMillis(System.currentTimeMillis());
                        cal1.clear();
                        cal1.set(finalYear1,  finalMonth1, finalDay1);




                        PendingIntent sender1= PendingIntent.getBroadcast(EditCourse.this, Integer.parseInt(notfi1),intent,0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis() + 3000, sender1);


                    }  else if ( SDAlert.isChecked() && !EDAlert.isChecked()){
                        Course course = new Course();
                        course.setCourseId(courseID);
                        course.setCourseTitle(titleInput.getText().toString());
                        course.setCourseStartDate(startDate.getText().toString());
                        course.setCourseEndDate(endDate.getText().toString());
                        course.setCourseStatus(statusInput.getText().toString());
                        course.setCourseNotes(notesInput.getText().toString());
                        course.setCourseStartDateAlert(courseID);
                        course.setCourseEndDateAlert(null);
                        helper.updateCourse(course);

                        String notfiID = String.valueOf(courseID);
                        String notfi1 = String.valueOf(courseID + 1);
                        String SD = startDate.getText().toString();
                        int finalMonth = (Integer.parseInt(SD.substring(0, 2))) - 1;
                        int finalDay = Integer.parseInt(SD.substring(3, 5));
                        int finalYear = Integer.parseInt(SD.substring(6, 10));

                        Calendar cal = Calendar.getInstance();

                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(finalYear,  finalMonth, finalDay);


                        Intent intent=new Intent(EditCourse.this, NotificationPub.class);
                        intent.putExtra(notificationTitle5, "Course Notification");
                        intent.putExtra(notificationID5, notfiID);
                        intent.putExtra(notificationMSG5, "Your course: " + titleInput.getText().toString() + " " + "start date is today.");


                        PendingIntent sender= PendingIntent.getBroadcast(EditCourse.this,Integer.parseInt(notfiID),intent,0);
                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);


                    }  else if ( !SDAlert.isChecked() && EDAlert.isChecked()){
                        Course course = new Course();
                        course.setCourseId(courseID);
                        course.setCourseTitle(titleInput.getText().toString());
                        course.setCourseStartDate(startDate.getText().toString());
                        course.setCourseEndDate(endDate.getText().toString());
                        course.setCourseStatus(statusInput.getText().toString());
                        course.setCourseNotes(notesInput.getText().toString());
                        course.setCourseStartDateAlert(null);
                        course.setCourseEndDateAlert(courseID + 1);
                        helper.updateCourse(course);

                        String notfiID = String.valueOf(courseID);
                        String notfi1 = String.valueOf(courseID + 1);


                        Intent intent=new Intent(EditCourse.this, NotificationPub.class);

                        intent.putExtra(notificationTitle6, "Course Notification");
                        intent.putExtra(notificationID6, notfi1);
                        intent.putExtra(notificationMSG6, "Your course: " + titleInput.getText().toString() + " " + "end date is today.");

                        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

                        String ED = endDate.getText().toString();
                        int finalMonth1 = (Integer.parseInt(ED.substring(0, 2))) - 1;
                        int finalDay1 = Integer.parseInt(ED.substring(3, 5));
                        int finalYear1 = Integer.parseInt(ED.substring(6, 10));

                        Calendar cal1 = Calendar.getInstance();

                        cal1.setTimeInMillis(System.currentTimeMillis());
                        cal1.clear();
                        cal1.set(finalYear1,  finalMonth1, finalDay1);




                        PendingIntent sender1= PendingIntent.getBroadcast(EditCourse.this, Integer.parseInt(notfi1),intent,0);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis() + 3000, sender1);



                    }  else if ( !SDAlert.isChecked() && !EDAlert.isChecked()) {

                        Course course = new Course();
                        course.setCourseId(courseID);
                        course.setCourseTitle(titleInput.getText().toString());
                        course.setCourseStartDate(startDate.getText().toString());
                        course.setCourseEndDate(endDate.getText().toString());
                        course.setCourseStatus(statusInput.getText().toString());
                        course.setCourseNotes(notesInput.getText().toString());
                        course.setCourseStartDateAlert(null);
                        course.setCourseEndDateAlert(null);
                        helper.updateCourse(course);

                    }


                    }



                SparseBooleanArray boolArray = courseAssessmentList.getCheckedItemPositions();
                int size = courseAssessmentList.getCount();
                if (courseAssessmentList.getCheckedItemCount() != 0) {
                    for (int i = 0; i < size; i++) {
                        if (boolArray.get(i)) {
                            Assessments temp = assessmentsFiltered.get(i);
                            temp.setCourseId(courseID);
                            helper.updateAssessment(temp);
                        } else if (!boolArray.get(i)) {
                            Assessments temp = assessmentsFiltered.get(i);
                            temp.setCourseId(null);
                            helper.updateAssessment(temp);
                        }
                    }

                } else if (courseAssessmentList.getCheckedItemCount() == 0) {
                    for (int i = 0; i < size; i++) {
                        Assessments temp = assessmentsFiltered.get(i);
                        temp.setCourseId(null);
                        helper.updateAssessment(temp);
                    }

                }

                SparseBooleanArray boolArray1 = courseMentorList.getCheckedItemPositions();
                int size1 = courseMentorList.getCount();
                if (courseMentorList.getCheckedItemCount() != 0) {
                    for (int i = 0; i < size1; i++) {
                        if (boolArray1.get(i) == true) {
                            Mentors temp = mentorsFiltered.get(i);
                            temp.setCourseId(courseID);
                            helper.updateMentors(temp);
                        } else if (!boolArray.get(i)) {
                            Mentors temp = mentorsFiltered.get(i);
                            temp.setCourseId(null);
                            helper.updateMentors(temp);
                        }
                    }


                } else if (courseMentorList.getCheckedItemCount() == 0) {
                    for (int i = 0; i < size1; i++) {
                        Mentors temp = mentorsFiltered.get(i);
                        temp.setCourseId(null);
                        helper.updateMentors(temp);
                    }

                }   AlertDialog alert = builder.create();
                alert.show();
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

                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, startDateSetListener, year, month, day);
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

                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, endDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String finalMonth;
                String finalDay;

                if (month <= 9) {
                    finalMonth = "0" + month;
                } else {
                    finalMonth = String.valueOf(month);
                }


                if (day <= 9) {
                    finalDay = "0" + day;
                } else {

                    finalDay = String.valueOf(day);
                }

                startDateResult = finalMonth + "/" + finalDay + "/" + year;
                startDate.setText(startDateResult);
            }
        };


        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String finalMonth;
                String finalDay;

                if (month <= 9) {
                    finalMonth = "0" + month;
                } else {
                    finalMonth = String.valueOf(month);
                }


                if (day <= 9) {
                    finalDay = "0" + day;
                } else {

                    finalDay = String.valueOf(day);
                }

                endDateResult = finalMonth + "/" + finalDay + "/" + year;
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
        builder1 = new AlertDialog.Builder(EditCourse.this);
        builder1.setMessage("Are you sure you want to delete this course?"  )
                .setCancelable(true)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        helper.deleteCourse(Long.parseLong(courseID));
                        Intent intent = new Intent(EditCourse.this, CourseActivity.class);
                        startActivity(intent);
                    }
                });

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog alert = builder1.create();
            alert.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
