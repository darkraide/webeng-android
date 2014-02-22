package Webservices;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import models.location;

/**
 * Created by sangcu on 2/15/14.
 */
public class LocationWebServices extends BaseWebServices {
    private String Uri="/location";
    public LocationWebServices(String host,String userid, String token) {
        super(host);
    }
    public List getLocations(){

        String jsonData=this.getRequest(this._host+this.Uri);

        Gson gson=new Gson();
        return gson.fromJson(jsonData,new ArrayList<location>().getClass());
    }
}
