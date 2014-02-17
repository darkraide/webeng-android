package com.example.webeng;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import BaseClasses.BaseActivity;

/**
 * Created by sangcu on 2/15/14.
 */
public class StartupActivity extends BaseActivity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getActionBar().hide();

        SharedPreferences sp=getSharedPreferences();

        String userid=sp.getString("userid",null);

        if(userid==null){
            gotoActivity(LoginActivity.class);
        }else
            gotoActivity(MainActivity.class);


        finish();
    }
}