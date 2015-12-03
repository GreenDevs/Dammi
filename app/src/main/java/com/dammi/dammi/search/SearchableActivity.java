package com.dammi.dammi.search;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.dammi.dammi.R;

public class SearchableActivity extends AppCompatActivity
{

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        this.context=this;

        init();
        handleIntent(getIntent());
    }


    private void init()
    {
        Toolbar toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        ///########### THIS LINE OF CODE IS JUST TO EXPAND THE SEARCH VIEW BY DEFAUL
        MenuItem searchItem=menu.findItem(R.id.action_search);
        searchItem.expandActionView();

        ///######## THESE LINES OF CODE CONFIGURE THE SEARCH VIEW, ENABLE AUTOMATIC QUERY DELIVERY TO ANOTHER ACTIVITY ON INTENT
        SearchManager manager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        ComponentName cn=new ComponentName(this, SearchableActivity.class);
        searchView.setSearchableInfo(manager.getSearchableInfo(cn));

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query=intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions=new SearchRecentSuggestions(context,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed()
    {
        finish();///////////////
        super.onBackPressed();
    }
}
