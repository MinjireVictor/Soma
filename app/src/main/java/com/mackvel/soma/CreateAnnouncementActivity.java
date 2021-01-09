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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mackvel.soma.Model.Schools;
import com.mackvel.soma.Model.Students;

import java.util.HashMap;

public class CreateAnnouncementActivity extends AppCompatActivity {
    RadioButton maths,english,kiswahili,science,socialstudies,cre;
    String teacher_name,teacher_school;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    EditText Announcement;
    String formatted_grade,subject,Selected_subject;
    String[] student_grade ={"1","2","3","4","5","6","7","8"};
    AutoCompleteTextView grade;
    Button Announce;
    TextView text;
    DatabaseReference databaseReference,databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        maths=findViewById(R.id.maths_announcement);
        english=findViewById(R.id.english_announcement);
        kiswahili=findViewById(R.id.maths_announcement);
        science=findViewById(R.id.maths_announcement);
        socialstudies=findViewById(R.id.maths_announcement);
        cre=findViewById(R.id.maths_announcement);
        text=findViewById(R.id.text);
        grade= findViewById(R.id.et_grade);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        teacher_name=DataPreference.getString("teacher name","");
        teacher_school=DataPreference.getString("teacher school","");

        maths=findViewById(R.id.maths_announcement);
        english=findViewById(R.id.english_announcement);
        kiswahili=findViewById(R.id.kiswahili_announcement);
        science=findViewById(R.id.science_announcement);
        socialstudies=findViewById(R.id.socialstudies_announcement);
        cre=findViewById(R.id.cre_announcement);
        Announcement=findViewById(R.id.et_anouncement);
        Announce=findViewById(R.id.bt_announce);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,student_grade);
        //Getting the instance of AutoCompleteTextView
        grade.setThreshold(1);//will start working from first character
        grade.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        Announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_grade = grade.getText().toString().trim();
                String txt_announcement=Announcement.getText().toString().trim();
                subject=DataPreference.getString("subject","");
                formatted_grade="class"+txt_grade;
                if (TextUtils.isEmpty(txt_grade)||TextUtils.isEmpty(txt_announcement)|| TextUtils.isEmpty(subject)){
                    Toast.makeText(CreateAnnouncementActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    sendAnnouncement(txt_announcement,formatted_grade);
                }

            }
        });
    }
    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        Selected_subject="";
        switch (view.getId()){
            case R.id.maths_announcement:
                if (checked){
                    subject="maths";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
            case R.id.english_announcement:
                if (checked){
                    subject="english";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
            case R.id.kiswahili_announcement:
                if (checked){
                    subject="kiswahili";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
            case R.id.science_announcement:
                if (checked){
                    subject="science";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
            case R.id.socialstudies_announcement:
                if (checked){
                    subject="socialstudies";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
            case R.id.cre_announcement:
                if (checked){
                    subject="cre";
                    dataEditor.putString("subject",subject);
                    dataEditor.apply();

                }
                break;
        }
        Toast.makeText(getApplicationContext(), subject, Toast.LENGTH_SHORT).show();


    }
    private void sendAnnouncement(String announcement, final String formatted_grade){

        subject=DataPreference.getString("subject","");
        final String final_announcement=teacher_name+": "+announcement;
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String student_school=snapshot.child("schoolname").getValue(String.class);
                    String student_grade=snapshot.child("grade").getValue(String.class);
                    if (student_grade!=null && student_school!=null){
                        if (student_school.equals(teacher_school)&&(student_grade.equals(formatted_grade))){
                            String studentid=snapshot.child("id").getValue(String.class);
                            databaseReference1=FirebaseDatabase.getInstance().getReference("Students").child(studentid).child("Announcements");
                            HashMap <String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put(subject,final_announcement);
                            databaseReference1.updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        for (int i=1;i<=2;i++){
                                            Toast.makeText(CreateAnnouncementActivity.this, "Announcement submitted", Toast.LENGTH_SHORT).show();
                                        }


                                    }else{
                                        Toast.makeText(CreateAnnouncementActivity.this, "Could not submit announcement", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}