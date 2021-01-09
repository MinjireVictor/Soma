package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.HashMap;

public class DoExamPapersActivity extends AppCompatActivity {
    ImageView imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,start,stop,tv_time_left;
    Button FillAnswers;
    DatabaseReference databaseReference;
    int called;
    private static final int IMAGE_REQUEST=1;
    String grade,subject,exam,duration,school_name,key,view_paper;
    String student_id,student_grade;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    private Uri imageUri;
    Uri uri;
    private long timeLeftInMilliseconds;
    private StorageTask uploadTask;
    StorageReference fileReference;
    StorageReference storageReference;
    Handler handler = new Handler();
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exam_papers);
        imageView=findViewById(R.id.iv_page1);
        imageView1=findViewById(R.id.iv_page2);
        imageView2=findViewById(R.id.iv_page3);
        imageView3=findViewById(R.id.iv_page4);
        imageView5=findViewById(R.id.iv_page6);
        imageView6=findViewById(R.id.iv_page7);
        start=findViewById(R.id.start_time);
        tv1= findViewById(R.id.tv1);
        stop=findViewById(R.id.stop_time);
        tv_time_left=findViewById(R.id.time_left);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();

        student_id=DataPreference.getString("student id","");
        student_grade=DataPreference.getString("student grade","");
        key=DataPreference.getString("school key","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        imageUpdate();
        storageReference= FirebaseStorage.getInstance().getReference("Exams");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageUpdate();
            }
        },100);
        FillAnswers=findViewById(R.id.bt_fill_answers);
        FillAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataEditor.remove("called").apply();
                startActivity(new Intent(DoExamPapersActivity.this,FillAnswersActivity.class));
            }
        });

        view_paper=getIntent().getStringExtra("view paper");
        if (view_paper!=null){
           FillAnswers.setVisibility(View.GONE);
        }else{
            FillAnswers.setVisibility(View.VISIBLE);
        }
        called=DataPreference.getInt("called",0);
        if (called < 1){

            databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations")
                    .child(student_grade).child(exam).child(subject).child("Duration");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    duration= dataSnapshot.getValue(String.class);
                    Toast.makeText(DoExamPapersActivity.this, "Duration= "+ duration, Toast.LENGTH_SHORT).show();
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
        int this_hour=DataPreference.getInt("this hour",0);
        int this_minute=DataPreference.getInt("this minute",0);
        String startTime;
        startTime=""+this_hour;
        startTime+=":";
        if (this_minute<10)startTime+="0";
        startTime+=this_minute;
        start.setText(startTime);

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

            }
        },500);
    }


    private void startTimer(){
        Calendar calender = Calendar.getInstance();
        int this_hour=calender.get(Calendar.HOUR_OF_DAY);
        int this_minute=calender.get(Calendar.MINUTE);
        int finish_hour=DataPreference.getInt("finish_hour",0);
        int finish_minute=DataPreference.getInt("finish_minute",0);
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
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
    private void imageUpdate(){
        for (int i = 1; i<=6; i ++) {
            final int finalI = i;
            databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations")
                    .child(student_grade).child(exam).child(subject).child(String.valueOf(i)).child("image_URL");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image_url = dataSnapshot.getValue(String.class);
                        Toast.makeText(DoExamPapersActivity.this, "image url= "+image_url, Toast.LENGTH_SHORT).show();
                    if (image_url != null) {
                        if (image_url.equals("")) {
                            if (finalI == 1) {
                                imageView.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 2) {
                                imageView1.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 3) {
                                imageView2.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 4) {
                                imageView3.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 5) {
                                imageView5.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 6) {
                                imageView6.setImageResource(R.drawable.image_bg);
                            }

                        } else {
                            //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                            if (finalI == 1) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView);
                            } else if (finalI == 2) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView1);
                            } else if (finalI == 3) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView2);
                            } else if (finalI == 4) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView3);
                            } else if (finalI == 5) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView5);
                            } else if (finalI == 6) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView6);
                            }

                        }
                    } else {
                        if (finalI == 1) {
                            imageView.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 2) {
                            imageView1.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 3) {
                            imageView2.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 4) {
                            imageView3.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 5) {
                            imageView5.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 6) {
                            imageView6.setImageResource(R.drawable.image_bg);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void sendNotification() {
        Intent notificationIntent = new Intent(DoExamPapersActivity. this, DoExamPapersActivity. class );
        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP );
        PendingIntent resultIntent = PendingIntent. getActivity (DoExamPapersActivity. this, 0 , notificationIntent , 0 ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DoExamPapersActivity. this, default_notification_channel_id )
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
        if (view_paper!=null){
            if (view_paper.equals("view paper")){

                startActivity(new Intent(DoExamPapersActivity.this,ViewResultsExamStudent.class));
            }
        }
       else{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                             sendNotification();
                             called=0;
                             startActivity(new Intent(DoExamPapersActivity.this,StudentExamPapersActivity.class));

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            dialog.dismiss();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(DoExamPapersActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }
}