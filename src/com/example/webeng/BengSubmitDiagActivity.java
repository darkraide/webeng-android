package com.example.webeng;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import BaseClasses.BaseActivity;
import Webservices.BengWebServices;
import database.BengRegisteredHelper;
import models.BengModelItem;
import models.WeBengConstant;
import models.location;

/**
 * Created by sangcu on 2/23/14.
 */
public class BengSubmitDiagActivity extends BaseActivity {
    LinearLayout layoutForm;
    LinearLayout layoutLoading;
    BengWebServices bengServices;

    //local controls
    TextView txtBengDescription;
    TextView txtLocation;
    TextView txtPlace;
    TextView txtHowContact;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beng_submit_dialog);
        layoutForm=(LinearLayout)findViewById(R.id.layoutForm);
        layoutLoading=(LinearLayout)findViewById(R.id.layoutLoading);
        txtBengDescription=(TextView)findViewById(R.id.txtBengDescription);
        txtLocation=(TextView)findViewById(R.id.txtLocation);
        txtPlace=(TextView)findViewById(R.id.txtPlace);
        txtHowContact=(TextView)findViewById(R.id.txtHowContact);



        bengServices=new BengWebServices(getResources().getString(R.string.serverhost));
        new BengAsync().execute(userid.toString(),token.toString());

    }
    private void updateValueOnForm(BengModelItem model){
        txtBengDescription.setText(model.getDescription());
        location[] location=model.getLocation();
        StringBuilder builder=new StringBuilder();
        if(location!=null){
            for (int i=0;i<location.length;i++){
                builder.append(location[i].getName());
                if(i<location.length-1)
                    builder.append(", ");
            }
        }
        txtLocation.setText(builder.toString());
        txtPlace.setText(model.getAddress());
        txtHowContact.setText(model.getContact());
    }
    public void onDiscard(View view) {
        finish();
    }

    public void onRegisteredBeng(View view) {

        new BengSummitAsync().execute(getIntent().getStringExtra(WeBengConstant.BENGID_KEY),userid.toString(),token.toString());
    }

    private class BengAsync extends AsyncTask<String,Void,BengModelItem>{

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            layoutForm.setVisibility(View.GONE);
            layoutLoading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected BengModelItem doInBackground(String... params) {
            return bengServices.getBeng(getIntent().getStringExtra(WeBengConstant.BENGID_KEY), params[0].toString(), params[1].toString());
        }
        @Override
        protected void onPostExecute(BengModelItem model) {
            super.onPostExecute(model);
            if(model==null)
            {
                if(canHandleHttpCode(bengServices.getErrorCode())){
                    //we need to handle status code
                    canHandleHttpCode(0);
                    finish();
                }
            }else{
                layoutForm.setVisibility(View.VISIBLE);
                layoutLoading.setVisibility(View.GONE);
                updateValueOnForm(model);
            }
        }

    }

    private class BengSummitAsync extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPreExecute(){
            layoutForm.setVisibility(View.GONE);
            layoutLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return bengServices.submitBeng(strings[0], strings[1], strings[2]);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            layoutForm.setVisibility(View.VISIBLE);
            layoutLoading.setVisibility(View.GONE);
            if(result){
                finish();
                alert(getString(R.string.inform), getString(R.string.beng_submit_success_msg));
                new BengRegisteredHelper(getBaseContext()).addRegistered(getIntent().getStringExtra(WeBengConstant.BENGID_KEY),new Date());
            }
            else if(bengServices.getErrorCode()!=200)
                alert(getString(R.string.inform),bengServices.getResponseErrorMessage().getMessage());
        }
    }
}