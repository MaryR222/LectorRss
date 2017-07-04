package com.example.rss.rssapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mrezapue on 3/7/17.
 */

public class DatailActivity extends AppCompatActivity

    {


        private static final String TAG = DatailActivity.class.getSimpleName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);




        // Recover url
        String urlExtra = getIntent().getStringExtra("url-extra");

        // Get WebView
        WebView webview = (WebView)findViewById(R.id.webview);

        // HEnable Javascript in rendering
        webview.getSettings().setJavaScriptEnabled(true);

        // Cast locally
        webview.setWebViewClient(new WebViewClient());

        // Load the content of the url
        webview.loadUrl(urlExtra);


    }

    }
