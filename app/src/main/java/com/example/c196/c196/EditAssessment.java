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
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class EditAssessment extends AppCompatActivity {

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
    private AlertDialog.Builder builder1;
    long id;
    private String assessIDs;
    public static final String notificationTitle3 = "notificationTitle3";
    public static final String notificationID3 = "notificationID3";
    public static final String notificationMSG3 = "notificationMSG3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        titleInput = (EditText) findViewById(R.id.assessmentTitleInput);
        objAssessment = (CheckBox) findViewById(R.id.objCheckBox);
        perAssessment = (CheckBox) findViewById(R.id.perCheckBox);
        goalDate = (Button) findViewById(R.id.goalDateInput);
        GDAlert = (CheckBox) findViewById(R.id.GDAlertCheckBox);

        builder = new AlertDialog.Builder(EditAssessment.this);
        builder.setMessage("Assessment Updated Successfully."  )
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EditAssessment.this, AssessmentsActivity.class);
                        startActivity(intent);
                    }
                });


        String assesTitle = getIntent().getStringExtra(AssessmentsActivity.assess_title);
        String assessDD = getIntent().getStringExtra(AssessmentsActivity.assess_DD);
        String assessGD = getIntent().getStringExtra(AssessmentsActivity.assess_GD);
        String assess = getIntent().getStringExtra(AssessmentsActivity.assess);
        String assessGDAlert = getIntent().getStringExtra(AssessmentsActivity.assessGDAlert);
        assessIDs = getIntent().getStringExtra(AssessmentsActivity.aid);

        dueDate = (Button) findViewById(R.id.assessmentDueDateInput);

        titleInput.setText(assesTitle);
        goalDate.setText(assessGD);
        dueDate.setText(assessDD);

        if (assess.equals("Objective")) {
            objAssessment.setChecked(true);
        } else if (assess.equals("Performance")) {
            perAssessment.setChecked(true);
        }
        if (assessGDAlert != null) {
            GDAlert.setChecked(true);
        }

        Button fab = (Button) findViewById(R.id.updateButton);
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
                        assessment.setAssessmentId(assessIDs);
                        assessment.setAssessmentTitle(titleInput.getText().toString());
                        assessment.setAssessment(objAssessment.getText().toString());
                        assessment.setAssessmentDueDate(dueDate.getText().toString());
                        assessment.setAssessmentGoalDate(goalDate.getText().toString());
                        assessment.setAssessmentGoalDateAlert(String.valueOf(assessIDs));
                        String objId = String.valueOf(helper.updateAssessment(assessment));

                            String GD = goalDate.getText().toString();
                            int finalMonth = (Integer.parseInt(GD.substring(0, 2))) - 1;
                            int finalDay = Integer.parseInt(GD.substring(3, 5));
                            int finalYear = Integer.parseInt(GD.substring(6, 10));




                            Calendar cal = Calendar.getInstance();

                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.clear();
                            cal.set(finalYear,  finalMonth, finalDay);


                            Intent intent=new Intent(EditAssessment.this, NotificationPub.class);
                            intent.putExtra(notificationTitle3, "Assessment Notification");
                            intent.putExtra(notificationID3, objId);
                            intent.putExtra(notificationMSG3, "Your assessment: " + titleInput.getText().toString() + " " + "goal date is today.");
                            PendingIntent sender= PendingIntent.getBroadcast(EditAssessment.this,Integer.parseInt(objId),intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 3000, sender);


                        }  else if ( !GDAlert.isChecked()) {
                            Assessments assessment = new Assessments();
                            assessment.setAssessmentId(assessIDs);
                            assessment.setAssessmentTitle(titleInput.getText().toString());
                            assessment.setAssessment(objAssessment.getText().toString());
                            assessment.setAssessmentDueDate(dueDate.getText().toString());
                            assessment.setAssessmentGoalDate(goalDate.getText().toString());
                            assessment.setAssessmentGoalDateAlert(null);
                            String objId = String.valueOf(helper.updateAssessment(assessment));
                        }
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else if (perAssessment.isChecked()) {
                        if (GDAlert.isChecked()) {


                            Assessments assessment = new Assessments();
                            assessment.setAssessmentId(assessIDs);
                            assessment.setAssessmentTitle(titleInput.getText().toString());
                            assessment.setAssessment("Performance");
                            assessment.setAssessmentDueDate(dueDate.getText().toString());
                            assessment.setAssessmentGoalDate(goalDate.getText().toString());
                            assessment.setAssessmentGoalDateAlert(String.valueOf(assessIDs));;
                            String perId = String.valueOf(helper.updateAssessment(assessment));

                            String GD = goalDate.getText().toString();
                            int finalMonth = (Integer.parseInt(GD.substring(0, 2))) - 1;
                            int finalDay = Integer.parseInt(GD.substring(3, 5));
                            int finalYear = Integer.parseInt(GD.substring(6, 10));




                            Calendar cal = Calendar.getInstance();

                            cal.setTimeInMillis(System.currentTimeMillis());
                            cal.clear();
                            cal.set(finalYear,  finalMonth, finalDay);


                            Intent intent=new Intent(EditAssessment.this, NotificationPub.class);
                            intent.putExtra(notificationTitle3, "Assessment Notification");
                            intent.putExtra(notificationID3, perId);
                            intent.putExtra(notificationMSG3, "Your assessment: " + titleInput.getText().toString() + " " + "goal date is today.");
                            PendingIntent sender= PendingIntent.getBroadcast(EditAssessment.this,Integer.parseInt(perId),intent,0);
                            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 3000, sender);
                        } else if (!GDAlert.isChecked()){

                            Assessments assessment = new Assessments();
                            assessment.setAssessmentId(assessIDs);
                            assessment.setAssessmentTitle(titleInput.getText().toString());
                            assessment.setAssessment("Performance");
                            assessment.setAssessmentDueDate(dueDate.getText().toString());
                            assessment.setAssessmentGoalDate(goalDate.getText().toString());
                            assessment.setAssessmentGoalDateAlert(null);
                            String perId = String.valueOf(helper.updateAssessment(assessment));


                        }

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditAssessment.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, dueDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        goalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditAssessment.this, android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, goalDateSetListener, year, month, day);
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
        builder1 = new AlertDialog.Builder(EditAssessment.this);
        builder1.setMessage("Are you sure you want to delete this assessment?"  )
                .setCancelable(true)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        helper.deleteAssessment(Long.parseLong(assessIDs));
                        Intent intent = new Intent(EditAssessment.this, AssessmentsActivity.class);
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
