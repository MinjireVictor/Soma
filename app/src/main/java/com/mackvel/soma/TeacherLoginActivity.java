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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mackvel.soma.Model.Schools;
import com.mackvel.soma.Model.Teachers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherLoginActivity extends AppCompatActivity {
    EditText email, password, schoolcode;
    Button btn_login, btn_logout;
    FirebaseAuth auth;
    TextView fogot_password;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    TextView sign_up_teacher;
    String teacher_id, teacher_school, school_code;
    FirebaseUser firebaseUser;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    int found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        schoolcode = findViewById(R.id.school_code);
        btn_login = findViewById(R.id.btn_login);
        sign_up_teacher = findViewById(R.id.sign_up_teacher);
        auth = FirebaseAuth.getInstance();
        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();
        sign_up_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherLoginActivity.this, TeacherRegisterActivity.class));
            }
        });
        // fogot_password=findViewById(R.id.forgot_password);

        final String school_code = DataPreference.getString("school code", "");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_school_code = schoolcode.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_school_code)) {
                    Toast.makeText(TeacherLoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    if (txt_school_code.equals(school_code)) {
                        auth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(TeacherLoginActivity.this, TeacherActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {
                                    View parentLayout = findViewById(android.R.id.content);
                                    email.setError("check");
                                    password.setError("check");
                                    Snackbar.make(parentLayout, "Failed...Incorrect email or password", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        pd = new ProgressDialog(TeacherLoginActivity.this);
                        pd.setMessage("Logging in...");
                        pd.show();
                        SwitchTeacher();
                    }
                }
            }
        });

    }

    public void LoginTeacher() {
        final String school_code = DataPreference.getString("school code", "");
        String txt_school_code = schoolcode.getText().toString();
        dataEditor.remove("found").apply();
        if (school_code.equals(txt_school_code)) {
            pd.dismiss();
            Intent intent = new Intent(TeacherLoginActivity.this, TeacherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else {
            pd.dismiss();
            schoolcode.setError("check");
            Snackbar.make(getWindow().getDecorView().getRootView(),"School code is incorrect",Snackbar.LENGTH_LONG).show();

        }
    }

    public void SwitchTeacher() {
        FirebaseAuth.getInstance().signOut();
        final String txt_email = email.getText().toString();
        String txt_password = password.getText().toString();
        auth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    teacher_id = firebaseUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Teachers teachers = snapshot.getValue(Teachers.class);
                                String teachers_id = teachers.getId();
                                if (teachers_id.equals(teacher_id)) {
                                    found++;
                                    teacher_school = teachers.getSchoolname();
                                    dataEditor.putString("teacher id",teachers_id);
                                    dataEditor.putString("teacher school", teacher_school);
                                    dataEditor.putInt("found", found);
                                    dataEditor.apply();
                                    teacherFound();


                                } else {

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    pd.dismiss();
                    email.setError("check");
                    password.setError("check");
                    Snackbar.make(getWindow().getDecorView().getRootView(),""+txt_email+"\n"+"is not registered as teacher",
                            Snackbar.LENGTH_LONG).show();

                }
            }
        });


    }

    private void teacherFound() {
        found = DataPreference.getInt("found", 0);
        teacher_school = DataPreference.getString("teacher school", "");

        if (found > 0) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Schools");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Schools schools = snapshot.getValue(Schools.class);
                        String schoolname = schools.getSchoolname();

                        if (schoolname.equals(teacher_school)) {
                            String key = snapshot.getKey();
                            school_code = schools.getSchoolcode();
                            dataEditor.putString("school key", key);
                            dataEditor.putString("school code", school_code);
                            dataEditor.apply();

                            LoginTeacher();
                        } else {
                            pd.dismiss();
                            Snackbar.make(getWindow().getDecorView().getRootView(),"Teacher school not found in registered schools",Snackbar.LENGTH_LONG).show();




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