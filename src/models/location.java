package models;

import com.google.gson.Gson;

/**
 * Created by sangcu on 2/14/14.
 */
public class location {
    private String id;
    private String name;

    public location(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toJSON() {
        Gson gson;
        gson = new Gson();

        return gson.toJson(this);
    }

    public static location fromJson(String json) {
        location location;
        location = new Gson().fromJson(json, models.location.class);
        return location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
