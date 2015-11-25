package com.dammi.dammi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dammi.dammi.home.HomeAdapter;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private HomeAdapter homeAdapter;
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
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.homeRecycler);
        GridLayoutManager manager=new GridLayoutManager(context, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if(position==0) return 2;
                return 1;
            }
        });

        recyclerView.setLayoutManager(manager);
        homeAdapter=new HomeAdapter(context);
        recyclerView.setAdapter(homeAdapter);

    }


    @Override
    protected void onStop()
    {
        ///THIS METHOD IS FOR CLEANING MEMORY OBTAINED FORM HOME SLIDE SHOW
        if(homeAdapter.getSliderLayout()!=null)
        {
            homeAdapter.getSliderLayout().stopAutoCycle();
            Toast.makeText(context, "CALLED ", Toast.LENGTH_SHORT).show();
        }
        super.onStop();
    }
}
