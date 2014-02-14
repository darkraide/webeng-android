package parser;

import java.util.ArrayList;

import models.BengModelItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class parseArrayBeng extends baseparser {

	@Override
	public Object Parse(Object jsonstring) {
		ArrayList<BengModelItem> messages = new ArrayList<BengModelItem>();

		ParseBeng parsebeng = new ParseBeng();
		if (jsonstring != null) {
			try {
				JSONArray jsonObject = new JSONArray((String)jsonstring);

			
				for (int i = 0; i < jsonObject.length(); i++) {
					String json_message = jsonObject.get(i).toString();
					Log.v("parseArray", json_message);
					if (json_message != null) {
						BengModelItem objMsg = new BengModelItem();
						objMsg = (BengModelItem) parsebeng.Parse(json_message);

						messages.add(objMsg);
					}
				}

			} catch (JSONException je) {
				Log.e("JSONparse", "error while parsing", je);
			}

		}
		return messages;
	}
}
