package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class FinishSetExam extends AppCompatActivity {
    TextView tv1,tv2;
    EditText hours, minutes;
    Button submit;
    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String teacher_school,key,student_grade,grade,subject,exam,questions,question_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_set_exam);
        tv1= findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        hours=findViewById(R.id.et_hours);
        minutes=findViewById(R.id.et_minutes);
        submit=findViewById(R.id.bt_submit_exam);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        teacher_school=DataPreference.getString("teacher school","");
        student_grade=DataPreference.getString("student grade","");
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        Intent intent=getIntent();
        questions= intent.getStringExtra("exam question");
        dataEditor.putString("questions",questions).apply();
        Toast.makeText(this, "questions= "+questions, Toast.LENGTH_SHORT).show();
        question_type=DataPreference.getString("teacher option","");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions=DataPreference.getString("questions","");

                String txt_hrs= hours.getText().toString();
                String txt_min=minutes.getText().toString();
                if (TextUtils.isEmpty(txt_hrs)||TextUtils.isEmpty(txt_min)){
                    Toast.makeText(FinishSetExam.this, "Please enter exam duration", Toast.LENGTH_SHORT).show();
                }else{
                    Calendar calender = Calendar.getInstance();
                    int this_year=calender.get(Calendar.YEAR);
                    int this_month=calender.get(Calendar.MONTH);
                    int this_date= calender.get(Calendar.DATE);
                    int this_hour=calender.get(Calendar.HOUR_OF_DAY);
                    int this_minute=calender.get(Calendar.MINUTE);
                    int this_second=calender.get(Calendar.SECOND);
                    calender.set(this_year,this_month, this_date,this_hour,this_minute,this_second);
                    String time_stamp = String.valueOf(calender.getTimeInMillis());

                    int duration=(Integer.parseInt(txt_hrs) * 60)+ Integer.parseInt(txt_min);
                    if (question_type.equals("set open")){
                        databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("OpenEnd").child(grade).child(exam)
                                .child(subject);

                    }else if (question_type.equals("upload exam")) {

                        databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations")
                                .child(grade).child(exam).child(subject);
                    }

                    else{
                        databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child(grade).child(exam)
                                .child(subject);

                    }

                    HashMap <String, Object> hashMap= new HashMap<>();
                    hashMap.put("Duration",String.valueOf(duration));
                    hashMap.put("timestamp",time_stamp);
                    hashMap.put("total",questions);
                    databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(FinishSetExam.this, "Exam submitted", Toast.LENGTH_SHORT).show();
                                if (question_type.equals("set open")){
                                    dataEditor.putString(grade+subject+exam+" set",grade+subject+exam+" open set").apply();

                                }else{
                                    dataEditor.putString(grade+subject+exam+" set",grade+subject+exam+" set").apply();
                                }
                                dataEditor.remove("exam question");
                                dataEditor.remove("teacher timestamp");
                                dataEditor.remove("teacher timestamp1");
                                dataEditor.remove("teacher timestamp2");
                                dataEditor.apply();
                                startActivity(new Intent(FinishSetExam.this,TeacherActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                
                            }

                        }
                    });
                }
            }
        });
    }
}