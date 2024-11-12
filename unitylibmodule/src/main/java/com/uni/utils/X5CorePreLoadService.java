package com.uni.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

public class X5CorePreLoadService extends IntentService {
    private static final String TAG = X5CorePreLoadService.class.getSimpleName();
    private static Handler handler;

    public X5CorePreLoadService() {
        super(TAG);
    }

    public static void setHandler(Handler h) {
        handler = h;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initX5();
    }

    private void initX5() {
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setCoreMinVersion(QbSdk.CORE_VER_ENABLE_202207);

        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int stateCode) {
                if (stateCode == 0) {
                    Log.i(TAG, "下载成功");
                } else {
                    Log.e(TAG, "下载失败，状态码: " + stateCode);
                }
                handleCompletion();
            }

            @Override
            public void onInstallFinish(int stateCode) {
                Log.i(TAG, "安装完成，状态码: " + stateCode);
                handleCompletion();
            }

            @Override
            public void onDownloadProgress(int progress) {
                Log.i(TAG, "下载进度: " + progress);
            }
        });

        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);
        }

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
        @Override
        public void onViewInitFinished(boolean success) {
            Log.i(TAG, "X5 初始化完成: " + success);
        }

        @Override
        public void onCoreInitFinished() {
            Log.i(TAG, "X5 核心初始化完成");
            handleCompletion();
        }
    };

    private void handleCompletion() {
        if (handler != null) {
            handler.sendEmptyMessage(1);
        }
    }
}
