package models;

import com.google.android.gms.common.util.VisibleForTesting;

import java.util.Date;

/**
 * Created by sangcu on 2/27/14.
 */
public class RegisterDeviceModel {
    private String userid;
    private String model;
    private String registerId;
    private Integer type;
    private Date registerDay;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registrationId) {
        this.registerId = registrationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(Date registerDay) {
        this.registerDay = registerDay;
    }
}
