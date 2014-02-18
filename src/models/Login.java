package models;

/**
 * Created by sangcu on 2/15/14.
 */
public class Login {
    private String userid;
    private  String token;
    private String message;
    public Login(String userid,String token,String message){
        this.setUserId(userid);
        this.setToken(token);
        this.setMessage(message);
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
