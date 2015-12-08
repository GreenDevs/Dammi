package com.dammi.dammi.activitydetails;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dammi.dammi.R;
import com.dammi.dammi.activitydetails.fragments.ExpDetailsFragment;
import com.dammi.dammi.activitydetails.fragments.ExperienceFragment;
import com.dammi.dammi.activitydetails.fragments.RateReviewFragment;
import com.dammi.dammi.activitydetails.fragments.AboutUsFragment;
import com.dammi.dammi.activitydetails.fragments.ViewPagerAdapter;

public class DetailsActivity extends AppCompatActivity
{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();
    }

    private void init(){

        Toolbar toolbar=(Toolbar)findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager=(ViewPager)findViewById(R.id.tab_viewpager);
        if(viewPager!=null)
        setViewPager();

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        if(tabLayout!=null)
            setTabLayout();
    }

    private void setViewPager(){

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExperienceFragment(), "ABOUT EXPERIENCE");
        adapter.addFragment(new ExpDetailsFragment(), "EXPERIENCE DETAILS");
        adapter.addFragment(new AboutUsFragment(), "ABOUT HOSTS");
        adapter.addFragment(new RateReviewFragment(), "RATE AND REVIEW");
        viewPager.setAdapter(adapter);
    }

    private void setTabLayout(){

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }
}
