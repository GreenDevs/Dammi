package com.dammi.dammi.activitylist;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dammi.dammi.R;
import com.dammi.dammi.Volley.CacheRequest;
import com.dammi.dammi.Volley.MyApplication;
import com.dammi.dammi.Volley.VolleySingleton;
import com.dammi.dammi.drawer.NavigationDrawer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity
{
    ///JSON PARTS
    private static final String URL="http://dammitravels.com/api/?table=list&&sort_by=costasc";
    private static final String ACTIVITY_ID="activity_id";
    private static final String HOST_NAME="operator_name";
    private static final String TITLE="title";
    private static final String LOCATION="location";
    private static final String CURRENCY="currency";
    private static final String COSTS="cost";
    private static final String NO_OF_DAYS="no_of_days";
    private static final String NO_OF_NIGHTS="no_of_nights";
    private static final String RATING_AVG="rating_avg";
    private static final String COVER_PIC="cover_pic";

    private Menu menu;
    private boolean isListView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Toolbar toolbar;
    private Context context;
    private RequestQueue requestQueue;
    private ActivityListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        
        requestQueue= VolleySingleton.getInstance().getQueue();
        sendRequest();
    }

    private  void init()
    {

        context=this;
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        isListView = true;
        setDrawer();

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
        mStaggeredGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            mStaggeredGridLayoutManager.setSpanCount(1);
            recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        }
        else
        {
            mStaggeredGridLayoutManager.setSpanCount(2);
        }

        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mAdapter=new ActivityListAdapter(this);
        recyclerView.setAdapter(mAdapter);


    }

    private void setDrawer() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawer drawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setNavig(drawerLayout, toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_toggle) {
            toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
      MenuItem  item = menu.findItem(R.id.action_toggle);
        if (isListView) {
            mStaggeredGridLayoutManager.setSpanCount(2);
            item.setIcon(R.drawable.ic_view_list_white_24dp);
            item.setTitle("Show as list");
            isListView = false;
        } else {
            mStaggeredGridLayoutManager.setSpanCount(1);
            item.setIcon(R.drawable.ic_dashboard_white_24dp);
            item.setTitle("Show as grid");
            isListView = true;
        }
    }

    
    //############# ACTIVITY LIST REQUEST #######################
    private void sendRequest()
    {

        CacheRequest cacheRequest=new CacheRequest(Request.Method.GET, URL,

                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        try
                        {
                            final String jsonResponseString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            JSONArray jsonArray=new JSONArray(jsonResponseString);

                            List<ActivityItem> list=parseJson(jsonArray);
                            mAdapter.updateList(list);

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

    private List<ActivityItem> parseJson(JSONArray jsonArray){

        List<ActivityItem> tmp=new ArrayList<>();
        if(jsonArray!=null){
            if(jsonArray.length()>0){

                for(int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonItem=jsonArray.getJSONObject(i);
                        String title, location, hostName, duration, priceTag, imageUrl;
                        int actId;

                        actId=jsonItem.getInt(ACTIVITY_ID);
                        title=jsonItem.getString(TITLE);
                        location=jsonItem.getString(LOCATION);
                        hostName=jsonItem.getString(HOST_NAME);
                        duration=jsonItem.getString(NO_OF_DAYS)+"D/"+jsonItem.getString(NO_OF_NIGHTS)+"N";
                        priceTag=jsonItem.getString(CURRENCY)+jsonItem.getString(COSTS);
                        imageUrl= MyApplication.HOME_URL+jsonItem.getString(COVER_PIC).replace("\\","");
                        tmp.add(new ActivityItem(title, location, hostName, duration, priceTag, actId, imageUrl));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return tmp;
    }
}
