package com.uni.unitylibmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
    public void StartWebView(String url)
    {
        printLog("Welcome");
        printLog(url);
        UnityLibModuleActivity.openUrl = url;
        Log.e("StartWebView", "StartWebView  url ==" + url);

        Intent intent = new Intent(this.mContext, UnityLibModuleActivity.class);
        this.mContext.startActivity(intent);
    }

    public boolean showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
        return true;
    }

    private void printLog(String s){
        //Log.d("vivian",s);
        showToast(s);
    }
}