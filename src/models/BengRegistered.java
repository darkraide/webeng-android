package models;

import java.lang.Integer;
import java.util.Date;

/**
 * Created by sangcu on 3/8/14.
 */
public class BengRegistered {
    private String _Id;
    private String bengid;
    private String userId;
    private Date created;
    private Integer randomid;
    public String get_Id() {
        return _Id;
    }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBengid() {
        return bengid;
    }

    public void setBengid(String bengid) {
        this.bengid = bengid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getRandomid() {
        return randomid;
    }

    public void setRandomid(Integer randomid) {
        this.randomid = randomid;
    }
}
