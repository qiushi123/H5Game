package com.duwuh5;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by mfp on 15-12-22.
 */
public class BrowserActivity extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener {
    @InjectView(R.id.mProgress)
    ProgressBar mProgress;

    @InjectView(R.id.webview)
    WebView mWebView;

    @InjectView(R.id.layout_back)
    RelativeLayout layout_back;

    @InjectView(R.id.img_browser_back)
    ImageView img_browser_back;

    @InjectView(R.id.img_browser_next)
    ImageView img_browser_next;


    private String url;

    private GestureDetector detector;
    private int flingWidth;


    private static final String TAG = BrowserActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.inject(this);
        layout_back.setOnClickListener(this);
        img_browser_back.setOnClickListener(this);
        img_browser_next.setOnClickListener(this);
        initweb();
        url = "file:///android_asset/index.html";
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }


    private void initweb() {
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.setWebViewClient(new InnerWebViewClient());
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                } else {
                    if (View.GONE == mProgress.getVisibility()) {
                        mProgress.setVisibility(View.VISIBLE);
                    }
                    mProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.img_browser_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }
                break;
            case R.id.img_browser_next:
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
                break;
        }
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        if (e2.getX() - e1.getX() > flingWidth && Math.abs(velocityX) > 200) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return false;
    }

    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            uichange();
        }
    }


    public void uichange() {
        if (mWebView.canGoBack()) {
            img_browser_back.setImageResource(R.drawable.icon_browser_back);
        } else {
            img_browser_back.setImageResource(R.drawable.icon_browser_unback);
        }
        if (mWebView.canGoForward()) {
            img_browser_next.setImageResource(R.drawable.icon_browser_next);
        } else {
            img_browser_next.setImageResource(R.drawable.icon_browser_unnext);
        }
    }

}
