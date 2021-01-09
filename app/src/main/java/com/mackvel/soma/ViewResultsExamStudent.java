package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewResultsExamStudent extends AppCompatActivity {
    TextView ans1, ans2, ans3, ans4, ans5, ans6, ans7, ans8, ans9, ans10, ans11, ans12, ans13, ans14, ans15, ans16, ans17, ans18, ans19, ans20, ans21, ans22, ans23, ans24, ans25, ans26, ans27, ans28, ans29, ans30, ans31, ans32, ans33, ans34, ans35, ans36, ans37, ans38, ans39, ans40, ans41, ans42, ans43, ans44, ans45, ans46, ans47, ans48, ans49, ans50;
    ImageView mark1, mark2, mark3, mark4, mark5, mark6, mark7, mark8, mark9, mark10, mark11, mark12, mark13,
            mark14, mark15, mark16, mark17, mark18, mark19, mark20, mark21, mark22, mark23, mark24, mark25, mark26,
            mark27, mark28, mark29, mark30, mark31, mark32, mark33, mark34, mark35, mark36, mark37, mark38, mark39,
            mark40, mark41, mark42, mark43, mark44, mark45, mark46, mark47, mark48, mark49, mark50;
    TextView correctAns1, correctAns2, correctAns3, correctAns4, correctAns5, correctAns6,
            correctAns7, correctAns8, correctAns9, correctAns10, correctAns11, correctAns12, correctAns13,
            correctAns14, correctAns15, correctAns16, correctAns17, correctAns18, correctAns19, correctAns20,
            correctAns21, correctAns22, correctAns23, correctAns24, correctAns25, correctAns26, correctAns27,
            correctAns28, correctAns29, correctAns30, correctAns31, correctAns32, correctAns33, correctAns34,
            correctAns35, correctAns36, correctAns37, correctAns38, correctAns39, correctAns40, correctAns41,
            correctAns42, correctAns43, correctAns44, correctAns45, correctAns46, correctAns47, correctAns48,
            correctAns49, correctAns50,tv_score;
    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String grade, subject, exam, examQuestion, key, student_id, status;
    Button View_paper;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results_exam_student);
        ans1 = findViewById(R.id.Ans_one);
        ans2 = findViewById(R.id.Ans_2);
        ans3 = findViewById(R.id.Ans_3);
        ans4 = findViewById(R.id.Ans_4);
        ans5 = findViewById(R.id.Ans_5);
        ans6 = findViewById(R.id.Ans_6);
        ans7 = findViewById(R.id.Ans_7);
        ans8 = findViewById(R.id.Ans_8);
        ans9 = findViewById(R.id.Ans_9);
        ans10 = findViewById(R.id.Ans_10);
        ans11 = findViewById(R.id.Ans_11);
        ans12 = findViewById(R.id.Ans_12);
        ans13 = findViewById(R.id.Ans_13);
        ans14 = findViewById(R.id.Ans_14);
        ans15 = findViewById(R.id.Ans_15);
        ans16 = findViewById(R.id.Ans_16);
        ans17 = findViewById(R.id.Ans_17);
        ans18 = findViewById(R.id.Ans_18);
        ans19 = findViewById(R.id.Ans_19);
        ans20 = findViewById(R.id.Ans_20);
        ans21 = findViewById(R.id.Ans_21);
        ans22 = findViewById(R.id.Ans_22);
        ans23 = findViewById(R.id.Ans_23);
        ans24 = findViewById(R.id.Ans_24);
        ans25 = findViewById(R.id.Ans_25);
        ans26 = findViewById(R.id.Ans_26);
        ans27 = findViewById(R.id.Ans_27);
        ans28 = findViewById(R.id.Ans_28);
        ans29 = findViewById(R.id.Ans_29);
        ans30 = findViewById(R.id.Ans_30);
        ans31 = findViewById(R.id.Ans_31);
        ans32 = findViewById(R.id.Ans_32);
        ans33 = findViewById(R.id.Ans_33);
        ans34 = findViewById(R.id.Ans_34);
        ans35 = findViewById(R.id.Ans_35);
        ans36 = findViewById(R.id.Ans_36);
        ans37 = findViewById(R.id.Ans_37);
        ans38 = findViewById(R.id.Ans_38);
        ans39 = findViewById(R.id.Ans_39);
        ans40 = findViewById(R.id.Ans_40);
        ans41 = findViewById(R.id.Ans_41);
        ans42 = findViewById(R.id.Ans_42);
        ans43 = findViewById(R.id.Ans_43);
        ans44 = findViewById(R.id.Ans_44);
        ans45 = findViewById(R.id.Ans_45);
        ans46 = findViewById(R.id.Ans_46);
        ans47 = findViewById(R.id.Ans_47);
        ans48 = findViewById(R.id.Ans_48);
        ans49 = findViewById(R.id.Ans_49);
        ans50 = findViewById(R.id.Ans_50);

        mark1 = findViewById(R.id.mark_one);
        mark2 = findViewById(R.id.mark_2);
        mark3 = findViewById(R.id.mark_3);
        mark4 = findViewById(R.id.mark_4);
        mark5 = findViewById(R.id.mark_5);
        mark6 = findViewById(R.id.mark_6);
        mark7 = findViewById(R.id.mark_7);
        mark8 = findViewById(R.id.mark_8);
        mark9 = findViewById(R.id.mark_9);
        mark10 = findViewById(R.id.mark_10);
        mark11 = findViewById(R.id.mark_11);
        mark12 = findViewById(R.id.mark_12);
        mark13 = findViewById(R.id.mark_13);
        mark14 = findViewById(R.id.mark_14);
        mark15 = findViewById(R.id.mark_15);
        mark16 = findViewById(R.id.mark_16);
        mark17 = findViewById(R.id.mark_17);
        mark18 = findViewById(R.id.mark_18);
        mark19 = findViewById(R.id.mark_19);
        mark20 = findViewById(R.id.mark_20);
        mark21 = findViewById(R.id.mark_21);
        mark22 = findViewById(R.id.mark_22);
        mark23 = findViewById(R.id.mark_23);
        mark24 = findViewById(R.id.mark_24);
        mark25 = findViewById(R.id.mark_25);
        mark26 = findViewById(R.id.mark_26);
        mark27 = findViewById(R.id.mark_27);
        mark28 = findViewById(R.id.mark_28);
        mark29 = findViewById(R.id.mark_29);
        mark30 = findViewById(R.id.mark_30);
        mark31 = findViewById(R.id.mark_31);
        mark32 = findViewById(R.id.mark_32);
        mark33 = findViewById(R.id.mark_33);
        mark34 = findViewById(R.id.mark_34);
        mark35 = findViewById(R.id.mark_35);
        mark36 = findViewById(R.id.mark_36);
        mark37 = findViewById(R.id.mark_37);
        mark38 = findViewById(R.id.mark_38);
        mark39 = findViewById(R.id.mark_39);
        mark40 = findViewById(R.id.mark_40);
        mark41 = findViewById(R.id.mark_41);
        mark42 = findViewById(R.id.mark_42);
        mark43 = findViewById(R.id.mark_43);
        mark44 = findViewById(R.id.mark_44);
        mark45 = findViewById(R.id.mark_45);
        mark46 = findViewById(R.id.mark_46);
        mark47 = findViewById(R.id.mark_47);
        mark48 = findViewById(R.id.mark_48);
        mark49 = findViewById(R.id.mark_49);
        mark50 = findViewById(R.id.mark_50);

        correctAns1 = findViewById(R.id.correctAns_1);
        correctAns2 = findViewById(R.id.correctAns_2);
        correctAns3 = findViewById(R.id.correctAns_3);
        correctAns4 = findViewById(R.id.correctAns_4);
        correctAns5 = findViewById(R.id.correctAns_5);
        correctAns6 = findViewById(R.id.correctAns_6);
        correctAns7 = findViewById(R.id.correctAns_7);
        correctAns8 = findViewById(R.id.correctAns_8);
        correctAns9 = findViewById(R.id.correctAns_9);
        correctAns10 = findViewById(R.id.correctAns_10);
        correctAns11 = findViewById(R.id.correctAns_11);
        correctAns12 = findViewById(R.id.correctAns_12);
        correctAns13 = findViewById(R.id.correctAns_13);
        correctAns14 = findViewById(R.id.correctAns_14);
        correctAns15 = findViewById(R.id.correctAns_15);
        correctAns16 = findViewById(R.id.correctAns_16);
        correctAns17 = findViewById(R.id.correctAns_17);
        correctAns18 = findViewById(R.id.correctAns_18);
        correctAns19 = findViewById(R.id.correctAns_19);
        correctAns20 = findViewById(R.id.correctAns_20);
        correctAns21 = findViewById(R.id.correctAns_21);
        correctAns22 = findViewById(R.id.correctAns_22);
        correctAns23 = findViewById(R.id.correctAns_23);
        correctAns24 = findViewById(R.id.correctAns_24);
        correctAns25 = findViewById(R.id.correctAns_25);
        correctAns26 = findViewById(R.id.correctAns_26);
        correctAns27 = findViewById(R.id.correctAns_27);
        correctAns28 = findViewById(R.id.correctAns_28);
        correctAns29 = findViewById(R.id.correctAns_29);
        correctAns30 = findViewById(R.id.correctAns_30);
        correctAns31 = findViewById(R.id.correctAns_31);
        correctAns32 = findViewById(R.id.correctAns_32);
        correctAns33 = findViewById(R.id.correctAns_33);
        correctAns34 = findViewById(R.id.correctAns_34);
        correctAns35 = findViewById(R.id.correctAns_35);
        correctAns36 = findViewById(R.id.correctAns_36);
        correctAns37 = findViewById(R.id.correctAns_37);
        correctAns38 = findViewById(R.id.correctAns_38);
        correctAns39 = findViewById(R.id.correctAns_39);
        correctAns40 = findViewById(R.id.correctAns_40);
        correctAns41 = findViewById(R.id.correctAns_41);
        correctAns42 = findViewById(R.id.correctAns_42);
        correctAns43 = findViewById(R.id.correctAns_43);
        correctAns44 = findViewById(R.id.correctAns_44);
        correctAns45 = findViewById(R.id.correctAns_45);
        correctAns46 = findViewById(R.id.correctAns_46);
        correctAns47 = findViewById(R.id.correctAns_47);
        correctAns48 = findViewById(R.id.correctAns_48);
        correctAns49 = findViewById(R.id.correctAns_49);
        correctAns50 = findViewById(R.id.correctAns_50);

        tv_score=findViewById(R.id.tv_score);

        View_paper=findViewById(R.id.bt_view_paper);

        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();
        student_id = DataPreference.getString("student id", "");
        key = DataPreference.getString("school key", "");
        grade = DataPreference.getString("student grade", "");
        subject = DataPreference.getString("subject", "");
        exam = DataPreference.getString("exam", "");
        pd = new ProgressDialog(ViewResultsExamStudent.this);
        pd.setMessage("Fetching results...");
        pd.show();

        View_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(ViewResultsExamStudent.this,DoExamPapersActivity.class);
               intent.putExtra("view paper","view paper");
               startActivity(intent);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child("score");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_score=dataSnapshot.getValue(String.class);
                if(student_score!=null){
                    tv_score.setText(student_score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        examQuestion = "1";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans1.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                } else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "You have not done this test", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        examQuestion = "2";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans2.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        examQuestion = "3";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans3.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        examQuestion = "4";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans4.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        examQuestion = "5";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans5.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "6";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans6.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "7";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans7.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "8";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans8.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "9";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans9.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "10";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans10.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "11";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans11.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "12";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans12.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "13";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans13.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "14";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans14.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "15";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans15.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "16";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans16.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "17";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans17.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "18";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans18.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "19";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans19.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "20";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans20.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "21";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans21.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "22";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans22.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "23";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans23.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "24";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans24.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "25";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans25.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        examQuestion = "26";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans26.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "27";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans27.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "28";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans28.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "29";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans29.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "30";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans30.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "31";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans31.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "32";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans32.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "33";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans33.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "34";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans34.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "35";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans35.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "36";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans36.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "37";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans37.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "38";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans38.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "39";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans39.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "40";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans40.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "41";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans41.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "42";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans42.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "43";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans43.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "44";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans44.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "45";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans45.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "46";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans46.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "47";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans47.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "48";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans48.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "49";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans49.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        examQuestion = "50";
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                .child("ExaminationAns").child(exam).child(subject).child(examQuestion);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String student_answer = dataSnapshot.getValue(String.class);
                if (student_answer != null) {
                    ans50.setText(student_answer);
                    compareAns(examQuestion, student_answer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        pd.dismiss();
    }

    private void compareAns(final String examQuestion, final String student_answer) {

          for (int i=1; i<=50; i++){
              databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key)
                      .child("ExaminationAns").child(grade).child(exam).child(subject).child(examQuestion);
              databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id)
                      .child("ExaminationAns").child(exam).child(subject).child(examQuestion);

              databaseReference.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                      String correctAns = dataSnapshot.getValue(String.class);
                      Toast.makeText(ViewResultsExamStudent.this, "correct answer= "+ correctAns, Toast.LENGTH_SHORT).show();
                      if (correctAns != null && student_answer!=null) {
                          if (correctAns.equals(student_answer)) {
                              status = "correct";
                              Status(status, examQuestion, correctAns);
                          } else {
                              status = "wrong";
                              Status(status, examQuestion, correctAns);
                          }
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });

          }



    }

    private void Status(String status, String examQuestion, String correctAns) {
        if (examQuestion.equals("1")) {
            if (status.equals("correct")) {
                mark1.setImageResource(R.drawable.correct);
            } else {
                mark1.setImageResource(R.drawable.incorrect);
                correctAns1.setText(correctAns);
            }
        } else if (examQuestion.equals("2")) {
            if (status.equals("correct")) {
                mark2.setImageResource(R.drawable.correct);
            } else {
                mark2.setImageResource(R.drawable.incorrect);
                correctAns2.setText(correctAns);
            }

        } else if (examQuestion.equals("3")) {
            if (status.equals("correct")) {
                mark3.setImageResource(R.drawable.correct);
            } else {
                mark3.setImageResource(R.drawable.incorrect);
                correctAns3.setText(correctAns);
            }

        } else if (examQuestion.equals("4")) {
            if (status.equals("correct")) {
                mark4.setImageResource(R.drawable.correct);
            } else {
                mark4.setImageResource(R.drawable.incorrect);
                correctAns4.setText(correctAns);
            }

        } else if (examQuestion.equals("5")) {
            if (status.equals("correct")) {
                mark5.setImageResource(R.drawable.correct);
            } else {
                mark5.setImageResource(R.drawable.incorrect);
                correctAns5.setText(correctAns);
            }

        } else if (examQuestion.equals("6")) {
            if (status.equals("correct")) {
                mark6.setImageResource(R.drawable.correct);
            } else {
                mark6.setImageResource(R.drawable.incorrect);
                correctAns6.setText(correctAns);
            }

        } else if (examQuestion.equals("7")) {
            if (status.equals("correct")) {
                mark7.setImageResource(R.drawable.correct);
            } else {
                mark7.setImageResource(R.drawable.incorrect);
                correctAns7.setText(correctAns);
            }

        } else if (examQuestion.equals("8")) {
            if (status.equals("correct")) {
                mark8.setImageResource(R.drawable.correct);
            } else {
                mark8.setImageResource(R.drawable.incorrect);
                correctAns8.setText(correctAns);
            }
        }


        else if (examQuestion.equals("9")) {
                if (status.equals("correct")) {
                    mark9.setImageResource(R.drawable.correct);
                } else {
                    mark9.setImageResource(R.drawable.incorrect);
                    correctAns9.setText(correctAns);
                }

            } else if (examQuestion.equals("10")) {
                if (status.equals("correct")) {
                    mark10.setImageResource(R.drawable.correct);
                } else {
                    mark10.setImageResource(R.drawable.incorrect);
                    correctAns9.setText(correctAns);
                }

            } else if (examQuestion.equals("11")) {
                if (status.equals("correct")) {
                    mark11.setImageResource(R.drawable.correct);
                } else {
                    mark11.setImageResource(R.drawable.incorrect);
                    correctAns11.setText(correctAns);
                }

            } else if (examQuestion.equals("12")) {
                if (status.equals("correct")) {
                    mark12.setImageResource(R.drawable.correct);
                } else {
                    mark12.setImageResource(R.drawable.incorrect);
                    correctAns12.setText(correctAns);
                }

            } else if (examQuestion.equals("13")) {
                if (status.equals("correct")) {
                    mark13.setImageResource(R.drawable.correct);
                } else {
                    mark13.setImageResource(R.drawable.incorrect);
                    correctAns13.setText(correctAns);
                }

            } else if (examQuestion.equals("14")) {
                if (status.equals("correct")) {
                    mark14.setImageResource(R.drawable.correct);
                } else {
                    mark14.setImageResource(R.drawable.incorrect);
                    correctAns14.setText(correctAns);
                }

            } else if (examQuestion.equals("15")) {
                if (status.equals("correct")) {
                    mark15.setImageResource(R.drawable.correct);
                } else {
                    mark15.setImageResource(R.drawable.incorrect);
                    correctAns15.setText(correctAns);
                }

            } else if (examQuestion.equals("16")) {
                if (status.equals("correct")) {
                    mark16.setImageResource(R.drawable.correct);
                } else {
                    mark16.setImageResource(R.drawable.incorrect);
                    correctAns16.setText(correctAns);
                }

            } else if (examQuestion.equals("17")) {
                if (status.equals("correct")) {
                    mark17.setImageResource(R.drawable.correct);
                } else {
                    mark17.setImageResource(R.drawable.incorrect);
                    correctAns17.setText(correctAns);
                }

            } else if (examQuestion.equals("18")) {
                if (status.equals("correct")) {
                    mark18.setImageResource(R.drawable.correct);
                } else {
                    mark18.setImageResource(R.drawable.incorrect);
                    correctAns18.setText(correctAns);
                }

            } else if (examQuestion.equals("19")) {
                if (status.equals("correct")) {
                    mark19.setImageResource(R.drawable.correct);
                } else {
                    mark19.setImageResource(R.drawable.incorrect);
                    correctAns19.setText(correctAns);
                }

            } else if (examQuestion.equals("20")) {
                if (status.equals("correct")) {
                    mark20.setImageResource(R.drawable.correct);
                } else {
                    mark20.setImageResource(R.drawable.incorrect);
                    correctAns20.setText(correctAns);
                }

            } else if (examQuestion.equals("21")) {
                if (status.equals("correct")) {
                    mark21.setImageResource(R.drawable.correct);
                } else {
                    mark21.setImageResource(R.drawable.incorrect);
                    correctAns21.setText(correctAns);
                }

            } else if (examQuestion.equals("22")) {
                if (status.equals("correct")) {
                    mark22.setImageResource(R.drawable.correct);
                } else {
                    mark22.setImageResource(R.drawable.incorrect);
                    correctAns22.setText(correctAns);
                }

            } else if (examQuestion.equals("23")) {
                if (status.equals("correct")) {
                    mark23.setImageResource(R.drawable.correct);
                } else {
                    mark23.setImageResource(R.drawable.incorrect);
                    correctAns23.setText(correctAns);
                }

            } else if (examQuestion.equals("24")) {
                if (status.equals("correct")) {
                    mark24.setImageResource(R.drawable.correct);
                } else {
                    mark24.setImageResource(R.drawable.incorrect);
                    correctAns24.setText(correctAns);
                }

            } else if (examQuestion.equals("25")) {
                if (status.equals("correct")) {
                    mark25.setImageResource(R.drawable.correct);
                } else {
                    mark25.setImageResource(R.drawable.incorrect);
                    correctAns25.setText(correctAns);
                }

            } else if (examQuestion.equals("26")) {
                if (status.equals("correct")) {
                    mark26.setImageResource(R.drawable.correct);
                } else {
                    mark26.setImageResource(R.drawable.incorrect);
                    correctAns26.setText(correctAns);
                }

            } else if (examQuestion.equals("27")) {
                if (status.equals("correct")) {
                    mark27.setImageResource(R.drawable.correct);
                } else {
                    mark27.setImageResource(R.drawable.incorrect);
                    correctAns27.setText(correctAns);
                }

            } else if (examQuestion.equals("28")) {
                if (status.equals("correct")) {
                    mark28.setImageResource(R.drawable.correct);
                } else {
                    mark28.setImageResource(R.drawable.incorrect);
                    correctAns28.setText(correctAns);
                }

            } else if (examQuestion.equals("29")) {
                if (status.equals("correct")) {
                    mark29.setImageResource(R.drawable.correct);
                } else {
                    mark29.setImageResource(R.drawable.incorrect);
                    correctAns29.setText(correctAns);
                }

            } else if (examQuestion.equals("30")) {
                if (status.equals("correct")) {
                    mark30.setImageResource(R.drawable.correct);
                } else {
                    mark30.setImageResource(R.drawable.incorrect);
                    correctAns30.setText(correctAns);
                }

            } else if (examQuestion.equals("31")) {
                if (status.equals("correct")) {
                    mark31.setImageResource(R.drawable.correct);
                } else {
                    mark31.setImageResource(R.drawable.incorrect);
                    correctAns31.setText(correctAns);
                }

            } else if (examQuestion.equals("32")) {
                if (status.equals("correct")) {
                    mark32.setImageResource(R.drawable.correct);
                } else {
                    mark32.setImageResource(R.drawable.incorrect);
                    correctAns32.setText(correctAns);
                }

            } else if (examQuestion.equals("33")) {
                if (status.equals("correct")) {
                    mark33.setImageResource(R.drawable.correct);
                } else {
                    mark33.setImageResource(R.drawable.incorrect);
                    correctAns33.setText(correctAns);
                }

            } else if (examQuestion.equals("34")) {
                if (status.equals("correct")) {
                    mark34.setImageResource(R.drawable.correct);
                } else {
                    mark34.setImageResource(R.drawable.incorrect);
                    correctAns34.setText(correctAns);
                }

            } else if (examQuestion.equals("35")) {
                if (status.equals("correct")) {
                    mark35.setImageResource(R.drawable.correct);
                } else {
                    mark35.setImageResource(R.drawable.incorrect);
                    correctAns35.setText(correctAns);
                }

            } else if (examQuestion.equals("36")) {
                if (status.equals("correct")) {
                    mark36.setImageResource(R.drawable.correct);
                } else {
                    mark36.setImageResource(R.drawable.incorrect);
                    correctAns36.setText(correctAns);
                }

            } else if (examQuestion.equals("37")) {
                if (status.equals("correct")) {
                    mark37.setImageResource(R.drawable.correct);
                } else {
                    mark37.setImageResource(R.drawable.incorrect);
                    correctAns37.setText(correctAns);
                }

            } else if (examQuestion.equals("38")) {
                if (status.equals("correct")) {
                    mark38.setImageResource(R.drawable.correct);
                } else {
                    mark38.setImageResource(R.drawable.incorrect);
                    correctAns38.setText(correctAns);
                }

            } else if (examQuestion.equals("39")) {
                if (status.equals("correct")) {
                    mark39.setImageResource(R.drawable.correct);
                } else {
                    mark39.setImageResource(R.drawable.incorrect);
                    correctAns39.setText(correctAns);
                }

            } else if (examQuestion.equals("40")) {
                if (status.equals("correct")) {
                    mark40.setImageResource(R.drawable.correct);
                } else {
                    mark40.setImageResource(R.drawable.incorrect);
                    correctAns40.setText(correctAns);
                }

            } else if (examQuestion.equals("41")) {
                if (status.equals("correct")) {
                    mark41.setImageResource(R.drawable.correct);
                } else {
                    mark41.setImageResource(R.drawable.incorrect);
                    correctAns41.setText(correctAns);
                }

            } else if (examQuestion.equals("42")) {
                if (status.equals("correct")) {
                    mark42.setImageResource(R.drawable.correct);
                } else {
                    mark42.setImageResource(R.drawable.incorrect);
                    correctAns42.setText(correctAns);
                }

            } else if (examQuestion.equals("43")) {
                if (status.equals("correct")) {
                    mark43.setImageResource(R.drawable.correct);
                } else {
                    mark43.setImageResource(R.drawable.incorrect);
                    correctAns43.setText(correctAns);
                }

            } else if (examQuestion.equals("44")) {
                if (status.equals("correct")) {
                    mark44.setImageResource(R.drawable.correct);
                } else {
                    mark44.setImageResource(R.drawable.incorrect);
                    correctAns44.setText(correctAns);
                }

            } else if (examQuestion.equals("45")) {
                if (status.equals("correct")) {
                    mark45.setImageResource(R.drawable.correct);
                } else {
                    mark45.setImageResource(R.drawable.incorrect);
                    correctAns45.setText(correctAns);
                }

            } else if (examQuestion.equals("46")) {
                if (status.equals("correct")) {
                    mark46.setImageResource(R.drawable.correct);
                } else {
                    mark46.setImageResource(R.drawable.incorrect);
                    correctAns46.setText(correctAns);
                }

            } else if (examQuestion.equals("47")) {
                if (status.equals("correct")) {
                    mark47.setImageResource(R.drawable.correct);
                } else {
                    mark47.setImageResource(R.drawable.incorrect);
                    correctAns47.setText(correctAns);
                }

            } else if (examQuestion.equals("48")) {
                if (status.equals("correct")) {
                    mark48.setImageResource(R.drawable.correct);
                } else {
                    mark48.setImageResource(R.drawable.incorrect);
                    correctAns48.setText(correctAns);
                }

            } else if (examQuestion.equals("49")) {
                if (status.equals("correct")) {
                    mark49.setImageResource(R.drawable.correct);
                } else {
                    mark49.setImageResource(R.drawable.incorrect);
                    correctAns49.setText(correctAns);
                }

            } else if (examQuestion.equals("50")) {
                if (status.equals("correct")) {
                    mark50.setImageResource(R.drawable.correct);
                } else {
                    mark50.setImageResource(R.drawable.incorrect);
                    correctAns50.setText(correctAns);
                }

            }

        }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewResultsExamStudent.this,StudentExamPapersActivity.class));
    }
}

