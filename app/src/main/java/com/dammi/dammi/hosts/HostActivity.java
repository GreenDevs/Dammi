package com.dammi.dammi.hosts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HostActivity extends AppCompatActivity {

    private static final String URL="http://www.dammitravels.com/api/?table=operator_list&&sort_by=ratingasc";
    private static final String HOST_NAME="name";//
    private static final String RATING="rating";//
    private static final String ACTIVITY_COUNT="no_of_activities"; //
    private static final String EVENT_COUNT="no_of_events";//
    private static final String IMAGE_URL="o_pic";//

    private static final String ACTIVITY_ID="o_id";
    private static final String ADDRESS="address";//
    private static final String MOBILE_NUMBER_1="mob_no1";
    private static final String MOBILE_NUMBER_2="mob_no2";
    private static final String DESCRIPTION="description";


    private RequestQueue requestQueue;
    private HostAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        init();

        requestQueue= VolleySingleton.getInstance().getQueue();
        sendRequest();


    }

    private void init()
    {
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.host_recycler);
        recyclerView.setHasFixedSize(true);
        mAdapter=new HostAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

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




    private void sendRequest()
    {
        CacheRequest cacheRequest = new CacheRequest(Request.Method.GET,URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                JSONArray jsonArray=null;
                try {
                    String jsonResponse = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    jsonArray = new JSONArray(jsonResponse);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<HostItem> list = parseJson(jsonArray);
                Toast.makeText(HostActivity.this, "List size="+list.size(),Toast.LENGTH_LONG).show();
                 mAdapter.updateAdapter(list);
            }
        }
        ,
        new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            }
        });

        cacheRequest.setTag(this);
        requestQueue.add(cacheRequest);
    }

    private List<HostItem> parseJson(JSONArray jsonArray) {
        List<HostItem> hostItemList = new ArrayList<>();

        if(jsonArray!=null){
            if(jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject;
                    String hostName,address,rating,imageUrl,mobileNo1,mobileNo2,description;
                    int hostId,activityCount,eventCount;

                    try {
                        jsonObject = jsonArray.getJSONObject(i);

//                        hostId = jsonObject.getInt(ACTIVITY_ID);
//                        address = jsonObject.getString(ADDRESS);
//                        mobileNo1 = jsonObject.getString(MOBILE_NUMBER_1);
//                        mobileNo2 = jsonObject.getString(MOBILE_NUMBER_2);
//                        description = jsonObject.getString(DESCRIPTION);

                        hostName = jsonObject.getString(HOST_NAME);
                        rating = jsonObject.getString(RATING);
                        activityCount = jsonObject.getInt(ACTIVITY_COUNT);
                        eventCount = jsonObject.getInt(EVENT_COUNT);
                        imageUrl = MyApplication.HOME_URL+"/api/"+jsonObject.getString(IMAGE_URL);
                        hostItemList.add(new HostItem(imageUrl,rating,hostName,activityCount,eventCount,imageUrl));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return hostItemList;
    }
}
