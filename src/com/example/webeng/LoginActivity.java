package com.example.webeng;

import BaseClasses.BaseActivity;
import Fonts.FontManager;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class LoginActivity extends BaseActivity{
	private TextView userid;
	private TextView password;
	private TextView webengg;
	private TextView newUser;
	FontManager font = FontManager.getInstance();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		

		Typeface light = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue Light.ttf");
		Typeface medium = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue Medium.ttf");
		Typeface bold = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue BoldMedium.otf");
		Typeface untralight = Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue UltraLight.ttf");
		font.prepareFont(light, medium, bold, untralight);
		
		webengg = (TextView)findViewById(R.id.webengg);
	
		FontManager mfont =	FontManager.getInstance();
		webengg.setTypeface(mfont.mUntralight);
		
		this.getActionBar().hide();
		userid = (TextView)findViewById(R.id.userid);
		userid.setTypeface(mfont.mUntralight);
		password = (TextView)findViewById(R.id.password);
		password.setTypeface(mfont.mUntralight);

        newUser=(TextView)findViewById(R.id.SignUp);
        newUser.setTypeface(mfont.mUntralight);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
