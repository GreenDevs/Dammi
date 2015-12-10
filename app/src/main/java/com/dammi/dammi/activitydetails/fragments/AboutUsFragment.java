package com.dammi.dammi.activitydetails.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dammi.dammi.R;

/**
 * Created by trees on 12/4/15.
 */
public class AboutUsFragment extends Fragment {


    private static final String HOST_ID="operator_id";
    private static final String HOST_NAME="operator_name";
    private static final String TITLE="title";
    private static final String OVERVIEW="overview";
    private static final String PARTICIPANT_COUNT="participant";
    private static final String DURATION="duration";
    private static final String COVER_PIC="cover_pic";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rating_review, container, false);
    }
}
