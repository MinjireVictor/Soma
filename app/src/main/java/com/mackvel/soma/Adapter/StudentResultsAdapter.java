package com.mackvel.soma.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mackvel.soma.Model.StudentResults;

import com.google.android.material.snackbar.Snackbar;
import com.mackvel.soma.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class StudentResultsAdapter extends ArrayAdapter<StudentResults> implements View.OnClickListener {
    private ArrayList<StudentResults> dataSet;
    Context mContext;



    private static class ViewHolder{
        TextView txtName;
        ImageView txtMarks;
    }
    public StudentResultsAdapter(@NonNull Context context, ArrayList<StudentResults> studentResults) {
        super(context, R.layout.student_results, studentResults);
        this.dataSet=studentResults;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int position=(Integer)v.getTag();
        Object object =getItem(position);
        StudentResults studentResults=(StudentResults)object;
        switch (v.getId()){
            case R.id.tv_student_name:
                Snackbar.make(v, "Click on the image to see results ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
//            case R.id.tv_marks:
////                Snackbar.make(v, "Marks " +studentResults.getMarks(), Snackbar.LENGTH_LONG)
////                        .setAction("No action", null).show();
//
//
//                break;

        }


    }
    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StudentResults studentResults = getItem(position);
        Typeface myTypeface2= Typeface.createFromAsset(getContext().getAssets(),"fonts/SalesforceSans.ttf");
        ViewHolder viewHolder;
        final View result;
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
          //  convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_results, parent, false);
            viewHolder = new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.student_results,parent,false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.txtMarks = (ImageView) convertView.findViewById(R.id.tv_marks);
            result=convertView;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        // Lookup view for data population

        viewHolder.txtName.setText(studentResults.getStudent_name());
      //  viewHolder.txtMarks.setText(studentResults.getMarks());
        viewHolder.txtName.setOnClickListener(this);
        viewHolder.txtName.setTag(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_student_name);
        ImageView tvHome = (ImageView) convertView.findViewById(R.id.tv_marks);
//        // Populate the data into the template view using the data object
        //tvHome.setTypeface(myTypeface2);
       tvName.setTypeface(myTypeface2);
//        tvName.setText(studentResults.student_name);
//        tvHome.setText(studentResults.marks);
        // Return the completed view to render on screen
        return convertView;
    }

}
