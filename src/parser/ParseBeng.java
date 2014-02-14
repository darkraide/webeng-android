package parser;

import models.BengModelItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ParseBeng extends baseparser {

	@Override
	public Object Parse(Object jsonString) {
		JSONObject json_message = null;
		BengModelItem objMsg = new BengModelItem();
		try {
			json_message = new JSONObject((String)jsonString);

			if (json_message != null) {

				objMsg.setUpdated(json_message.getString("updated"));
				objMsg.setStatus(json_message.getInt("status"));
				objMsg.setDescription(json_message.getString("description"));
				objMsg.setContact(json_message.getString("contact"));
				objMsg.setAddress(json_message.getString("address"));
				objMsg.setType(json_message.getInt("bengtype"));
				objMsg.setDeadline(json_message.getString("deadline"));

				objMsg.setUser(json_message.getString("user"));
				objMsg.setId(json_message.getString("_id"));

				objMsg.setWinner((Boolean) (json_message.get("winner")));
				objMsg.setLocation(parseArrayInt((JSONArray) json_message
						.get("location")));
				objMsg.setPhoto(parseArrayString((JSONArray) json_message
						.get("photos")));
				objMsg.set__v(json_message.getString("__v"));

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		Log.v("parseBeng",e.toString());
		}
		return objMsg;
	}

	private int[] parseArrayInt(JSONArray array) {
		int[] arrayint = new int[array.length()];
		for (int i = 0; i < array.length(); i++) {
			try {
				arrayint[i] = array.getInt(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("parseBeng",e.toString());
			}
		}
		return arrayint;
	}

	private String[] parseArrayString(JSONArray array) {
		String[] arrayString = new String[array.length()];
		for (int i = 0; i < array.length(); i++) {
			try {
				arrayString[i] = array.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.v("parseBeng",e.toString());
			}
		}
		return arrayString;
	}
}
