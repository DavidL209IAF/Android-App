package com.example.c196.c196;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditMentor extends AppCompatActivity {

    private EditText mentorName;
    private EditText mentorPhone;
    private EditText mentorEmail;
    DBHelper helper;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder1;
    long id;
    private String mentorID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mentorName = (EditText) findViewById(R.id.mentorNameInput);
        mentorPhone = (EditText) findViewById(R.id.mentorPhoneInput);
        mentorEmail = (EditText) findViewById(R.id.mentorEmailInput);

        builder = new AlertDialog.Builder(EditMentor.this);
        builder.setMessage("Mentor Uodated Successfully.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(EditMentor.this, MentorsActivity.class);
                        startActivity(intent);
                    }
                });

        mentorID = getIntent().getStringExtra(MentorsActivity.mentor_id);
        String mentorNameS = getIntent().getStringExtra(MentorsActivity.mentor_name);
        String mentorPhoneS = getIntent().getStringExtra(MentorsActivity.mentor_number);
        String mentorEmailS = getIntent().getStringExtra(MentorsActivity.mentor_email);

        mentorName.setText(mentorNameS);
        mentorPhone.setText(mentorPhoneS);
        mentorEmail.setText(mentorEmailS);

        Button fab = (Button) findViewById(R.id.updateButton);
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
                    mentor.setMentorId(mentorID);
                    mentor.setMentorName(mentorName.getText().toString());
                    mentor.setMentorPhone(mentorPhone.getText().toString());
                    mentor.setMentorEmail(mentorEmail.getText().toString());
                    id = helper.updateMentors(mentor);

                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        builder1 = new AlertDialog.Builder(EditMentor.this);
        builder1.setMessage("Are you sure you want to delete this mentor?"  )
                .setCancelable(true)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        helper.deleteMentors(Long.parseLong(mentorID));
                        Intent intent = new Intent(EditMentor.this, MentorsActivity.class);
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
