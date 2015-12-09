package com.dammi.dammi.activitylist;

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
import com.dammi.dammi.R;
import com.dammi.dammi.drawer.NavigationDrawer;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ListActivity extends AppCompatActivity
{
    private Menu menu;
    private boolean isListView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
    }

    private  void init()
    {

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        isListView = true;
        setDrawer();

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.list_recycler);
        recyclerView.setHasFixedSize(true);
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

        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        recyclerView.setAdapter(new ListAdapter(this));


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
}
