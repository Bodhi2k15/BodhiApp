package com.lmntrx.bodhi15;

import android.app.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    //WebView
    private WebView mWebview ;

    //Site Url
    String mURL="http://bodhiofficial.in/";

    //Context Variable
    public static Context CON;

    //result=isWorkinginternet?
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CON=this;

        //Executing Async task
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result=new NetworkStatusCheck().execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();


        if(result){ //Network Availability Check

            mWebview  = new WebView(this);

            mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

            final Activity activity = this;

            mWebview.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
                }
            });

            mWebview.loadUrl(mURL);
            setContentView(mWebview);

        }
        else{

            setContentView(R.layout.notconnected);
        }
    }
}



