package com.nought.hellowebviewjs;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebAppInterface {
    Context mContext;
    MainActivity.TextViewChanger mTextViewChanger;

    WebAppInterface(Context c) {
        mContext = c;
    }

    public void setTextChanger(MainActivity.TextViewChanger textChanger) {
        this.mTextViewChanger = textChanger;
    }

    /**
     * 如果 targetSdkVersion >=17，一定要加注解，否则JS无法调用这个方法
     */
    @JavascriptInterface
    public void jsCallJava(String arg) {
        if (mTextViewChanger != null) {
            mTextViewChanger.changeText(arg);
        }
    }
}
