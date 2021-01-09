package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ExamSubmittedActivity extends AppCompatActivity {
    TextView textView;
    Button view_results;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    ImageView back;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_submitted);
        view_results=findViewById(R.id.view_results);
        textView= findViewById(R.id.textView9);
        back= findViewById(R.id.iv_back);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();



        view_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(ExamSubmittedActivity.this,ViewResultsStudent.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.remove("exam1 submitted");
                startActivity(new Intent(ExamSubmittedActivity.this,StudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ExamSubmittedActivity.this,StudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}