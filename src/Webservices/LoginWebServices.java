package Webservices;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import models.Login;

/**
 * Created by sangcu on 2/15/14.
 */
public class LoginWebServices extends BaseWebServices {
    private String Uri="/user/login";
    private String host;
    private LoginWebServices(String host) {
        super(host);
    }
    public Login Login(String userName,String password){
        List<NameValuePair> body=new ArrayList<NameValuePair>(2);
        body.add(new BasicNameValuePair("email",userName));
        body.add(new BasicNameValuePair("password",password));

        String jsonData=this.postRequest(host + Uri,body );
        if(jsonData!=null){
            return new Gson().fromJson(jsonData,Login.class);
        }
        return  null;
    }
}
