package Webservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.BengModelItem;
import models.location;

/**
 * Created by sangcu on 2/15/14.
 */
public class LocationWebServices extends BaseWebServices {
    private String Uri = "/location/";

    public LocationWebServices(String host) {
        super(host);
    }

    public List getLocations() {

        String jsonData = this.getRequest(this._host + this.Uri);
        Type listType = new TypeToken<ArrayList<location>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(jsonData, listType);
    }
}
