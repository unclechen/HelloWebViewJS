package com.nought.hellowebviewjs;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloWebViewClient extends WebViewClient {
    private static final String TAG = HelloWebViewClient.class.getSimpleName();
    private static final String PREFIX = "bridge://uncle.nought.com";
    private static final Pattern ARG_PATTERN = Pattern.compile(PREFIX + "\\?arg=(.*)");

    private MainActivity.TextViewChanger mTextViewChanger;

    public HelloWebViewClient(MainActivity.TextViewChanger textViewChanger) {
        mTextViewChanger = textViewChanger;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "Get params from JS: " + url);
        parseJSParams(url);
        return true;
    }

    private void parseJSParams(String url) {
        // 解析自定义参数
        if (url.startsWith(PREFIX)) {
            Matcher matcher = ARG_PATTERN.matcher(url);
            if (matcher.matches()) {
                mTextViewChanger.changeText(matcher.group(1));
            }
        }
    }
}
