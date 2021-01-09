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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TeacherRegisterActivity extends AppCompatActivity {
    EditText username,email,password,schoolName,schoolCode;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        username=findViewById(R.id.Username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        schoolName=findViewById(R.id.school);
        schoolCode=findViewById(R.id.school_code);
        btn_register=findViewById(R.id.btn_register_teacher);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        databaseReference=FirebaseDatabase.getInstance().getReference("Schools");
        auth=FirebaseAuth.getInstance();
        pd = new ProgressDialog(TeacherRegisterActivity.this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt_username=username.getText().toString();
                final String txt_email=email.getText().toString();
                final String txt_password=password.getText().toString();
                final String txt_school=schoolName.getText().toString();
                final String txt_school_code=schoolCode.getText().toString();

                if (TextUtils.isEmpty(txt_username)|| TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_password)|| TextUtils.isEmpty(txt_school)
                        || TextUtils.isEmpty(txt_school_code)){
                    Toast.makeText(TeacherRegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6){
                    Toast.makeText(TeacherRegisterActivity.this, "Password must be atleast 6 characters", Toast.LENGTH_SHORT).show();
                }else{

                    pd.setMessage("Registering teacher...");
                    pd.show();

                    databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                                Schools schools= snapshot.getValue(Schools.class);
                                String school_name=schools.getSchoolname();
                                String school_code=schools.getSchoolcode();
                                if (school_name.equals(txt_school)){
                                   // Toast.makeText(TeacherRegisterActivity.this, "School is registered", Toast.LENGTH_SHORT).show();
                                    if (school_code.equals(txt_school_code)){
                                       // Toast.makeText(TeacherRegisterActivity.this, "school code matches school", Toast.LENGTH_SHORT).show();
                                        String school_key=snapshot.getKey();
                                        dataEditor.putString("school code",school_code);
                                        dataEditor.putString("school key", school_key);
                                        dataEditor.apply();
                                        register(txt_email,txt_password,txt_school,txt_school_code,txt_username);
                                    }else{
                                        pd.dismiss();
                                        schoolCode.setError("check");

                                    }
                                }
                                else{
                                    pd.dismiss();
                                    schoolName.setError("check");

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
    private void register(final String email, String password, final String school_name, final String school_code,final String teacher_name){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser= auth.getCurrentUser();
                            String teacherid= firebaseUser.getUid();
                            databaseReference=FirebaseDatabase.getInstance().getReference("Teachers").child(teacherid);
                            HashMap<String, String> hashMap= new HashMap<>();
                            hashMap.put("id", teacherid);
                            hashMap.put("username",teacher_name);
                            hashMap.put("schoolname",school_name);
                            hashMap.put("schoolcode",school_code);
                            dataEditor.putString("teacher school", school_name);
                            dataEditor.apply();
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Toast.makeText(TeacherRegisterActivity.this, "Teacher is registered", Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(TeacherRegisterActivity.this,TeacherLoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            pd.dismiss();
                            Toast.makeText(TeacherRegisterActivity.this, "You cant register with this username or email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}