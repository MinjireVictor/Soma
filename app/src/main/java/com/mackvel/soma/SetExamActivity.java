package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mackvel.soma.Model.Questions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Set;

public class SetExamActivity extends AppCompatActivity {
    RelativeLayout setQuestion,setPreviousQuestion, Finish;
    TextView TvQuestion,question_no,tv1;
    EditText EtQuestion,EtOptionA,EtOptionB,EtOptionC,EtOptionD,EtCorrectAns;
    int exam_question;
    ImageView question_imageView,iv_internet;
    String grade,subject,exam,examQuestion,school_name,Key;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    DatabaseReference databaseReference;
    FirebaseUser fuser;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;
    StorageReference fileReference;
    long then;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exam);

        setQuestion=findViewById(R.id.bt_setQuestion);
        EtQuestion=findViewById(R.id.et_question);
        TvQuestion=findViewById(R.id.tv_question);
        EtOptionA=findViewById(R.id.et_optionA);
        EtOptionB=findViewById(R.id.et_optionB);
        EtOptionC=findViewById(R.id.et_optionC);
        EtOptionD=findViewById(R.id.et_optionD);
        question_no=findViewById(R.id.question_No);
        iv_internet= findViewById(R.id.iv_internet);
        EtCorrectAns=findViewById(R.id.et_correctAnswer);
        question_imageView=findViewById(R.id.question_imageView);
        setPreviousQuestion=findViewById(R.id.bt_resetPrevious);
        Finish=findViewById(R.id.bt_finish);
        tv1=findViewById(R.id.tv1);
        Typeface myTypeface2= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/SalesforceSans.ttf");
        tv1.setTypeface(myTypeface2);

        storageReference= FirebaseStorage.getInstance().getReference("Uploads");
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();
        exam_question=DataPreference.getInt("exam question",0);
        examQuestion=String.valueOf(exam_question);
        int a = (exam_question+1);
        question_no.setText(String.valueOf(a));

        Key=DataPreference.getString("school key","");
        school_name=DataPreference.getString("teacher school","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");

        updateImage(exam_question);
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exam_question=DataPreference.getInt("exam question",0);
                updateImage(exam_question);

            }
        },2000);


        EtQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                TvQuestion.setText("Type question "+exam_question);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TvQuestion.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        question_imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    then = (Long) System.currentTimeMillis();
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    if(((Long) System.currentTimeMillis() - then) > 1200){
                        return true;
                    }
                }
                return false;
            }
        });


       question_imageView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {

               DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case DialogInterface.BUTTON_POSITIVE:
                               //Yes button clicked

                               question_imageView.setImageResource(R.drawable.image_bg);
                               databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key)
                                       .child(grade).child(exam).child(subject).child(examQuestion).child("image_URL");
                               databaseReference.removeValue();
                               break;

                           case DialogInterface.BUTTON_NEGATIVE:
                               //No button clicked
                               dialog.dismiss();
                               break;
                       }
                   }
               };

               androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(SetExamActivity.this);
               builder.setMessage("Remove image?").setPositiveButton("Yes", dialogClickListener)
                       .setNegativeButton("No", dialogClickListener).show();

               return false;
           }
       });

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               exam_question=DataPreference.getInt("exam question",0);


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked

                                startActivity(new Intent(SetExamActivity.this,FinishSetExam.class).putExtra(
                                        "exam question",String.valueOf(exam_question)).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                dialog.dismiss();
                                break;
                        }
                    }
                };

                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(SetExamActivity.this);
                builder.setMessage("Finish setting exam at question "+exam_question+" ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });
        setPreviousQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam_question=DataPreference.getInt("exam question",0);
                exam_question--;

                if (exam_question>0){
                    dataEditor.putInt("exam question",exam_question);
                    dataEditor.apply();
                    EtQuestion.getText().clear();
                    EtOptionA.getText().clear();
                    EtOptionB.getText().clear();
                    EtOptionC.getText().clear();
                    EtOptionD.getText().clear();
                    EtCorrectAns.getText().clear();
                    question_no.setText(String.valueOf(exam_question));
                    updateImage(exam_question);

                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Start setting test...",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        question_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();


            }
        });
        setQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question=EtQuestion.getText().toString();
                String optionA=EtOptionA.getText().toString();
                String optionB=EtOptionB.getText().toString();
                String optionC=EtOptionC.getText().toString();
                String optionD=EtOptionD.getText().toString();
                String correct_Ans=EtCorrectAns.getText().toString();


                if (TextUtils.isEmpty(question)|| TextUtils.isEmpty(optionA)|| TextUtils.isEmpty(optionB)
                        || TextUtils.isEmpty(optionC) || TextUtils.isEmpty(optionD)|| TextUtils.isEmpty(correct_Ans)
                        || TextUtils.isEmpty(grade)|| TextUtils.isEmpty(exam)|| TextUtils.isEmpty(subject)){
                    Toast.makeText(SetExamActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                }
                else {
                    exam_question=DataPreference.getInt("exam question",0);
                    SetQuestion(exam_question);
                }
            }
        });
    }
    private void updateImage(int exam_question){
        examQuestion=String.valueOf(exam_question);
        databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key)
                .child(grade).child(exam).child(subject).child(examQuestion).child("image_URL");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image_url=dataSnapshot.getValue(String.class);
                if (image_url != null) {
                    if (image_url.equals("")){
                        question_imageView.setImageResource(R.drawable.image_bg);

                    }else{
                        //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                        Glide.with(getApplicationContext()).load(image_url).into(question_imageView);

                    }
                }else{
                    question_imageView.setImageResource(R.drawable.image_bg);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void SetQuestion(final int questionNo){
        final ProgressDialog pd = new ProgressDialog(SetExamActivity.this);
        pd.setMessage("Setting question...");
        pd.show();

        examQuestion=String.valueOf(questionNo);
        updateImage(questionNo);
        String question=EtQuestion.getText().toString();
        String optionA=EtOptionA.getText().toString().toLowerCase();
        String optionB=EtOptionB.getText().toString().toLowerCase();
        String optionC=EtOptionC.getText().toString().toLowerCase();
        String optionD=EtOptionD.getText().toString().toLowerCase();
        String correct_Ans=EtCorrectAns.getText().toString();
        if (TextUtils.isEmpty(question)|| TextUtils.isEmpty(optionA)|| TextUtils.isEmpty(optionB)
                || TextUtils.isEmpty(optionC) || TextUtils.isEmpty(optionD)|| TextUtils.isEmpty(correct_Ans)
                || TextUtils.isEmpty(grade)|| TextUtils.isEmpty(exam)|| TextUtils.isEmpty(subject)){
            Toast.makeText(SetExamActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

        }else{
            databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key)
                    .child(grade).child(exam).child(subject).child(examQuestion);
            HashMap<String, Object> hashMap= new HashMap<>();
            hashMap.put("question", question);
            hashMap.put("optionA",optionA);
            hashMap.put("optionB",optionB);
            hashMap.put("optionC",optionC);
            hashMap.put("optionD",optionD);
            hashMap.put("correctans",correct_Ans);
            databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        pd.dismiss();
                        exam_question++;
                        dataEditor.putInt("exam question",exam_question);
                        dataEditor.apply();
                        EtQuestion.getText().clear();
                        EtOptionA.getText().clear();
                        EtOptionB.getText().clear();
                        EtOptionC.getText().clear();
                        EtOptionD.getText().clear();
                        EtCorrectAns.getText().clear();

                        Toast.makeText(SetExamActivity.this, "Question "+ exam_question+" set", Toast.LENGTH_SHORT).show();
                        int a = (exam_question+1);
                        question_no.setText(String.valueOf(a));
                        updateImage(exam_question);
                    }
                }
            });
        }
    }
    private void openImage(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(SetExamActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri!=null){
             fileReference=storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtention(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }


            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String mUri=downloadUri.toString();
                        exam_question=DataPreference.getInt("exam question",0);
                        examQuestion=String.valueOf(exam_question);
                        databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key)
                                .child(grade).child(exam).child(subject).child(examQuestion);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image_URL",mUri);
                        databaseReference.updateChildren(map);
                        updateImage(exam_question);
                        pd.dismiss();

                    }else{
                        Toast.makeText(SetExamActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            imageUri=data.getData();

            if (uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(),"Upload in progress",
                        Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key).child(grade).child(exam).child(subject);
                        databaseReference.removeValue();
                        dataEditor.remove("exam question").apply();
                        startActivity(new Intent(SetExamActivity.this,ExamActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(SetExamActivity.this);
        builder.setMessage("Abandon exam?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}