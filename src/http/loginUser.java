package http;

import java.util.ArrayList;
import java.util.List;

import models.UserInfor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class loginUser extends basehttp {
	public loginUser(Context context,String Url) {
		super();
		this.context = context;
		this.url = Url;
		this.type = 1;
	}

	private Context context;

	/*
	 * void postlogin(){ DefaultHttpClient httpclient = new DefaultHttpClient();
	 * HttpPost httppost = new
	 * HttpPost("http://webenggg.herokuapp.com/user/login");
	 * 
	 * 
	 * try { httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 * HttpResponse response = httpclient.execute(httppost); HttpEntity ht =
	 * response.getEntity();
	 * 
	 * BufferedHttpEntity buf = new BufferedHttpEntity(ht);
	 * 
	 * InputStream is = buf.getContent(); } catch (UnsupportedEncodingException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
	 * }
	 * 
	 * 
	 * }
	 */

	@Override
	public List<NameValuePair> parameters() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs
				.add(new BasicNameValuePair("email", "sangcn@gmail.com"));
		nameValuePairs.add(new BasicNameValuePair("password", "admin123"));
		return nameValuePairs;
	}

	@Override
	public Object parse(String jsonstring) {

		if (jsonstring != null) {
			try {
				JSONObject jsonObject = new JSONObject(jsonstring);

			
			
					if (jsonObject != null) {
						UserInfor objMsg = new UserInfor();
						objMsg.setMessage(jsonObject.getString("message"));
						objMsg.setToken(jsonObject.getString("token"));
					
				
						objMsg.setUserid((jsonObject.getString("userid")));
						
						return  objMsg;
					}
				}

			 catch (JSONException je) {
				Log.e("JSONparse", "error while parsing", je);
			}
			
		}
		return null;
	}

		
	
}
