package com.nought.hellowebviewjs;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HelloWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }
}
