package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mackvel.soma.Model.Schools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StudentRegisterActivity extends AppCompatActivity {
    EditText student_name,school,email,password;
    AutoCompleteTextView grade;
    Button RegisterStudent;
    FirebaseAuth auth;
    Query query;
    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    String formatted_grade;
    String[] student_grade ={"1","2","3","4","5","6","7","8"};
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        student_name=findViewById(R.id.student_name);
        school=findViewById(R.id.school);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        grade=findViewById(R.id.grade);
        RegisterStudent=findViewById(R.id.btn_student_register);
        auth=FirebaseAuth.getInstance();
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,student_grade);
        //Getting the instance of AutoCompleteTextView
        grade.setThreshold(1);//will start working from first character
        grade.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


        RegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String txt_student_name=student_name.getText().toString().trim();
                final String txt_email=email.getText().toString().trim();
                final String txt_password=password.getText().toString().trim();
                final String txt_school=school.getText().toString().trim();
                final String txt_grade=grade.getText().toString().trim();
                formatted_grade="class"+txt_grade;
                databaseReference=FirebaseDatabase.getInstance().getReference("Schools");

                if (TextUtils.isEmpty(txt_student_name)||TextUtils.isEmpty(txt_email)
                        ||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(txt_school)||TextUtils.isEmpty(txt_grade)){
                    Toast.makeText(StudentRegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(StudentRegisterActivity.this, "Password must be atleast 6 characters", Toast.LENGTH_SHORT).show();

                }else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                                Schools schools=snapshot.getValue(Schools.class);
                                String school_name=schools.getSchoolname();
                                if (school_name.equals(txt_school)){
                                    Toast.makeText(StudentRegisterActivity.this, "School is registered", Toast.LENGTH_SHORT).show();
                                    String school_key=snapshot.getKey();
                                    dataEditor.putString("school key",school_key);
                                    dataEditor.putString("school name",school_name);
                                    dataEditor.apply();
                                    register(txt_email,txt_password,txt_student_name,txt_school);
                                }else{
                                    school.setError("check");
                                   // Toast.makeText(StudentRegisterActivity.this, "School is either mis-spelt or not registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
    private void register(final String email, String password, final String studentname, final String schoolname) {
        progressDialog= new ProgressDialog(StudentRegisterActivity.this);
        progressDialog.setMessage("Registering student...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String studentId = firebaseUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference("Students").child(studentId);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", studentId);
                            hashMap.put("username", studentname);
                            hashMap.put("schoolname",schoolname);
                            hashMap.put("grade",formatted_grade);
                            dataEditor.putString("student id",studentId);
                            dataEditor.apply();
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(StudentRegisterActivity.this, StudentLoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(StudentRegisterActivity.this, "You cant register with this username or email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}