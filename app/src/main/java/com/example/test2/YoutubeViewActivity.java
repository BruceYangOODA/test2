package com.example.test2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class YoutubeViewActivity extends Activity {
    WebView webview;
    EditText txtUrl;
    Button btnGoView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeview);
        buildView();
    }

    private void buildView(){
        txtUrl = (EditText) findViewById(R.id.txtUrl);
        txtUrl.setText("http://www.youtube.com");
        btnGoView = (Button) findViewById(R.id.btnGoWeb);
        btnGoView.setOnClickListener(btnGoViewListener);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(txtUrl.getText().toString());
        webview.setWebViewClient(new YoutubeViewActivity.HelloWebViewClient());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("test", keyCode+"-------");
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            Log.i("test", keyCode+"+++++++++++++++++");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private OnClickListener btnGoViewListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl(txtUrl.getText().toString());
            webview.setWebViewClient(new YoutubeViewActivity.HelloWebViewClient());
        }
    };


    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
