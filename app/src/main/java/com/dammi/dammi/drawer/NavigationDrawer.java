package com.dammi.dammi.drawer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dammi.dammi.R;
import com.dammi.dammi.search.SearchableActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;

/**
 * Created by trees on 12/3/15.
 */
public class NavigationDrawer extends Fragment implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private NavigationView navigView;
    private Activity activity;

    // google plus login related variables.

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "NavigationDrawer";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private ConnectionResult mConnectionResult;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;



    // Google places api related variable.
    private int PLACE_PICKER_REQUEST = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        navigView=(NavigationView)view.findViewById(R.id.main_drawer);

        // Inflating nav_header layout on top of navigation drawer layout
        View temp=getActivity().getLayoutInflater().inflate(R.layout.nav_header,navigView,false);
        navigView.addHeaderView(temp);
        imgProfilePic= (ImageView) temp.findViewById(R.id.imgProfilePic);
        txtName= (TextView) temp.findViewById(R.id.txtName);
        txtEmail= (TextView) temp.findViewById(R.id.txtEmail);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        navigView.setNavigationItemSelectedListener(this);

        //Build Google api client
        initGplusApiClient();
    }

    public void setNavig(DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(activity, drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    //google plus enabled login code
    private synchronized void initGplusApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();
        //connect GoogleApiClient
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        //disconnect GoogleApiClient
        if (mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
    }

    //onConnectionFailed() was started with startIntentSenderForResult and the code RC_SIGN_IN,
   // we can capture the result inside Activity.onActivityResult.
    @Override
    public void  onActivityResult(int requestCode, int responseCode,
                                    Intent intent)
    {
        if (requestCode == RC_SIGN_IN)
        {
            if (responseCode != Activity.RESULT_OK)
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

//    Called when items inside navigation drawer is clicked.
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.login:
            //called when signIn Button is clicked
                signInWithGPlus();
                break;

            case R.id.logout:
                //called when sign out button is clicked
                signOutWithGPlus();
                break;

            case R.id.nearby:
                //called when Nearby button is clicked
                showNearByPlaces();
                break;

            default:
                startActivity(new Intent(activity, SearchableActivity.class));
                return true;
        }
        return true;
    }

// Sign in button clicked.
    private void signInWithGPlus(){
        if (!mGoogleApiClient.isConnecting())
        {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    //Sign out button clicked.
    private void signOutWithGPlus(){
        if (mGoogleApiClient.isConnected())
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        txtName.setText(activity.getResources().getString(R.string.user_name));
        txtEmail.setText(activity.getResources().getString(R.string.user_email));
        imgProfilePic.setImageResource(R.drawable.higenic);
    }


    //Nearby button clicked.
    private void showNearByPlaces(){
        try
        {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(activity);
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
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


//on the successfull connection onConnected is called
    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        getProfileInformation();
    }

    private void getProfileInformation()
    {
        try
        {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null)
            {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);

                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                txtName.setText(personName);
                txtEmail.setText(email);

                Log.d(TAG,"person Name="+personName+"  Email="+email);


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
                Toast.makeText(getActivity(), "Person information is null", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


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

//    called when the connection is suspended
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


//When the GoogleApiClient object is unable to establish a connection onConnectionFailed() is called
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution())
        {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), activity,
                    0).show();
            return;
        }

        if (!mIntentInProgress)
        {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked)
            {

                resolveSignInError();
            }
        }

    }

//Used for resolving errors during signIn
    private void resolveSignInError()
    {
        if (mConnectionResult.hasResolution())
        {
            try
            {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(activity, RC_SIGN_IN);
            }
            catch (IntentSender.SendIntentException e)
            {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
}
