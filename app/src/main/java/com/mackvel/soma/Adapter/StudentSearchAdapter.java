package com.mackvel.soma.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.mackvel.soma.Model.StudentSearch;
import com.mackvel.soma.R;


import java.util.ArrayList;

import androidx.annotation.NonNull;

public class StudentSearchAdapter extends ArrayAdapter<StudentSearch> {
    public StudentSearchAdapter(@NonNull Context context, ArrayList<StudentSearch> studentSearches) {
        super(context, 0,studentSearches);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StudentSearch studentSearch = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_search, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_student_name);

        // Populate the data into the template view using the data object
        tvName.setText(studentSearch.student_name);

        // Return the completed view to render on screen
        return convertView;
    }
}
