package com.example.c196.c196;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMentor extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhone;
    private EditText mentorEmail;
    DBHelper helper;
    private AlertDialog.Builder builder;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mentorName = (EditText) findViewById(R.id.mentorNameInput);
        mentorPhone = (EditText) findViewById(R.id.mentorPhoneInput);
        mentorEmail = (EditText) findViewById(R.id.mentorEmailInput);


        builder = new AlertDialog.Builder(AddMentor.this);
        builder.setMessage("Mentor Added Successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddMentor.this, MentorsActivity.class);
                        startActivity(intent);
                    }
                });

        Button fab = (Button) findViewById(R.id.saveButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mentorName.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter a mentor name.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (mentorPhone.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please enter a mentor phone number.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (mentorEmail.getText().toString().length() == 0) {
                    Snackbar.make(view, "Please select a mentor email.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {

                    Mentors mentor = new Mentors();
                    mentor.setMentorName(mentorName.getText().toString());
                    mentor.setMentorPhone(mentorPhone.getText().toString());
                    mentor.setMentorEmail(mentorEmail.getText().toString());
                    id = helper.createMentors(mentor);

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
