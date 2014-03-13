package com.example.webeng;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Collection;
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
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private UiLifecycleHelper uiHelper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;
    BengModelItem bengModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        setContentView(R.layout.beng_submit_dialog);
        layoutForm=(LinearLayout)findViewById(R.id.layoutForm);
        layoutLoading=(LinearLayout)findViewById(R.id.layoutLoading);
        txtBengDescription=(TextView)findViewById(R.id.txtBengDescription);
        txtLocation=(TextView)findViewById(R.id.txtLocation);
        txtPlace=(TextView)findViewById(R.id.txtPlace);
        txtHowContact=(TextView)findViewById(R.id.txtHowContact);
        if (savedInstanceState != null) {
            pendingPublishReauthorization =
                    savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);
        }


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
        bengModel=model;
    }
    public void onDiscard(View view) {
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
                alert(getString(R.string.msgErrorTitle),getString(R.string.share_fb_error));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
                if(data.getCharSequence("com.facebook.platform.extra.COMPLETION_GESTURE").equals(FacebookDialog.COMPLETION_GESTURE_CANCEL))
                    alert(getString(R.string.msgErrorTitle), getString(R.string.share_fb_error));
                else
                    new BengSummitAsync().execute(getIntent().getStringExtra(WeBengConstant.BENGID_KEY), userid.toString(), token.toString());
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    public void onRegisteredBeng(View view) {

        //call share dialog.

        Session session = Session.getActiveSession();

        if (session != null){

            // Check for publish permissions
            List<String> permissions = session.getPermissions();
            if (!isSubsetOf(PERMISSIONS, permissions)) {
                pendingPublishReauthorization = true;
                Session.NewPermissionsRequest newPermissionsRequest = new Session
                        .NewPermissionsRequest(this, PERMISSIONS);
                session.requestNewPublishPermissions(newPermissionsRequest);
                return;
            }

            Bundle postParams = new Bundle();
            postParams.putString("name", getString(R.string.msg_post_name));
            postParams.putString("message", getString(R.string.msg_post_message));
            postParams.putString("caption", getString(R.string.msg_post_caption));
            postParams.putString("description",bengModel.getDescription());
            postParams.putString("link", getString(R.string.fanpageLink));
            postParams.putString("picture", bengModel.getThump());

            Request.Callback callback= new Request.Callback() {
                public void onCompleted(Response response) {

                    JSONObject graphResponse = response
                            .getGraphObject()
                            .getInnerJSONObject();
                    String postId = null;
                    try {
                        postId = graphResponse.getString("id");
                    } catch (JSONException e) {
                        Log.i("FB",
                                "JSON error "+ e.getMessage());
                    }
                    FacebookRequestError error = response.getError();
                    if (error != null) {
                        layoutForm.setVisibility(View.VISIBLE);
                        layoutLoading.setVisibility(View.GONE);
                        alert(getString(R.string.msgErrorTitle), getString(R.string.share_fb_error));
                    } else {
                        new BengSummitAsync().execute(getIntent().getStringExtra(WeBengConstant.BENGID_KEY), userid.toString(), token.toString());
                    }
                }

            };

            Request request = new Request(session, "me/feed", postParams,
                    HttpMethod.POST, callback);
            layoutForm.setVisibility(View.GONE);
            layoutLoading.setVisibility(View.VISIBLE);
            RequestAsyncTask task = new RequestAsyncTask(request);
            task.execute();
        }
    }


    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
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
//                alert(getString(R.string.inform), getString(R.string.beng_submit_success_msg));
                new BengRegisteredHelper(getBaseContext()).addRegistered(getIntent().getStringExtra(WeBengConstant.BENGID_KEY),new Date());
            }
            else if(bengServices.getErrorCode()!=200)
                alert(getString(R.string.inform),bengServices.getResponseErrorMessage().getMessage());
        }
    }
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.d("SESSION", state.toString());
            if (pendingPublishReauthorization &&
                    state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
                pendingPublishReauthorization = false;
                //publishStory();
            }
        }
    }
}