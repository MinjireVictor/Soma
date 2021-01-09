package com.mackvel.soma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mackvel.soma.Model.Schools;
import com.mackvel.soma.Model.Students;
import com.mackvel.soma.Model.Teachers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout do_exam,view_exam,do_open_test,do_exam_papers;
    TextView tv_student_name;
    Button logout;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String student_id,student_grade,student_name;
    FirebaseAuth auth;
    DatabaseReference databaseReference,databaseReference1,databaseReference2,databaseReference3,databaseReference4
            ,databaseReference5,school_ref;
    TextView tv_maths,tv_english,tv_kiswahili,tv_science,tv_socialstudies,tv_cre;
    TextView tv_maths_announcement,tv_english_announcement,tv_kiswahili_announcement,tv_science_announcement,tv_socialstudiees_announcement,
            tv_cre_announcement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        do_exam=findViewById(R.id.do_exam);
        view_exam=findViewById(R.id.view_exam);
        tv_student_name=findViewById(R.id.tv_student_name);
        tv_maths=findViewById(R.id.tv_maths_announcement);
        tv_english=findViewById(R.id.tv_english_announcement);
        tv_kiswahili=findViewById(R.id.tv_kiswahili_announcement);
        tv_science=findViewById(R.id.tv_science_announcement);
        tv_socialstudies=findViewById(R.id.tv_socialstudies_announcement);
        tv_cre=findViewById(R.id.tv_cre_announcement);

        do_exam.setOnClickListener(this);
        view_exam.setOnClickListener(this);
        logout=findViewById(R.id.bt_logout);
        logout.setOnClickListener(this);
        do_open_test=findViewById(R.id.do_open_questions);
        do_exam_papers=findViewById(R.id.do_exam_papers);
        do_exam_papers.setOnClickListener(this);
        do_open_test.setOnClickListener(this);

        auth=FirebaseAuth.getInstance();
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        dataEditor.putString("logged in","student logged in");
        dataEditor.apply();
        student_id=DataPreference.getString("student id","");
        student_name=DataPreference.getString("student name","");
        student_grade=DataPreference.getString("student grade","");
        fetchAnouncements();

        if (student_name.equals("")){
            updateDetails();
        }else{
            tv_student_name.setText(student_name);
        }

    }

    private void fetchAnouncements(){

        databaseReference=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("maths");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String maths_announcement=dataSnapshot.getValue(String.class);

                if (maths_announcement!=null){
                    tv_maths.setText(maths_announcement);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference1=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("english");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String announcement=dataSnapshot.getValue(String.class);
                if (announcement!=null){
                    tv_english.setText(announcement);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference2=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("kiswahili");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String announcement=dataSnapshot.getValue(String.class);

                if (announcement!=null){
                    tv_kiswahili.setText(announcement);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference3=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("science");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String announcement=dataSnapshot.getValue(String.class);
                if (announcement!=null){
                    tv_science.setText(announcement);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference4=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("socialstudies");
        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String announcement=dataSnapshot.getValue(String.class);
                if (announcement!=null){
                    tv_socialstudies.setText(announcement);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference5=FirebaseDatabase.getInstance().getReference("Students")
                .child(student_id).child("Announcements").child("cre");
        databaseReference5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String announcement=dataSnapshot.getValue(String.class);
                if (announcement!=null){
                    tv_cre.setText(announcement);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateDetails(){
        student_id=DataPreference.getString("student id","");
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        school_ref=FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String id=snapshot.child("id").getValue(String.class);

                    if(id!=null){
                        if (id.equals(student_id)){
                            final String schoolname=snapshot.child("schoolname").getValue(String.class);
                            String student_name=snapshot.child("username").getValue(String.class);
                            tv_student_name.setText(student_name);
                            String student_grade=snapshot.child("grade").getValue(String.class);

                            dataEditor.putString("student id", student_id);
                            dataEditor.putString("student name",student_name);
                            dataEditor.putString("student school", schoolname);
                            dataEditor.putString("student grade",student_grade);
                            dataEditor.apply();
                            school_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                        Schools schools=dataSnapshot1.getValue(Schools.class);
                                        String school_name=schools.getSchoolname();
                                        if (schoolname.equals(school_name)){
                                            String school_key=dataSnapshot1.getKey();

                                            dataEditor.putString("school key",school_key).apply();
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.do_exam:
                dataEditor.remove("view results");
                dataEditor.remove("student option");
                dataEditor.remove("resultsType");
                dataEditor.apply();
                startActivity(new Intent(StudentActivity.this,StudentSubjectActivity.class));
                break;
            case R.id.view_exam:
                dataEditor.remove("view results");
                dataEditor.remove("student option");
                dataEditor.remove("resultsType");
                dataEditor.apply();
                startActivity(new Intent(StudentActivity.this,StudentExamTypeActivity.class));


                break;
            case R.id.bt_logout:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                dataEditor.clear().apply();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(StudentActivity.this,MainActivity.class).
                                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                builder.setMessage("Do you wish to log out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
            case R.id.do_open_questions:
                dataEditor.remove("view results");
                dataEditor.remove("resultsType");
                dataEditor.putString("student option","do open");
                dataEditor.apply();
                startActivity(new Intent(StudentActivity.this,StudentSubjectActivity.class));
                break;
            case R.id.do_exam_papers:
                dataEditor.putString("student option","do papers");
                dataEditor.remove("view results");
                dataEditor.remove("resultsType");
                dataEditor.apply();
                startActivity(new Intent(StudentActivity.this,StudentSubjectActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
        builder.setMessage("Exit Application?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }
}