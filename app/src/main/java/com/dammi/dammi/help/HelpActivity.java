package com.dammi.dammi.help;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dammi.dammi.MainActivity;
import com.dammi.dammi.R;
import com.dammi.dammi.activitylist.ListActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HelpActivity  extends AppCompatActivity implements View.OnClickListener
{
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.slider1,R.drawable.slider2,R.drawable.slider3,R.drawable.slider4};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    TextView tvSkip,tvDone;
    private HelpActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 14)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_help);
        this.context = this;
        init();
    }



    private void init()
    {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(HelpActivity.this, ImagesArray));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;

        tvDone= (TextView) findViewById(R.id.txt_done);
        tvSkip= (TextView) findViewById(R.id.txt_skip);

        indicator.setRadius(5 * density);
        NUM_PAGES =IMAGES.length;

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2)
            {
                if(pos==2||pos==1||pos==0)
                {
                    tvSkip.setVisibility(View.VISIBLE);
                    tvDone.setOnClickListener(context);
                    tvSkip.setOnClickListener(context);
                 }

                else if(pos==3)
                {
                    tvSkip.setVisibility(View.INVISIBLE);
                    tvDone.setOnClickListener(context);
                }
            }

            @Override
            public void onPageScrollStateChanged(int pos)
            {


            }
        });
    }


    private void onDoneClicked()
    {
        this.finish();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txt_done:
                onDoneClicked();
                break;

            case R.id.txt_skip:
                mPager.setCurrentItem(mPager.getCurrentItem()+1,true);
                break;

            default:
                break;
        }
    }
}
