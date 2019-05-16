package com.example.c196.c196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TermListAdapter extends ArrayAdapter<Term> {

    private List<Term> terms;

    public TermListAdapter( Context context, int resource,  List<Term> objects) {
        super(context, resource, objects);

        terms = objects;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_term_activity_rev, parent, false);
        }

        Term term = terms.get(position);
        TextView termTitle = convertView.findViewById(R.id.termTitle);
        termTitle.setText(term.getTermTitle());

        TextView termDates = convertView.findViewById(R.id.termDates);
        termDates.setText("Start: " + term.getTermStartDate() + " - " + "End: " + term.getTermEndDate());


        return convertView;
    }
}

