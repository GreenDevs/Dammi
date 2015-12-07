package com.dammi.dammi.activitydetails.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dammi.dammi.R;

/**
 * Created by trees on 12/4/15.
 */
public class ExperienceFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.about_experience, container, false);

        ImageView rate_icon=(ImageView)view.findViewById(R.id.rate_icon);
        rate_icon.setColorFilter(getActivity().getResources().getColor(R.color.colorAccent));

        return view;
    }
}