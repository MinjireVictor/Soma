package com.mackvel.soma;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class ClassFragment extends Fragment {
    private GridLayout gridLayout;
    private CardView cardView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_class, container, false);
        gridLayout= view.findViewById(R.id.class_grid);
        setClickEvent(gridLayout);
        return view;


    }
    private void setClickEvent (final GridLayout gridLayout){
        for (int i=0;i<gridLayout.getChildCount();i++){
            final CardView cardView1=(CardView)gridLayout.getChildAt(i);
            cardView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ViewGroup viewGroup=(ViewGroup)cardView.getChildAt(0);
                    String text=((TextView)viewGroup.getChildAt(1)).getText().toString();
                    // start Activity

                }
            });
        }
    }
}