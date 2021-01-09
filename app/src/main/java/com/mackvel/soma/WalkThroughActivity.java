package com.mackvel.soma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mackvel.soma.Adapter.SliderAdapter;

public class WalkThroughActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;
    private SliderAdapter sliderAdapter;
    private TextView[]mDots;
    private Button Next,Back;
    private int mCurrentPage;
    SharedPreferences OnceSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_walk_through);

        mDotsLayout=(LinearLayout)findViewById(R.id.dotsLayout);
        mSlideViewPager=(ViewPager) findViewById(R.id.slideViewPager);
        Next=(Button) findViewById(R.id.nextBtn);

        Back=(Button) findViewById(R.id.backBtn);
        Context context;

        addDotsIndicator(0);
        sliderAdapter= new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentPage==mDots.length-1){

                    Intent intent = new Intent(WalkThroughActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else {
                    mSlideViewPager.setCurrentItem(mCurrentPage+1);
                }
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });


    }
    public void addDotsIndicator(int position){

        mDots=new TextView[3];
        mDotsLayout.removeAllViews();
        for (int i=0;i< mDots.length; i++){
            mDots[i]= new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage=position;
            if (position==0){
                Next.setEnabled(true);
                Back.setEnabled(false);
                Back.setVisibility(View.INVISIBLE);
                Next.setText("Next");
                Back.setText("");
            }else if (position== mDots.length-1){
                Next.setEnabled(true);
                Back.setEnabled(true);
                Back.setVisibility(View.VISIBLE);
                Next.setText("Finish");
                Back.setText("Back");

            }else{
                Next.setEnabled(true);
                Back.setEnabled(true);
                Back.setVisibility(View.VISIBLE);
                Next.setText("Next");
                Back.setText("Back");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}