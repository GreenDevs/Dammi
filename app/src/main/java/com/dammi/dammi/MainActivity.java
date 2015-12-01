package com.dammi.dammi;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.dammi.dammi.home.HomeAdapter;
import com.dammi.dammi.search.SearchableActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private Context context;
    private HomeAdapter homeAdapter;
    private Toolbar toolbar;
    private NavigationView mDrawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        init();

    }

    private void init()
    {
        //FOR TOOL BAAR
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //FOR RECYCLER VIEW HOME
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.homeRecycler);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        homeAdapter=new HomeAdapter(context);
        recyclerView.setAdapter(homeAdapter);

        fab=(FloatingActionButton)findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(context, SearchableActivity.class);
                startActivity(intent);
            }
        });
        setDrawer();

    }


    private void setDrawer()
    {
        mDrawer=(NavigationView)findViewById(R.id.main_drawer);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        mDrawer.setNavigationItemSelectedListener(this);
        drawerToggle=new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    @Override
    protected void onStop()
    {
        ///THIS METHOD IS FOR CLEANING MEMORY OBTAINED FORM HOME SLIDE SHOW
        if(homeAdapter.getSliderLayout()!=null)
             homeAdapter.getSliderLayout().stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        startActivity(new Intent(context, SearchableActivity.class));
        return true;
    }
}
