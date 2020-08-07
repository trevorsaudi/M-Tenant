package com.saudi.m_tenant_firebase;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Third_Fragment extends Fragment {
    TextView done,back;
    ViewPager viewPager;
    public Third_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_, container, false);
//        initialize the viewpager from the mainactivity
        viewPager = getActivity().findViewById(R.id.viewpager);
        back = view.findViewById(R.id.slideThreeBack);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    viewPager.setCurrentItem(1);
            }
        });


        done = view.findViewById(R.id.slideThreeDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), SignUp.class);
                startActivity(intent);
            }
        });

        return view;

    }
}
