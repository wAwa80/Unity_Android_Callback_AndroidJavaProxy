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
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView v, String u) {
                if (u.endsWith("Rummyola.apk")) {
                    // JavaScript 函数被调用
                    // 在这里执行你的处理逻辑
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(u));
                    // 检查是否存在可处理该 Intent 的应用程序
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        // 如果没有可用的浏览器应用程序，你可以在这里添加备选方案，例如提示用户下载浏览器应用
                    }
                    return true; // 返回 true 表示已经处理了 URL 请求
                }
                return false;// 返回 false 表示继续正常的 URL 处理
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
        //User Agent 设置： Google Play 页面可能还检查用户代理标头。你可以尝试设置 WebView 的用户代理标头，使其看起来更像标准浏览器。例如：
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
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
