package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends Activity {
    private WebView webView;
    private ImageView imageView;

    private ProgressBar progressBar;
    private TextView tvPro;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBar();
        setContentView(R.layout.activity_web_view);

        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WebActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        openSplash();
        setWebView();
    }

    /**
     * 打开loading图
     */
    private void openSplash(){
        imageView = findViewById(R.id.imageView);
        // 设置要显示的图片
        imageView.setImageResource(R.drawable.splash);
        tvPro = findViewById(R.id.tv_pro);
        tvPro.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        progressBar = findViewById(R.id.progress);
        linearLayout = findViewById(R.id.ll_pro);
    }


    /**
     * 设置WebView
     */
    private void setWebView() {
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("shouldOverrideUrl", "postMessage  data==" + url);
                // 在这里检查 URL 请求，以确定 JavaScript 函数是否被调用

                if (url.endsWith("Rummyola.apk")) {
                    // JavaScript 函数被调用
                    // 在这里执行你的处理逻辑
                    Log.e("shouldOverrideUrl", "postMessage  data== 准备打开" );
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    // 检查是否存在可处理该 Intent 的应用程序
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        // 如果没有可用的浏览器应用程序，你可以在这里添加备选方案，例如提示用户下载浏览器应用
                    }
                    return true; // 返回 true 表示已经处理了 URL 请求
                }
                return false; // 返回 false 表示继续正常的 URL 处理
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
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

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);  // 缩放至屏幕的大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        webView.addJavascriptInterface(new JsInterface(this, webView) , "jsBridge");
        settings.setJavaScriptEnabled(true); //使 WebView 支持 JS
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setSupportMultipleWindows(true);
        settings.setDomStorageEnabled(true);
//        webView.loadUrl("https://www.tat.bet");
        webView.loadUrl("https://huidu.bet/game/gamesUrl?id=898bd5bf-dac0-48e4-808b-8464aa480f3c");
        // 重新定义 JavaScript 函数的行为
//        webView.loadUrl("javascript:function DownSoft() { window.jsBridge?.postMessage('openWindow', 'https://ydbak02poi.poercrkjl.com/Rummyola_APK/Rummyola.apk') }");


        // https://betttt.com/
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(attributes);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
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
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (webView == null) {
                    return;
                }
                webView.loadUrl(data.getStringExtra("loadUrl"));
                webView.evaluateJavascript("javascript:window.closeGame()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.e("JsInterface", "closeGame");
                    }
                });
            }
        }
    }
}