package com.uni.unitylibmodule;

import android.app.Activity;
import android.content.Context;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.QbSdk.PreInitCallback;

public class X5Initializer {
    public static boolean isX5InitFinished = false;
    public static void initX5Environment(Context appContext, Activity activity) {
        QbSdk.initX5Environment(appContext, new PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                System.out.println(" X5 core initialization is finished.");
                // X5 core initialization is finished.

            }

            @Override
            public void onViewInitFinished(boolean isX5) {
                // Called when the pre-initialization is finished.
                if (isX5) {
                    System.out.println(" Now it's safe to create the WebView with X5 core, X5 core is used");
                } else {
                    System.out.println("X5 core isn't available; the system WebView will be used, System core is used");
                }
            }
        });
    }
}