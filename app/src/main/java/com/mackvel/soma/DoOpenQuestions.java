package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DoOpenQuestions extends AppCompatActivity {
    TextView question, question_number,score, start,stop,tv_time_left,tv1,all_questions;
    TextView tv_photo_required;
    EditText et_student_answer;
    RelativeLayout submit_exam,previous,next;
    ImageView iv_internet;
    DatabaseReference questionRef,request_image_ref,allQ_ref
            ,questionImage_ref,answerImage_ref;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String subject,exam,examQuestion,answer,key,student_id,student_grade
            ,duration,not_set;
    int marks,exam_question;
    ImageView questionImageView,answerImageView;
    FirebaseUser fuser;
    DatabaseReference databaseReference;
    private long timeLeftInMilliseconds;
    boolean timerRunning;
    int called,total_questions;
    SimpleDateFormat dateFormat,format;
    ProgressDialog progressDialog;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;
    StorageReference fileReference;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_open_questions);

        question=findViewById(R.id.tv_question);
        question_number=findViewById(R.id.tv_questionNo);
        tv_photo_required=findViewById(R.id.tv_required_photo);
        answerImageView=findViewById(R.id.iv_student_answer);
        all_questions=findViewById(R.id.tv_all_questions);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.bt_next);
        start=findViewById(R.id.start_time);
        et_student_answer=findViewById(R.id.et_answer);
        tv1= findViewById(R.id.tv1);
        stop=findViewById(R.id.stop_time);
        tv_time_left=findViewById(R.id.time_left);
        submit_exam=findViewById(R.id.bt_submit_exam);
        iv_internet= findViewById(R.id.iv_internet);
        questionImageView=findViewById(R.id.question_imageView);
        score=findViewById(R.id.score);
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference("Uploads");
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();

        student_id=DataPreference.getString("student id","");
        student_grade=DataPreference.getString("student grade","");
        key=DataPreference.getString("school key","");
        //  grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        called=DataPreference.getInt("called",0);
        Context context;
        progressDialog=new ProgressDialog(DoOpenQuestions.this);

        allQ_ref= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(student_grade).child(exam)
                .child(subject).child("total");
        allQ_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.getValue(String.class);
                if (total==null){
                    not_set="not set";
                    dataEditor.putString(not_set,"not set").apply();
                }else{
                    dataEditor.remove(not_set).apply();
                    all_questions.setText(total);
                    total_questions=Integer.parseInt(total);
                    dataEditor.putInt("total questions",total_questions).apply();
                    exam_question=DataPreference.getInt("openQuestionNo",0);
                    if (exam_question>total_questions){
                        dataEditor.remove("openQuestionNo");
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference.child(key).child("OpenEnd").child(student_grade).child(exam)
                .child(subject).child("timestamp");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String timestamp=dataSnapshot.child(key).child("OpenEnd").child(student_grade).child(exam)
                        .child(subject).child("timestamp").getValue(String.class);
                if (timestamp == null){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Test has not been set",Snackbar.LENGTH_LONG).show();
                    submit_exam.setClickable(false);


                }else{
                    submit_exam.setClickable(true);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (called < 1){
            databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key)
                    .child("OpenEnd").child(student_grade).child(exam)
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

                    }else{

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{

        }
        int this_hour=DataPreference.getInt("this hour",0);
        Toast.makeText(DoOpenQuestions.this, "this hour= "+this_hour, Toast.LENGTH_SHORT).show();
        int this_minute=DataPreference.getInt("this minute",0);
        Toast.makeText(DoOpenQuestions.this, "this hour= "+this_minute, Toast.LENGTH_SHORT).show();
        String startTime;
        startTime=""+this_hour;
        startTime+=":";
        if (this_minute<10)startTime+="0";
        startTime+=this_minute;
        start.setText(startTime);

        exam_question=DataPreference.getInt("openQuestionNo",0);
        examQuestion=String.valueOf(exam_question);
        updateQestion(exam_question);
        updateQuestionImage(exam_question);
        updateAnswerImage(exam_question);
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
                exam_question=DataPreference.getInt("openQuestionNo",0);
                updateQestion(exam_question);
                updateQuestionImage(exam_question);
                updateAnswerImage(exam_question);

            }
        },200);
        updateQestion(exam_question);
        updateQuestionImage(exam_question);
        updateAnswerImage(exam_question);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreviousQuestion();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String student_answer=et_student_answer.getText().toString();
                exam_question=DataPreference.getInt("openQuestionNo",0);
                examQuestion=String.valueOf(exam_question);
                if (TextUtils.isEmpty(student_answer)){
                    Toast.makeText(DoOpenQuestions.this, "Type an answer", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                            .child(exam).child(subject).child(examQuestion);
                    HashMap <String, Object> hashMap= new HashMap<>();
                    hashMap.put("studentAns",student_answer);
                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //  Toast.makeText(DoExamActivity.this, "Ans saved", Toast.LENGTH_SHORT).show();
                                exam_question=DataPreference.getInt("openQuestionNo",0);
                                total_questions=DataPreference.getInt("total questions",0);
                                exam_question++;
                                et_student_answer.getText().clear();
                                int a=exam_question+1;
                                if (total_questions>=a){
                                    dataEditor.putInt("openQuestionNo",exam_question).apply();
                                    updateQuestionImage(exam_question);
                                    updateAnswerImage(exam_question);
                                    updateQestion(exam_question);
                                }
                                else{
                                    Toast.makeText(DoOpenQuestions.this, "No more questions, press finish", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Snackbar.make(getWindow().getDecorView().getRootView(),"Error, Answer wasnt saved",Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        submit_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog= new AlertDialog.Builder(DoOpenQuestions.this);
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
        answerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openImage();
            }
        });


    }
    private void updateQuestionImage(int exam_question){
        questionImage_ref=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd")
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

    private void updateAnswerImage(final int exam_question){
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd")
                .child(student_grade).child(exam).child(subject).child(String.valueOf(exam_question)).child("request photo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String request_photo=dataSnapshot.getValue(String.class);
              //  Toast.makeText(DoOpenQuestions.this, ""+request_photo, Toast.LENGTH_SHORT).show();
               // dataEditor.putString("request photo",request_photo).apply();

                if(request_photo!=null){
                    if (request_photo.equals("yes")){
                        tv_photo_required.setVisibility(View.VISIBLE);
                        answerImage_ref= FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns")
                                .child(exam).child(subject).child(String.valueOf(exam_question)).child("image_URL");
                        answerImage_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String image_url=dataSnapshot.getValue(String.class);
                                if (image_url != null) {
                                    if (image_url.equals("")){
                                       answerImageView.setImageResource(R.drawable.image_bg);

                                    }else{
                                        //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                                        Glide.with(getApplicationContext()).load(image_url).into(answerImageView);

                                    }
                                }else{
                                   answerImageView.setImageResource(R.drawable.image_bg);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                }else{
                        answerImageView.setImageDrawable(null);
                        tv_photo_required.setVisibility(View.GONE);
                    }
                }else{
                    answerImageView.setImageDrawable(null);
                    tv_photo_required.setVisibility(View.GONE);
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
        Toast.makeText(this, "finish hour= "+finish_hour, Toast.LENGTH_SHORT).show();
        int finish_minute=DataPreference.getInt("finish_minute",0);
        Toast.makeText(this, "finish hour= "+finish_minute, Toast.LENGTH_SHORT).show();
//        if (finish_minute>=60){
//            finish_minute=finish_minute%60;
//            finish_hour++;
//        }
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
                    next.setClickable(false);
                    previous.setClickable(false);

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

            examQuestion=String.valueOf(exam_question);
            int a=exam_question+1;
            question_number.setText(String.valueOf(a));
            questionRef= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(student_grade)
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

        }

    }
    private void PreviousQuestion(){
        exam_question=DataPreference.getInt("openQuestionNo",0);
        if (exam_question>0){
            exam_question--;
            dataEditor.putInt("openQuestionNo",exam_question);
            dataEditor.apply();
            updateQestion(exam_question);
            updateAnswerImage(exam_question);
            updateQuestionImage(exam_question);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam).child(subject);
        final HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("timestamp", time_stamp);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    dataEditor.remove("called")
                            .remove("questionNo").remove("finish_hour").remove("finish_minute")
                            .remove("this hour").remove("this minute").remove("marks").apply();
                    dataEditor.remove("called").remove("start time").remove("stop time").remove("called")
                            .remove("time left").apply();
                    dataEditor.putString(student_grade + subject + exam + " openSubmitted", student_grade + subject + exam + " openSubmitted").apply();

                    Toast.makeText(DoOpenQuestions.this, "Results submitted succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoOpenQuestions.this, StudentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK ));

                } else {
                    Toast.makeText(DoOpenQuestions.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void openImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(DoOpenQuestions.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri!=null){
            fileReference=storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtention(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }


            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String mUri=downloadUri.toString();
                        exam_question=DataPreference.getInt("exam q",0);
                        examQuestion=String.valueOf(exam_question);
                        databaseReference=FirebaseDatabase.getInstance().getReference("Students").child(student_id).child("OpenEndAns").child(exam)
                                .child(subject).child(examQuestion);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image_URL",mUri);
                        databaseReference.updateChildren(map);
                        updateAnswerImage(exam_question);
                        pd.dismiss();

                    }else{
                        Toast.makeText(DoOpenQuestions.this,"Failed",Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"No image selected", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            imageUri=data.getData();

            if (uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(),"Upload in progress",
                        Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }
    }
    @Override
    protected void onRestart() {
        startTimer();
        super.onRestart();
    }
    private void sendNotification() {
        Intent notificationIntent = new Intent(DoOpenQuestions. this, DoOpenQuestions.class);
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER );
        notificationIntent.setAction(Intent. ACTION_MAIN );
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultIntent = PendingIntent. getActivity (DoOpenQuestions. this, 0 , notificationIntent , 0 ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DoOpenQuestions. this, default_notification_channel_id )
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

                        startActivity(new Intent(DoOpenQuestions.this,StudentExamOpenActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(DoOpenQuestions.this);
        builder.setMessage("Do you want to exit exam?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}