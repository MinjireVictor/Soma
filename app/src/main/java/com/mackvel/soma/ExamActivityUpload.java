package com.mackvel.soma;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExamActivityUpload extends AppCompatActivity {
    CardView Exam1,Exam2,Exam3;
    String exam,overall,exam_upload,key,grade,subject,upload_timestamp;
    String upload_timestamp2, upload_timestamp3;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference,databaseReference2;
    DatabaseReference databaseReference3,getDatabaseReference4;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_upload);

        Exam1=findViewById(R.id.bt_exam1);
        Exam2=findViewById(R.id.bt_exam2);
        Exam3=findViewById(R.id.bt_exam3);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        text=findViewById(R.id.text);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/GothamBold.ttf");
        text.setTypeface(myTypeface2);

        overall=DataPreference.getString("overall","");
        key=DataPreference.getString("school key","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");

        exam="exam1";
        databaseReference2= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("Examinations").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher = dataSnapshot.getValue(String.class);
                dataEditor.putString("upload timestamp",teacher).apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam2";

        databaseReference3= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("Examinations").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher1= dataSnapshot.getValue(String.class);
                dataEditor.putString("upload timestamp1",teacher1).apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        exam="exam3";
        databaseReference3= FirebaseDatabase.getInstance().getReference("Schools")
                .child(key).child("Examinations").child(grade).child(exam).child(subject).child("timestamp");
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String teacher2= dataSnapshot.getValue(String.class);
                dataEditor.putString("upload timestamp2",teacher2).apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Exam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam1";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                exam_upload=DataPreference.getString(grade+subject+exam+" upload","");
                upload_timestamp=DataPreference.getString("upload timestamp","");
                if (exam_upload.equals(grade+subject+exam+" upload")){

                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload another exam")
                                .setMessage("This exam was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();

                                startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }else if (upload_timestamp!=null && !upload_timestamp.isEmpty()){
                        //  Toast.makeText(ExamActivity.this, "in teacher timestamp", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload exam")
                                .setMessage("This test was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();

                                startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();

                    }
                    else {
                        startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }



               }

        });


        Exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam="exam2";
                dataEditor.putString("exam",exam);
                dataEditor.apply();
                exam_upload=DataPreference.getString(grade+subject+exam+" upload","");
                upload_timestamp2=DataPreference.getString("upload timestamp1","");

                    if (exam_upload.equals(grade+subject+exam+" upload")){

                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload another exam")
                                .setMessage("This test was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();

                                startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }else if (upload_timestamp2!=null && !upload_timestamp2.isEmpty()){

                        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload another exam")
                                .setMessage("This test was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child("Examinations").child(key).child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();

                                startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();

                    }
                    else {
                        startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }


                }




        });
        Exam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam = "exam3";
                dataEditor.putString("exam", exam);
                dataEditor.apply();
                exam_upload = DataPreference.getString(grade + subject + exam + " upload", "");
                upload_timestamp3 = DataPreference.getString("upload timestamp2", "");
                if (exam_upload.equals(grade + subject + exam + " upload")) {

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload another exam")
                                .setMessage("This test was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child("Examinations").child(key).child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();
                                startActivity(new Intent(ExamActivityUpload.this, TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    } else if (upload_timestamp3 != null && !upload_timestamp3.isEmpty()) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExamActivityUpload.this);
                        alertDialog.setTitle("Upload another exam")
                                .setMessage("This test was already uploaded, do you wish to upload another exam");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(key).child("Examinations").child(grade)
                                        .child(exam).child(subject);
                                databaseReference.removeValue();

                                startActivity(new Intent(ExamActivityUpload.this, TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


                            }
                        });
                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();


                    }
                    else {
                        startActivity(new Intent(ExamActivityUpload.this,TestExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }

                }
        });


    }

}