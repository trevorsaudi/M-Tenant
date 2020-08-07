package com.saudi.m_tenant_firebase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class First_Fragment extends Fragment {

    TextView next;
    ViewPager viewPager;


    public First_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_, container, false);
//        initialize the viewpager from the mainactivity
        viewPager = getActivity().findViewById(R.id.viewpager);
        next = view.findViewById(R.id.slideonenext);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        return view;

    }


}
