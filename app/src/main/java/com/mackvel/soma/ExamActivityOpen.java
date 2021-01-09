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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamActivityOpen extends AppCompatActivity {
    CardView Exam1,Exam2,Exam3;
    String exam,TeacherViewResults,overall,exam_set,key,grade,subject,teacher_timestamp,mark_questions;
    String teacher_timestamp2, teacher_timestamp3;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference,databaseReference2;
    DatabaseReference databaseReference3,getDatabaseReference4;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_open);

        Exam1=findViewById(R.id.bt_exam1);
        Exam2=findViewById(R.id.bt_exam2);
        Exam3=findViewById(R.id.bt_exam3);
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        mark_questions=DataPreference.getString("mark questions","");
        TeacherViewResults=DataPreference.getString("TeacherViewResults","");
        exam="exam1";
        databaseReference2= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("OpenEnd").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher = dataSnapshot.getValue(String.class);
                dataEditor.putString("teacher timestamp",teacher).apply();
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

                dataEditor.putString("teacher timestamp1",teacher1).apply();

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

                dataEditor.putString("teacher timestamp2",teacher2).apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Exam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam1";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                exam_set=DataPreference.getString(grade+subject+exam+" set","");
                teacher_timestamp=DataPreference.getString("teacher timestamp","");

                if (TeacherViewResults.equals("")){
                    if (exam_set.equals(grade+subject+exam+" open set")){

                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }else if (teacher_timestamp!=null && !teacher_timestamp.isEmpty()){
                        //  Toast.makeText(ExamActivity.this, "in teacher timestamp", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


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
                    else {
                        startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }


                }
                else if (TeacherViewResults.equals("TeacherViewOpenResults")){
                    dataEditor.remove("TeacherViewOpenResults");
                    dataEditor.apply();
                    startActivity(new Intent(ExamActivityOpen.this,ClassResults.class));


                }
                else if (mark_questions.equals("mark questions")){
                    startActivity(new Intent(ExamActivityOpen.this,ClassResults.class));

                }
            }
        });


        Exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam2";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                exam_set=DataPreference.getString(grade+subject+exam+" set","");
                teacher_timestamp2=DataPreference.getString("teacher timestamp1","");
                if (TeacherViewResults.equals("")){
                    if (exam_set.equals(grade+subject+exam+" open set")){

                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }else if (teacher_timestamp2!=null && !teacher_timestamp2.isEmpty()){
                     //   Toast.makeText(ExamActivityOpen.this, "in teacher timestamp", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


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
                    else {
                        startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }


                }
                else if (TeacherViewResults.equals("TeacherViewOpenResults")){

                    dataEditor.remove("TeacherViewOpenResults");
                    dataEditor.apply();
                    startActivity(new Intent(ExamActivityOpen.this,ClassResults.class));

                }
            }

        });
        Exam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam = "exam3";
                dataEditor.putString("exam", exam);
                dataEditor.apply();
                exam_set = DataPreference.getString(grade + subject + exam + " set", "");
                teacher_timestamp3 = DataPreference.getString("teacher timestamp2", "");

                if (TeacherViewResults.equals("")) {
                    if (exam_set.equals(grade + subject + exam + " open set")) {

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this, SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    } else if (teacher_timestamp3 != null && !teacher_timestamp3.isEmpty()) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExamActivityOpen.this);
                        alertDialog.setTitle("Reset exam")
                                .setMessage("This test was already set, do you wish to set another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                dataEditor.remove(grade+subject+exam+" set");
                                dataEditor.remove("exam question");
                                dataEditor.apply();
                                startActivity(new Intent(ExamActivityOpen.this, SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();


                    }
                    else {
                        startActivity(new Intent(ExamActivityOpen.this,SetOpenQuestion.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                }
                else{

                    dataEditor.remove("TeacherViewResults");
                    dataEditor.apply();
                    startActivity(new Intent(ExamActivityOpen.this,ClassResults.class));

                }
            }
        });

    }
}