package org.crosswalkproject.xwalkjsload;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
//import android.webkit.JavascriptInterface;
import android.widget.Button;

import org.xwalk.core.JavascriptInterface;
import org.xwalk.core.XWalkView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends Activity {
    private XWalkView mWebView;
    private List<String> list;
    private int mkeyCode;
    private Button disableJS;
    private String url = "file:///android_asset/index.html";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (XWalkView) findViewById(R.id.myXWalkView);
        initData();
        disableJS = (Button) findViewById(R.id.disablejs);
        disableJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.removeJavascriptInterface("javatojs");
                mWebView.reload(0);
            }
        });

//        WebSettings webSettings = mWebView.getSettings();
//        // 是否允许在webview中执行javascript
//        webSettings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new injectJavaScriptInterface(), "javatojs");
        //加载网页
        mWebView.load(url, null);

    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mkeyCode = keyCode;
        Log.i("AA", "keyCode=" + keyCode);
        mWebView.load("javascript: OnKeyUp()", null);
        return super.onKeyUp(keyCode, event);
    }


    void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("我是List中的第" + (i + 1) + "行");
        }
    }

    public class injectJavaScriptInterface {
        /**
         * 该方法将在js脚本中，通过window.javatojs.....()进行调用
         *
         */
        @JavascriptInterface
        public Object getObject(int index) {
            Log.i("A", "getObject");
            return list.get(index);
        }
        @JavascriptInterface
        public int getKeyCode(){
            return mkeyCode;
        }
        @JavascriptInterface
        public int getSize() {
            Log.i("A", "getSize = " + list.size());
            return list.size();
        }

        @JavascriptInterface
        public void Callfunction() {
            Log.i("A", "Callfunction");

            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.load("javascript: GetList()", null);
                }
            });
        }

        @JavascriptInterface
        public void printStr(String str) {
            Log.i("A", "GetList:" + str);
        }
    }
}