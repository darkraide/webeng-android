package Webservices;

import android.util.Log;

import com.google.gson.Gson;

import models.RegisterDeviceModel;

/**
 * Created by sangcu on 2/27/14.
 */
public class RegisterDeviceWebServices extends BaseWebServices {
    String Uri="/device/register";
    public RegisterDeviceWebServices(String host) {
        super(host);
    }
    public boolean registerDevice(RegisterDeviceModel register,String userid,String token){
        Log.d("REG",new Gson().toJson(register));
        String jsonData=postRequest(_host+Uri,new Gson().toJson(register),userid,token);
        if(jsonData!=null && getErrorCode()==200){
            return  true;
        }
        return false;
    }
}
