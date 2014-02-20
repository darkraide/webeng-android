package com.example.webeng;

import BaseClasses.BaseActivity;
import Fonts.FontManager;
import Webservices.LoginWebServices;
import models.Login;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends BaseActivity{
	private TextView userid;
	private TextView password;
	private TextView webengg;
	private TextView newUser;
    Button btnLogin;
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
		
		userid = (TextView)findViewById(R.id.userid);
		userid.setTypeface(mfont.mUntralight);
		password = (TextView)findViewById(R.id.password);
		password.setTypeface(mfont.mUntralight);

        newUser=(TextView)findViewById(R.id.SignUp);
        newUser.setTypeface(mfont.mUntralight);
        btnLogin=(Button)findViewById(R.id.btnlogin);
        btnLogin.setTypeface(mfont.mUntralight);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

    public void onLoginClick(View view) {
        CharSequence user=userid.getText();
        CharSequence pass=password.getText();
        String host=getResources().getString(R.string.serverhost);
        turnOnProgressDialog(getResources().getString( R.string.logging),getResources().getString(R.string.loggingToServer));
        new LoginAsyncTask().execute(host,user.toString(),pass.toString());

    }
    private class LoginAsyncTask extends AsyncTask<String,Boolean,Boolean>{



        @Override
        protected Boolean doInBackground(String... strings) {
            LoginWebServices loginServices=new LoginWebServices(strings[0]);

            Login logged=loginServices.Login(strings[1],strings[2]);
            if(logged!=null&& logged.getUserId()!=null&&logged.getToken()!=null){
                SharedPreferences sp=getSharedPreferences();
                SharedPreferences.Editor srp=sp.edit();
                srp.putString("userid",logged.getUserId());
                srp.putString("token",logged.getToken());
                srp.commit();
                gotoActivity(MainActivity.class);
                
                finish();
                return true;
            }
            else{
                return  false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            turnOffProgressDialog();
            if(!result)
                alert(getResources().getString(R.string.msgNetworkTitle) ,getResources().getString( R.string.msgNetworkConnectingError));

        }
    }
}
