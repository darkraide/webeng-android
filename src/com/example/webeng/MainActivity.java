package com.example.webeng;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import Webservices.LoginWebServices;
import database.BengRegisteredHelper;
import models.BengRegistered;
import models.UserModel;
import models.WeBengConstant;
import widget.PullAndLoadListView;
import widget.PullToRefreshListView;

import Adapter.BengItemAdapter;
import BaseClasses.BaseActivity;
import Fonts.FontManager;
import Webservices.BengWebServices;

import models.BengModelItem;

public class MainActivity extends BaseActivity implements OnItemClickListener {

    private ImageView imgLoading;
    private static List<BengModelItem> items;
    private static Context context;
    private static ProgressBar progressBar;
    FontManager font = FontManager.getInstance();
    BengItemAdapter bengAdapter;
    BengWebServices bws;

    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private UiLifecycleHelper uiHelper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
    private boolean pendingPublishReauthorization = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setScreenName(getResources().getString(R.string.main_screen));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);
        progressBar = (ProgressBar) findViewById(R.id.progressGetItem);

        imgLoading = (ImageView) findViewById(R.id.imgTapToLoad);

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_menu, null);

        //final TextView title = ((TextView) v.findViewById(R.id.titleActivity));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        lp.gravity = Gravity.LEFT;

        TextView t=(TextView)v.findViewById(R.id.txtActionBarTitle);
        t.setTypeface(font.mLight);

        v.setLayoutParams(lp);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(v);

        items = new ArrayList<BengModelItem>();
        // font

        context = this.getApplicationContext();

        getListView().setOnItemClickListener(this);
        getListView().setEmptyView(findViewById(R.id.emptyList));

        if (userid == null || token == null) {
            gotoActivity(LoginActivity.class);
            finish();
            return;
        }

        final BengAsyncTask task = new BengAsyncTask();
        task.execute(userid.toString(), token.toString());

        // Set a listener to be invoked when the list should be refreshed.
        getListView()
                .setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

                    public void onRefresh() {
                        // Do work to refresh the list here.
                        new PullDownAsyncTask().execute(userid.toString(), token.toString());
                    }
                });
        getListView()
                .setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {

                    public void onLoadMore() {
                        // Do the work to load more items at the end of list
                        // here
                        new LoadMoreAsyncTask().execute(userid.toString(), token.toString());
                    }
                });
        getListView().setDrawSelectorOnTop(true);

        checkIntentData();
        //check submitted
        new BengSubmittedAsync().execute();
        //check user profile
        new UserCheckerAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(bengAdapter!=null){
            bengAdapter.updateBengRegistered();
            bengAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        checkIntentData();

    }

    private void checkIntentData(){
        Intent intent= getIntent();
        String bengid=intent.getStringExtra(WeBengConstant.BENGID_KEY);
        if(bengid!=null){
            gotoActivity(BengSubmitDiagActivity.class);
        }
    }
    private PullAndLoadListView getListView() {
        return (PullAndLoadListView) findViewById(R.id.ListBeng);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        Toast.makeText(getApplicationContext(), "create benggg",
                Toast.LENGTH_SHORT).show();

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create) {
            this.setContentView(R.layout.create_beng);
            Toast.makeText(getApplicationContext(), "create benggg",
                    Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    public void onReloadClick(View view) {
        imgLoading.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final BengAsyncTask task = new BengAsyncTask();
        task.execute(userid.toString(), token.toString());
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
            }

        });
    }

    public void onCommentClick(View view) {

    }

    public void onSetting(MenuItem item) {
    }

    public void onShare(MenuItem item) {
        FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                .setLink(getString(R.string.fanpageLink))
                .setName(getString(R.string.msg_share_name))
                .setDescription(getString(R.string.msg_post_caption))
                .setApplicationName(getString(R.string.fb_app_name))
                .build();
        uiHelper.trackPendingDialogCall(shareDialog.present());
    }

    private class BengAsyncTask extends
            AsyncTask<String, Long, List<BengModelItem>> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
        }

        @Override
        protected List<BengModelItem> doInBackground(String... arg0) {
            try {

                String index = "0";

                if (items != null && items.isEmpty() == false)
                    index = items.get(items.size() - 1).getUpdated();
                Log.d("async", index.toString());
                List<BengModelItem> result;
                bws = new BengWebServices(getResources().getString(R.string.serverhost));
                result = bws.getBengs(index, arg0[0], arg0[1]);
                if (result == null) {
                    if (canHandleHttpCode(bws.getErrorCode())) {
                        result = bws.getBengs(index, arg0[0], arg0[1]);
                    }
                }
                return result;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BengModelItem> result) {
            if (result != null && result.size() > 0) {
                if (items != null) {

                    items.addAll(result);
                } else
                    items = result;
                if (bengAdapter == null) {
                    bengAdapter = new BengItemAdapter(MainActivity.this,
                            items);
                    getListView().setAdapter(bengAdapter);
                }
                bengAdapter.notifyDataSetChanged();
            }
            if (items.size() == 0) {
                progressBar.setVisibility(View.GONE);
                if(bws.getErrorCode()!=401)
                    imgLoading.setVisibility(View.VISIBLE);
                getListView().setVisibility(View.VISIBLE);
            } else {
                imgLoading.setVisibility(View.GONE);
            }
            //getListView().setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            progressBar.setVisibility(View.VISIBLE);
            getListView().setVisibility(View.INVISIBLE);
            super.onProgressUpdate(values);
        }

    }

    private class PullDownAsyncTask extends BengAsyncTask {
        @Override
        protected void onPostExecute(List<BengModelItem> result) {

            getListView().onRefreshComplete();
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            getListView().onRefreshComplete();

        }
    }

    private class LoadMoreAsyncTask extends BengAsyncTask {
        @Override
        protected void onPostExecute(List<BengModelItem> result) {
            getListView().onLoadMoreComplete();
            super.onPostExecute(result);

        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            getListView().onLoadMoreComplete();

        }
    }
    private class BengSubmittedAsync extends AsyncTask<Void,Void,List<BengRegistered>>{

        @Override
        protected List<BengRegistered> doInBackground(Void... voids) {
            return new BengWebServices(getString(R.string.serverhost)).getBengSumitted(userid.toString(),token.toString());
        }

        @Override
        protected void onPostExecute(List<BengRegistered> result) {
            if(result!=null && result.size()>0){
                BengRegisteredHelper helper=new BengRegisteredHelper(context);
                for(int i=0;i<result.size();i++){
                    helper.addRegistered(result.get(i).getBengid(),result.get(i).getCreated());
                }
                helper.close();
                if(bengAdapter!=null){
                    bengAdapter.updateBengRegistered();
                    bengAdapter.notifyDataSetChanged();
                }

            }
            super.onPostExecute(result);
        }
    }

    private class UserCheckerAsync extends AsyncTask<Void,Void, UserModel>{

        @Override
        protected UserModel doInBackground(Void... voids) {
            UserModel user=new LoginWebServices(getString(R.string.serverhost)).getUser(userid.toString(),token.toString());
            return user;
        }

        @Override
        protected void onPostExecute(UserModel user) {
            super.onPostExecute(user);
            if(user!=null&&user.getLocations()==null){
                getIntent().putExtra(WeBengConstant.FB_ID, user.getFbId());
                getIntent().putExtra(WeBengConstant.EMAIL_KEY, user.getEmail());

                getIntent().putExtra(WeBengConstant.GENDER, user.getSex());
                getIntent().putExtra(WeBengConstant.ACCESS_TOKEN,user.getFbToken());
                getIntent().putExtra(WeBengConstant.FB_NAME,user.getName());
                gotoActivity(UserCreate.class);
                finish();
            }
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


    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

}
