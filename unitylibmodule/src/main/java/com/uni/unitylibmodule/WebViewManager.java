package com.uni.unitylibmodule;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebViewManager {

    private static WebViewManager instance;
    private WebView webView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView tvPro;
    private FrameLayout container;

    private Activity activity;

    private WebViewManager(Activity activity) {
        this.activity = activity;
        WebView.setWebContentsDebuggingEnabled(true);
        initWebView();
    }

    public static WebViewManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new WebViewManager(activity);
        }
        return instance;
    }

    /**
     * 初始化 WebView
     */
    private void initWebView() {
        activity.runOnUiThread(() -> {
            if (container == null) {
                container = new FrameLayout(activity);
                activity.addContentView(container, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                ));
                container.setVisibility(FrameLayout.GONE);
            }

            if (webView == null) {
                webView = new WebView(activity);
                container.addView(webView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));

                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setSupportZoom(true);
                settings.setDomStorageEnabled(true);
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.d("WebViewManager", "URL loading: " + url);
                        if (url.equals(ButtonPlugin.GetBackLobbyUrl())) {
                            Log.d("WebViewManager", "Back lobby triggered.");
                            closeWebView();
                            return true;
                        }
                        return false;
                    }
                });

                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                        Log.d("WebViewManager", "Progress: " + progress);
                        if (progress == 100) {
                            if (imageView != null) {
                                imageView.setVisibility(FrameLayout.GONE);
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 加载 URL
     */
    public void loadUrl(String url) {
        activity.runOnUiThread(() -> {
            if (webView != null) {
                webView.loadUrl(url);
                container.setVisibility(FrameLayout.VISIBLE);
            }
        });
    }

    /**
     * 显示 WebView
     */
    public void showWebView() {
        activity.runOnUiThread(() -> {
            if (container != null) {
                container.setVisibility(FrameLayout.VISIBLE);
            }
        });
    }

    /**
     * 隐藏 WebView
     */
    public void hideWebView() {
        activity.runOnUiThread(() -> {
            if (container != null) {
                container.setVisibility(FrameLayout.GONE);
            }
        });
    }

    /**
     * 销毁 WebView
     */
    public void destroyWebView() {
        activity.runOnUiThread(() -> {
            if (webView != null) {
                webView.clearHistory();
                webView.clearCache(true);
                webView.loadUrl("about:blank");
                webView.onPause();
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            }
            if (container != null) {
                container.removeAllViews();
                container = null;
            }
        });
    }

    /**
     * 关闭 WebView
     */
    public void closeWebView() {
        hideWebView();
        UnityCallback.getInstance().OnEventAct("backLobby");
        Log.d("WebViewManager", "WebView closed.");
    }
}
