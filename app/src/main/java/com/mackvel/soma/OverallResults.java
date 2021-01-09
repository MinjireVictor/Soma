package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.mackvel.soma.Adapter.StudentResultsAdapter;
import com.mackvel.soma.Model.Results;
import com.mackvel.soma.Model.StudentResults;
import com.mackvel.soma.Model.Students;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OverallResults extends AppCompatActivity {
    DatabaseReference databaseReference, databaseReference1;
    ListView listView;
    String teacher_id, teacher_school, student_school, student_name, student_id, student_grade, marks,
            key, grade, subject, exam;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String Overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_results);

        listView = findViewById(R.id.list);
        ArrayList<StudentResults> student_results = new ArrayList<StudentResults>();
        final StudentResultsAdapter studentResultsAdapter = new StudentResultsAdapter(this, student_results);
        listView.setAdapter(studentResultsAdapter);
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        teacher_id = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();
        teacher_school = DataPreference.getString("teacher school", "");
        student_grade = DataPreference.getString("student grade", "");
        key = DataPreference.getString("school key", "");
        grade = DataPreference.getString("grade", "");
        subject = DataPreference.getString("subject", "");
        exam = DataPreference.getString("exam", "");
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Students students = snapshot.getValue(Students.class);
                    student_school = students.getSchoolname();
                    String student_grade = students.getGrade();
                    if (student_school.equals(teacher_school) && (student_grade.equals(grade))) {
                        student_name = students.getUsername();
                        String studentid = students.getId();
                        databaseReference1 = FirebaseDatabase.getInstance().getReference("Students")
                                .child(studentid).child(exam);
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    Results results= snapshot.getValue(Results.class);
                                    int maths=(Integer.parseInt(results.getMaths()));
                                    int english=(Integer.parseInt(results.getEnglish()));
                                    int kiswahili=(Integer.parseInt(results.getKiswahili()));
                                    int science=(Integer.parseInt(results.getScience()));
                                    int socialstudies=(Integer.parseInt(results.getSocialstudies()));
                                    int cre=(Integer.parseInt(results.getCre()));
                                    Overall=String.valueOf(maths+english+kiswahili+science+socialstudies+cre);
                                    StudentResults newStudentResults = new StudentResults(student_name, Overall);
                                    studentResultsAdapter.add(newStudentResults);
                                    studentResultsAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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
        startActivity(new Intent(OverallResults.this,ExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        super.onBackPressed();
    }
}

