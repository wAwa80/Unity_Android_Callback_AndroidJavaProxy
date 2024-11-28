package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

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
//        启动 WebActivity 时不触发暂停，尝试以下方法避免 Unity 暂停：
//        在启动 WebActivity 的 Intent 中添加标志 FLAG_ACTIVITY_NO_ANIMATION 和 FLAG_ACTIVITY_NEW_TASK
        // 测试结果：下面的设置不生效，Unity 还是会暂停
        intentWeb.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK);
        unityActivity.startActivity(intentWeb);
        Log.e("showButtonActivity", "showButtonActivity  在启动 WebActivity 的 Intent 中添加标志 FLAG_ACTIVITY_NO_ANIMATION 和 FLAG_ACTIVITY_NEW_TASK ");
//        unityActivity.startActivity(intentBtn);
    }

    public static void closeButtonActivity() {
        WebActivity.closeActivity();
    }
    public static void showErrorCustomDialog(){
        WebActivity webActivity = WebActivity.getInstance();
        if (webActivity != null) {
            // 对 WebActivity 执行操作
            webActivity.showCustomDialog();
        }
    }
}

