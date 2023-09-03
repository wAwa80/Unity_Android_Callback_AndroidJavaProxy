package com.uni.unitylibmodule;

import android.content.Context;
import android.util.Log;

public class UnityCallback implements ToUnityCallback {

    private Context mContext;
    private static UnityCallback instance = null;

    public UnityCallback(){
    }

    public static UnityCallback getInstance(){
        if (instance == null) {
            instance = new UnityCallback();
        }
        return instance;
    }

    private static ToUnityCallback _callback;
    public static void SetCallback(ToUnityCallback callback){
        _callback = callback;
//        Log.e("android SetCallback", "  == " );
    }

    @Override
    public void OnEventAct(String s) {
        if (_callback != null){
//            Log.e("android OnEventAct", " 1 == " + s );
            _callback.OnEventAct(s);
        }

//        Log.e("android OnEventAct", " 2 == " + s );
    }
}
