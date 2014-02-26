package models;

import com.google.gson.Gson;

/**
 * Created by sangcu on 2/14/14.
 */
public class location {
    private String _id;
    private String name;
    private Integer key;
    private Integer __v;
    private String describle;

    public location(String _id, String name) {
        this._id = _id;
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
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public String getDescrible() {
        return describle;
    }

    public void setDescrible(String describle) {
        this.describle = describle;
    }
}
