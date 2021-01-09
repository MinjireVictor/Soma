package com.mackvel.soma.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mackvel.soma.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter (Context context){
        this.context= context;
    }

    public int [] slide_images = {
            R.drawable.isometeic4,
            R.drawable.isometric1,
            R.drawable.isometric3,
            // R.drawable.money,


    };
    public  String [] slide_headings= {
            "WELCOME",
            "SET QUESTIONS",
            "DO TESTS"
            //"DONATE"
    };

    public String [] slide_deescriptions= {
            " Welcome to Soma",
            " Teachers can set classwork and exam questions with ease ",
            " Students can do tests at any time "
            //"Donate to the cause and help us make this app better"

    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE );
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        Typeface saleforece = Typeface.createFromAsset(context.getAssets(),"fonts/SalesforceSans.ttf");
        Typeface gothamBold = Typeface.createFromAsset(context.getAssets(),"fonts/GothamBold.ttf");
        Typeface gothamBook = Typeface.createFromAsset(context.getAssets(),"fonts/GothamBook.otf");




        ImageView slideImageView=(ImageView)view.findViewById(R.id.slide_image);
        TextView slideHeading=(TextView) view.findViewById(R.id.slide_heading);
        slideHeading.setTypeface(gothamBold);
        TextView slideDescription=(TextView) view.findViewById(R.id.slide_description);
        slideDescription.setTypeface(saleforece);


        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_deescriptions[position]);

        container.addView(view);
        return view;




    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }


}
