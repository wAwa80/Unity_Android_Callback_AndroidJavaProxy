package com.uni.unitylibmodule;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;


public class Message {
//    private static  String TAG = "JsInterface";
//    private UnityLibModuleActivity mActivity;
//    private WebView mWebView;
//    public Message(UnityLibModuleActivity activity, WebView webView){
//        mActivity = activity;
//        mWebView = webView;
//    }

    // 返回值
    @JavascriptInterface
    public void postMessage(String key, String value) {
//        Log.e(TAG, "postMessage  name==" + name);
//        Log.e(TAG, "postMessage  data==" + data);
        try {
//            mActivity.SendMsgToUnity(key, value);
            JSONObject jsonObject = new JSONObject(value);
            String url = jsonObject.getString("url");
            if (IsEqualsHeader(key)) {
//                mActivity.OpenNewView(url);
//                mActivity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWebView.loadUrl(url);
//                    }
//                });
            }
//            Log.e(TAG, "postMessage  url==" + url);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private  Boolean IsEqualsHeader(String name){
        String s = GetConstHeaderName() + GetConstWHeaderName();
        return s.equals(name);
    }

    public String GetConstHeaderName(){
        return ConstHeader.oHeader;
    }

    public String GetConstWHeaderName(){
        return ConstHeader.wHeader;
    }

    private static final String connecterName = "AFFunctionObj";
    private static final String methodName = "AFFunctionName";
    /**
     *
     * @param name 方法名
     * @param args  参数
     */
    public static void SendMsgToUnity(String name, String args) {
//        UnityPlayer.UnitySendMessage(connecterName, methodName, name + "_" +args);
    }


}
