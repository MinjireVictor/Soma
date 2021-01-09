package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btn_login;
    FirebaseAuth auth;
    TextView sign_up;
    FirebaseUser firebaseUser;
    TextView textView;
    ProgressDialog progressDialog;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btn_login=findViewById(R.id.btn_login);
        sign_up=findViewById(R.id.sign_up_student);
        textView= findViewById(R.id.textView);
        FirebaseAuth.getInstance().signOut();

        progressDialog=new ProgressDialog(StudentLoginActivity.this);
        DataPreference = getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor = DataPreference.edit();

        auth=FirebaseAuth.getInstance();
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SalesforceSans.ttf");

        email.setTypeface(myTypeface2);
        password.setTypeface(myTypeface2);
        btn_login.setTypeface(myTypeface2);
        sign_up.setTypeface(myTypeface2);
        textView.setTypeface(myTypeface2);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_email=email.getText().toString().trim();
                String txt_password=password.getText().toString().trim();


                if (TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_password)){
                    Toast.makeText(StudentLoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.setMessage("Logging In...");
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                FirebaseUser firebaseUser= auth.getCurrentUser();
                                final String studentid= firebaseUser.getUid();
                                dataEditor.putString("student id",studentid).apply();
                                Intent intent= new Intent(StudentLoginActivity.this, StudentActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {

                                progressDialog.dismiss();
                                email.setError("check");
                                password.setError("check");
                                Toast.makeText(StudentLoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this, StudentRegisterActivity.class));

            }
        });
    }
}