package com.lmntrx.bodhi15;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    //WebView
    private WebView mWebview ;

    //Site Url
    String mURL="http://bodhiofficial.in/";

    //Context Variable
    public static Context CON;

    //result=isWorkinginternet?
    boolean isConnected;

    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.place_holder_activity);

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        CON=this;

        //Executing Async task
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isConnected=new NetworkStatusCheck().execute().get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();


        if(isConnected){ //if Network is available

            findViewById(R.id.placeHolder).setVisibility(View.GONE);

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



