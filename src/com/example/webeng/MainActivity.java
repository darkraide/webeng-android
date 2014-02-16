package com.example.webeng;

import http.Listbengitems;
import http.Listbengitems.Listbeng;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.BengModelItem;
import models.BengModelListItem;
import models.BengModelListItem.BengType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import Adapter.BengItemAdapter;
import Fonts.FontManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private static ListView benglist;
	private static List<BengModelItem> items;
	private static Context context;
	private static ProgressBar getitem;
	FontManager font = FontManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		benglist = (ListView) findViewById(R.id.ListBeng);

		getitem = (ProgressBar) findViewById(R.id.progressGetItem);
		// benglist.addFooterView(getitem);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.mymenu, null);

		final TextView title = ((TextView) v.findViewById(R.id.titleActivity));
		final ImageButton btn_newbeng = ((ImageButton) v
				.findViewById(R.id.createbeng));
		btn_newbeng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newbeng = new Intent(MainActivity.this, CreateBeng.class);
				startActivity(newbeng);

			}
		});
		title.setTypeface(font.mMedium);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		lp.gravity = Gravity.CENTER;
		title.setGravity(Gravity.CENTER);
		title.setMaxEms(10);
		title.setMaxLines(1);
		v.setLayoutParams(lp);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(v);

		items = new ArrayList<BengModelItem>();
		// font

		context = this.getApplicationContext();

		benglist.setOnItemClickListener(this);
		benglist.setEmptyView(findViewById(R.id.emptyList));

		getBengItem beng = new getBengItem();
		beng.execute();
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
	public void refresh(View view){          //refresh is onClick name given to the button
	    onRestart();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	public class getBengItem extends
			AsyncTask<String, Long, List<BengModelItem>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			getitem.setVisibility(View.VISIBLE);

			super.onPreExecute();
		}

		@Override
		protected List<BengModelItem> doInBackground(String... arg0) {
			List<BengModelItem> list = null ;
			// Listbeng benglist = null;
			Bitmap bitmap = null;
			try {
				
				Listbengitems getbenglist = new Listbengitems(
						"http://webenggg.herokuapp.com//beng/dashboard/0");
				list = (List<BengModelItem>) getbenglist.valuereturn();
				if (list != null)
					Log.v("getlis", "ok");
				Log.v("list",list.get(0).getAddress());
				// Message msg = handler.obtainMessage(1, bitmap);
				// handler.sendMessage(msg);

			} catch (Exception ex) {
Log.v("listlist",ex.toString());
			}

			return list;

		}

		@Override
		protected void onPostExecute(List<BengModelItem> result) {
			if (result != null && result.size() > 0) {
				items = result;
				BengItemAdapter bengadapter = new BengItemAdapter(context,
						items);
				benglist.setAdapter(bengadapter);
				
			}
			//refresh(MainActivity.);
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Long... values) {
			getitem.setVisibility(View.VISIBLE);
			benglist.setVisibility(View.INVISIBLE);
			super.onProgressUpdate(values);
		}

	}

}
