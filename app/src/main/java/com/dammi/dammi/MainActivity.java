package com.dammi.dammi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dammi.dammi.Volley.CacheRequest;
import com.dammi.dammi.Volley.VolleySingleton;
import com.dammi.dammi.drawer.NavigationDrawer;
import com.dammi.dammi.help.HelpActivity;
import com.dammi.dammi.home.HomeAdapter;
import com.dammi.dammi.search.SearchableActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private HomeAdapter homeAdapter;
    private Toolbar toolbar;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appIntro();


        context = this;
        init();

        VolleySingleton singleton= VolleySingleton.getInstance();
        requestQueue=singleton.getQueue();

       // sendNewsRequest();
        
    }


    private void init() {
        //FOR TOOL BAR
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

//        //transparent status bar
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //FOR RECYCLER VIEW HOME
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.homeRecycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        homeAdapter = new HomeAdapter(context);
        recyclerView.setAdapter(homeAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchableActivity.class);
                startActivity(intent);
            }
        });
        setDrawer();

    }


    private void setDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawer drawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setNavig(drawerLayout, toolbar);
    }


    @Override
    protected void onStop() {
        ///THIS METHOD IS FOR CLEANING MEMORY OBTAINED FORM HOME SLIDE SHOW
        if (homeAdapter.getSliderLayout() != null)
            homeAdapter.getSliderLayout().stopAutoCycle();
        super.onStop();
    }



    private void sendNewsRequest()
    {

        final String URL="http://www.goalnepal.com/json_news_2015.php?page=1";

        CacheRequest newsRequest=new CacheRequest(Request.Method.GET, URL,

                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        try
                        {
                            final String jsonResponseString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            JSONObject responseJson=new JSONObject(jsonResponseString);

                            Toast.makeText(context, ""+responseJson.toString(), Toast.LENGTH_SHORT).show();

                        }
                        catch (UnsupportedEncodingException | JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                ,
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Parse Exception", Toast.LENGTH_SHORT).show();
                    }
                });


        newsRequest.setTag(this);
        requestQueue.add(newsRequest);
    }



    private void appIntro()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart)
                {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, HelpActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        thread.start();
    }

}

