package com.example.webeng;

import BaseClasses.BaseActivity;
import Fonts.FontManager;
import Webservices.LoginWebServices;
import models.Login;
import models.WeBengConstant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;

public class LoginActivity extends BaseActivity {
    private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";

    private LoginButton buttonLoginLogout;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private UiLifecycleHelper uiHelper;
    private TextView userid;
    private TextView password;
    private TextView webengg;
    private TextView newUser;
    LoginWebServices loginServices;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setScreenName(getResources().getString(R.string.login_screen));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        webengg = (TextView) findViewById(R.id.webengg);

        FontManager mfont = FontManager.getInstance();
        webengg.setTypeface(mfont.mUntralight);

        userid = (TextView) findViewById(R.id.userid);
        //userid.setTypeface(mfont.mUntralight);
        password = (TextView) findViewById(R.id.password);
        //password.setTypeface(mfont.mUntralight);

        newUser = (TextView) findViewById(R.id.SignUp);
        //newUser.setTypeface(mfont.mUntralight);
        btnLogin = (Button) findViewById(R.id.btnlogin);
        //btnLogin.setTypeface(mfont.mUntralight);
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

        btnLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    onLoginClick(btnLogin);
                }
                return false;
            }
        });

        buttonLoginLogout = (LoginButton)findViewById(R.id.buttonLoginLogout);
        buttonLoginLogout.setReadPermissions(Arrays.asList("email","basic_info"));
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session session = Session.getActiveSession();

        buttonLoginLogout.setSessionStatusCallback(statusCallback);

        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setPermissions(Arrays.asList("basic_info", "email")).setCallback(statusCallback));
            }
        }else
            updateView();

        //updateView();
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.webeng",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void onLoginClick(View view) {
        CharSequence user = userid.getText();
        CharSequence pass = password.getText();
        String host = getResources().getString(R.string.serverhost);
        turnOnProgressDialog(getResources().getString(R.string.login), getResources().getString(R.string.loggingToServer));
        new LoginAsyncTask().execute(host, user.toString(), pass.toString());

    }


    @Override
    public void onStart() {
        super.onStart();
        //Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Session.getActiveSession().removeCallback(statusCallback);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    private void updateView() {
        final Session session = Session.getActiveSession();
        if (session!=null&& session.isOpened()) {
            // If the session is open, make an API call to get user data
            // and define a new callback to handle the response
            Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    // If the response is successful
                    if (session == Session.getActiveSession()) {
                        if (user != null && user.asMap().get(WeBengConstant.EMAIL_KEY)!=null) {
                            getIntent().putExtra(WeBengConstant.FB_ID, user.getId());
                            getIntent().putExtra(WeBengConstant.EMAIL_KEY, user.getProperty(WeBengConstant.EMAIL_KEY).toString());
                            if(user.asMap().containsKey(WeBengConstant.GENDER))
                                getIntent().putExtra(WeBengConstant.GENDER, user.asMap().get(WeBengConstant.GENDER).toString());
                            getIntent().putExtra(WeBengConstant.ACCESS_TOKEN,session.getAccessToken());
                            getIntent().putExtra(WeBengConstant.FB_NAME,user.getFirstName()+" "+user.getLastName());
                            new LoginFBAsync().execute(getString(R.string.serverhost),user.getId(),session.getAccessToken(),user.getProperty(WeBengConstant.EMAIL_KEY).toString());
                            Log.d("FB",user.getId());
                            Log.d("FB",session.getAccessToken());
                        }
                    }else
                        turnOffProgressDialog();

                }
            });
            turnOnProgressDialog(getString(R.string.login),getString(R.string.loggingToServer));
            Request.executeBatchAsync(request);

        }
    }

    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    public void onNewUser(View view) {
        gotoActivity(UserCreate.class);
        finish();
    }

    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            updateView();
            Log.d("SESSION",state.toString());
        }
    }
    private class LoginAsyncTask extends AsyncTask<String, Boolean, Integer> {


        @Override
        protected Integer doInBackground(String... strings) {
            if(loginServices==null)
                loginServices = new LoginWebServices(strings[0]);

            Login logged = loginServices.Login(strings[1], strings[2]);
            if (logged != null && logged.getUserId() != null && logged.getToken() != null) {
                saveUser(logged);
                gotoActivity(MainActivity.class);

                finish();
                return 200;
            } else {
                return loginServices.getErrorCode();
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            turnOffProgressDialog();
            if (canHandleHttpCode(result))
                if(result==400||result==500)
                    alert(getString(R.string.msgErrorTitle),getString(R.string.msgUserPassInvalid));
                else
                    canHandleHttpCode(0);
        }
    }
    private class LoginFBAsync extends AsyncTask<String,Integer,Integer>{

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if(result==200){
                gotoActivity(MainActivity.class);
                finish();
            }else if(result==201){
                gotoActivity(UserCreate.class);
            }else{
                canHandleHttpCode(result);
            }
            turnOffProgressDialog();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            if(loginServices==null)
                loginServices = new LoginWebServices(strings[0]);
            Login logged=loginServices.loginFacebook(strings[1], strings[2], strings[3]);
            if (logged != null && logged.getUserId() != null && logged.getToken() != null) {
                saveUser(logged);
                return loginServices.getResponseCode();
            }
            return loginServices.getErrorCode();
        }
    }
    private void saveUser(Login session){
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor srp = sp.edit();
        srp.putString("userid", session.getUserId());
        srp.putString("token", session.getToken());
        srp.commit();
    }

}
