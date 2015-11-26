package com.nought.hellowebviewjs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Button mBtnJavaCallJs;
    private TextView mTextView;
    private WebAppInterface mWebAppInterface;

    private TextViewChanger mTextChanger = new TextViewChanger() {
        @Override
        public void changeText(final String arg) {
            /**
             * 官方说明文档：
             * Note: The object that is bound to your JavaScript runs in another thread and not in the thread
             * in which it was constructed.
             *
             * mWebAppInterface虽然是在UI线程创建的，但是bind到JS以后就是在另一条线程中运行的，因此刷新UI的时候需要注意
             */
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mTextView != null) {
                        mTextView.append("\n" + arg);
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mBtnJavaCallJs = (Button) findViewById(R.id.btn_call_js);
        mTextView = (TextView) findViewById(R.id.text);
        mWebAppInterface = new WebAppInterface(this);
        initWebView();
        initButton();
        initTextView();
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(mWebAppInterface, "Android"); // JS通过Interface调用Java
        mWebView.setWebViewClient(new HelloWebViewClient(mTextChanger)); // JS通过WebViewClient调用Java
        mWebView.loadUrl("file:///android_asset/hello.html");
    }

    private void initButton() {
        mBtnJavaCallJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 注意参数的传递需要符合JS的语法，用单引号或者反斜杠转义
                 */
                String js = "javascript:javaCallJS(\"Java called JS.\")";
                mWebView.loadUrl(js);
            }
        });
    }

    private void initTextView() {
        mWebAppInterface.setTextChanger(mTextChanger);
    }

    public interface TextViewChanger {
        void changeText(String arg);
    }

}
