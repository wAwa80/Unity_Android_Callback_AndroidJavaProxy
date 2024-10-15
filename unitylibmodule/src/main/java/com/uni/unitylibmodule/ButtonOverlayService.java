package com.uni.unitylibmodule;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class ButtonOverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("onCreate", "isSuccess");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        overlayView = LayoutInflater.from(this).inflate(R.layout.button_layout, null);

        Button myButton = overlayView.findViewById(R.id.myButton);
        myButton.setOnClickListener(view ->
                Toast.makeText(ButtonOverlayService.this, "Button Clicked!", Toast.LENGTH_SHORT).show()
        );

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // For Android O and above
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FORMAT_CHANGED
        );

//        params.gravity = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        windowManager.addView(overlayView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
