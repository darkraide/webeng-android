package com.example.webeng;

import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.support.v4.app.FragmentActivity;

import android.util.Log;

public class facebookactivity extends FragmentActivity {
	private MainFragment mainFragment;
	private static final String TAG = "MainFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
	}
	

	
}
