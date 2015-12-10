package com.dammi.dammi.activitydetails.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dammi.dammi.R;

/**
 * Created by trees on 12/4/15.
 */
public class RateReviewFragment extends Fragment
{

    private WebView wv1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.booking_form, container, false);
        wv1=(WebView)view.findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        triggerWeb();
        return view;
    }

    private class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    private void triggerWeb()
    {
        String url = "http://dammitravels.netai.net/view_reviews.php?a_id=1011";
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);
    }

}
