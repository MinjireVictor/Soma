package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mackvel.soma.Adapter.StudentResultsAdapter;
import com.mackvel.soma.Model.StudentResults;
import com.mackvel.soma.Model.Students;
import com.mackvel.soma.Model.Teachers;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassResults extends AppCompatActivity {

    DatabaseReference databaseReference,databaseReference1,databaseReference2;
    ListView listView;
    String teacher_school,student_school,student_name,studentid,student_grade,marks,view_results,teacher_option,
    key,grade,subject,exam,results;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    StudentResultsAdapter studentResultsAdapter;
    TextView total, textView12;
    ArrayList<StudentResults> student_results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_results);

        listView=findViewById(R.id.list);
        total=findViewById(R.id.total);
        textView12=findViewById(R.id.textView12);
        student_results= new ArrayList<StudentResults>();
        studentResultsAdapter= new StudentResultsAdapter(this,student_results);
        listView.setAdapter(studentResultsAdapter);


        databaseReference= FirebaseDatabase.getInstance().getReference("Teachers");
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        teacher_school=DataPreference.getString("teacher school","");
        student_grade=DataPreference.getString("student grade","");
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        view_results=DataPreference.getString("TeacherViewResults","");
        results=DataPreference.getString("Result","");
        textView12.setText(grade+" students");
        teacher_option=DataPreference.getString("teacher option","");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentResults studentResults=student_results.get(position);
                String student_name=studentResults.getStudent_name();
                if (view_results.equals("TeacherViewResults")){
                    Intent intent = new Intent(ClassResults.this,ViewResultsTeacher.class);
                    intent.putExtra("student name",student_name);
                    startActivity(intent);

                }
                else if (view_results.equals("TeacherViewOpenResults")){
                    Intent intent = new Intent(ClassResults.this,ViewResultsOpenTeacher.class);
                    intent.putExtra("student name",student_name);
                    startActivity(intent);

                }else if (view_results.equals("TeacherViewUploadedPapers")){
                    Intent intent = new Intent(ClassResults.this,ViewUploadedResultsTeacher.class);
                    intent.putExtra("student name",student_name);
                    startActivity(intent);
                }

                else if (teacher_option.equals("mark questions")){
                    Intent intent = new Intent(ClassResults.this,TeacherMarkActivity.class);
                    intent.putExtra("student name",student_name);
                    startActivity(intent);

                }


            }
        });
        databaseReference2=FirebaseDatabase.getInstance().getReference("Schools");
        databaseReference2.child(key).child(grade).child(exam).child(subject).child("total");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total_questions = dataSnapshot.child(key).child(grade).child(exam).child(subject).child("total").getValue(String.class);
               // total.setText(String.valueOf(total_questions));
                if (!teacher_option.equals("mark questions")){
                    if (results.equals("Class results")){
                        showResults();
                    }
                    else if (results.equals("Search student")){
                        showResults();
                    }
                    else if (total_questions==null){
                        Snackbar.make(getWindow().getDecorView().getRootView(),"Exam Does not exist",Snackbar.LENGTH_LONG)
                                .setAction("Exit", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }

                                ).show();

                    }else{
                        showResults();
                    }
                }
                else{
                    showResults();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showResults(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    student_school=snapshot.child("schoolname").getValue(String.class);
                    String student_grade=snapshot.child("grade").getValue(String.class);
                    if (student_school!=null){
                        if (student_school.equals(teacher_school)&&(student_grade.equals(grade))){

                        student_name=snapshot.child("username").getValue(String.class);
                        studentid=snapshot.child("id").getValue(String.class);
                        databaseReference1= FirebaseDatabase.getInstance().getReference("Students");
                        databaseReference1.child(studentid).child(exam).child(subject).child(subject);
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                marks=dataSnapshot.child(studentid).child(exam).child(subject).child(subject).getValue(String.class);
                                dataEditor.putString("received mark",marks).apply();
                                marks=DataPreference.getString("received mark","");


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        student_name=snapshot.child("username").getValue(String.class);
                        marks=DataPreference.getString("received mark","");
                        StudentResults newStudentResults = new StudentResults(student_name, marks);
                        studentResultsAdapter.add(newStudentResults);
                        studentResultsAdapter.notifyDataSetChanged();


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
    public void onBackPressed() {
        dataEditor.remove("TeacherViewResults").apply();
        startActivity(new Intent(ClassResults.this, SubjectActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        finish();
    }
}