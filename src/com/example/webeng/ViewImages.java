package com.example.webeng;



import Adapter.ImageAdapter;
import BaseClasses.BaseActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ViewImages extends BaseActivity {
	private static final String PhOTOS = "photos"; 
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.viewimage);

	    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		Intent intent = getIntent();
		String[] Photos = intent.getStringArrayExtra(PhOTOS);
	    ImageAdapter adapter = new ImageAdapter(this,Photos);
	    viewPager.setAdapter(adapter);
	  }
}
