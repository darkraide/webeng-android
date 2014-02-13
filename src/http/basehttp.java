package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public abstract class basehttp {

	protected String url;
	protected int type;
	public String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		final StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}
		Log.v("convertStreamToString2", sb.toString());
		return sb.toString();
	}

	public abstract List<NameValuePair> parameters();

	public InputStream postdata() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(parameters()));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity ht = response.getEntity();

			BufferedHttpEntity buf = new BufferedHttpEntity(ht);

			InputStream is = buf.getContent();
			return is;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public InputStream getdata() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
	/*	if(!url.endsWith("?"))
	        url += "?";
	
		  String paramString = URLEncodedUtils.format(parameters(), "utf-8");

		    url += paramString;*/
		Log.v("getdata URL",url);
		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("token", parameters().get(0).getValue());
		httpget.addHeader("userid", parameters().get(1).getValue());
		try {

			HttpResponse response = httpclient.execute(httpget);
			HttpEntity ht = response.getEntity();

			BufferedHttpEntity buf = new BufferedHttpEntity(ht);

			InputStream is = buf.getContent();
			return is;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
public Object valuereturn(){
	InputStream data;
	if(type == 1){
		data = postdata();
	}
	else
		data = getdata();
	return parse(convertStreamToString(data));
}
	public abstract Object parse(String jsonstring);
}