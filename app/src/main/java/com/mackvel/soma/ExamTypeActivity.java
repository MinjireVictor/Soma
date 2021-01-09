package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.nio.file.OpenOption;

public class ExamTypeActivity extends AppCompatActivity implements View.OnClickListener {
    CardView ClosedTest,OpenTest,UploadedPapers;
    String results;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_type);
        ClosedTest=findViewById(R.id.bt_closedTest);
        OpenTest=findViewById(R.id.bt_openTest);
        UploadedPapers=findViewById(R.id.bt_uploadedPapers);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        text=findViewById(R.id.text);
        results=DataPreference.getString("Result","");
        ClosedTest.setOnClickListener(this);
        OpenTest.setOnClickListener(this);
        UploadedPapers.setOnClickListener(this);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_openTest:
                dataEditor.putString("TeacherViewResults", "TeacherViewOpenResults");
                dataEditor.apply();
                startActivity(new Intent(ExamTypeActivity.this,ClassActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bt_closedTest:
                dataEditor.putString("TeacherViewResults", "TeacherViewResults");
                dataEditor.apply();
                startActivity(new Intent(ExamTypeActivity.this,ClassActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bt_uploadedPapers:

                dataEditor.putString("TeacherViewResults", "TeacherViewUploadedPapers");
                dataEditor.apply();
                startActivity(new Intent(ExamTypeActivity.this,ClassActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}