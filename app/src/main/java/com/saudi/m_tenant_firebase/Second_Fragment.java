package com.saudi.m_tenant_firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Second_Fragment extends Fragment {
    TextView next;
    TextView back;
    ViewPager viewPager;

    public Second_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_, container, false);
//        initialize the viewpager from the mainactivity
        viewPager = getActivity().findViewById(R.id.viewpager);
        next = view.findViewById(R.id.slidetwonext);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });


        back = view.findViewById(R.id.slideTwoBack);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                viewPager.setCurrentItem(0);
            }
        });

        return view;

    }
}
