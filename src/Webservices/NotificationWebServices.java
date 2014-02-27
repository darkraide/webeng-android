package Webservices;

import BaseClasses.BaseActivity;

/**
 * Created by sangcu on 2/23/14.
 */
public class NotificationWebServices extends BaseWebServices {
    private String url="/devices/register";
    public NotificationWebServices(String host) {
        super(host);
    }
    public boolean registeDevice(String jsonRegId,String userid,String token){
        String jsonData;
        jsonData = postRequest(_host+url,jsonRegId);
        if(null != jsonData && 200 == getErrorCode()){
            return true;
        }
        return false;

    }
}
