package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mackvel.soma.Model.Students;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewResultsStudent extends AppCompatActivity {
    TextView question, question_number,score,OptionA
            ,OptionB,OptionC,OptionD,questionNo,correctAns,studentAns,tv_all_questions;
    FirebaseUser fuser;
    DatabaseReference databaseReference,allQ_ref,questionImage_ref;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String grade,subject,exam,examQuestion,key,student_id,correct_ans,student_ans;
    int exam_question, total_questions;
    ImageView questionImageView,back_imageView;
    RelativeLayout next,previous;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
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
        questionImageView=findViewById(R.id.question_imageView);
        studentAns=findViewById(R.id.tv_yourAns);
        tv_all_questions= findViewById(R.id.tv_all_questions);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        student_id=DataPreference.getString("student id","");
        key=DataPreference.getString("school key","");

        grade=DataPreference.getString("student grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        exam_question=DataPreference.getInt("question number",0);

        updateQestion(exam_question);
        updateImage(exam_question);
        compareAns(String.valueOf(exam_question));
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam_question=DataPreference.getInt("question number",0);
                updateQestion(exam_question);
                updateImage(exam_question);
                compareAns(String.valueOf(exam_question));

            }
        },2000);






        allQ_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.getValue(String.class);
                if (total!=null){
                    tv_all_questions.setText(total);
                    total_questions=Integer.parseInt(total);
                    dataEditor.putInt("total_questions",total_questions).apply();

                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"No results available",Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Students").child(student_id).child(exam).child(subject).child(subject);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String subject_score=dataSnapshot.getValue(String.class);
                score.setText(subject_score);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam_question=DataPreference.getInt("question number",0);
                exam_question++;
                int a=exam_question+1;
                if (total_questions>=a){
                    dataEditor.putInt("question number",exam_question).apply();
                    updateQestion(exam_question);
                    updateImage(exam_question);
                    compareAns(String.valueOf(exam_question));

                }else {
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
                    Toast.makeText(ViewResultsStudent.this, "No previous questions", Toast.LENGTH_SHORT).show();
                }

            }


        });

    }


    private void updateQestion( int exam_question) {
            examQuestion=String.valueOf(exam_question);
            int a= exam_question+1;
            questionNo.setText(String.valueOf(a));
            databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("question");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Question=dataSnapshot.child(key).child(grade).child(exam)
                            .child(subject).child(examQuestion).child("question").getValue(String.class);
                    question.setText(Question);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference=FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionA");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.child(key).child(grade).child(exam)
                            .child(subject).child(examQuestion).child("optionA").getValue(String.class);
                    OptionA.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionB");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.child(key).child(grade).child(exam)
                            .child(subject).child(examQuestion).child("optionB").getValue(String.class);
                    OptionB.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionC");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.child(key).child(grade).child(exam)
                            .child(subject).child(examQuestion).child("optionC").getValue(String.class);
                    OptionC.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.child(key).child(grade).child(exam)
                    .child(subject).child(examQuestion).child("optionD");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String option=dataSnapshot.child(key).child(grade).child(exam)
                            .child(subject).child(examQuestion).child("optionD").getValue(String.class);
                    OptionD.setText(option);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
    private void updateImage(int exam_question){
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
                        //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
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
    private void compareAns(final String examQuestion){

        databaseReference=FirebaseDatabase.getInstance().getReference("Students");
        databaseReference.child(student_id).child("ExamAns")
                .child(exam).child(subject).child(examQuestion);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                student_ans=dataSnapshot.child(student_id).child("ExamAns")
                        .child(exam).child(subject).child(examQuestion).getValue(String.class);
                if (student_ans!=null){
                    studentAns.setText("Your answer was "+student_ans);
                }
                else{
                    studentAns.setText("Student has not done this exam");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference.child(key).child(grade).child(exam)
                .child(subject).child(examQuestion).child("correctans");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                correct_ans=dataSnapshot.child(key).child(grade).child(exam)
                        .child(subject).child(examQuestion).child("correctans").getValue(String.class);
                if (correct_ans!=null){
                    correctAns.setText("The correct answer is "+correct_ans);
                }
                else{
                    correctAns.setText("The correct answer is not given");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void previousQuestion() {
        exam_question=DataPreference.getInt("question number",0);
        if (exam_question>0){
            exam_question--;
            dataEditor.putInt("question number",exam_question);
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
        startActivity(new Intent(ViewResultsStudent.this,StudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }


}