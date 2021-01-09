package com.mackvel.soma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class MarkingSchemeActivity extends AppCompatActivity {

    RadioButton oneA,oneB,oneC,oneD,twoA,twoB,twoC,twoD,threeA,threeB,threeC,threeD, fourA,fourB,fourC,fourD,
            fiveA,fiveB,fiveC,fiveD,sixA,sixB,sixC,sixD,
            sevenA,sevenB,sevenC,sevenD,eightA,eightB,eightC,eightD,nineA,nineB,nineC,nineD,tenA,tenB,tenC,tenD,
            elevenA,elevenB,elevenC,elevenD
            ,twelveA,twelveB,twelveC,twelveD,thirteenA,thirteenB,thirteenC,thirteenD,
            fourteenA,fourteenB,fourteenC,fourteenD,fiveteenA,fiveteenB,fiveteenC,fiveteenD,
            sixteenA,sixteenB,sixteenC,sixteenD,seventeenA,seventeenB,seventeenC,seventeenD,eighteenA,eighteenB,eighteenC,eighteenD
            ,nineteenA,nineteenB,nineteenC,nineteenD,twentyA,twentyB,twentyC,twentyD,twentyoneA,twentyoneB,twentyoneC,
            twentyoneD,twentytwoA,twentytwoB,twentytwoC,twentytwoD,twentythreeA,twentythreeB,twentythreeC,twentythreeD,
            twentyfourA,twentyfourB,twentyfourC,twentyfourD,twentyfiveA,twentyfiveB,twentyfiveC,twentyfiveD
            ,twentysixA,twentysixB,twentysixC,twentysixD,twentysevenA,twentysevenB,twentysevenC,twentysevenD,
            twentyeightA,twentyeightB,twentyeightC,twentyeightD,twentynineA,twentynineB,twentynineC,twentynineD,
            thirtyA,thirtyB,thirtyC,thirtyD,thirtyoneA,thirtyoneB,thirtyoneC,thirtyoneD,thirtytwoA,thirtytwoB,
            thirtytwoC,thirtytwoD,thirtythreeA,thirtythreeB,thirtythreeC,thirtythreeD,thirtyfourA,thirtyfourB,thirtyfourC,
            thirtyfourD,thirtyfiveA,thirtyfiveB,thirtyfiveC,thirtyfiveD,thirtysixA,thirtysixB,thirtysixC,thirtysixD,
            thirtysevenA,thirtysevenB,thirtysevenC,thirtysevenD,thirtyeightA,thirtyeightB,thirtyeightC,thirtyeightD,thirtynineA,
            thirtynineB,thirtynineC,thirtynineD,fortyA,fortyB,fortyC,fortyD
            ,fortyoneA,fortyoneB,fortyoneC,fortyoneD,fortytwoA,fortytwoB,fortytwoC,fortytwoD,
            fortythreeA,fortythreeB,fortythreeC,fortythreeD,fortyfourA,fortyfourB,fortyfourC,fortyfourD,
            fortyfiveA,fortyfiveB,fortyfiveC,fortyfiveD,fortysixA,fortysixB,fortysixC,fortysixD,
            fortysevenA,fortysevenB,fortysevenC,fortysevenD,fortyeightA,fortyeightB,fortyeightC,fortyeightD,fortynineA,
            fortynineB,fortynineC,fortynineD
            ,fiftyA,fiftyB,fiftyC,fiftyD;
    Button submit;
    DatabaseReference databaseReference;
    String grade,subject,exam,pageNo,school_name,Key;
    SharedPreferences DataPreference;
    SharedPreferences.Editor dataEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marking_scheme);

        DataPreference=getSharedPreferences("DataPreference", Context.MODE_PRIVATE);
        dataEditor=DataPreference.edit();


        Key=DataPreference.getString("school key","");
        school_name=DataPreference.getString("teacher school","");
        grade=DataPreference.getString("grade","");
        subject=DataPreference.getString("subject","");
        exam=DataPreference.getString("exam","");

        oneA = (RadioButton)findViewById(R.id.oneA);
        oneB = (RadioButton)findViewById(R.id.oneB);
        oneC = (RadioButton)findViewById(R.id.oneC);
        oneD = (RadioButton)findViewById(R.id.oneD);

        twoA = (RadioButton)findViewById(R.id.twoA);
        twoB = (RadioButton)findViewById(R.id.twoB);
        twoC = (RadioButton)findViewById(R.id.twoC);
        twoD = (RadioButton)findViewById(R.id.twoD);

        threeA = (RadioButton)findViewById(R.id.threeA);
        threeB = (RadioButton)findViewById(R.id.threeB);
        threeC = (RadioButton)findViewById(R.id.threeC);
        threeD = (RadioButton)findViewById(R.id.threeD);

        fourA = (RadioButton)findViewById(R.id.fourA);
        fourB = (RadioButton)findViewById(R.id.fourB);
        fourC = (RadioButton)findViewById(R.id.fourC);
        fourD = (RadioButton)findViewById(R.id.fourD);

        fiveA = (RadioButton)findViewById(R.id._5A);
        fiveB = (RadioButton)findViewById(R.id._5B);
        fiveC = (RadioButton)findViewById(R.id._5C);
        fiveD = (RadioButton)findViewById(R.id._5D);

        sixA = (RadioButton)findViewById(R.id._6A);
        sixB = (RadioButton)findViewById(R.id._6B);
        sixC = (RadioButton)findViewById(R.id._6C);
        sixD = (RadioButton)findViewById(R.id._6D);

        sevenA = (RadioButton)findViewById(R.id._7A);
        sevenB = (RadioButton)findViewById(R.id._7B);
        sevenC = (RadioButton)findViewById(R.id._7C);
        sevenD = (RadioButton)findViewById(R.id._7D);

        eightA = (RadioButton)findViewById(R.id._8A);
        eightB = (RadioButton)findViewById(R.id._8B);
        eightC = (RadioButton)findViewById(R.id._8C);
        eightD = (RadioButton)findViewById(R.id._8D);

        nineA = (RadioButton)findViewById(R.id._9A);
        nineB = (RadioButton)findViewById(R.id._9B);
        nineC = (RadioButton)findViewById(R.id._9C);
        nineD = (RadioButton)findViewById(R.id._9D);

        tenA = (RadioButton)findViewById(R.id._10A);
        tenB = (RadioButton)findViewById(R.id._10B);
        tenC = (RadioButton)findViewById(R.id._10C);
        tenD = (RadioButton)findViewById(R.id._10D);

        elevenA = (RadioButton)findViewById(R.id._11A);
        elevenB = (RadioButton)findViewById(R.id._11B);
        elevenC = (RadioButton)findViewById(R.id._11C);
        elevenD = (RadioButton)findViewById(R.id._11D);

        twelveA = (RadioButton)findViewById(R.id._12A);
        twelveB = (RadioButton)findViewById(R.id._12B);
        twelveC = (RadioButton)findViewById(R.id._12C);
        twelveD = (RadioButton)findViewById(R.id._12D);

        thirteenA = (RadioButton)findViewById(R.id._13A);
        thirteenB = (RadioButton)findViewById(R.id._13B);
        thirteenC = (RadioButton)findViewById(R.id._13C);
        thirteenD = (RadioButton)findViewById(R.id._13D);

        fourteenA = (RadioButton)findViewById(R.id._14A);
        fourteenB = (RadioButton)findViewById(R.id._14B);
        fourteenC = (RadioButton)findViewById(R.id._14C);
        fourteenD = (RadioButton)findViewById(R.id._14D);

        fiveteenA = (RadioButton)findViewById(R.id._15A);
        fiveteenB = (RadioButton)findViewById(R.id._15B);
        fiveteenC = (RadioButton)findViewById(R.id._15C);
        fiveteenD = (RadioButton)findViewById(R.id._15D);

        sixteenA = (RadioButton)findViewById(R.id._16A);
        sixteenB = (RadioButton)findViewById(R.id._16B);
        sixteenC = (RadioButton)findViewById(R.id._16C);
        sixteenD = (RadioButton)findViewById(R.id._16D);

        seventeenA = (RadioButton)findViewById(R.id._17A);
        seventeenB = (RadioButton)findViewById(R.id._17B);
        seventeenC = (RadioButton)findViewById(R.id._17C);
        seventeenD = (RadioButton)findViewById(R.id._17D);

        eighteenA = (RadioButton)findViewById(R.id._18A);
        eighteenB = (RadioButton)findViewById(R.id._18B);
        eighteenC = (RadioButton)findViewById(R.id._18C);
        eighteenD = (RadioButton)findViewById(R.id._18D);

        nineteenA = (RadioButton)findViewById(R.id._19A);
        nineteenB = (RadioButton)findViewById(R.id._19B);
        nineteenC = (RadioButton)findViewById(R.id._19C);
        nineteenD = (RadioButton)findViewById(R.id._19D);

        twentyA = (RadioButton)findViewById(R.id._20A);
        twentyB = (RadioButton)findViewById(R.id._20B);
        twentyC = (RadioButton)findViewById(R.id._20C);
        twentyD = (RadioButton)findViewById(R.id._20D);

        twentyoneA = (RadioButton)findViewById(R.id._21A);
        twentyoneB = (RadioButton)findViewById(R.id._21B);
        twentyoneC = (RadioButton)findViewById(R.id._21C);
        twentyoneD = (RadioButton)findViewById(R.id._21D);

        twentytwoA = (RadioButton)findViewById(R.id._22A);
        twentytwoB = (RadioButton)findViewById(R.id._22B);
        twentytwoC = (RadioButton)findViewById(R.id._22C);
        twentytwoD = (RadioButton)findViewById(R.id._22D);

        twentythreeA = (RadioButton)findViewById(R.id._23A);
        twentythreeB = (RadioButton)findViewById(R.id._23B);
        twentythreeC = (RadioButton)findViewById(R.id._23C);
        twentythreeD = (RadioButton)findViewById(R.id._23D);

        twentyfourA = (RadioButton)findViewById(R.id._24A);
        twentyfourB = (RadioButton)findViewById(R.id._24B);
        twentyfourC = (RadioButton)findViewById(R.id._24C);
        twentyfourD = (RadioButton)findViewById(R.id._24D);

        twentyfiveA = (RadioButton)findViewById(R.id._25A);
        twentyfiveB = (RadioButton)findViewById(R.id._25B);
        twentyfiveC = (RadioButton)findViewById(R.id._25C);
        twentyfiveD = (RadioButton)findViewById(R.id._25D);

        twentysixA = (RadioButton)findViewById(R.id._26A);
        twentysixB = (RadioButton)findViewById(R.id._26B);
        twentysixC = (RadioButton)findViewById(R.id._26C);
        twentysixD = (RadioButton)findViewById(R.id._26D);

        twentysevenA = (RadioButton)findViewById(R.id._27A);
        twentysevenB = (RadioButton)findViewById(R.id._27B);
        twentysevenC = (RadioButton)findViewById(R.id._27C);
        twentysevenD = (RadioButton)findViewById(R.id._27D);

        twentyeightA = (RadioButton)findViewById(R.id._28A);
        twentyeightB = (RadioButton)findViewById(R.id._28B);
        twentyeightC = (RadioButton)findViewById(R.id._28C);
        twentyeightD = (RadioButton)findViewById(R.id._28D);

        twentynineA = (RadioButton)findViewById(R.id._29A);
        twentynineB = (RadioButton)findViewById(R.id._29B);
        twentynineC = (RadioButton)findViewById(R.id._29C);
        twentynineD = (RadioButton)findViewById(R.id._29D);

        thirtyA = (RadioButton)findViewById(R.id._30A);
        thirtyB = (RadioButton)findViewById(R.id._30B);
        thirtyC = (RadioButton)findViewById(R.id._30C);
        thirtyD = (RadioButton)findViewById(R.id._30D);

        thirtyoneA = (RadioButton)findViewById(R.id._31A);
        thirtyoneB = (RadioButton)findViewById(R.id._31B);
        thirtyoneC = (RadioButton)findViewById(R.id._31C);
        thirtyoneD = (RadioButton)findViewById(R.id._31D);

        thirtytwoA = (RadioButton)findViewById(R.id._32A);
        thirtytwoB = (RadioButton)findViewById(R.id._32B);
        thirtytwoC = (RadioButton)findViewById(R.id._32C);
        thirtytwoD = (RadioButton)findViewById(R.id._32D);


        thirtythreeA = (RadioButton)findViewById(R.id._33A);
        thirtythreeB = (RadioButton)findViewById(R.id._33B);
        thirtythreeC = (RadioButton)findViewById(R.id._33C);
        thirtythreeD = (RadioButton)findViewById(R.id._33D);

        thirtyfourA = (RadioButton)findViewById(R.id._34A);
        thirtyfourB = (RadioButton)findViewById(R.id._34B);
        thirtyfourC = (RadioButton)findViewById(R.id._35C);
        thirtyfourD = (RadioButton)findViewById(R.id._35D);

        thirtyfiveA = (RadioButton)findViewById(R.id._35A);
        thirtyfiveB = (RadioButton)findViewById(R.id._35B);
        thirtyfiveC = (RadioButton)findViewById(R.id._35C);
        thirtyfiveD = (RadioButton)findViewById(R.id._35D);

        thirtysixA = (RadioButton)findViewById(R.id._36A);
        thirtysixB = (RadioButton)findViewById(R.id._36B);
        thirtysixC = (RadioButton)findViewById(R.id._36C);
        thirtysixD = (RadioButton)findViewById(R.id._36D);

        thirtysevenA = (RadioButton)findViewById(R.id._37A);
        thirtysevenB = (RadioButton)findViewById(R.id._37B);
        thirtysevenC = (RadioButton)findViewById(R.id._37C);
        thirtysevenD = (RadioButton)findViewById(R.id._37D);

        thirtyeightA = (RadioButton)findViewById(R.id._38A);
        thirtyeightB = (RadioButton)findViewById(R.id._38B);
        thirtyeightC = (RadioButton)findViewById(R.id._38C);
        thirtyeightD = (RadioButton)findViewById(R.id._38D);

        thirtynineA = (RadioButton)findViewById(R.id._39A);
        thirtynineB = (RadioButton)findViewById(R.id._39B);
        thirtynineC = (RadioButton)findViewById(R.id._39C);
        thirtynineD = (RadioButton)findViewById(R.id._39D);

        fortyA = (RadioButton)findViewById(R.id._40A);
        fortyB = (RadioButton)findViewById(R.id._40B);
        fortyC = (RadioButton)findViewById(R.id._40C);
        fortyD = (RadioButton)findViewById(R.id._40D);

        fortyoneA = (RadioButton)findViewById(R.id._41A);
        fortyoneB = (RadioButton)findViewById(R.id._41B);
        fortyoneC = (RadioButton)findViewById(R.id._41C);
        fortyoneD = (RadioButton)findViewById(R.id._41D);

        fortytwoA = (RadioButton)findViewById(R.id._42A);
        fortytwoB = (RadioButton)findViewById(R.id._42B);
        fortytwoC = (RadioButton)findViewById(R.id._42C);
        fortytwoD = (RadioButton)findViewById(R.id._42D);

        fortythreeA = (RadioButton)findViewById(R.id._43A);
        fortythreeB = (RadioButton)findViewById(R.id._43B);
        fortythreeC = (RadioButton)findViewById(R.id._43C);
        fortythreeD = (RadioButton)findViewById(R.id._43D);

        fortyfourA = (RadioButton)findViewById(R.id._44A);
        fortyfourB = (RadioButton)findViewById(R.id._44B);
        fortyfourC = (RadioButton)findViewById(R.id._44C);
        fortyfourD = (RadioButton)findViewById(R.id._44D);

        fortyfiveA = (RadioButton)findViewById(R.id._45A);
        fortyfiveB = (RadioButton)findViewById(R.id._45B);
        fortyfiveC = (RadioButton)findViewById(R.id._45C);
        fortyfiveD = (RadioButton)findViewById(R.id._45D);

        fortysixA = (RadioButton)findViewById(R.id._46A);
        fortysixB = (RadioButton)findViewById(R.id._46B);
        fortysixC = (RadioButton)findViewById(R.id._46C);
        fortysixD = (RadioButton)findViewById(R.id._46D);

        fortysevenA = (RadioButton)findViewById(R.id._47A);
        fortysevenB = (RadioButton)findViewById(R.id._47B);
        fortysevenC = (RadioButton)findViewById(R.id._47C);
        fortysevenD = (RadioButton)findViewById(R.id._47D);

        fortyeightA = (RadioButton)findViewById(R.id._48A);
        fortyeightB = (RadioButton)findViewById(R.id._48B);
        fortyeightC = (RadioButton)findViewById(R.id._48C);
        fortyeightD = (RadioButton)findViewById(R.id._48D);

        fortynineA = (RadioButton)findViewById(R.id._49A);
        fortynineB = (RadioButton)findViewById(R.id._49B);
        fortynineC = (RadioButton)findViewById(R.id._49C);
        fortynineD = (RadioButton)findViewById(R.id._49D);

        fiftyA=(RadioButton)findViewById(R.id._50A);
        fiftyB=(RadioButton)findViewById(R.id._50B);
        fiftyC=(RadioButton)findViewById(R.id._50C);
        fiftyD=(RadioButton)findViewById(R.id._50D);

        submit=findViewById(R.id.bt_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(MarkingSchemeActivity.this,FinishSetExam.class));

                final Intent intent= new Intent(MarkingSchemeActivity.this,FinishSetExam.class);
                intent.putExtra("exam question", "50");

                Calendar calender = Calendar.getInstance();
                String time_stamp = String.valueOf(calender.getTimeInMillis());

                databaseReference=FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("Examinations").child(grade)
                        .child(exam).child(subject);
                HashMap<String, Object> hashMap= new HashMap<>();
                hashMap.put("timestamp",time_stamp);
                  databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          Toast.makeText(MarkingSchemeActivity.this, "Marking scheme created", Toast.LENGTH_SHORT).show();
                          startActivity(intent);
                      }
                  }
              });
            }
        });


    }
    private void SaveAnswer(String question_no, String option){
        databaseReference= FirebaseDatabase.getInstance().getReference("Schools").child(Key).child("ExaminationAns")
                .child(grade).child(exam).child(subject);
        HashMap <String,Object> hashMap= new HashMap<>();
        hashMap.put(question_no,option);
        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MarkingSchemeActivity.this, "Ans Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MarkingSchemeActivity.this, "Error, Answer wasnt saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String option="";
        String question_no="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.oneA:
                if(checked) {
                    option = "A";
                    question_no = "1";
                    SaveAnswer(question_no,option);
                }


                break;
            case R.id.oneB:
                if(checked) {
                    option = "B";
                    question_no = "1";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id.oneC:
                if(checked) {
                    option = "C";
                    question_no = "1";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id.oneD:
                if(checked) {
                    option = "D";
                    question_no = "1";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id.twoA:
                if(checked) {
                    option = "A";
                    question_no = "2";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id.twoB:
                if(checked) {
                    option = "B";
                    question_no = "2";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id.twoC:
                if(checked) {
                    option = "C";
                    question_no = "2";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.twoD:
                if(checked) {
                    option = "D";
                    question_no = "2";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.threeA:
                if(checked) {
                    option = "A";
                    question_no = "3";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.threeB:
                if(checked) {
                    option = "B";
                    question_no = "3";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.threeC:
                if(checked) {
                    option = "C";
                    question_no = "3";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.threeD:
                if(checked) {
                    option = "D";
                    question_no = "3";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.fourA:
                if(checked) {
                    option = "A";
                    question_no = "4";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.fourB:
                if(checked) {
                    option = "B";
                    question_no = "4";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.fourC:
                if(checked) {
                    option = "C";
                    question_no = "4";
                    SaveAnswer(question_no,option);
                }

                break;case R.id.fourD:
                if(checked) {
                    option = "D";
                    question_no = "4";
                    SaveAnswer(question_no,option);
                }

                break;case R.id._5A:
                if(checked) {
                    option = "A";
                    question_no = "5";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._5B:
                if(checked) {
                    option = "B";
                    question_no = "5";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._5C:
                if(checked) {
                    option = "C";
                    question_no = "5";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._5D:
                if(checked) {
                    option = "A";
                    question_no = "5";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._6A:
                if(checked) {
                    option = "A";
                    question_no = "6";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._6B:
                if(checked) {
                    option = "B";
                    question_no = "6";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._6C:
                if(checked) {
                    option = "C";
                    question_no = "6";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._6D:
                if(checked) {
                    option = "D";
                    question_no = "6";
                    SaveAnswer(question_no,option);
                }

                break;case R.id._7A:
                if(checked) {
                    option = "A";
                    question_no = "7";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._7B:
                if(checked) {
                    option = "B";
                    question_no = "7";
                    SaveAnswer(question_no,option);
                }

                break;

            case R.id._7C:
                if(checked) {
                    option = "C";
                    question_no = "7";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._7D:
                if(checked) {
                    option = "D";
                    question_no = "7";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._8A:
                if(checked) {
                    option = "A";
                    question_no = "8";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._8B:
                if(checked) {
                    option = "B";
                    question_no = "8";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._8C:
                if(checked) {
                    option = "C";
                    question_no = "8";
                    SaveAnswer(question_no,option);
                }

                break;case R.id._8D:
                if(checked) {
                    option = "D";
                    question_no = "8";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._9A:
                if(checked) {
                    option = "A";
                    question_no = "9";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._9B:
                if(checked) {
                    option = "B";
                    question_no = "9";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._9C:
                if(checked) {
                    option = "C";
                    question_no = "9";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._9D:
                if(checked) {
                    option = "D";
                    question_no = "9";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._10A:
                if(checked) {
                    option = "A";
                    question_no = "10";
                    SaveAnswer(question_no,option);
                }

                break;
            case R.id._10B:
                if(checked) {
                    option = "B";
                    question_no = "10";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._10C:
                if(checked) {
                    option = "C";
                    question_no = "10";
                    SaveAnswer(question_no,option);
                }

                break;
                case R.id._10D:
                if(checked) {
                    option = "D";
                    question_no = "10";
                    SaveAnswer(question_no,option);
                }

                break;


        }
        Toast.makeText(getApplicationContext(), question_no+" "+option, Toast.LENGTH_SHORT).show();
    }
}