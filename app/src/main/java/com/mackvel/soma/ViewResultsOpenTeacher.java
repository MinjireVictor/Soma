package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mackvel.soma.Model.Students;

import java.text.SimpleDateFormat;

public class ViewResultsOpenTeacher extends AppCompatActivity {
    TextView question, question_number,score,tv1,all_questions,tv_student_name
            ,teachers_comments,student_answer,answer_state;


    RelativeLayout previous,next;
    ImageView iv_internet;
    DatabaseReference questionRef,studentAns_ref,allQ_ref
            ,questionImage_ref,answerImage_ref,teacher_commentRef,studentIdRef,scoreRef;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String subject,exam,examQuestion,answer,key,student_id,grade,student_name;
    int marks,exam_question,total_questions;
    ImageView questionImageView,answerImageView;
    FirebaseUser fuser;
    DatabaseReference databaseReference;
    ImageView iv_answerstate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results_open_teacher);

        score=findViewById(R.id.tv_score);
        all_questions=findViewById(R.id.tv_total_questions);
        tv_student_name=findViewById(R.id.tv_student_name);
        question_number=findViewById(R.id.question_no);
        questionImageView=findViewById(R.id.question_imageView);
        answerImageView=findViewById(R.id.student_imageView);
        question=findViewById(R.id.tv_question);
        student_answer =findViewById(R.id.tv_student_answer);
        teachers_comments=findViewById(R.id.tv_teachers_comment);
        answer_state=findViewById(R.id.tv_answer_state);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
        iv_answerstate=findViewById(R.id.iv_answer_state);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        student_name=getIntent().getStringExtra("student name");
        tv_student_name.setText(student_name);
        exam=DataPreference.getString("exam","");
        subject=DataPreference.getString("subject","");
        key=DataPreference.getString("school key","");
        exam_question=DataPreference.getInt("q_number",0);

        studentIdRef= FirebaseDatabase.getInstance().getReference("Students");
        studentIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren() ){
                 //   Students students=snapshot.getValue(Students.class);
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
        updateComment(exam_question);
        updateStudentAns((exam_question));
        updateAnswerImage(exam_question);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam_question=DataPreference.getInt("q_number",0);
                updateQestion(exam_question);
                updateImage(exam_question);
                updateComment(exam_question);
                updateStudentAns((exam_question));
                updateAnswerImage(exam_question);

            }
        },200);
        scoreRef=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child("score");
        scoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_score=dataSnapshot.getValue(String.class);
                if (student_score!=null){
                    score.setText(student_score);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        allQ_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.getValue(String.class);
                if (total!=null){
                    all_questions.setText(total);
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
                exam_question=DataPreference.getInt("q_number",0);
                exam_question++;
                int a=exam_question+1;
                if (total_questions>=a){
                    dataEditor.putInt("q_number",exam_question).apply();
                    updateQestion(exam_question);
                    updateImage(exam_question);
                    updateComment(exam_question);
                    updateStudentAns((exam_question));
                    updateAnswerImage(exam_question);
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
                    Toast.makeText(ViewResultsOpenTeacher.this, "no previous questions", Toast.LENGTH_SHORT).show();
                }

            }


        });






    }
    private void updateQestion(int exam_question) {

        examQuestion = String.valueOf(exam_question);
        int a = exam_question + 1;
        question_number.setText(String.valueOf(a));
        questionRef = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade).child(exam)
                .child(subject).child(examQuestion).child("question");
        questionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Question = dataSnapshot.getValue(String.class);
                question.setText(Question);
                if (Question == null) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), subject + " " + exam + " does not exist", Snackbar.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
    private void updateStudentAns(int exam_question){
        student_id = DataPreference.getString("student id", "");

        studentAns_ref = FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(String.valueOf(exam_question)).child("studentAns");
        studentAns_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                answer = dataSnapshot.getValue(String.class);
                student_answer.setText(answer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(String.valueOf(exam_question)).child("status");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status=dataSnapshot.getValue(String.class);
                answer_state.setText("Student answer is "+status);
                if (status!=null){
                    if (status.equals("correct")){
                        iv_answerstate.setImageResource(R.drawable.check);
                    }else if (status.equals("wrong")){
                        iv_answerstate.setImageResource(R.drawable.incorrect);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void updateImage( int exam_question){
        grade=DataPreference.getString("grade","");
        questionImage_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd")
                .child(grade).child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
        questionImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url=dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")){
                     //   questionImageView.setImageResource(R.drawable.image_bg);

                    }else{
                        Glide.with(getApplicationContext()).load(image_url).into(questionImageView);

                    }
                }else{
                  //  questionImageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void updateComment(int exam_question){
        student_id = DataPreference.getString("student id", "");

        teacher_commentRef=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(String.valueOf(exam_question)).child("comment");
        teacher_commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String comment=(String) dataSnapshot.getValue();
                teachers_comments.setText(comment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void updateAnswerImage(int exam_question){
        student_id = DataPreference.getString("student id", "");
        grade=DataPreference.getString("grade","");
        answerImage_ref=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                .child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
        answerImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url=dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")){
                     //   answerImageView.setImageResource(R.drawable.image_bg);

                    }else{
                        Glide.with(getApplicationContext()).load(image_url).into(answerImageView);

                    }
                }else{
                  //  answerImageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void previousQuestion() {
        exam_question=DataPreference.getInt("q_number",0);
        if (exam_question>0){
            exam_question--;
            dataEditor.putInt("q_number",exam_question);
            dataEditor.apply();
            updateQestion(exam_question);
            updateImage(exam_question);
           updateAnswerImage(exam_question);
           updateComment(exam_question);
           updateImage(exam_question);
           updateStudentAns(exam_question);
        }
        else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"No Previous question",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        dataEditor.remove("q_number").apply();
        String results=DataPreference.getString("Result","");
        if (results.equals("Class results")){
            startActivity(new Intent(ViewResultsOpenTeacher.this,ClassResults.class));
        }else {
            startActivity(new Intent(ViewResultsOpenTeacher.this,StudentSearchActivity.class));
        }

    }
}