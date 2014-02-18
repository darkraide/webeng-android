package models;

import com.example.webeng.R;
import com.google.gson.Gson;

/**
 * Created by sangcu on 2/14/14.
 */
public class Location {
    private String id;
    private String name;

    public Location(String id,String name){
        this.id=id;
        this.name=name;
    }
    public String toJSON(){
        Gson gson;
        gson = new Gson();

        return gson.toJson(this);
    }
    public static Location fromJson(String json){
        Location location;
        location = new Gson().fromJson(json, Location.class);
        return location;
    }
}
