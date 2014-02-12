package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.BengModelItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class getlistbengitems {
	public Listbeng getall() {
		Listbeng listbengs = null;
		URL url = null;
		try {
			// url = new URL('http://webenggg.apiary.io/bengs');

			// HttpGet httpRequest = null;

			HttpGet getRequest = new HttpGet("http://webenggg.apiary.io/bengs");

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpResponse getResponse = httpClient.execute(getRequest);

			/*
			 * final int statusCode =
			 * getResponse.getStatusLine().getStatusCode();
			 * 
			 * if (statusCode != HttpStatus.SC_OK) {
			 * Log.w(getClass().getSimpleName(), 'Error ' + statusCode + ' for
			 * URL ' + url); return null; }
			 */
			HttpEntity getResponseEntity = getResponse.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(getResponseEntity);

			InputStream is = buf.getContent();
			// final String a = convertStreamToString(is);
			// InputStream httpResponseStream = getResponseEntity.getContent();

			String tem = "{\n       'items': [\n\n                      {\n                           'Id':'754',\n                           'Type': 1 ,                          'Name':\\'Chuột Magic Mouse, hàng bảo hành chính hãng Apple\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ],\n                           'Photos':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'645',\n                           'Type': 1,\n                           'Name':\\'Mũ bảo hiểm Amoro\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ],\n                           'Photos':[\n                            \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'754',\n                           'Type': 2 //1: Bengging, 2: Winner\n                           'Name':\\'Chuột Magic Mouse, hàng bảo hành chính hãng Apple\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'2',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ],\n                           'Photos':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'645',\n                           'Type': 1,\n                           'Name':\\'Mũ bảo hiểm Amoro\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ],\n                           'Photos':[\n                            \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'754',\n                           'Type': 1 //1: Bengging, 2: Winner\n                           'Name':\\'Chuột Magic Mouse, hàng bảo hành chính hãng Apple\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ],\n                           'Photos':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'645',\n                           'Type': 1,\n                           'Name':\\'Mũ bảo hiểm Amoro\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ],\n                           'Photos':[\n                            \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'754',\n                           'Type': 1 //1: Bengging, 2: Winner\n                           'Name':\\'Chuột Magic Mouse, hàng bảo hành chính hãng Apple\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ],\n                           'Photos':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'645',\n                           'Type': 1,\n                           'Name':\\'Mũ bảo hiểm Amoro\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ],\n                           'Photos':[\n                            \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'754',\n                           'Type': 1 //1: Bengging, 2: Winner\n                           'Name':\\'Chuột Magic Mouse, hàng bảo hành chính hãng Apple\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ],\n                           'Photos':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/h3.jpeg\\'\n                           ]    \n                      },\n                      {\n                           'Id':'645',\n                           'Type': 1,\n                           'Name':\\'Mũ bảo hiểm Amoro\\',\n                           'Deadline':'Wed Jan 22 2014 19:01:45 GMT+0700',\n                           'Status':'1',\n                           'Timestamp':'AZBDDAZZ',\n                           'Thumps':[\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ],\n                           'Photos':[\n                            \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum1.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thum2.jpg\\',\n                           \\'https://s3-ap-southeast-1.amazonaws.com/imageware-asia/webeng/thump3.jpeg\\'\n                           ]    \n                      }\n                 ],\n        'pager': {'next': '/bengs?p=56555'},\n        'time': \\'Wed Jan 22 2014 19:01:45 GMT+0700\\'\n}";
			Reader inputStreamReader = new InputStreamReader(is, "UTF-8");
			final String a = convertStreamToString2(inputStreamReader);
			parse(tem);
			// Log.v('aaa',a);
			Gson gson = new Gson();
			// listbengs= gson.fromJson(new InputStreamReader(input),
			// Listbeng.class);
			JsonReader reader = new JsonReader(new StringReader(
					convertStreamToString(is)));
			reader.setLenient(true);

			listbengs = gson.fromJson(inputStreamReader, Listbeng.class);

		} catch (Exception ex) {
			// getRequest.abort();
			Log.v("HTTP", "Error for URL " + url, ex);
		}
		return listbengs;
	}

	public static ArrayList<BengModelItem> parse(String jsonString) {

		ArrayList<BengModelItem> messages = new ArrayList<BengModelItem>();
		char[] buf = null;
		char ch = jsonString.charAt(155);

		if (jsonString != null) {
			try {
				JSONArray jsonObject = new JSONArray(jsonString);

				// JSONArray msgs = (JSONArray) jsonObject.get("items");
				for (int i = 0; i < jsonObject.length(); i++) {
					JSONObject json_message = jsonObject.getJSONObject(i);
					if (json_message != null) {
						BengModelItem objMsg = new BengModelItem();
						objMsg.setUpdated(json_message.getString("updated"));
						objMsg.setStatus(json_message.getString("status"));
						objMsg.setDescription(json_message
								.getString("description"));
						objMsg.setContact(json_message.getString("contact"));
						objMsg.setAddress(json_message.getString("address"));
						objMsg.setUser(json_message.getString("user"));
						objMsg.setId(json_message.getString("_id"));
						objMsg.setType(json_message.getInt("Type"));
					//	objMsg.setName(json_message.getString("Name"));

						objMsg.setDeadline(json_message.getString("Deadline"));

						objMsg.setWinner((json_message.getString("winner")));
						objMsg.setPhoto((String[]) json_message.get("photos"));
						objMsg.set__v(json_message.getString("__v"));
						// objMsg.setThumps((String[])
						// (json_message.get("Thumps")));
						// objMsg.setThumps((String[])
						// (json_message.get("Photos")));
						messages.add(objMsg);
					}
				}

			} catch (JSONException je) {
				Log.e("JSONparse", "error while parsing", je);
			}
		}

		return messages;

	}

	private String convertStreamToString2(Reader reader) {
		int inChar;
		StringBuffer stringBuffer = new StringBuffer();

		try {
			while ((inChar = reader.read()) != -1) {
				stringBuffer.append((char) inChar);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return stringBuffer.toString();
	}

	private String convertStreamToString(InputStream is) {
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
		return sb.toString();
	}

	public class Listbeng {
		public ArrayList<BengModelItem> listbengs;
		public pager pager;
		public String time;

		public Listbeng() {
			listbengs = new ArrayList<BengModelItem>();
		}

		/*
		 * public List<BengModelItem> getListbengs() { return listbengs; }
		 * 
		 * public void setListbengs(ArrayList<BengModelItem> listbengs) {
		 * this.listbengs = listbengs; }
		 */
	}

	public class pager {
		private String next;

		public String getNext() {
			return next;
		}

		public void setNext(String next) {
			this.next = next;
		}
	}

}
