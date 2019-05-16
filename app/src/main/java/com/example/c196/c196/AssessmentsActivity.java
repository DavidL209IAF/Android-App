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

public class AssessmentsActivity extends AppCompatActivity {
    private ListView assessmentList;
    public static final String aid = "aid";
    public static final String assess_title = "assess_id";
    public static final String assess_GD = "term_SD";
    public static final String assess_DD = "term_ED";
    public static final String assess = "assess";
    public static final String assessGDAlert = "assess_GDAlert";

;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentsActivity.this, AddAssessment.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //create databdase
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();

//populate list for assessments

        // Assessments assessment=  new Assessments("Test Assessment", "Objective", "11-10-18",  "12-10-19");
        // helper.createAssessment(assessment);
        final List<Assessments> assessments = helper.getAllAssessments();
        AssessmentListAdapter adapter = new AssessmentListAdapter(this, R.layout.content_assessments, assessments);
        assessmentList = findViewById(R.id.assessments_list);
        assessmentList.setAdapter(adapter);

        assessmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AssessmentsActivity.this, EditAssessment.class);

                Assessments assessment = assessments.get(position);
                intent.putExtra(aid, assessment.getAssessmentId());
                intent.putExtra(assess_title, assessment.getAssessmentTitle());
                intent.putExtra(assess_DD, assessment.getAssessmentDueDate());
                intent.putExtra(assess_GD, assessment.getAssessmentGoalDate());
                intent.putExtra(assess, assessment.getAssessment());
                intent.putExtra(assessGDAlert, assessment.getAssessmentGoalDateAlert());
                startActivity(intent);
            }
        });
    }

}
