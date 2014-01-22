package com.example.webeng;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import models.BengModelItem;
import models.BengModelItem.BengType;

import Fonts.FontManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener   {

	private static ListView benglist;
	private static List<BengModelItem> items;
	private static Context context;
	FontManager font = new FontManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		benglist= (ListView)findViewById(R.id.ListBeng);
		LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.mymenu, null);

		final TextView title =  ((TextView)v.findViewById(R.id.titleActivity));
		final ImageButton btn_newbeng = ((ImageButton)v.findViewById(R.id.createbeng));
		btn_newbeng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntentA1A2 = new Intent(MainActivity.this, CreateBeng.class);
				startActivity(myIntentA1A2);
				
			}
		});
		title.setTypeface(font.mBold);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		lp.gravity = Gravity.CENTER;
		title.setGravity(Gravity.CENTER);
		title.setMaxEms(10);
		title.setMaxLines(1);
		v.setLayoutParams(lp);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getActionBar().setCustomView(v);
		
		items = new ArrayList<BengModelItem>();
		// font
		Typeface light=Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue Light.ttf");
		Typeface medium=Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue Medium.ttf");
		Typeface bold=Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue BoldMedium.otf");
		Typeface untralight=Typeface.createFromAsset(getAssets(),
				"fonts/HelveticaNeue UltraLight.ttf");
		font.prepareFont(light, medium, bold, untralight);
		
		context = this.getApplicationContext();
		
		benglist.setOnItemClickListener(this);
		benglist.setEmptyView(findViewById(R.id.emptyList));
		getimage = new Thread(new Runnable () {
			@Override
			public void run() {
				try {
			        URL url = new URL("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");
			        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
			        HttpGet httpRequest = null;

			        httpRequest = new HttpGet(url.toURI());
			        
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpResponse response = (HttpResponse) httpclient
			                .execute(httpRequest);

			        HttpEntity entity = response.getEntity();
			        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			        InputStream input = b_entity.getContent();

			        Bitmap bitmap = BitmapFactory.decodeStream(input);
			        
			       
			        Message msg = handler.obtainMessage(1, bitmap);
			        handler.sendMessage(msg);

			    } catch (Exception ex) {

			    }
			
			}//run 
		});
		getimage.start();
	}
	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Bitmap imagereturn = (Bitmap) msg.obj ;
			if(imagereturn != null ){
				items.add(new BengModelItem("carsdadsasdasdasdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasdasdasdas","24-1-2014",imagereturn,BengType.BENGING));
				items.add(new BengModelItem("flower","10-1-2014",imagereturn,BengType.WINNER));
				BengItemAdapter bengadapter = new BengItemAdapter(context,items);
				benglist.setAdapter(bengadapter);
				
			}
			super.handleMessage(msg);
		}
		
	};
	Thread getimage; 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
			Toast.makeText(getApplicationContext(), "create benggg", Toast.LENGTH_SHORT).show();
		
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.create){
			this.setContentView(R.layout.create_beng);
			Toast.makeText(getApplicationContext(), "create benggg", Toast.LENGTH_SHORT).show();
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
