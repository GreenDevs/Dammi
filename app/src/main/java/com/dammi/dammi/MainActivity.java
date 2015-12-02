package com.dammi.dammi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dammi.dammi.home.HomeAdapter;
import com.dammi.dammi.search.SearchableActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private Context context;
    private HomeAdapter homeAdapter;
    private Toolbar toolbar;
    private NavigationView mDrawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FloatingActionButton fab;


    // google plus login related variables.
    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;



    // Google places api related variables.
    private int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        init();
        initGplaceVariables();


        // Button click listeners
        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);

        initGplusApiClient();

    }



    private void initGplusApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }


private void initGplaceVariables()
{
    btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
    btnSignOut = (Button) findViewById(R.id.btn_sign_out);
    btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
    imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
    txtName = (TextView) findViewById(R.id.txtName);
    txtEmail = (TextView) findViewById(R.id.txtEmail);

}

    private void init()
    {
        //FOR TOOL BAR
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
                Intent intent = new Intent(context, SearchableActivity.class);
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


    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop()
    {
        ///THIS METHOD IS FOR CLEANING MEMORY OBTAINED FORM HOME SLIDE SHOW
        if(homeAdapter.getSliderLayout()!=null)
             homeAdapter.getSliderLayout().stopAutoCycle();

        super.onStop();
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }

    }





    ///////////////////////////////Google plus login ////////////////////////////////////////////////////////////////

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.nav_item_10)
        {
           // Toast.makeText(context,"item ten selected!!",Toast.LENGTH_SHORT).show();
            onItemTenButtonClicked();
            return true;
        }
        else
        {
            startActivity(new Intent(context, SearchableActivity.class));
            return true;
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent)
    {
        if (requestCode == RC_SIGN_IN)
        {
            if (responseCode != RESULT_OK)
            {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting())
            {
                mGoogleApiClient.connect();
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST
                && responseCode == Activity.RESULT_OK)
        {
            //do nothing.
        }
        else
        {
            super.onActivityResult(requestCode, responseCode, intent);
        }

    }

    @Override
    public void onConnected(Bundle bundle)
    {
        mSignInClicked = false;
       // Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        mGoogleApiClient.connect();
        updateUI(false);
    }


    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn)
    {
        if (isSignedIn)
        {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);

        }
        else
        {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
            case R.id.btn_sign_out:
                // Signout button clicked
                signOutFromGplus();
                break;
            case R.id.btn_revoke_access:
                // Revoke access button clicked
                revokeGplusAccess();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        if (!result.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress)
        {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked)
            {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    /**
     * Sign-in into google
     * */
    private void signInWithGplus()
    {
        if (!mGoogleApiClient.isConnecting())
        {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
    private void resolveSignInError()
    {
        if (mConnectionResult.hasResolution())
        {
            try
            {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            }
            catch (IntentSender.SendIntentException e)
            {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    /**
     * Fetching user's information name, email, profile pic
     * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                //String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

//                Toast.makeText(context, "Name: " + personName + ", plusProfile: "
//                        + personGooglePlusProfile + ", email: " + email
//                        + ", Image: " + personPhotoUrl, Toast.LENGTH_LONG).show();

                txtName.setText(personName);
                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls)
        {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try
            {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result)
        {
            bmImage.setImageBitmap(result);
        }
    }

    /**
     * Sign-out from google
     * */
    private void signOutFromGplus()
    {
        if (mGoogleApiClient.isConnected())
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess()
    {
        if (mGoogleApiClient.isConnected())
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }

                    });
        }
    }

    private void onItemTenButtonClicked()
    {
        try
        {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the intent by requesting a result,
            // identified by a request code.
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        }
        catch (GooglePlayServicesRepairableException e)
        {
            e.printStackTrace();
        }
        catch (GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }
    }
}
