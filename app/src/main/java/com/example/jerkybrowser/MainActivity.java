package com.example.jerkybrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
 EditText urlInput;
 ImageView clearUrl;
 WebView webView;
 ProgressBar progressBar;

 ImageView webBack, webForward,webRefresh, webShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

progressBar= new ProgressBar(MainActivity.this);
//progressBar = findViewById(R.id.progress_bar);
        urlInput=findViewById(R.id.url_input);
        clearUrl=findViewById(R.id.clear_icon);
        webView=findViewById(R.id.web_view);
        webBack=findViewById(R.id.web_back);
        webForward=findViewById(R.id.web_forward);
        webRefresh=findViewById(R.id.web_refresh);
        webShare= findViewById(R.id.web_share);
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
   //     webSettings.getBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webView.setWebViewClient(new MyWebViewClient());









webBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (webView.canGoBack()){
            webView.goBack();
        }

    }
});


webForward.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(webView.canGoForward()){
            webView.goForward();
        }
    }
});


webRefresh.setOnClickListener((new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        webView.reload();
    }
}));

webShare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(Intent.ACTION_VIEW);
        intent.setAction(Intent.ACTION_SEND);
        intent .putExtra(Intent.EXTRA_TEXT,webView.getUrl());
        intent.setType("text/plain");
        startActivity(intent);




    }
});








        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

            }
        });








        loadMyUrl("google.com");
        urlInput.setOnEditorActionListener((TextView v, int i, KeyEvent event) -> {
            if (i== EditorInfo.IME_ACTION_GO || i == EditorInfo.IME_ACTION_DONE){
                InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(urlInput.getWindowToken(),0);
                loadMyUrl(urlInput.getText().toString());
                return true;

            }
            return false;
        });
      clearUrl .setOnClickListener(v -> urlInput.setText(" "));
    }















    void loadMyUrl(String url){
        boolean matchUrl= Patterns.WEB_URL.matcher(url).matches();
        if (matchUrl){
            webView .loadUrl(url);

        }else{
            webView.loadUrl("google.com/search?q="+url);

        }
    }








    @Override
    public void onBackPressed() {

        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }







    class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
           return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            urlInput.setText(webView.getUrl());
            progressBar.setVisibility(view.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.INVISIBLE);
        }
    }




}