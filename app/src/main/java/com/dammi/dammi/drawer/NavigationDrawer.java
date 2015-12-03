package com.dammi.dammi.drawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dammi.dammi.R;

/**
 * Created by trees on 12/3/15.
 */
public class NavigationDrawer extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigView;
    private Activity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        navigView=(NavigationView)view.findViewById(R.id.main_drawer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        navigView.setNavigationItemSelectedListener(this);
    }

    public void setNavig(DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(getActivity(), drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
