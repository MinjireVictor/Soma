package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamMarkActivity extends AppCompatActivity {
    CardView Exam1,Exam2,Exam3;
    String exam,TeacherViewResults,overall,exam_set,key,grade,subject,teacher_timestamp;
    String teacher_timestamp2, teacher_timestamp3;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference,databaseReference2;
    DatabaseReference databaseReference3,getDatabaseReference4;
    TextView text;
    ImageView iv_available1,iv_available2,iv_available3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_mark);
        Exam1=findViewById(R.id.bt_exam1);
        Exam2=findViewById(R.id.bt_exam2);
        Exam3=findViewById(R.id.bt_exam3);
        iv_available1=findViewById(R.id.iv_available1);
        iv_available2=findViewById(R.id.iv_available2);
        iv_available3=findViewById(R.id.iv_available3);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);
        exam="exam1";

        databaseReference2= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("OpenEnd").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher = dataSnapshot.getValue(String.class);
                if (teacher!=null){
                    dataEditor.putString("teacher timestamp",teacher).apply();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Test 1 results could be available for marking",Snackbar.LENGTH_LONG).show();
                    iv_available1.setVisibility(View.VISIBLE);
                }else{
                    iv_available1.setVisibility(View.GONE);
                }
                //   Toast.makeText(ExamActivity.this, "teacher tmspmp"+teacher, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam2";
        databaseReference3= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("OpenEnd").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher1= dataSnapshot.getValue(String.class);
                if (teacher1!=null){
                    dataEditor.putString("teacher timestamp1",teacher1).apply();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Test 2 results could be available for marking",Snackbar.LENGTH_LONG).show();
                    iv_available2.setVisibility(View.VISIBLE);
                }else{
                    iv_available2.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam3";
        databaseReference3= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("OpenEnd").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher2= dataSnapshot.getValue(String.class);
                if (teacher2!=null){
                    dataEditor.putString("teacher timestamp2",teacher2).apply();
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Test 3 results could be available for marking",Snackbar.LENGTH_LONG).show();
                    iv_available3.setVisibility(View.VISIBLE);
                }else{
                    iv_available3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Exam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exam="exam1";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                startActivity(new Intent(ExamMarkActivity.this,ClassResults.class));
            }
        });
        Exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exam="exam2";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                startActivity(new Intent(ExamMarkActivity.this,ClassResults.class));
            }
        });
        Exam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exam="exam3";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                startActivity(new Intent(ExamMarkActivity.this,ClassResults.class));
            }
        });
    }
}