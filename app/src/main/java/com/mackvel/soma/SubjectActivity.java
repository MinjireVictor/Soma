package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.security.auth.Subject;

public class SubjectActivity extends AppCompatActivity implements View.OnClickListener {
    CardView Maths,English,Kiswahili,Science,Social_studies,Cre;
    String subject,TeacherViewResults,teacher_option,results;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Maths=findViewById(R.id.bt_maths);
        English=findViewById(R.id.bt_english);
        Kiswahili=findViewById(R.id.bt_kiswahili);
        Science=findViewById(R.id.bt_science);
        Social_studies=findViewById(R.id.bt_socialstudies);
        Cre=findViewById(R.id.bt_cre);
        text=findViewById(R.id.text);
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
        results=DataPreference.getString("Result","");

        teacher_option=DataPreference.getString("teacher option","");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_maths:
                subject="maths";
                dataEditor.putString("subject",subject);
                dataEditor.apply();

                 if (teacher_option.equals("upload exam")){
                     startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                 }
                 else if (teacher_option.equals("set open")){
                     startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                 }
                 else if(teacher_option.equals("mark questions")){
                     startActivity(new Intent(SubjectActivity.this,ExamMarkActivity.class));
                 }
                 else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class));
                }
                 else{
                     startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                 }


                break;
            case R.id.bt_english:
                subject="english";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (teacher_option.equals("upload exam")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                }
                else if (teacher_option.equals("set open")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                }
                else if(teacher_option.equals("mark questions")){
                    startActivity(new Intent(SubjectActivity.this,ClassResults.class));
                }
                else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

                else{
                    startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                }
                break;
            case R.id.bt_kiswahili:
                subject="kiswahili";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (teacher_option.equals("upload exam")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                }
                else if (teacher_option.equals("set open")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                }
                else if(teacher_option.equals("mark questions")){
                    startActivity(new Intent(SubjectActivity.this,ClassResults.class));
                }
                else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

                else{
                    startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                }
                break;
            case R.id.bt_science:
                subject="science";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (teacher_option.equals("upload exam")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                }
                else if (teacher_option.equals("set open")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                }
                else if(teacher_option.equals("mark questions")){
                    startActivity(new Intent(SubjectActivity.this,ClassResults.class));
                }
                else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

                else{
                    startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                }
                break;
            case R.id.bt_socialstudies:
                subject="socialstudies";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (teacher_option.equals("upload exam")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                }
                else if (teacher_option.equals("set open")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                }
                else if(teacher_option.equals("mark questions")){
                    startActivity(new Intent(SubjectActivity.this,ClassResults.class));
                }
                else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

                else{
                    startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                }
                break;
            case R.id.bt_cre:
                subject="cre";
                dataEditor.putString("subject",subject);
                dataEditor.apply();
                if (teacher_option.equals("upload exam")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityUpload.class));
                }
                else if (teacher_option.equals("set open")){
                    startActivity(new Intent(SubjectActivity.this,ExamActivityOpen.class));
                }
                else if(teacher_option.equals("mark questions")){
                    startActivity(new Intent(SubjectActivity.this,ClassResults.class));
                }
                else if (results.equals("Search student")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                }else if (results.equals("Class results")){
                    startActivity(new Intent(SubjectActivity.this,ExamResultsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

                else{
                    startActivity(new Intent(SubjectActivity.this,ExamActivity.class));
                }
                break;

        }

    }
}