package com.example.c196.c196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MentorListAdapter extends ArrayAdapter<Mentors> {


    private List<Mentors> mentors;

    public MentorListAdapter(Context context, int resource, List<Mentors> objects) {
        super(context, resource, objects);

        mentors = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_mentors, parent, false);
        }

        Mentors mentor = mentors.get(position);
        TextView mentorName = convertView.findViewById(R.id.mentorName);
        mentorName.setText(mentor.getMentorName());

        return convertView;
    }



}
