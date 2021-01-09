package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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

public class TestExamActivity extends AppCompatActivity implements OnClickListener{
    ImageView imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    ImageView upload,upload2,upload3,upload4,upload5,upload6;
    ImageView remove,remove2,remove3,remove4,remove5,remove6;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    Button CreateMarkingScheme;
    DatabaseReference databaseReference;
    int page_no,page_1,page_2,page_3,page_4,page_5,page_6;
    private static final int IMAGE_REQUEST=1;
    String grade,subject,exam,pageNo,school_name,Key;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;
    private Uri imageUri;
    Uri uri;
    private StorageTask uploadTask;
    StorageReference fileReference;
    StorageReference storageReference;
    Handler handler = new Handler();
    String imageUrl, imageZoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_exam);
        imageView=findViewById(R.id.iv_page1);
        imageView1=findViewById(R.id.iv_page2);
        imageView2=findViewById(R.id.iv_page3);
        imageView3=findViewById(R.id.iv_page4);
        imageView5=findViewById(R.id.iv_page6);
        imageView6=findViewById(R.id.iv_page7);
        upload=findViewById(R.id.iv_upload);
        upload2=findViewById(R.id.iv_upload2);
        upload3=findViewById(R.id.iv_upload3);
        upload4=findViewById(R.id.iv_upload4);
        upload5=findViewById(R.id.iv_upload5);
        upload6=findViewById(R.id.iv_upload6);

        remove=findViewById(R.id.iv_remove);
        remove2=findViewById(R.id.iv_remove2);
        remove3=findViewById(R.id.iv_remove3);
        remove4=findViewById(R.id.iv_remove4);
        remove5=findViewById(R.id.iv_remove5);
        remove6=findViewById(R.id.iv_remove6);


        CreateMarkingScheme=findViewById(R.id.bt_create_marking_scheme);
        tv1=findViewById(R.id.tv_page1);
        tv2=findViewById(R.id.tv_page2);
        tv3=findViewById(R.id.tv_page3);
        tv4=findViewById(R.id.tv_page5);
        tv5=findViewById(R.id.tv_page6);
        tv6=findViewById(R.id.tv_page7);
        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();

        imageView.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
//        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);

        upload.setOnClickListener(this);
        upload2.setOnClickListener(this);
        upload3.setOnClickListener(this);
        upload4.setOnClickListener(this);
        upload5.setOnClickListener(this);
        upload6.setOnClickListener(this);

        remove.setOnClickListener(this);
        remove2.setOnClickListener(this);
        remove3.setOnClickListener(this);
        remove4.setOnClickListener(this);
        remove5.setOnClickListener(this);
        remove6.setOnClickListener(this);

        imageView.setOnClickListener(mTouchListener);
        imageView1.setOnClickListener(mTouchListener);
        imageView2.setOnClickListener(mTouchListener);
        imageView3.setOnClickListener(mTouchListener);
      //  imageView4.setOnTouchListener(mTouchListener);
        imageView5.setOnClickListener(mTouchListener);
        imageView6.setOnClickListener(mTouchListener);


        Key=DataPreference.getString("school key","");
        school_name=DataPreference.getString("teacher school","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");
        imageUpdate();
        storageReference= FirebaseStorage.getInstance().getReference("Exams");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageUpdate();
            }
        },100);

        CreateMarkingScheme.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestExamActivity.this,MarkingSchemeActivity.class));
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bt_create_marking_scheme:
                startActivity(new Intent(TestExamActivity.this,MarkingSchemeActivity.class));

                break;
            case R.id.iv_upload:
                page_no=1;
                imageUpload(page_no);

                break;
            case R.id.iv_upload2:
                page_no=2;
                imageUpload(page_no);

                break;
            case R.id.iv_upload3:
                page_no=3;
                imageUpload(page_no);

                break;
            case R.id.iv_upload4:
                page_no=4;
                imageUpload(page_no);

                break;
            case R.id.iv_upload5:
                page_no=5;
                imageUpload(page_no);
                break;
            case R.id.iv_upload6:
                page_no=6;
                imageUpload(page_no);

                break;

            case R.id.iv_remove:
                page_no=1;

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.removeValue();
                imageView.setImageResource(R.drawable.image_bg);

                break;
            case R.id.iv_remove2:
                page_no=2;

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.removeValue();
                imageView1.setImageResource(R.drawable.image_bg);

                break;
            case R.id.iv_remove3:
                page_no=3;

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.removeValue();
                imageView2.setImageResource(R.drawable.image_bg);

                break;
            case R.id.iv_remove4:
                page_no=4;

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.removeValue();
                imageView3.setImageResource(R.drawable.image_bg);

                break;
                case R.id.iv_remove5:
                    page_no=5;

                    databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                            .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                    databaseReference.removeValue();
                    imageView5.setImageResource(R.drawable.image_bg);

                break;
            case R.id.iv_remove6:
                page_no=6;

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.removeValue();
                imageView6.setImageResource(R.drawable.image_bg);

                break;
        }

    }
    private void imageUpload(final int page_no){

        dataEditor.putInt("page",page_no);
        dataEditor.apply();
        openImage();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                        .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String image_url=dataSnapshot.getValue(String.class);
                        if (image_url != null) {
                            if (image_url.equals("")){
                                if (page_no==1){
                                    imageView.setImageResource(R.drawable.image_bg);
                                }else if (page_no==2){
                                    imageView1.setImageResource(R.drawable.image_bg);
                                }else if (page_no==3){
                                    imageView2.setImageResource(R.drawable.image_bg);
                                }
                                else if (page_no==4){
                                    imageView3.setImageResource(R.drawable.image_bg);
                                }
                                else if (page_no==5){
                                    imageView5.setImageResource(R.drawable.image_bg);
                                }
                                else if (page_no==6){
                                    imageView6.setImageResource(R.drawable.image_bg);
                                }


                            }else{
                                //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);


                                if (page_no==1){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView);
                                }else if (page_no==2){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView1);
                                }else if (page_no==3){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView2);
                                }
                                else if (page_no==4){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView3);
                                }
                                else if (page_no==5){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView5);
                                }
                                else if (page_no==6){
                                    Glide.with(getApplicationContext()).load(image_url).into(imageView6);
                                }

                            }
                        }else{
                            if (page_no==1){
                                imageView.setImageResource(R.drawable.image_bg);
                            }else if (page_no==2){
                                imageView1.setImageResource(R.drawable.image_bg);
                            }else if (page_no==3){
                                imageView2.setImageResource(R.drawable.image_bg);
                            }
                            else if (page_no==4){
                                imageView3.setImageResource(R.drawable.image_bg);
                            }
                            else if (page_no==5){
                                imageView5.setImageResource(R.drawable.image_bg);
                            }
                            else if (page_no==6){
                                imageView6.setImageResource(R.drawable.image_bg);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        },1000);


    }

    private void imageUpdate(){
        for (int i = 1; i<=6; i ++) {
            final int finalI = i;
            databaseReference = FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                    .child(grade).child(exam).child(subject).child(String.valueOf(i)).child("image_URL");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image_url = dataSnapshot.getValue(String.class);
              //      Toast.makeText(TestExamActivity.this, "image url= "+image_url, Toast.LENGTH_SHORT).show();
                    if (image_url != null) {
                        if (image_url.equals("")) {
                            if (finalI == 1) {
                                imageView.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 2) {
                                imageView1.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 3) {
                                imageView2.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 4) {
                                imageView3.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 5) {
                                imageView5.setImageResource(R.drawable.image_bg);
                            } else if (finalI == 6) {
                                imageView6.setImageResource(R.drawable.image_bg);
                            }

                        } else {
                            //  Glide.with(getContext()).load(user.getImageURL()).into(image_profile);
                            if (finalI == 1) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView);
                            } else if (finalI == 2) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView1);
                            } else if (finalI == 3) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView2);
                            } else if (finalI == 4) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView3);
                            } else if (finalI == 5) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView5);
                            } else if (finalI == 6) {
                                Glide.with(getApplicationContext()).load(image_url).into(imageView6);
                            }

                        }
                    } else {
                        if (finalI == 1) {
                            imageView.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 2) {
                            imageView1.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 3) {
                            imageView2.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 4) {
                            imageView3.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 5) {
                            imageView5.setImageResource(R.drawable.image_bg);
                        } else if (finalI == 6) {
                            imageView6.setImageResource(R.drawable.image_bg);
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    private String Image(final int page_no){
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                .child(grade).child(exam).child(subject).child(String.valueOf(page_no)).child("image_URL");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageUrl=dataSnapshot.getValue(String.class);
                if (imageUrl != null) {

                }else{
                    Toast.makeText(TestExamActivity.this, "No image", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return imageUrl;

    }

    private View.OnClickListener mTouchListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //  page_no=DataPreference.getInt("page",0);

            final Dialog builder = new Dialog(TestExamActivity.this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });

            int a=v.getId();

            if(R.id.iv_page1==a)
            {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(1)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        imageView.setImageDrawable(null);
                        Glide.with(getApplicationContext()).load(Image(1)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);

                //path of image
            }
            else if(R.id.iv_page2==a) {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(2)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        imageView.setImageDrawable(null);
                        Glide.with(getApplicationContext()).load(Image(2)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);
            }
            else if(R.id.iv_page3==a) {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(3)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        imageView.setImageDrawable(null);
                        Glide.with(getApplicationContext()).load(Image(3)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);
                // uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/profile"); //path of image
            }
            else if(R.id.iv_page4==a) {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(4)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        Glide.with(getApplicationContext()).load(Image(4)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);
                //   uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/profile"); //path of image
            }
            else if(R.id.iv_page6==a) {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(5)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        imageView.setImageDrawable(null);
                        Glide.with(getApplicationContext()).load(Image(5)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);
                //  uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/profile"); //path of image
            }
            else if(R.id.iv_page7==a) {
                ImageView imageView = new ImageView(TestExamActivity.this);
                imageView.setImageDrawable(null);
                Glide.with(getApplicationContext()).load(Image(6)).into(imageView);               //set the image in dialog popup
                //below code fullfil the requirement of xml layout file for dialoge popup

                builder.addContentView(imageView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                builder.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imageView = new ImageView(TestExamActivity.this);
                        Glide.with(getApplicationContext()).load(Image(6)).into(imageView);               //set the image in dialog popup
                        //below code fullfil the requirement of xml layout file for dialoge popup

                        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        builder.show();
                    }
                },100);
                // uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/profile"); //path of image
            }

        }

    };

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
        final ProgressDialog pd = new ProgressDialog(TestExamActivity.this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri!=null){
            fileReference=storageReference.child(grade+subject+exam+String.valueOf(page_no)+
                    "."+getFileExtention(imageUri));
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
                        page_no=DataPreference.getInt("page",0);
                        pageNo=String.valueOf(page_no);
                        databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations")
                                .child(grade).child(exam).child(subject).child(pageNo);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("image_URL",mUri);
                        databaseReference.updateChildren(map);
                        pd.dismiss();

                    }else{
                        Toast.makeText(TestExamActivity.this,"Failed",Toast.LENGTH_SHORT).show();
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
}