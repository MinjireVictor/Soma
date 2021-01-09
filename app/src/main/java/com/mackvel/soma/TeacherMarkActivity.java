package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mackvel.soma.Model.Students;

import java.util.HashMap;
import java.util.Map;

public class TeacherMarkActivity extends AppCompatActivity {
    TextView question_no, tv_student_name, tv_question, tv_student_answer, tv_score, tv_allquestions;
    ImageView question_imageView, studentAns_imageView, wrong, right, iv_status;
    EditText comment;
    RelativeLayout next, previous;
    DatabaseReference questionRef, databaseReference, studentIdRef, allQ_ref, questionImage_ref,scoreRef;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String Key, school_name, grade, subject, exam, examQuestion, key, student_name, correct_ans, student_ans, student_id;
    int exam_question;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;
    StorageReference fileReference;
    int total_questions;
    int score,correct,incorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_mark);

        tv_student_name = findViewById(R.id.tv_student_name);
        tv_question = findViewById(R.id.tv_question);
        tv_score = findViewById(R.id.tv_score);
        question_no = findViewById(R.id.tvQuestion_no);
        tv_allquestions = findViewById(R.id.tv_total_questions);
        tv_student_answer = findViewById(R.id.tv_student_answer);
        question_imageView = findViewById(R.id.question_imageView);
        studentAns_imageView = findViewById(R.id.student_imageView);
        iv_status=findViewById(R.id.iv_status);
        right = findViewById(R.id.right_imageView);
        wrong = findViewById(R.id.wrong_imageView);
        comment = findViewById(R.id.et_comment);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();
        exam_question = DataPreference.getInt("question number", 0);
        examQuestion = String.valueOf(exam_question);
        int a = (exam_question + 1);
        question_no.setText(String.valueOf(a));

        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();
        student_name = getIntent().getStringExtra("student name");
        tv_student_name.setText(student_name);
//        exam = getIntent().getStringExtra("student exam");
        key = DataPreference.getString("school key", "");
        subject = DataPreference.getString("subject", "");
        exam = DataPreference.getString("exam", "");
        exam_question = DataPreference.getInt("question number", 0);
        examQuestion = String.valueOf(exam_question);

        studentIdRef = FirebaseDatabase.getInstance().getReference("Students");
        studentIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Students students = snapshot.getValue(Students.class);
//                    String studentname = students.getUsername();
                    String studentname=snapshot.child("username").getValue(String.class);
                    if (student_name.equals(studentname)) {
                        student_id = snapshot.child("id").getValue(String.class);
                        grade = snapshot.child("grade").getValue(String.class);
                        dataEditor.putString("student id", student_id);
                        dataEditor.putString("grade", grade);
                        dataEditor.apply();
                        //    Toast.makeText(ViewResultsTeacher.this, student_id, Toast.LENGTH_SHORT).show();
                    } else {
                        //  Toast.makeText(ViewResultsTeacher.this, "student id could not be found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        grade = DataPreference.getString("grade", "");
        //Toast.makeText(this, grade, Toast.LENGTH_SHORT).show();
        updateQestion(exam_question);
        updateImage(exam_question);
        updateStudentImage(exam_question);
        compareAns(String.valueOf(exam_question));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam_question = DataPreference.getInt("question number", 0);
                updateQestion(exam_question);
                updateImage(exam_question);
                updateStudentImage(exam_question);
                compareAns(String.valueOf(exam_question));


            }
        }, 2000);

        scoreRef=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child("score");
        scoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_score= dataSnapshot.getValue(String.class);

                if (student_score!=null){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "You have already marked "+student_name+"'s work.",
                            Snackbar.LENGTH_LONG).show();
                    Toast.makeText(TeacherMarkActivity.this, "You have already marked "+student_name+"'s work.", Toast.LENGTH_SHORT).show();
                    tv_score.setText(student_score);
                    right.setClickable(false);
                    wrong.setClickable(false);
                    next.setClickable(false);
                    previous.setClickable(false);
                }else{
                    next.setClickable(false);
                    previous.setClickable(false);
                    right.setClickable(true);
                    wrong.setClickable(true);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        allQ_ref = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total = dataSnapshot.getValue(String.class);
                if (total != null) {
                    tv_allquestions.setText(total);
                    total_questions = Integer.parseInt(total);
                    dataEditor.putInt("total_questions", total_questions).apply();
                    // Toast.makeText(ViewResultsTeacher.this, "all q= "+total, Toast.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Student has not done test", Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        student_id = DataPreference.getString("student id", "");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_status.setImageDrawable(null);
                total_questions=DataPreference.getInt("total_questions",0);
                exam_question = DataPreference.getInt("question number", 0);
                exam_question++;
                correct=0;
                incorrect=0;
                int a = exam_question + 1;


                if (total_questions >= a) {
                    dataEditor.putInt("question number", exam_question).apply();
                    int b=exam_question-1;
                    updateQestion(exam_question);
                    updateImage(exam_question);
                    updateStudentImage(exam_question);
                    compareAns(String.valueOf(exam_question));
                    databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                            .child(exam).child(subject).child(String.valueOf(b));
                    String t_comment=comment.getText().toString();
                    if (TextUtils.isEmpty(t_comment)){

                    }else{
                        HashMap <String, Object> hashMap= new HashMap<>();
                        hashMap.put("comment",t_comment);
                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                comment.getText().clear();
                            }
                        });
                    }

                }
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    student_id = DataPreference.getString("student id", "");
                                    score=DataPreference.getInt("marks",0);
                                    String student_score=String.valueOf(score);
                                    databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                                            .child(exam).child(subject);
                                    HashMap <String,Object> hashMap= new HashMap<>();
                                    hashMap.put("score",student_score);
                                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(TeacherMarkActivity.this, "Marks submitted", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(TeacherMarkActivity.this,ClassResults.class));
                                        }
                                    });
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:

                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(TeacherMarkActivity.this);
                    builder.setMessage("You have finished marking "+student_name+"'s work. Do you wish to submit results ").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exam_question > 0) {
                    previousQuestion();
                    if (score>0){
                        score--;
                        UpdateMarks(score);
                    }

                } else {
                    Toast.makeText(TeacherMarkActivity.this, "no previous questions", Toast.LENGTH_SHORT).show();
                }

            }


        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (correct>0){

                }else{
                    if (incorrect>0)incorrect=0;
                    score++;
                    correct++;
                    iv_status.setImageResource(R.drawable.success);
                    UpdateMarks(score);
                    databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                            .child(exam).child(subject).child(examQuestion);
                    HashMap<String, Object> hashMap= new HashMap<>();
                    hashMap.put("status","correct");

                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(TeacherMarkActivity.this, "correct", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incorrect>0){


                }else {
                    if (score > 0) {
                        if (correct>0)correct=0;
                        score--;
                        iv_status.setImageResource(R.drawable.wrong);
                        UpdateMarks(score);
                        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                                .child(exam).child(subject).child(examQuestion);
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("status", "wrong");
                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(TeacherMarkActivity.this, "correct", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }

    private void updateStudentImage(int exam_question){
        DatabaseReference databaseReference;
        examQuestion=String.valueOf(exam_question);
        grade = DataPreference.getString("grade", "");
        student_id = DataPreference.getString("student id", "");
        questionImage_ref =FirebaseDatabase.getInstance().getReference("Students");
        questionImage_ref.child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(examQuestion).child("image_URL");
        questionImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url = dataSnapshot.child(student_id).child("OpenEndAns")
                        .child(exam).child(subject).child(examQuestion).child("image_URL").getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")) {
//                        studentAns_imageView.setImageResource(R.drawable.image_bg);

                    } else {
                        Glide.with(getApplicationContext()).load(image_url).into(studentAns_imageView);

                    }
                } else {
//                    studentAns_imageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateQestion ( int exam_question){
        grade = DataPreference.getString("grade", "");
        examQuestion = String.valueOf(exam_question);
        int a = exam_question + 1;
        question_no.setText(String.valueOf(a));
        questionRef = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade).child(exam)
                .child(subject).child(examQuestion).child("question");
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Question = dataSnapshot.getValue(String.class);
                tv_question.setText(Question);
                if (Question == null) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), subject + " " + exam + " does not exist", Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void compareAns ( final String examQuestion){
        //DatabaseReference databaseReference;
        grade = DataPreference.getString("grade", "");
        student_id = DataPreference.getString("student id", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        databaseReference.child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(examQuestion).child("studentAns");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student_ans = dataSnapshot.child(student_id).child("OpenEndAns")
                        .child(exam).child(subject).child(examQuestion).child("studentAns").getValue(String.class);
                if (student_ans != null) {
                    //   studentAns.setText("Student answer was " + student_ans);
                    tv_student_answer.setText(student_ans);
                } else {
                    tv_student_answer.setText("Student has not done this test");
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void UpdateMarks(int marks){
        total_questions=DataPreference.getInt("total_questions",0);

        if (total_questions>=marks){
            dataEditor.putInt("marks",marks);
            dataEditor.apply();
            tv_score.setText(String.valueOf(marks));
             // Toast.makeText(this, "marks"+marks, Toast.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"You have finished this exam",Snackbar.LENGTH_LONG).show();
        }
    }

    private void updateImage (int exam_question){
        grade = DataPreference.getString("grade", "");
        questionImage_ref = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd")
                .child(grade).child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
        questionImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url = dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")) {
//                        question_imageView.setImageResource(R.drawable.image_bg);

                    } else {
                        Glide.with(getApplicationContext()).load(image_url).into(question_imageView);

                    }
                } else {
//                    question_imageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void previousQuestion () {
        exam_question = DataPreference.getInt("question number", 0);
        if (exam_question > 0) {
            exam_question--;
            dataEditor.putInt("question number", exam_question);
            dataEditor.apply();
            updateQestion(exam_question);
            updateImage(exam_question);
            compareAns(String.valueOf(exam_question));
            updateStudentImage(exam_question);
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "No Previous question", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed () {
        startActivity(new Intent(TeacherMarkActivity.this, ClassResults.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}