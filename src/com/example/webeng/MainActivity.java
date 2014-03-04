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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    SharedPreferences sp;
    CharSequence userid;
    CharSequence token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setScreenName(getResources().getString(R.string.main_screen));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressGetItem);

        imgLoading = (ImageView) findViewById(R.id.imgTapToLoad);

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_menu, null);

        final TextView title = ((TextView) v.findViewById(R.id.titleActivity));
        final ImageButton btn_newbeng = ((ImageButton) v
               .findViewById(R.id.sharebutton));
        btn_newbeng.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               

            }
        });
        //title.setTypeface(font.mMedium);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        lp.gravity = Gravity.LEFT;
        title.setGravity(Gravity.LEFT);
        title.setMaxEms(10);
        title.setMaxLines(1);
        v.setLayoutParams(lp);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(v);

        items = new ArrayList<BengModelItem>();
        // font

        context = this.getApplicationContext();

        getListView().setOnItemClickListener(this);
        getListView().setEmptyView(findViewById(R.id.emptyList));
        sp = getSharedPreferences();
        userid = sp.getString("userid", null);
        token = sp.getString("token", null);

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
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        checkIntentData();

    }
    private void checkIntentData(){
        Intent intent= getIntent();
        Integer bengid=intent.getIntExtra("a",0);
        if(bengid>0){
            gotoActivity(BengSubmitDiagActivity.class);
        }
        Log.d("GCM",bengid.toString());
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

    public void onCommentClick(View view) {

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
                BengWebServices bws = new BengWebServices(getResources().getString(R.string.serverhost));
                result = bws.getBengs(index, arg0[0], arg0[1]);
                if (result == null) {
                    if (handleHttpCode(bws.getErrorCode())) {
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

}
