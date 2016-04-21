package com.duwuh5.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ArticleWebView extends WebView {



    public interface ArticleWebViewListener {
        void onScrollOverOneScreen();
        void onScrollLessThanOneScreen();
    }

    private ArticleWebViewListener mListener = null;
    private boolean overedPage = false;

    public ArticleWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ArticleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticleWebView(Context context) {
        super(context);
    }

    public void setArticleWebViewListener(ArticleWebViewListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.getHeight() > 0 && t > this.getHeight()/3 && mListener != null) {
            if (!overedPage) {
                overedPage = true;
                mListener.onScrollOverOneScreen();
            }
        }
        if (this.getHeight() > 0 && t < this.getHeight()/3 && mListener != null) {
            if (overedPage) {
                overedPage = false;
                mListener.onScrollLessThanOneScreen();
            }
        }
    }
}
