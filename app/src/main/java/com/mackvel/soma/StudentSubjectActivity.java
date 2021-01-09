package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StudentSubjectActivity extends AppCompatActivity implements View.OnClickListener {
    CardView Maths,English,Kiswahili,Science,Social_studies,Cre,Submit;
    String subject, resultsType,student_option;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String do_open,do_exam_papers,view_results;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);

        Maths=findViewById(R.id.bt_maths);
        English=findViewById(R.id.bt_english);
        Kiswahili=findViewById(R.id.bt_kiswahili);
        Science=findViewById(R.id.bt_science);
        text=findViewById(R.id.text);
        Social_studies=findViewById(R.id.bt_socialstudies);
        Cre=findViewById(R.id.bt_cre);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);

        Maths.setOnClickListener(this);
        English.setOnClickListener(this);
        Kiswahili.setOnClickListener(this);
        Science.setOnClickListener(this);
        Social_studies.setOnClickListener(this);
        Cre.setOnClickListener(this);


        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        student_option=DataPreference.getString("student option","");
        resultsType=DataPreference.getString("resultsType","");


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_maths:
                subject="maths";
                dataEditor.putString("subject",subject);
                dataEditor.apply();

                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }

                break;
            case R.id.bt_english:
                subject="english";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }

                break;
            case R.id.bt_kiswahili:
                subject="kiswahili";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }
                break;
            case R.id.bt_science:
                subject="science";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }

                break;
            case R.id.bt_socialstudies:
                subject="socialstudies";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }

                break;
            case R.id.bt_cre:
                subject="cre";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (student_option.equals("do open")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }
                else if (student_option.equals("do papers")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));

                }else if (resultsType.equals("view openResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamOpenActivity.class));
                }else if (resultsType.equals("view examResults")){
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamPapersActivity.class));
                }
                else{
                    startActivity(new Intent(StudentSubjectActivity.this,StudentExamActivity.class));
                }

                break;
            default:

                break;

        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentSubjectActivity.this,StudentActivity.class));
    }
}