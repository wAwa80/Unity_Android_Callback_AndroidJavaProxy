package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

public class MainActivity extends UnityPlayerActivity {

    private Context mContext;
    private static MainActivity mMainActivity = null;

    public MainActivity(Context context) {
        this.mContext = context;
    }


    public static MainActivity getInstance(Context context){
        if (mMainActivity == null) {
            mMainActivity = new MainActivity(context);
        }
        return mMainActivity;
    }
    //Unity中会调用这个方法，从而开打WebView
    public void StartInternal(String u)
    {
//        printLog("Welcome");
//        printLog(url);
        UnityLibModuleActivity.interS = u;
//        Log.e("StartWebView", "StartWebView  url ==" + url);

        Intent intent = new Intent(this.mContext, UnityLibModuleActivity.class);
        this.mContext.startActivity(intent);
    }

//    public boolean showToast(String content){
//        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
//        return true;
//    }
//
//    private void printLog(String s){
//        //Log.d("vivian",s);
//        showToast(s);
//    }

    //------------------------------------

}