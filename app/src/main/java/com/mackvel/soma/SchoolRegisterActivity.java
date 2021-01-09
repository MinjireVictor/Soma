package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mackvel.soma.Model.Schools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SchoolRegisterActivity extends AppCompatActivity {
    EditText school_name, school_code;
    Button register_school,main;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference;
    int registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_register);
        school_name=findViewById(R.id.et_school_name);
        school_code=findViewById(R.id.et_school_code);
        register_school=findViewById(R.id.bt_registerSchool);
        main=findViewById(R.id.bt_main);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolRegisterActivity.this,SplashScreen.class));

            }
        });

        databaseReference= FirebaseDatabase.getInstance().getReference("Schools");
        register_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered++;
                final String txt_school_name=school_name.getText().toString().trim();
                final String txt_school_code=school_code.getText().toString().trim();

                HashMap <String, String> hashMap= new HashMap<>();
                hashMap.put("schoolname", txt_school_name);
                hashMap.put("schoolcode", txt_school_code);
                databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            ValueEventListener valueEventListener= new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        Schools schools=dataSnapshot1.getValue(Schools.class);
                                        String schoolname=schools.getSchoolname();
                                        String schoolcode=schools.getSchoolcode();
                                        if (schoolname.equals(txt_school_name)&& schoolcode.equals(txt_school_code)){
                                            String key=dataSnapshot1.getKey();
                                          //  Toast.makeText(SchoolRegisterActivity.this, key, Toast.LENGTH_SHORT).show();
                                            dataEditor.putString("Key",key);
                                            dataEditor.apply();

                                        }else{

                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(SchoolRegisterActivity.this, "canot read nor write" , Toast.LENGTH_SHORT).show();

                                }
                            };
                            databaseReference.addValueEventListener(valueEventListener);

                            Toast.makeText(SchoolRegisterActivity.this, "School Registered", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SchoolRegisterActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        }

                      //  startActivity(new Intent(SchoolRegisterActivity.this,MainActivity.class));
                    }
                });



            }
        });
    }
}