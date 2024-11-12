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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

//import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends Activity {
    private com.tencent.smtt.sdk.WebView webView;
    private ImageView imageView;

    private ProgressBar progressBar;
    private TextView tvPro;
    private LinearLayout linearLayout;

    private static WebActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        super.onCreate(savedInstanceState);

        // Hide the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide the notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_web_view);

        // Set full screen
        setFullScreenMode();
        // Keep a reference to the instance
        instance = this;

        setStatusBar();

        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(WebActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                closeActivity();
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
//        // 设置要显示的图片
//        imageView.setImageResource(R.drawable.splash);
//        tvPro = findViewById(R.id.tv_pro);
//        tvPro.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        progressBar = findViewById(R.id.progress);
//        linearLayout = findViewById(R.id.ll_pro);
    }


    /**
     * 设置WebView
     */
    private void setWebView() {
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("shouldOverrideUrl", "postMessage  data==" + url);
                // 在这里检查 URL 请求，以确定 JavaScript 函数是否被调用

                // JavaScript 函数被调用
                // 在这里执行你的处理逻辑
//                Log.e("shouldOverrideUrl", "postMessage  data== 准备打开" );
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
                // 检查是否存在可处理该 Intent 的应用程序
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                    return true; // 返回 true 表示已经处理了 URL 请求
//                } else {
//                    // 如果没有可用的浏览器应用程序，你可以在这里添加备选方案，例如提示用户下载浏览器应用
//                }
                if(url.equals(ButtonPlugin.GetBackLobbyUrl())){
                    Log.e("shouldOverrideUrl", "back lobby  ==" + url);
                    closeActivity();
                    return true;
                }
                return false; // 返回 false 表示继续正常的 URL 处理
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // 在这里更新加载进度
//                progressBar.setProgress(progress);
//                tvPro.setText("getting info [ " + progress + "% ]");
                if (progress == 100) {
                    // 加载完成时隐藏进度条
//                    linearLayout.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                }
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//        settings.setLoadWithOverviewMode(true);  // 缩放至屏幕的大小
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        webView.addJavascriptInterface(new JsInterface(this, webView) , "jsBridge");
        settings.setJavaScriptEnabled(true); //使 WebView 支持 JS
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setSupportMultipleWindows(true);
        settings.setAllowFileAccess(true); // 允许文件访问
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true); // 自动加载图像
        settings.setBlockNetworkImage(false); // 不阻止网络图像加载
        settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 允许混合内容
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 使用默认缓存策略
        settings.setAppCacheEnabled(true); // 启用应用内缓存
        settings.setUseWideViewPort(true); // 允许使用广泛的视口
        settings.setLoadWithOverviewMode(true); // 适应屏幕大小
        settings.setDisplayZoomControls(false); // 不显示缩放控件
        settings.setBuiltInZoomControls(true); // 允许缩放
        settings.setSupportZoom(true);

        webView.loadUrl("http://192.168.6.229:8079/?isEncrypt=true&member_account=sss001&member_password=qqq111&game_id=10102&game_guest=false&server_url=http%3A%2F%2F192.168.6.128:9001");
        String url = ButtonPlugin.GetUrl();
        if (url != null){
            Log.e("WebActivity", "url not null");
            webView.loadUrl(url);
        }
        // 重新定义 JavaScript 函数的行为
//        webView.loadUrl("javascript:function DownSoft() { window.jsBridge?.postMessage('openWindow', 'https://ydbak02poi.poercrkjl.com/Rummyola_APK/Rummyola.apk') }");

        webView.evaluateJavascript("if (typeof WebAssembly === 'object') { console.log('WebAssembly is supported'); } else { console.log('WebAssembly is NOT supported'); }", null);
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

    private void setFullScreenMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Use WindowInsetsController for API level 30 and above
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                insetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            // Use SYSTEM_UI_FLAG for lower API levels
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
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
//            if (webView != null && webView.canGoBack()) {
//                closeActivity();
//            } else {
//                closeActivity();
//            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    /**
     * 用于处理其他活动返回的结果。当一个 Activity 启动另一个 Activity 并希望在它完成后接收结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * 监听触摸键
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // If the user touches outside the button, close the activity
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//            closeActivity();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public static void closeActivity() {
        if (instance != null) {
//            instance.webView = null;
//            instance.imageView = null;
//            instance.progressBar = null;
//            instance.tvPro = null;
//            instance.linearLayout = null;

            UnityCallback.getInstance().OnEventAct("backLobby");
            Log.e("WebActivity", "backLobby");
            instance.finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.loadUrl("about:blank");
            webView.onPause();
            webView.removeAllViews();
            webView.destroyDrawingCache();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}