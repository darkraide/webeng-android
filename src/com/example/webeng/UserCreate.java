package com.example.webeng;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

import Adapter.LocationCheckAdapter;
import BaseClasses.BaseActivity;
import Webservices.LocationWebServices;
import Webservices.LoginWebServices;
import models.UserModel;
import models.WeBengConstant;
import models.location;

/**
 * Created by sangcu on 2/13/14.
 */
public class UserCreate extends BaseActivity {
    private ViewFlipper viewFlipper;
    private float lastX;
    private String email;
    private String password;
    private String gender;
    private String fbid;
    private String fbtoken;
    private String fbName;
    private ListView lvLocations;
     Button btnNext;
    Button btnDone;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnDone = (Button) findViewById(R.id.btnDone);
        lvLocations=(ListView)findViewById(R.id.lvLocations);

        viewFlipper.setInAnimation(this, R.anim.in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.out_to_left);

        email=getIntent().getStringExtra(WeBengConstant.EMAIL_KEY);
        gender=getIntent().getStringExtra(WeBengConstant.GENDER);
        fbid=getIntent().getStringExtra(WeBengConstant.FB_ID);
        fbtoken=getIntent().getStringExtra(WeBengConstant.ACCESS_TOKEN);
        fbName=getIntent().getStringExtra(WeBengConstant.FB_NAME);
        if(email!=null&&fbid!=null){
            viewFlipper.removeViewAt(0);
            if(gender!=null)
                viewFlipper.removeViewAt(0);
        }
        updateButtonStatus();
        new LocationAsync().execute();

    }
    private void updateButtonStatus(){
        if (viewFlipper.getChildCount()==1 || viewFlipper.getDisplayedChild() == 1) {
            btnNext.setVisibility(View.GONE);
            btnDone.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.GONE);
        }
        viewFlipper.showNext();
    }
    private Integer[] getLocations(){
        List<Integer> result=new ArrayList<Integer>();
        for(int i=0;i<lvLocations.getAdapter().getCount();i++){
            location l=(location)lvLocations.getAdapter().getItem(i);
            if(l!=null&&l.isChecked())
                result.add(l.getKey());
        }
        Integer[] s=new Integer[0];
        return result.toArray(s);
    }
    public void onDone(View view) {
        UserModel user=new UserModel();
        user.setEmail(email);
        user.setFbId(fbid);
        user.setFbToken(fbtoken);
        user.setSex(gender);
        user.setLocations(getLocations());
        user.setName(fbName);
        user.setPassword(password);

        if(email==null||email.isEmpty())
            viewFlipper.setDisplayedChild(0);
        else if(gender==null || gender.isEmpty())
            viewFlipper.setDisplayedChild(1);
        else if(getLocations()==null||getLocations().length==0)
            viewFlipper.setDisplayedChild(2);
        else {
            if (email != null && gender != null && getLocations() != null) {
                turnOnProgressDialog(getString(R.string.login), getString(R.string.loggingToServer));
                new UserAsync().execute(user);
            } else {
                alert(getString(R.string.msg_data_invalid_title), getString(R.string.msg_data_invalid_message));
            }
        }
    }

    public void onNext(View view) {
        updateButtonStatus();
    }

    private class LocationAsync extends AsyncTask<Void,Void,List<location>>{

        @Override
        protected List<location> doInBackground(Void... voids) {
            return new LocationWebServices(getString(R.string.serverhost)).getLocations();
        }
        @Override
        protected void onPostExecute(List<location> result){
            if(result!=null && result.size()>0){
                LocationCheckAdapter adapter = new LocationCheckAdapter(getBaseContext(),result);
                lvLocations.setAdapter(adapter);
            }
        }
    }
    private class UserAsync extends AsyncTask<UserModel,UserModel,Integer>{

        @Override
        protected Integer doInBackground(UserModel... users) {
            LoginWebServices service=new LoginWebServices(getString(R.string.serverhost));
            Boolean result=false;
            if(users[0].getFbToken()==null){
                result=service.registerUser(users[0]);
            }else
                result=service.updateUserInfo(users[0],userid.toString(),token.toString());

            if(result)
                return service.getResponseCode();

            return service.getErrorCode();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            turnOffProgressDialog();
            if(result==200){
                gotoActivity(MainActivity.class);
                finish();
            }else
                if(canHandleHttpCode(result)){
                    alert(getString(R.string.msgErrorTitle),getString(R.string.something_wrong));
                }

        }
    }
}