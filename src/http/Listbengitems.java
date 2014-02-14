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
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parser.parseArrayBeng;

import android.util.Log;

import com.example.webeng.Login;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Listbengitems extends basehttp {
	public Listbengitems(String Url) {
		this.url = Url;
		this.type = 2;
	}

	
	/*
	 * public ArrayList<BengModelItem> parse(String jsonString) {
	 * 
	 * ArrayList<BengModelItem> messages = new ArrayList<BengModelItem>();
	 * char[] buf = null; char ch = jsonString.charAt(155);
	 * 
	 * if (jsonString != null) { try { JSONArray jsonObject = new
	 * JSONArray(jsonString);
	 * 
	 * // JSONArray msgs = (JSONArray) jsonObject.get("items"); for (int i = 0;
	 * i < jsonObject.length(); i++) { JSONObject json_message =
	 * jsonObject.getJSONObject(i); if (json_message != null) { BengModelItem
	 * objMsg = new BengModelItem();
	 * objMsg.setUpdated(json_message.getString("updated"));
	 * objMsg.setStatus(json_message.getString("status"));
	 * objMsg.setDescription(json_message .getString("description"));
	 * objMsg.setContact(json_message.getString("contact"));
	 * objMsg.setAddress(json_message.getString("address"));
	 * objMsg.setUser(json_message.getString("user"));
	 * objMsg.setId(json_message.getString("_id"));
	 * objMsg.setType(json_message.getInt("Type")); //
	 * objMsg.setName(json_message.getString("Name"));
	 * 
	 * objMsg.setDeadline(json_message.getString("Deadline"));
	 * 
	 * objMsg.setWinner((json_message.getString("winner")));
	 * objMsg.setPhoto((String[]) json_message.get("photos"));
	 * objMsg.set__v(json_message.getString("__v")); //
	 * objMsg.setThumps((String[]) // (json_message.get("Thumps"))); //
	 * objMsg.setThumps((String[]) // (json_message.get("Photos")));
	 * messages.add(objMsg); } }
	 * 
	 * } catch (JSONException je) { Log.e("JSONparse", "error while parsing",
	 * je); } }
	 * 
	 * return messages;
	 * 
	 * }
	 */

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

	public class Listbeng {
		public ArrayList<BengModelItem> listbengs;
	

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

	

	@Override
	public List<NameValuePair> parameters() {
		String[] str = Login.hepler.getUser();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("token", str[1]));
		nameValuePairs.add(new BasicNameValuePair("userid", str[0]));
		return nameValuePairs;
	}

	@Override
	public Object parse(String jsonstring) {
		ArrayList<BengModelItem> messages = new ArrayList<BengModelItem>();
		parseArrayBeng parse = new parseArrayBeng();
		messages = (ArrayList<BengModelItem>) parse.Parse(jsonstring);

		return messages;
	}

}
