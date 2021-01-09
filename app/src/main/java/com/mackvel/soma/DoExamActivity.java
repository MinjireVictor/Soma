package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DoExamActivity extends AppCompatActivity {
    TextView question, question_number,score, start,stop,tv_time_left,tv1,all_questions;
    Button OptionA,OptionB,OptionC,OptionD;
    RelativeLayout submit_exam,previous;
    ImageView iv_internet;
    DatabaseReference questionRef,optionA_ref,optionB_ref,optionC_ref,optionD_ref,answer_Ref,allQ_ref
            ,questionImage_ref;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String subject,exam,examQuestion,answer,key,student_id,student_grade
            ,duration,not_set;
    int marks,exam_question;
    ImageView questionImageView,back_imageView;
    FirebaseUser fuser;
    DatabaseReference databaseReference;
    private long timeLeftInMilliseconds;
    boolean timerRunning;
    int called,total_questions;
    SimpleDateFormat dateFormat,format;
    ProgressDialog progressDialog;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exam);

        question=findViewById(R.id.tv_question);
        question_number=findViewById(R.id.tv_questionNo);
        OptionA=findViewById(R.id.optionA);
        OptionB=findViewById(R.id.optionB);
        OptionC=findViewById(R.id.optionC);
        OptionD=findViewById(R.id.optionD);
        all_questions=findViewById(R.id.tv_all_questions);
        previous=findViewById(R.id.previous);
        start=findViewById(R.id.start_time);
        tv1= findViewById(R.id.tv1);
        stop=findViewById(R.id.stop_time);
        tv_time_left=findViewById(R.id.time_left);
        submit_exam=findViewById(R.id.bt_submit_exam);
        iv_internet= findViewById(R.id.iv_internet);
        questionImageView=findViewById(R.id.question_imageView);
        score=findViewById(R.id.score);
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        student_id=DataPreference.getString("student id","");
        student_grade=DataPreference.getString("student grade","");
        key=DataPreference.getString("school key","");
      //  grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");

        Context context;
        progressDialog=new ProgressDialog(DoExamActivity.this);

        allQ_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.getValue(String.class);
                if (total==null){
                    not_set="not set";
                    dataEditor.putString(not_set,"not set").apply();
                }else{
                    all_questions.setText(total);
                    total_questions=Integer.parseInt(total);
                    dataEditor.putInt("total questions",total_questions).apply();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference.child(key).child(student_grade).child(exam)
                .child(subject).child("timestamp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String timestamp=dataSnapshot.child(key).child(student_grade).child(exam)
                        .child(subject).child("timestamp").getValue(String.class);
                if (timestamp == null){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Test has not been set",Snackbar.LENGTH_LONG).show();
                    submit_exam.setClickable(false);
                    OptionA.setClickable(false);
                    OptionB.setClickable(false);
                    OptionC.setClickable(false);
                    OptionD.setClickable(false);

                }else{
                    submit_exam.setClickable(true);
                    OptionA.setClickable(true);
                    OptionB.setClickable(true);
                    OptionC.setClickable(true);
                    OptionD.setClickable(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        called=DataPreference.getInt("called",0);
        if (called < 1){
            databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
                    .child(subject).child("Duration");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    duration= dataSnapshot.getValue(String.class);
                    if (duration!=null && !duration.equals("")){
                        int dur=Integer.parseInt(duration);
                        called++;
                        Calendar calender = Calendar.getInstance();
                        int this_hour=calender.get(Calendar.HOUR_OF_DAY);
                        int this_minute=calender.get(Calendar.MINUTE);
                        int hour= (int) (dur/60);
                        int minute= (int) (dur%60);
                        int finish_hour=this_hour+hour;
                        int finish_minute=this_minute+minute;
                        dataEditor.putInt("finish_hour",finish_hour);
                        dataEditor.putInt("finish_minute", finish_minute);
                        dataEditor.putInt("this hour",this_hour);
                        dataEditor.putInt("this minute",this_minute);
                        dataEditor.putInt("called", called);
                        dataEditor.apply();

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
//        if((new ConnectionDetector(DoExamActivity.this)).isConnectingToInternet()){
//            tv1.setText("No Internet");
//            iv_internet.setImageResource(R.drawable.err);
//        }else{
//            tv1.setText("Setting Exam");
//            iv_internet.setImageResource(R.drawable.upload_icon);
//        }
        int this_hour=DataPreference.getInt("this hour",0);
        int this_minute=DataPreference.getInt("this minute",0);
        String startTime;
        startTime=""+this_hour;
        startTime+=":";
        if (this_minute<10)startTime+="0";
        startTime+=this_minute;
        start.setText(startTime);

        exam_question=DataPreference.getInt("questionNo",0);
        updateQestion(exam_question);


        startTimer();
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int this_hour=DataPreference.getInt("this hour",0);
                int this_minute=DataPreference.getInt("this minute",0);
                String startTime;
                startTime=""+this_hour;
                startTime+=":";
                if (this_minute<10)startTime+="0";
                startTime+=this_minute;
                start.setText(startTime);
                startTimer();
                exam_question=DataPreference.getInt("questionNo",0);
                updateQestion(exam_question);

            }
        },500);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviousQuestion();
            }
        });
        submit_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog= new AlertDialog.Builder(DoExamActivity.this);
                alertDialog.setTitle("Submit Exam")
                        .setMessage("Are you sure you want to submit");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitExam();

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
        });

        OptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marks=DataPreference.getInt("marks",0);
                if (OptionA.getText().equals(answer)){
                    marks=marks+1;
                    UpdateMarks(marks);
                }else{

                }
                databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("ExamAns")
                        .child(exam).child(subject);
                HashMap <String, Object> hashMap= new HashMap<>();
                hashMap.put(examQuestion,OptionA.getText().toString());
                databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            exam_question=DataPreference.getInt("questionNo",0);
                            exam_question++;
                            dataEditor.putInt("questionNo",exam_question).apply();
                            updateQestion(exam_question);
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(),"Error, Answer wasnt saved",Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

        OptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marks=DataPreference.getInt("marks",0);
                if (OptionB.getText().equals(answer)){
                    marks=marks+1;
                    UpdateMarks(marks);

                }else{

                }
                databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("ExamAns")
                        .child(exam).child(subject);
                HashMap <String, Object> hashMap= new HashMap<>();
                hashMap.put(examQuestion,OptionB.getText().toString());
                databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            exam_question=DataPreference.getInt("questionNo",0);
                            exam_question++;
                            dataEditor.putInt("questionNo",exam_question).apply();
                            updateQestion(exam_question);
                            updateImage(exam_question);
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(),"Error, Answer wasnt saved",Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

        OptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marks=DataPreference.getInt("marks",0);
                if (OptionC.getText().equals(answer)){
                    marks=marks+1;
                    UpdateMarks(marks);

                }else{

                }
                databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("ExamAns")
                        .child(exam).child(subject);
                HashMap <String, Object> hashMap= new HashMap<>();
                hashMap.put(examQuestion,OptionC.getText().toString());
                databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            exam_question=DataPreference.getInt("questionNo",0);
                            exam_question++;
                            dataEditor.putInt("questionNo",exam_question).apply();
                            updateQestion(exam_question);
                            updateImage(exam_question);
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(),"Error, Answer wasnt saved",Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

        OptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marks=DataPreference.getInt("marks",0);
                if (OptionD.getText().equals(answer)){
                    marks=marks+1;
                    UpdateMarks(marks);
                }else{

                }
                databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("ExamAns")
                        .child(exam).child(subject);
                HashMap <String, Object> hashMap= new HashMap<>();
                hashMap.put(examQuestion,OptionD.getText().toString());
                databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                          //  Toast.makeText(DoExamActivity.this, "Ans saved", Toast.LENGTH_SHORT).show();
                            exam_question=DataPreference.getInt("questionNo",0);
                            exam_question++;
                            dataEditor.putInt("questionNo",exam_question).apply();
                            updateQestion(exam_question);
                            updateImage(exam_question);
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(),"Error, Answer wasnt saved",Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }
    private void updateImage(int exam_question){
        questionImage_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key)
                .child(student_grade).child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
        questionImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url=dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")){
                        // questionImageView.setImageResource(R.drawable.image_bg);

                    }else{
                        //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                        Glide.with(getApplicationContext()).load(image_url).into(questionImageView);

                    }
                }else{
                    // questionImageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void startTimer(){
        Calendar calender = Calendar.getInstance();
        int this_hour=calender.get(Calendar.HOUR_OF_DAY);
        int this_minute=calender.get(Calendar.MINUTE);
        int finish_hour=DataPreference.getInt("finish_hour",0);
        int finish_minute=DataPreference.getInt("finish_minute",0);
        if (finish_minute>=60){
            finish_minute=finish_minute%60;
            finish_hour++;
        }
        String finishTime;
        finishTime=""+finish_hour;
        finishTime+=":";

        if (finish_minute<10)finishTime+="0";
        finishTime+=finish_minute;
        stop.setText(finishTime);
        int hour_difference= finish_hour-this_hour;
        int minute_difference= finish_minute- this_minute;
        if (hour_difference<0){
            // submit exam here
           // startActivity(new Intent(DoExamActivity.this,ExamSubmittedActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));



        }else {
            timeLeftInMilliseconds = (hour_difference * 60 * 60 * 1000) + (minute_difference * 60 * 1000);

            CountDownTimer countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMilliseconds = millisUntilFinished;
                    updateTimer(timeLeftInMilliseconds);

                }
                @Override
                public void onFinish() {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Time is up, Press finish to submit your exam",Snackbar.LENGTH_LONG).show();
                    OptionA.setClickable(false);
                    OptionB.setClickable(false);
                    OptionC.setClickable(false);
                    OptionD.setClickable(false);
                }
            }.start();

        }

    }
    private void updateTimer(long time_left){
        int hours =  (int) time_left/3600000;
        int minutes= (int) (time_left%3600000)/1000/60;
        String timeLeftText;
        timeLeftText=""+hours;
        timeLeftText+=":";
        if (minutes<10)timeLeftText+="0";
        timeLeftText+=minutes;
        tv_time_left.setText(timeLeftText);

    }


    private void UpdateMarks(int marks){
        total_questions=DataPreference.getInt("total questions",0);

        if (total_questions>=marks){
            dataEditor.putInt("marks",marks);
            dataEditor.apply();
          //  Toast.makeText(this, "marks"+marks, Toast.LENGTH_SHORT).show();
        }else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"You have finished this exam",Snackbar.LENGTH_LONG).show();
        }


    }

    private void updateQestion(int exam_question) {
        total_questions=DataPreference.getInt("total questions",0);
        not_set=DataPreference.getString("not set","");
        if (exam_question>=total_questions || not_set.equals(" not set") ){
            Toast.makeText(this, "No more questions, press finish", Toast.LENGTH_SHORT).show();
        }else{
            updateImage(exam_question);
            examQuestion=String.valueOf(exam_question);
            int a=exam_question+1;
            question_number.setText(String.valueOf(a));
            questionRef= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade)
                    .child(exam).child(subject).child(examQuestion).child("question");
            questionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String Question=dataSnapshot.getValue(String.class);
                    question.setText(Question);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            optionA_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
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
            optionB_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
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

            optionC_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
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

            optionD_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
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

            answer_Ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(student_grade).child(exam)
                    .child(subject).child(examQuestion).child("correctans");
            answer_Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    answer=dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }
    private void PreviousQuestion(){
        exam_question=DataPreference.getInt("questionNo",0);
        if (exam_question>0){
            exam_question--;
            dataEditor.putInt("questionNo",exam_question);
            dataEditor.apply();
            updateQestion(exam_question);
        }
        else{
            Snackbar.make(getWindow().getDecorView().getRootView(),"No Previous question",Snackbar.LENGTH_LONG).show();
        }
    }
    private  void submitExam(){
        progressDialog.setMessage("Submitting Exam....");
        progressDialog.show();
        Calendar calender = Calendar.getInstance();
        int this_year=calender.get(Calendar.YEAR);
        int this_month=calender.get(Calendar.MONTH);
        int this_date= calender.get(Calendar.DATE);
        int this_hour=calender.get(Calendar.HOUR_OF_DAY);
        int this_minute=calender.get(Calendar.MINUTE);
        int this_second=calender.get(Calendar.SECOND);
        calender.set(this_year,this_month, this_date,this_hour,this_minute,this_second);
        final String time_stamp = String.valueOf(calender.getTimeInMillis());
        marks=DataPreference.getInt("marks",0);
        String mark=String.valueOf(marks);
        databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child(exam);
        final HashMap <String, Object> hashMap=new HashMap<>();
        hashMap.put(subject,mark);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dataEditor.remove("called")
                            .remove("questionNo").remove("finish_hour").remove("finish_minute")
                            .remove("this hour").remove("this minute").remove("marks").apply();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id).child(exam).child(subject);
                    hashMap.put("timestamp", time_stamp);
                    dataEditor.putString(student_grade + subject + exam + " submitted", student_grade + subject + exam + " submitted").apply();


                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                dataEditor.remove("called").remove("start time").remove("stop time").remove("called")
                                        .remove("time left").apply();
                                startActivity(new Intent(DoExamActivity.this, ExamSubmittedActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();

                            } else {
                                Toast.makeText(DoExamActivity.this, "time stamp could not be added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(DoExamActivity.this, "marks could not be added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        startTimer();
        super.onRestart();
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(DoExamActivity. this, DoExamActivity. class );
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultIntent = PendingIntent. getActivity (DoExamActivity. this, 0 , notificationIntent , 0 ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DoExamActivity. this, default_notification_channel_id )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle( "Warning" )
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(resultIntent)
                .setStyle( new NotificationCompat.InboxStyle())
                .setContentText( "You have left your test undone, click here to continue" ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        sendNotification();
                        startActivity(new Intent(DoExamActivity.this,StudentActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(DoExamActivity.this);
        builder.setMessage("Do you want to exit exam?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}