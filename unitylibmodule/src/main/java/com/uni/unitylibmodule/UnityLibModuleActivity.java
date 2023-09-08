package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UnityLibModuleActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.unity_lib_layout);

        OpenSplash();
        Start();

    }

    private void OpenSplash(){
        // 设置要显示的图片
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.splash);

        tvPro = findViewById(R.id.tv_pro);
        tvPro.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        progressBar = findViewById(R.id.progress);
        linearLayout = findViewById(R.id.ll_pro);
    }



    //webview
    private WebView w;
    private ImageView imageView;
    private ProgressBar progressBar;
    private TextView tvPro;
    private LinearLayout linearLayout;

    public static String interS = "";
    private Context mContext;
    public void Start(){

        w = findViewById(R.id.webView);
        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 当WebView加载完成时，显示WebView
                Log.e("onPageFinished", " url " + url);
                linearLayout.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                w.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView v, String u) {
                return false;
            }
        });
        w.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // 在这里更新加载进度
                progressBar.setProgress(progress);
                tvPro.setText("getting info [ " + progress + "% ]");
                if (progress == 100) {
                    // 加载完成时隐藏进度条
                    linearLayout.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                }
            }
        });
        transparentNavigationBar();
        setWebView();
        w.loadUrl(interS);
//        startActivityForResult(new Intent(this, WebActivity.class), 1);
    }
    /**
     * 设置WebView
     */
    private void setWebView() {
        WebSettings settings = w.getSettings();
        settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);  // 缩放至屏幕的大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        w.addJavascriptInterface(new Message(this, w) , ConstHeader.GetJBHeader());
        settings.setJavaScriptEnabled(true); //使 WebView 支持 JS
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);

    }

    /**
     * 沉浸式导航栏
     */
    private void transparentNavigationBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.setNavigationBarContrastEnforced(false);
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(systemUiVisibility| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (w != null && w.canGoBack()) {
                w.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onActivityResult(int rq, int rc, Intent d) {
        super.onActivityResult(rq, rc, d);
        if (rc == RESULT_OK) {
            if (rq == 1) {
                if (w == null) {
                    return;
                }
                w.loadUrl(d.getStringExtra("loadUrl") );
                w.evaluateJavascript(ConstHeader.GetCloseWindowHeader(), new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
//                        Log.e("JsInterface", "closeGame");
                    }
                });
            }
        }
    }

    //
    public void OpenNewView(String data){
        final Uri u = Uri.parse(data);
        final Intent it = new Intent(Intent.ACTION_VIEW, u);
        startActivity(it);
    }

    //
    public void SendMsgToUnity(String data, String s){
//        Log.e("android SendMsgToUnity", data + " " + s);
        UnityCallback.getInstance().OnEventAct(data + "_" + s);
    }

}
