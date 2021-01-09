package com.mackvel.soma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

public class StudentExamOpenActivity extends AppCompatActivity {
    CardView Exam1,Exam2,Exam3;
    String exam,view_results,exam_submitted,key,grade,subject,
            student_id;
    String teacher_timestamp,student_timestamp,teacher_timestamp1,student_timestamp1,teacher_timestamp2,student_timestamp2;
    Long teacherTimestamp,studentTimestamp;
    Long teacherTimestamp1,studentTimestamp1;
    Long teacherTimestamp2,studentTimestamp2;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference reference,reference2,reference3,reference4,reference5;
    DatabaseReference reference6,refernce7,reference8;
    String resultsType;
    ImageView iv_available1,iv_available2,iv_available3;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_exam_open);

        Exam1=findViewById(R.id.bt_exam1);
        Exam2=findViewById(R.id.bt_exam2);
        Exam3=findViewById(R.id.bt_exam3);
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);
        iv_available1=findViewById(R.id.iv_available1);
        iv_available2=findViewById(R.id.iv_available2);
        iv_available3=findViewById(R.id.iv_available3);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        grade=DataPreference.getString("student grade","");
        subject=DataPreference.getString("subject","");
        key=DataPreference.getString("school key","");
        resultsType=DataPreference.getString("resultsType","");
        student_id=DataPreference.getString("student id","");
        reference2= FirebaseDatabase.getInstance().getReference("Students");
        dataEditor.remove("student timestamp");
        dataEditor.remove("student timestamp1");
        dataEditor.remove("student timestamp2");
        dataEditor.remove("teacher timestamp");
        dataEditor.remove("teacher timestamp1");
        dataEditor.remove("teacher timestamp2");
        exam="exam1";
        dataEditor.remove(grade+subject+exam+ " available");
        exam="exam2";
        dataEditor.remove(grade+subject+exam+ " available");
        exam="exam3";
        dataEditor.remove(grade+subject+exam+ " available");
        dataEditor.apply();

        exam = "exam1";
        reference3=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                .child(exam).child(subject).child("timestamp");
        reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teachertms=dataSnapshot.getValue(String.class);
                if (teachertms!=null){

                    teacherTimestamp=Long.parseLong(teachertms);
                    dataEditor.putLong("teacher timestamp", teacherTimestamp).apply();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam2";
        reference4=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                .child(exam).child(subject).child("timestamp");
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teachertms=dataSnapshot.getValue(String.class);
                if (teachertms!=null){

                    teacherTimestamp1=Long.parseLong(teachertms);
                    dataEditor.putLong("teacher timestamp1", teacherTimestamp1).apply();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        exam="exam3";
        reference5=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                .child(exam).child(subject).child("timestamp");
        reference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teachertms=dataSnapshot.getValue(String.class);
                if (teachertms!=null){
                    teacherTimestamp2=Long.parseLong(teachertms);
                    dataEditor.putLong("teacher timestamp2", teacherTimestamp2).apply();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam1";

        reference6=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam)
                .child(subject).child("timestamp");
        reference6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_tmstmp=dataSnapshot.getValue(String.class);

                if (student_tmstmp!=null){
                    studentTimestamp=Long.parseLong(student_tmstmp);
                    dataEditor.putLong("student timestamp",studentTimestamp).apply();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        exam="exam2";
        refernce7=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam)
                .child(subject).child("timestamp");
        refernce7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_tmstmp=dataSnapshot.getValue(String.class);

                if (student_tmstmp!=null){
                    studentTimestamp1=Long.parseLong(student_tmstmp);
                    dataEditor.putLong("student timestamp1",studentTimestamp1).apply();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        exam="exam3";
        reference8=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam)
                .child(subject).child("timestamp");
        reference8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_tmstmp=dataSnapshot.getValue(String.class);

                if (student_tmstmp!=null){
                    studentTimestamp2=Long.parseLong(student_tmstmp);
                    dataEditor.putLong("student timestamp2",studentTimestamp2).apply();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam1";
        studentTimestamp=DataPreference.getLong("student timestamp",0);
        teacherTimestamp=DataPreference.getLong("teacher timestamp",0);
        if (teacherTimestamp>studentTimestamp){
          //  Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 1 available",Snackbar.LENGTH_LONG).show();
            dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
            iv_available1.setVisibility(View.VISIBLE);

        }else{
            dataEditor.remove(grade+subject+exam+ " available").apply();
            iv_available1.setVisibility(View.GONE);
        }
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam="exam1";
                studentTimestamp=DataPreference.getLong("student timestamp",0);
                teacherTimestamp=DataPreference.getLong("teacher timestamp",0);
                if (teacherTimestamp>studentTimestamp){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 1 available",Snackbar.LENGTH_LONG).show();
                    dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
                    iv_available1.setVisibility(View.VISIBLE);

                }else{
                    dataEditor.remove(grade+subject+exam+ " available").apply();
                    iv_available1.setVisibility(View.GONE);
                }

            }
        },500);

        exam="exam2";
        studentTimestamp1=DataPreference.getLong("student timestamp1",0);
        teacherTimestamp1=DataPreference.getLong("teacher timestamp1",0);
        if (teacherTimestamp1>studentTimestamp1){
           // Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 2 available",Snackbar.LENGTH_LONG).show();
            dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
            iv_available2.setVisibility(View.VISIBLE);

        }else{
            dataEditor.remove(grade+subject+exam+ " available").apply();
            iv_available2.setVisibility(View.GONE);
        }
        Handler handler2= new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam="exam2";
                studentTimestamp1=DataPreference.getLong("student timestamp1",0);
                teacherTimestamp1=DataPreference.getLong("teacher timestamp1",0);
                if (teacherTimestamp1>studentTimestamp1){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 2 available",Snackbar.LENGTH_LONG).show();
                    dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
                    iv_available2.setVisibility(View.VISIBLE);

                }else{
                    dataEditor.remove(grade+subject+exam+ " available").apply();
                    iv_available2.setVisibility(View.GONE);
                }

            }
        },500);

        exam="exam3";
        studentTimestamp2=DataPreference.getLong("student timestamp2",0);
        teacherTimestamp2=DataPreference.getLong("teacher timestamp2",0);
        if (teacherTimestamp2>studentTimestamp2){
           // Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 3 available",Snackbar.LENGTH_LONG).show();
            dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
            iv_available3.setVisibility(View.VISIBLE);

        }else{
            dataEditor.remove(grade+subject+exam+ " available").apply();
            iv_available3.setVisibility(View.GONE);
        }
        Handler handler3= new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam="exam3";
                studentTimestamp2=DataPreference.getLong("student timestamp2",0);
                teacherTimestamp2=DataPreference.getLong("teacher timestamp2",0);
                if (teacherTimestamp2>studentTimestamp2){

                    Snackbar.make(getWindow().getDecorView().getRootView(),"New "+subject+" Test 3 available",Snackbar.LENGTH_LONG).show();
                    dataEditor.putString(grade+subject+exam+ " available", grade+subject+exam+ " available");
                    iv_available3.setVisibility(View.VISIBLE);

                }else{
                    dataEditor.remove(grade+subject+exam+ " available").apply();
                    iv_available3.setVisibility(View.GONE);
                }

            }
        },500);





        Exam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam1";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                String available = DataPreference.getString(grade+subject+exam+ " available","");
                studentTimestamp=DataPreference.getLong("student timestamp",0);
                teacherTimestamp=DataPreference.getLong("teacher timestamp",0);

                if (resultsType.equals("view openResults")) {

                    if (teacherTimestamp>studentTimestamp){
                        Snackbar.make(getWindow().getDecorView().getRootView(), "You have not done this test", Snackbar.LENGTH_LONG).show();

                    }else{
                        startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));
                    }
                }
                else if(available.equals(grade+subject+exam+ " available")){

                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            reference2=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam).child(subject);
                            reference2.removeValue();
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();
                }else if (studentTimestamp!=0) {

                    Snackbar.make(getWindow().getDecorView().getRootView(), "Test Completed", Snackbar.LENGTH_LONG).setAction(
                            "View Results", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));


                                }
                            }
                    ).show();
                }else{


                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();

                }
            }

        });

        Exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam2";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                String available = DataPreference.getString(grade+subject+exam+ " available","");
                studentTimestamp1=DataPreference.getLong("student timestamp1",0);
                teacherTimestamp1=DataPreference.getLong("teacher timestamp1",0);

                if (resultsType.equals("view openResults")) {

                    if (teacherTimestamp1>studentTimestamp1){
                        Snackbar.make(getWindow().getDecorView().getRootView(), "You have not done this test", Snackbar.LENGTH_LONG).show();

                    }else{
                        startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));
                    }
                }
                else if(available.equals(grade+subject+exam+ " available")){

                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            reference2=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam).child(subject);
                            reference2.removeValue();
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();
                }else if (studentTimestamp1!=0) {

                    Snackbar.make(getWindow().getDecorView().getRootView(), "Test Completed", Snackbar.LENGTH_LONG).setAction(
                            "View Results", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));


                                }
                            }
                    ).show();
                }else{


                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();

                }
            }

        });

        Exam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam3";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                String available = DataPreference.getString(grade+subject+exam+ " available","");
                studentTimestamp2=DataPreference.getLong("student timestamp2",0);
                teacherTimestamp2=DataPreference.getLong("teacher timestamp2",0);
//                Snackbar.make(getWindow().getDecorView().getRootView(),"student timetamp "+studentTimestamp,
//                        Snackbar.LENGTH_LONG).show();
                if (resultsType.equals("view openResults")) {

                    if (teacherTimestamp2>studentTimestamp2){
                        Snackbar.make(getWindow().getDecorView().getRootView(), "You have not done this test", Snackbar.LENGTH_LONG).show();

                    }else{
                        startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));
                    }
                }
                else if(available.equals(grade+subject+exam+ " available")){

                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            reference2=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam).child(subject);
                            reference2.removeValue();
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();
                }else if (studentTimestamp2!=0) {

                    Snackbar.make(getWindow().getDecorView().getRootView(), "Test Completed", Snackbar.LENGTH_LONG).setAction(
                            "View Results", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(StudentExamOpenActivity.this, ViewResultsOpenStudent.class));


                                }
                            }
                    ).show();
                }else{


                    final AlertDialog.Builder alertDialog= new AlertDialog.Builder(StudentExamOpenActivity.this);
                    alertDialog.setTitle("Start Exam")
                            .setMessage("Make sure you have everything in place and press yes");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(StudentExamOpenActivity.this, DoOpenQuestions.class));
                        }
                    });
                    alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    alertDialog.show();

                }
            }

        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentExamOpenActivity.this,StudentSubjectActivity.class));
        super.onBackPressed();
    }
}