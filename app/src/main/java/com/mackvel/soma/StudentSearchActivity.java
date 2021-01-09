package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mackvel.soma.Adapter.StudentSearchAdapter;
import com.mackvel.soma.Model.StudentResults;
import com.mackvel.soma.Model.StudentSearch;
import com.mackvel.soma.Model.Students;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentSearchActivity extends AppCompatActivity {
    EditText search_student;
    AutoCompleteTextView subject,exam;
    private ListView listView;
 //   private ArrayAdapter<String> adapter;
 //   private ArrayList<String> arrayList;
    String teacher_id,teacher_school;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference;
    Query query;
    StudentSearchAdapter studentSearchAdapter;
    ArrayList<StudentSearch> studentSearches;
    String [] subjects={"maths","english","kiswahili","science","socialstudies","cre"};
    String[] exams ={"1","2","3"};
    String view_results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);
        search_student=findViewById(R.id.student_name);
        listView = (ListView) findViewById(R.id.list);
        subject=findViewById(R.id.subject);
        exam=findViewById(R.id.exam);
        studentSearches= new ArrayList<StudentSearch>();
        studentSearchAdapter= new StudentSearchAdapter(this,studentSearches);
        listView.setAdapter(studentSearchAdapter);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        view_results=DataPreference.getString("TeacherViewResults","");

        ArrayAdapter<String> exam_Adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,exams);
        //Getting the instance of AutoCompleteTextView

        exam.setThreshold(1);//will start working from first character
        exam.setAdapter(exam_Adapter);//setting the adapter data into the AutoCompleteTextView

        ArrayAdapter<String> subjects_adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,subjects);
        //Getting the instance of AutoCompleteTextView

        subject.setThreshold(1);//will start working from first character
        subject.setAdapter(subjects_adapter);//setting the adapter data into the AutoCompleteTextView

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        teacher_id=DataPreference.getString("teacher id", "");
        teacher_school=DataPreference.getString("teacher school", "");

        search_student.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<=0){
                    studentSearchAdapter.clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentSearch studentSearch=studentSearches.get(position);
                String txt_subject=subject.getText().toString().trim();
                String txt_exam=exam.getText().toString().trim();
                String exam="exam"+txt_exam;
                if (TextUtils.isEmpty(txt_subject)|| TextUtils.isEmpty(txt_exam)){
                    Snackbar.make(getWindow().getDecorView().getRootView(),"All fields are required",Snackbar.LENGTH_LONG).show();
                }else{
                    String student_name= studentSearch.getStudent_name();
                    dataEditor.putString("subject",txt_subject);
                    dataEditor.putString("exam",exam);
                    dataEditor.apply();
                    if (view_results.equals("TeacherViewResults")){
                        Intent intent = new Intent(StudentSearchActivity.this,ViewResultsTeacher.class);
                        intent.putExtra("student name",student_name);
                        startActivity(intent);

                    }
                    else if (view_results.equals("TeacherViewOpenResults")){
                        Intent intent = new Intent(StudentSearchActivity.this,ViewResultsOpenTeacher.class);
                        intent.putExtra("student name",student_name);
                        startActivity(intent);

                    }else if (view_results.equals("TeacherViewUploadedPapers")){
                        Intent intent = new Intent(StudentSearchActivity.this,ViewUploadedResultsTeacher.class);
                        intent.putExtra("student name",student_name);
                        startActivity(intent);
                    }
                }

            }
        });
        search_student.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void searchUsers(String toString){
        query=FirebaseDatabase.getInstance().getReference("Students").orderByChild("username")
                .startAt(toString)
                .endAt(toString+"\uf0ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Students students= snapshot.getValue(Students.class);
//                    String schoolname= students.getSchoolname();

                    String schoolname=snapshot.child("schoolname").getValue(String.class);


                    if (schoolname.equals(teacher_school)){
                        String studentname=snapshot.child("username").getValue(String.class);
                        StudentSearch studentSearch= new StudentSearch(studentname);
                        studentSearchAdapter.add(studentSearch);
                        studentSearchAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}