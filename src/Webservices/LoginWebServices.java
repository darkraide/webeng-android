package Webservices;

import com.example.webeng.UserCreate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.BengRegistered;
import models.Login;
import models.UserModel;

/**
 * Created by sangcu on 2/15/14.
 */
public class LoginWebServices extends BaseWebServices {
    private String Uri = "/user/login";
    private String UriFb="/user/login/facebook";
    private String UriUser="/user";
    public LoginWebServices(String host) {
        super(host);
    }

    public Login Login(String userName, String password) {
        List<NameValuePair> body = new ArrayList<NameValuePair>(2);
        body.add(new BasicNameValuePair("email", userName));
        body.add(new BasicNameValuePair("password", password));

        String jsonData = this.postRequest(_host + Uri, body);
        if (null != jsonData) {
            return new Gson().fromJson(jsonData, Login.class);
        }
        return null;
    }

    public Login loginFacebook(String fbid, String accesstoken,String email) {
        List<NameValuePair> body = new ArrayList<NameValuePair>(2);
        body.add(new BasicNameValuePair("email", email));
        body.add(new BasicNameValuePair("accesstoken", accesstoken));
        body.add(new BasicNameValuePair("fbid", fbid));

        String jsonData = this.postRequest(_host + UriFb, body);

        if (null != jsonData) {
            return new Gson().fromJson(jsonData, Login.class);
        }
        return null;
    }
    public boolean updateUserInfo(UserModel user,String userid,String token){
        String jsonUser=new Gson().toJson(user);

        String jsonResult=this.putRequest(_host+ UriUser,jsonUser,userid,token);

        if(null!=jsonResult&&getResponseCode()==200){
            return true;
        }
        return  false;

    }
    public Boolean registerUser(UserModel user){
        String jsonUser=new Gson().toJson(user);

        String jsonResult=this.postRequest(_host + UriUser, jsonUser);

        if(null!=jsonResult&&getResponseCode()==200){
            return true;
        }
        return  false;

    }
    public UserModel getUser(String userid,String token){
        String jsonData = getRequest(_host + UriUser, userid, token);
        if (null == jsonData)
            return null;

        Gson gson = new Gson();
        UserModel user = gson.fromJson(jsonData, UserModel.class);
        return user;
    }

}
