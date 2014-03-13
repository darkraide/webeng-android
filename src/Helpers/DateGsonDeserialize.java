package Helpers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import models.BengRegistered;

/**
 * Created by sangcu on 3/8/14.
 */
public class DateGsonDeserialize implements JsonDeserializer<BengRegistered> {
    public BengRegistered deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        BengRegistered result=new BengRegistered();

        if(((JsonObject)json).getAsJsonPrimitive("created")!=null){
            DateFormat fm = DateFormat.getDateTimeInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
            // TimeZone timezone = TimeZone.getTimeZone("GMT");
            ParsePosition parseErr = new ParsePosition(0);
            // dateFormat.setTimeZone(timezone);
            Date convertedDate = null;
           convertedDate = dateFormat.parse(((JsonObject)json).getAsJsonPrimitive("created").getAsString(), parseErr);
            result.setCreated(convertedDate);
        }


        result.set_Id(((JsonObject)json).getAsJsonPrimitive("_id").getAsString());
        result.setBengid(((JsonObject)json).getAsJsonPrimitive("bengid").getAsString());
        result.setUserId(((JsonObject)json).getAsJsonPrimitive("userid").getAsString());
        return  result;
    }
}
