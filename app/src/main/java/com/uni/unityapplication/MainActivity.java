package com.uni.unityapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.uni.unitylibmodule.ButtonActivity;
import com.uni.unitylibmodule.ButtonPlugin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonPlugin.initialize(this);
        ButtonPlugin.showButtonActivity();
//          Intent intent = new Intent(MainActivity.this, ButtonActivity.class);
//          startActivity(intent);
    }
}