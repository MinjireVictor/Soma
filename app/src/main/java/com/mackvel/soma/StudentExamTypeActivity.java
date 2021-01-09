package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StudentExamTypeActivity extends AppCompatActivity {
    CardView ClosedTest,OpenTest,ExamPapers;
    String resultsType;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exam_type);
        ClosedTest=findViewById(R.id.bt_closedTest);
        OpenTest=findViewById(R.id.bt_openTest);
        ExamPapers=findViewById(R.id.bt_examPapers);
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        resultsType="resultsType";

        ClosedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.putString("view results","view results");
                dataEditor.apply();
                startActivity(new Intent(StudentExamTypeActivity.this,StudentSubjectActivity.class));
            }
        });
        OpenTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.remove("view results");
                dataEditor.putString(resultsType,"view openResults");
                dataEditor.apply();
                startActivity(new Intent(StudentExamTypeActivity.this,StudentSubjectActivity.class));

            }
        });
        ExamPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.remove("view results");
                dataEditor.putString(resultsType,"view examResults");
                dataEditor.apply();
                startActivity(new Intent(StudentExamTypeActivity.this,StudentSubjectActivity.class));

            }
        });
    }
}