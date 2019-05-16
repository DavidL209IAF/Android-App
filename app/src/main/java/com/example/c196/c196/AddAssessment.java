package com.example.c196.c196;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AddAssessment extends AppCompatActivity {

    private Button dueDate;
    private Button goalDate;
    private EditText titleInput;
    private CheckBox objAssessment;
    private CheckBox perAssessment;
    private CheckBox GDAlert;
    String dueDateResult;
    String goalDateResult;
    DBHelper helper;
    private DatePickerDialog.OnDateSetListener dueDateSetListener;
    private DatePickerDialog.OnDateSetListener goalDateSetListener;
    private AlertDialog.Builder builder;
    String id;
    String month;
    String day;
    String year;
    public static final String notificationTitle4 = "notificationTitle4";
    public static final String notificationID4 = "notificationID4";
    public static final String notificationMSG4 = "notificationMSG4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        titleInput = (EditText) findViewById(R.id.assessmentTitleInput);
        objAssessment = (CheckBox) findViewById(R.id.objCheckBox);
        perAssessment = (CheckBox) findViewById(R.id.perCheckBox);
        GDAlert = (CheckBox) findViewById(R.id.GDAlertCheckBox);

        builder = new AlertDialog.Builder(AddAssessment.this);
        builder.setMessage("Assessment Added Successfully."  )
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddAssessment.this, AssessmentsActivity.class);
                        startActivity(intent);
                    }
                });

        Button fab = (Button) findViewById(R.id.saveButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (titleInput.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter an assessment title.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (dueDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select a due date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (goalDate.getText().toString().equals("Select Date")) {
                    Snackbar.make(view, "Please select a goal date.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if ( !objAssessment.isChecked() && !perAssessment.isChecked()) {

                    Snackbar.make(view, "Please select an assessment tyoe.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                    else
                 {

                     if (objAssessment.isChecked()) {
                         if (GDAlert.isChecked()) {
                             Assessments assessment = new Assessments();
                             assessment.setAssessmentTitle(titleInput.getText().toString());
                             assessment.setAssessment(objAssessment.getText().toString());
                             assessment.setAssessmentDueDate(dueDate.getText().toString());
                             assessment.setAssessmentGoalDate(goalDate.getText().toString());
                             assessment.setAssessmentGoalDateAlert(String.valueOf(id));
                             String GD = goalDate.getText().toString();
                            String objId = String.valueOf(helper.createAssessment(assessment));
                            assessment.setAssessmentGoalDateAlert(objId);
                             assessment.setAssessmentId(objId);
                             String objId1 = String.valueOf(helper.updateAssessment(assessment));
                             int finalMonth = (Integer.parseInt(GD.substring(0, 2))) - 1;
                             int finalDay = Integer.parseInt(GD.substring(3, 5));
                             int finalYear = Integer.parseInt(GD.substring(6, 10));




                             Calendar cal = Calendar.getInstance();

                             cal.setTimeInMillis(System.currentTimeMillis());
                             cal.clear();
                             cal.set(finalYear,  finalMonth, finalDay);


                             Intent intent=new Intent(AddAssessment.this, NotificationPub.class);
                             intent.putExtra(notificationTitle4, "Assessment Notification");
                             intent.putExtra(notificationID4, objId);
                             intent.putExtra(notificationMSG4, "Your assessment: " + titleInput.getText().toString() + " " + "goal date is today.");
                             PendingIntent sender= PendingIntent.getBroadcast(AddAssessment.this,Integer.parseInt(objId),intent,0);
                             AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                             alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 3000, sender);







                         } else if ( !GDAlert.isChecked()) {
                             Assessments assessment = new Assessments();
                             assessment.setAssessmentTitle(titleInput.getText().toString());
                             assessment.setAssessment(objAssessment.getText().toString());
                             assessment.setAssessmentDueDate(dueDate.getText().toString());
                             assessment.setAssessmentGoalDate(goalDate.getText().toString());
                             assessment.setAssessmentGoalDateAlert(null);
                             String objId = String.valueOf(helper.createAssessment(assessment));



                         }
                         AlertDialog alert = builder.create();
                         alert.show();
                     } else if (perAssessment.isChecked()) {

                         if (GDAlert.isChecked()) {
                             Assessments assessment = new Assessments();
                             assessment.setAssessmentTitle(titleInput.getText().toString());
                             assessment.setAssessment(perAssessment.getText().toString());
                             assessment.setAssessmentDueDate(dueDate.getText().toString());
                             assessment.setAssessmentGoalDate(goalDate.getText().toString());
                             assessment.setAssessmentGoalDateAlert(String.valueOf(id));
                             String GD = goalDate.getText().toString();
                             String perId = String.valueOf(helper.createAssessment(assessment));
                             assessment.setAssessmentGoalDateAlert(perId);
                             assessment.setAssessmentId(perId);
                             helper.updateAssessment(assessment);

                             int finalMonth = (Integer.parseInt(GD.substring(0, 2))) - 1;
                             int finalDay = Integer.parseInt(GD.substring(3, 5));
                             int finalYear = Integer.parseInt(GD.substring(6, 10));




                             Calendar cal = Calendar.getInstance();

                             cal.setTimeInMillis(System.currentTimeMillis());
                             cal.clear();
                             cal.set(finalYear,  finalMonth, finalDay);


                             Intent intent=new Intent(AddAssessment.this, NotificationPub.class);
                             intent.putExtra(notificationTitle4, "Assessment Notificaation");
                             intent.putExtra(notificationID4, perId);
                             intent.putExtra(notificationMSG4, "Your assessment: " + titleInput.getText().toString() + " " + "goal date is today.");
                             PendingIntent sender= PendingIntent.getBroadcast(AddAssessment.this,Integer.parseInt(perId),intent,0);
                             AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                             alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 3000, sender);


                         } else if ( !GDAlert.isChecked()) {

                             Assessments assessment = new Assessments();
                             assessment.setAssessmentTitle(titleInput.getText().toString());
                             assessment.setAssessment(perAssessment.getText().toString());
                             assessment.setAssessmentDueDate(dueDate.getText().toString());
                             assessment.setAssessmentGoalDate(goalDate.getText().toString());
                             assessment.setAssessmentGoalDateAlert(null);
                            String perId = String.valueOf(helper.createAssessment(assessment));
                         }
                         AlertDialog alert = builder.create();
                         alert.show();
                 }
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dueDate = (Button) findViewById(R.id.assessmentDueDateInput);
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(AddAssessment.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, dueDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        goalDate = (Button) findViewById(R.id.goalDateInput);
        goalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddAssessment.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, goalDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        dueDateSetListener = new DatePickerDialog.OnDateSetListener() {
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

                dueDateResult = finalMonth + "/" + finalDay + "/" + year;
                dueDate.setText(dueDateResult);
            }
        };


        goalDateSetListener = new DatePickerDialog.OnDateSetListener() {
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

                goalDateResult = finalMonth + "/" + finalDay + "/" + year;
                goalDate.setText(goalDateResult);
            }
        };

        objAssessment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (objAssessment.isChecked())
                    perAssessment.setChecked(false);
            }
        });

        perAssessment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (perAssessment.isChecked())
                    objAssessment.setChecked(false);
            }
        });




    }



}
