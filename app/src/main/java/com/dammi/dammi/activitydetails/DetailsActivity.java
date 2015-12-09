package com.dammi.dammi.activitydetails;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.dammi.dammi.R;
import com.dammi.dammi.Volley.CacheRequest;
import com.dammi.dammi.Volley.MyApplication;
import com.dammi.dammi.Volley.VolleySingleton;
import com.dammi.dammi.activitydetails.fragments.ExpDetailsFragment;
import com.dammi.dammi.activitydetails.fragments.ExperienceFragment;
import com.dammi.dammi.activitydetails.fragments.RateReviewFragment;
import com.dammi.dammi.activitydetails.fragments.AboutUsFragment;
import com.dammi.dammi.activitydetails.fragments.ViewPagerAdapter;
import com.dammi.dammi.activitylist.ActivityItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    //API PARTS

    private static final String URL="http://dammitravels.netai.net/?table=activity_main&&a_id=";
    private static final String AVERAGE_RATING="rating_avg";
    private static final String TITLE="title";
    private static final String COVER_PIC="cover_pic";
    private static final String AVILABLE="avilable";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private ImageView coverPic;
    private Toolbar toolbar;

    private DetailsActivity context;
    private RequestQueue requestQueue;
    public static int act_id;
    public static final String ACT_ID_TAG="activity_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this;


        act_id=getIntent().getIntExtra(ACT_ID_TAG, 0);

        requestQueue= VolleySingleton.getInstance().getQueue();
        init();
        initFab();
        requestData();


    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        if (viewPager != null)
            setViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if (tabLayout != null)
            setTabLayout();

        coverPic=(ImageView)findViewById(R.id.cover_pic);
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_phone);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_book);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(context);
        fab1.setOnClickListener(context);
        fab2.setOnClickListener(context);
    }

    private void setViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExperienceFragment(), "OVERVIEW");
        adapter.addFragment(new ExpDetailsFragment(), "DETAILS");
        adapter.addFragment(new AboutUsFragment(), "ABOUT US");
        adapter.addFragment(new RateReviewFragment(), "REVIEWS");
        viewPager.setAdapter(adapter);
    }

    private void setTabLayout() {

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

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_add:

                animateFAB();
                break;
            case R.id.fab_phone:
                call();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab_book:

                Log.d("Raj", "Fab 2");
                break;
        }
    }


    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    private void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:123456789"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {

        }
    }


    ///####################### PARSE JSON
    private void requestData(){

        CacheRequest cacheRequest=new CacheRequest(Request.Method.GET, URL+act_id,

                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        try
                        {
                            final String jsonResponseString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            JSONObject jsonObject=new JSONObject(jsonResponseString);
                            parseSetValues(jsonObject);

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
                    }
                });

        cacheRequest.setTag(this);
        requestQueue.add(cacheRequest);

    }


    //PARSE AND SET THE JSON VALUES

    private void parseSetValues(JSONObject jsonObject){
        if(jsonObject!=null){

            try {
                String imageUrl= MyApplication.HOME_URL+"/"+jsonObject.getString(COVER_PIC);
                String title=jsonObject.getString(TITLE);
                toolbar.setTitle(title);
                setCoverPic(imageUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCoverPic(String url){
        ImageLoader loader=VolleySingleton.getInstance().getmImageLoader();
        loader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                coverPic.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
//                coverPic.setImageResource(R.drawable.o);
            }
        });
    }

}
