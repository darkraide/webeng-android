package com.example.webeng;

import database.DataBaseHelper;
import models.UserInfor;
import http.loginUser;
import Fonts.FontManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Login extends Activity implements OnClickListener {
	private TextView userid;
	private TextView password;
	private TextView webengg;
	private TextView signup;
	private Button btnSignin;
	private Context mcontext;
	private String url = "http://webenggg.herokuapp.com/user/login";
	FontManager font = FontManager.getInstance();
	private static String USER = "Userinfo";
	private static String USERNAME = "username";
	private static String PASSWORD = "password";
	private String Username;
	private String Password;
	SharedPreferences mySharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		mcontext = this.getApplicationContext();
		
		mySharedPreferences = getSharedPreferences(USER,Login.MODE_PRIVATE);
		
	
		
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
		signup = (TextView)findViewById(R.id.SignUp);
		signup.setTypeface(mfont.mUntralight);
		btnSignin = (Button)findViewById(R.id.btnlogin);
		btnSignin.setTypeface(mfont.mUntralight);
		btnSignin.setOnClickListener(this);
		
		if (mySharedPreferences != null &&
				mySharedPreferences.contains(USERNAME)) {
				//object and key found, show all saved values
			loadUserinfo();
				}
		
	}

	private void saveUserinfo(){
		SharedPreferences.Editor myEditor = mySharedPreferences.edit();
		
		
		myEditor.putString(USERNAME, userid.getText().toString());
		myEditor.putString(PASSWORD, password.getText().toString());
		
		myEditor.commit();
	}
	private void loadUserinfo(){
		String user,passWord;
		user = mySharedPreferences.getString(USERNAME, "name");
		passWord = mySharedPreferences.getString(PASSWORD, "password");
		userid.setText(user);
		password.setText(passWord);
	}
	private void GetUserinfo(){
		Username = userid.getText().toString();
		Password = password.getText().toString();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	public static DataBaseHelper hepler;
	public class login extends AsyncTask<String,Long,Boolean>{

		@Override
		protected Boolean doInBackground(String... arg0) {
			loginUser login = new  loginUser(mcontext,url,Username,Password);
			UserInfor user = (UserInfor) login.valuereturn();
			if(user != null){
				hepler	 = new DataBaseHelper(mcontext);
			hepler.openDataBase();
			hepler.createTable();
			hepler.updateUser(user.getUserid(), user.getToken());
			String[] str = hepler.getUser();
			Log.v("doingbackground", "userid = "+str[0] +"\n token = "+ str[1]);
			return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(result == true){
				 Intent homeintent = new Intent(Login.this,
						 MainActivity.class);
				startActivity(homeintent);
				saveUserinfo();
			}
			else{
				
			}
			super.onPostExecute(result);
		}
		
	}
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id){
		case R.id.btnlogin:
			GetUserinfo();
			login Login = new login();
			Login.execute();
			//Login.cancel(true);
			break;
		}
	}
}
