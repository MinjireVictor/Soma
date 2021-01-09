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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


public class ClassActivity extends AppCompatActivity implements View.OnClickListener {
    CardView Class1,Class2,Class3,Class4,Class5,Class6,Class7,Class8,Submit;
    String grade;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    int class_1,class_2,class_3,class_4,class_5,class_6,class_7,class_8;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Class1=findViewById(R.id.bt_class1);
        Class2=findViewById(R.id.bt_class2);
        Class3=findViewById(R.id.bt_class3);
        Class4=findViewById(R.id.bt_class4);
        Class5=findViewById(R.id.bt_class5);
        Class6=findViewById(R.id.bt_class6);
        Class7=findViewById(R.id.bt_class7);
        Class8=findViewById(R.id.bt_class8);

        Class1.setOnClickListener(this);
        Class2.setOnClickListener(this);
        Class3.setOnClickListener(this);
        Class4.setOnClickListener(this);
        Class5.setOnClickListener(this);
        Class6.setOnClickListener(this);
        Class7.setOnClickListener(this);
        Class8.setOnClickListener(this);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_class1:

                grade="class1";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));


                break;
            case R.id.bt_class2:
                grade="class2";
                dataEditor.putString("grade",grade);
                dataEditor.apply();

                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));
                break;

            case R.id.bt_class3:
                grade="class3";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));


                break;
            case R.id.bt_class4:
                grade="class4";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));


                break;
            case R.id.bt_class5:
                grade="class5";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));

                break;
            case R.id.bt_class6:
                grade="class6";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));

                break;
            case R.id.bt_class7:
                grade="class7";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));

                break;
            case R.id.bt_class8:
                grade="class8";
                dataEditor.putString("grade",grade);
                dataEditor.apply();
                startActivity(new Intent(ClassActivity.this,SubjectActivity.class));
                break;
            default:


                break;



        }


    }
}