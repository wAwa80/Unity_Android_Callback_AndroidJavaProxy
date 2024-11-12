package com.uni.unityapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.uni.unitylibmodule.ButtonActivity;
import com.uni.unitylibmodule.ButtonPlugin;
import com.uni.unitylibmodule.X5Initializer;

public class MainActivity extends AppCompatActivity {
    public interface ButtonPluginCallback {
        void initializeButtonPlugin();
        void showButtonPluginActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // try to callback after X5Initializer;
//        ButtonPlugin.initialize(this);
//        ButtonPlugin.showButtonActivity();

        X5Initializer.initX5Environment(this, MainActivity.this);
//          Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
//          startActivity(intent);

    }
}

