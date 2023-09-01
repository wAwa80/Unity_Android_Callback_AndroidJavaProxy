package com.uni.unitylibmodule;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import  com.unity3d.player.UnityPlayer;

public class Message {
    private static  String TAG = "JsInterface";
    private UnityLibModuleActivity mActivity;
    private WebView mWebView;
    public Message(UnityLibModuleActivity activity, WebView webView){
        mActivity = activity;
        mWebView = webView;
    }

    // 返回值
    @JavascriptInterface
    public void postMessage(String name, String data) {
        Log.e(TAG, "postMessage  name==" + name);
        Log.e(TAG, "postMessage  data==" + data);
        try {
            SendMsgToUnity(name, data);
            JSONObject jsonObject = new JSONObject(data);
            String url = jsonObject.getString("url");
            if ("openWindow".equals(name)) {
                mActivity.OpenNewView(url);
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWebView.loadUrl(url);
//                    }
//                });
            }
            Log.e(TAG, "postMessage  url==" + url);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String connecterName = "AFFunctionObj"; //通信物体
    private static final String methodName = "AFFunctionName";
    /**
     *
     * @param name 方法名
     * @param args  参数
     */
    //发送消息到Unity
    public static void SendMsgToUnity(String name, String args) {
        UnityPlayer.UnitySendMessage(connecterName, methodName, name + "_" +args);
    }


}
