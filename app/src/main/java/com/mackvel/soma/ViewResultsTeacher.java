package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mackvel.soma.R;
import com.mackvel.soma.Model.Students;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewResultsTeacher extends AppCompatActivity {
    TextView question, question_number,score,OptionA
            ,OptionB,OptionC,OptionD,questionNo,correctAns,studentAns,tv_all_questions
            ,studentName;
    FirebaseUser fuser;
    DatabaseReference questionRef, optionA_ref,optionB_ref,optionC_ref,optionD_ref,studentAns_ref
            ,correctAns_ref,score_ref,studentIdRef,allQ_ref,questionImage_ref,databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String grade,subject,exam,examQuestion,key,student_name,correct_ans,student_ans,student_id;
    int exam_question,total_questions;
    ImageView questionImageView,back_imageView;
    RelativeLayout next,previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results_teacher);
        question=findViewById(R.id.tv_question);
        question_number=findViewById(R.id.tv_questionNo);
        OptionA=findViewById(R.id.optionA);
        OptionB=findViewById(R.id.optionB);
        OptionC=findViewById(R.id.optionC);
        OptionD=findViewById(R.id.optionD);
        score=findViewById(R.id.score);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        questionNo=findViewById(R.id.tv_questionNo);
        correctAns=findViewById(R.id.tv_correctAns);
        studentAns=findViewById(R.id.tv_yourAns);
        questionImageView=findViewById(R.id.question_imageView);
        tv_all_questions=findViewById(R.id.tv_all_questions);
        studentName=findViewById(R.id.student_name);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        student_name=getIntent().getStringExtra("student name");
        studentName.setText(student_name);
        exam=DataPreference.getString("exam","");
      //  exam=getIntent().getStringExtra("student exam");
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade", "");
        subject=DataPreference.getString("subject","");

        exam_question=DataPreference.getInt("questionNo",0);
        examQuestion=String.valueOf(exam_question);
        studentIdRef=FirebaseDatabase.getInstance().getReference("Students");
        studentIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren() ){
                  //  Students students=snapshot.getValue(Students.class);
                    String studentname=snapshot.child("username").getValue(String.class);
                    if (student_name.equals(studentname)){
                        student_id= snapshot.child("id").getValue(String.class);
                        grade=snapshot.child("grade").getValue(String.class);
                        dataEditor.putString("student id",student_id);
                        dataEditor.putString("grade",grade);
                        dataEditor.apply();
                    //    Toast.makeText(ViewResultsTeacher.this, student_id, Toast.LENGTH_SHORT).show();
                    }else{
                      //  Toast.makeText(ViewResultsTeacher.this, "student id could not be found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        grade=DataPreference.getString("grade","");
        //Toast.makeText(this, grade, Toast.LENGTH_SHORT).show();
        updateQestion(exam_question);
        updateImage(exam_question);
        compareAns(String.valueOf(exam_question));
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam_question=DataPreference.getInt("questionNo",0);
                updateQestion(exam_question);
                updateImage(exam_question);
                compareAns(String.valueOf(exam_question));

            }
        },2000);

        allQ_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.getValue(String.class);
                if (total!=null){
                    tv_all_questions.setText(total);
                    total_questions=Integer.parseInt(total);
                    dataEditor.putInt("total_questions",total_questions).apply();
                   // Toast.makeText(ViewResultsTeacher.this, "all q= "+total, Toast.LENGTH_SHORT).show();

                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Student has not done test",Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        student_id=DataPreference.getString("student id","");



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam_question=DataPreference.getInt("questionNo",0);
                exam_question++;
                int a=exam_question+1;
                if (total_questions>=a){
                    dataEditor.putInt("questionNo",exam_question).apply();
                    updateQestion(exam_question);
                    updateImage(exam_question);
                    compareAns(String.valueOf(exam_question));
                }
                else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"No more questions",Snackbar.LENGTH_LONG).show();
                }


            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exam_question>0){
                    previousQuestion();
                }else{
                    Toast.makeText(ViewResultsTeacher.this, "no previous questions", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }

    private void updateQestion(int exam_question) {

        examQuestion=String.valueOf(exam_question);
            int a = exam_question+1;
            questionNo.setText(String.valueOf(a));
            questionRef= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("question");
            questionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Question=dataSnapshot.getValue(String.class);
                    question.setText(Question);
                    if (Question==null){
                        Snackbar.make(getWindow().getDecorView().getRootView(),subject+" "+exam+" does not exist",Snackbar.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            optionA_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionA");
            optionA_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.getValue(String.class);
                    OptionA.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            optionB_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionB");
            optionB_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.getValue(String.class);
                    OptionB.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            optionC_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionC");
            optionC_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.getValue(String.class);
                    OptionC.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            optionD_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionD");
            optionD_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.getValue(String.class);
                    OptionD.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            student_id=DataPreference.getString("student id","");

            studentAns_ref=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("ExamAns")
                    .child(exam).child(subject).child(examQuestion);
            studentAns_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    student_ans=dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            correctAns_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("correctans");
            correctAns_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    correct_ans=dataSnapshot.getValue(String.class);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
    private void compareAns(final String examQuestion) {
        grade=DataPreference.getString("grade","");
        student_id=DataPreference.getString("student id","");
        score_ref= FirebaseDatabase.getInstance().getReference("Students").child(student_id).child(exam).child(subject).child(subject);
        score_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String subject_score=dataSnapshot.getValue(String.class);
                score.setText(subject_score);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");
        databaseReference.child(student_id).child("ExamAns")
                .child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student_ans = dataSnapshot.child(student_id).child("ExamAns")
                        .child(exam).child(subject).child(examQuestion).getValue(String.class);
                if(student_ans!=null){
                    studentAns.setText("Student answer was "+student_ans);
                }
                else{
                    studentAns.setText("Student has not done this exam");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference.child(key).child(grade).child(exam)
                .child(subject).child(examQuestion).child("correctans");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                correct_ans = dataSnapshot.child(key).child(grade).child(exam)
                        .child(subject).child(examQuestion).child("correctans").getValue(String.class);
                if (correct_ans!=null){
                    correctAns.setText("The correct answer is "+correct_ans);
                }
                else{
                    correctAns.setText("The correct answer was not given");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void updateImage(int exam_question){
        grade=DataPreference.getString("grade","");
        questionImage_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key)
                .child(grade).child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
        questionImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url=dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")){
                         questionImageView.setImageResource(R.drawable.image_bg);

                    }else{
                        Glide.with(getApplicationContext()).load(image_url).into(questionImageView);

                    }
                }else{
                     questionImageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void previousQuestion() {
        exam_question=DataPreference.getInt("questionNo",0);
        if (exam_question>0){
            exam_question--;
            dataEditor.putInt("questionNo",exam_question);
            dataEditor.apply();
            updateQestion(exam_question);
            updateImage(exam_question);
            compareAns(String.valueOf(exam_question));
        }
        else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"No Previous question",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewResultsTeacher.this,ClassResults.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}