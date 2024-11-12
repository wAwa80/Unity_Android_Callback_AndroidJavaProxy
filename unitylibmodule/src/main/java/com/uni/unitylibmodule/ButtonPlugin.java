package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.uni.utils.X5CorePreLoadService;

public class ButtonPlugin {

    private static Activity unityActivity;
    private static String url = null;
    private static String backLobbyUrl = null;
    public static void initialize(Activity activity) {
        Log.e("ButtonPlugin", "initialize");
        unityActivity = activity;
    }

    public static void showButton() {
        Log.e("ButtonPlugin", "showButton");

        Intent intent = new Intent(unityActivity, ButtonOverlayService.class);
        unityActivity.startService(intent);
    }

    public static void hideButton() {
        Log.e("ButtonPlugin", "hideButton");
        Intent intent = new Intent(unityActivity, ButtonOverlayService.class);
        unityActivity.stopService(intent);
    }

    public static void SetUrl(String _url, String _backLobbyUrl) {
        url = _url;
        backLobbyUrl = _backLobbyUrl;
    }

    public static String GetUrl() {
        return url;
    }

    public static String GetBackLobbyUrl() {
        return backLobbyUrl;
    }

    public static void showButtonActivity() {
//        Intent intentBtn = new Intent(unityActivity, ButtonActivity.class);
        Intent intentWeb = new Intent(unityActivity, WebActivity.class);
        unityActivity.startActivity(intentWeb);
//        unityActivity.startActivity(intentBtn);
    }

    public static void closeButtonActivity() {
        WebActivity.closeActivity();
    }
}

