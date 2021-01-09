package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherViewResults extends AppCompatActivity {
    RelativeLayout view_student_results,view_class_results;

    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_results);
        view_student_results=findViewById(R.id.view_student_results);
        view_class_results=findViewById(R.id.view_class_results);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();

        view_student_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.putString("Result", "Search student");
                dataEditor.apply();
                startActivity(new Intent(TeacherViewResults.this,ExamTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });
        view_class_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dataEditor.putString("TeacherViewResults", "TeacherViewResults");
                dataEditor.putString("Result", "Class results");
                dataEditor.apply();
                startActivity(new Intent(TeacherViewResults.this, ExamTypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}