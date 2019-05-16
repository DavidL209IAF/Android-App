package com.example.c196.c196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends ArrayAdapter<Course> {

    private List<Course> courses;

    public CourseListAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);

        courses = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_course, parent, false);
        }

        Course course = courses.get(position);
        TextView courseTitle = convertView.findViewById(R.id.courseTitle);
        courseTitle.setText(course.getCourseTitle());

        TextView courseDates = convertView.findViewById(R.id.courseDates);
        courseDates.setText("Start: " + course.getCourseStartDate() + " - " + "End: " + course.getCourseEndDate());


        return convertView;
    }




}
