package com.example.c196.c196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AssessmentListAdapter extends ArrayAdapter<Assessments> {

    private List<Assessments> assessments;

    public AssessmentListAdapter(Context context, int resource, List<Assessments> objects) {
            super(context, resource, objects);

            assessments = objects;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_assessments, parent, false);
        }

        Assessments assessment = assessments.get(position);
        TextView assessmentTitle = convertView.findViewById(R.id.assessmentTitle);
        assessmentTitle.setText(assessment.getAssessmentTitle());

        TextView assessmentDates = convertView.findViewById(R.id.assessmentDates);
        assessmentDates.setText("Due Date: " + assessment.getAssessmentDueDate() + " - " + "Goal Date: " +assessment.getAssessmentGoalDate());


        return convertView;
    }





}
