package com.mackvel.soma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mackvel.soma.Model.Teachers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Teacher_name;
    RelativeLayout set_exam,view_exam,upload_papers,mark_questions,set_open_question;
    Button logout;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String teacherid, teacher_name;
    ImageView announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        set_exam=findViewById(R.id.set_exam);
        view_exam=findViewById(R.id.view_exam);
        upload_papers=findViewById(R.id.upload_exam);
        set_open_question=findViewById(R.id.set_open_questions);
        mark_questions=findViewById(R.id.mark_questions);
        Teacher_name=findViewById(R.id.tv_teachername);
        logout=findViewById(R.id.btn_logout);
        announcement= findViewById(R.id.iv_announcement);
        set_open_question.setOnClickListener(this);
        mark_questions.setOnClickListener(this);
        set_exam.setOnClickListener(this);
        view_exam.setOnClickListener(this);
        upload_papers.setOnClickListener(this);
        logout.setOnClickListener(this);
        announcement.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        dataEditor.putString("logged in","teacher logged in").apply();
        teacherid=DataPreference.getString("teacher id","");
        teacher_name=DataPreference.getString("teacher name","");
        if (teacher_name.equals("")){
            updateDetails(teacherid);
        }else{
            Teacher_name.setText(teacher_name);
        }



    }

    private  void updateDetails(final String teacherid){
        databaseReference= FirebaseDatabase.getInstance().getReference("Teachers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Teachers teachers= snapshot.getValue(Teachers.class);
                    String id=teachers.getId();
                    if (id.equals(teacherid)){
                        String schoolname= teachers.getSchoolname();
                        String teacher_name= teachers.getUsername();
                        Teacher_name.setText(teacher_name);
                        dataEditor.putString("teacher name",teacher_name);
                        dataEditor.putString("teacher id", teacherid);
                        dataEditor.putString("teacher school", schoolname);
                        dataEditor.apply();
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
            case R.id.set_exam:
                dataEditor.remove("TeacherViewResults");
                dataEditor.remove("teacher option");
                dataEditor.remove("Result");
                dataEditor.apply();
                startActivity(new Intent(TeacherActivity.this,ClassActivity.class));
                break;
            case R.id.view_exam:
                dataEditor.remove("teacher option").apply();
                startActivity(new Intent(TeacherActivity.this,TeacherViewResults.class));
                break;
            case R.id.btn_logout:

                final AlertDialog.Builder alertDialog= new AlertDialog.Builder(TeacherActivity.this);
                alertDialog.setTitle("Log out")
                        .setMessage("Do you wish to log out?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseAuth.getInstance().signOut();
                        dataEditor.clear().apply();
                        startActivity(new Intent(TeacherActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                alertDialog.show();
                break;
            case R.id.upload_exam:
                dataEditor.putString("teacher option", "upload exam");
                dataEditor.remove("TeacherViewResults");
                dataEditor.remove("Result");
                dataEditor.apply();
                startActivity(new Intent(TeacherActivity.this,ClassActivity.class));
                break;
            case R.id.set_open_questions:
                dataEditor.putString("teacher option","set open");
                dataEditor.remove("TeacherViewResults");
                dataEditor.remove("Result");
                dataEditor.apply();
                startActivity(new Intent(TeacherActivity.this,ClassActivity.class));

                break;
            case R.id.mark_questions:
                dataEditor.remove("TeacherViewResults");
                dataEditor.putString("teacher option", "mark questions");
                dataEditor.remove("Result");
                dataEditor.apply();
                startActivity(new Intent(TeacherActivity.this,ClassActivity.class));
                break;
            case R.id.iv_announcement:
                dataEditor.remove("TeacherViewResults");
                dataEditor.remove("teacher option");
                dataEditor.remove("Result");
                startActivity(new Intent(TeacherActivity.this,CreateAnnouncementActivity.class));
                break;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(TeacherActivity.this);
        builder.setMessage("Exit Application?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}