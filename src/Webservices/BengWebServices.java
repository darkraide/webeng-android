package Webservices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Helpers.DateGsonDeserialize;
import models.BengModelItem;
import models.BengRegistered;

/**
 * Created by sangcu on 2/15/14.
 */
public class BengWebServices extends BaseWebServices {
    String UriBoard = "/beng/dashboard/";
    String UriBeng="/beng/";
    String UriBengSubmit="/beng/submit";
    String UriBengSubmitted="/beng/submit/registered";
    public BengWebServices(String host) {
        super(host);
    }

    public List<BengModelItem> getBengs(String index, String userid, String token) {
        String jsonData = getRequest(_host + UriBoard + index, userid, token);
        if (null == jsonData)
            return null;

        List<BengModelItem> bengs = null;
        Type listType = new TypeToken<ArrayList<BengModelItem>>() {
        }.getType();
        Gson gson = new Gson();
        bengs = gson.fromJson(jsonData, listType);
        return bengs;
    }
    public BengModelItem getBeng(String bengId,String userid,String token){
        Log.d("GCM",bengId==null?"null":bengId);
        String jsonData = getRequest(_host + UriBeng+bengId, userid, token);
        if(jsonData==null)
            return null;
        return new Gson().fromJson(jsonData,BengModelItem.class);
    }

    public Boolean submitBeng(String bengid,String userid,String token) {
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("bengid",bengid));
        String jsonData=postRequest(_host+UriBengSubmit,null,params,userid,token);
        if(jsonData!=null)
        {
            return true;
        }
        return false;
    }
    public List<BengRegistered> getBengSumitted(String userid,String token){
        String jsonData = getRequest(_host + UriBengSubmitted, userid, token);
        if (null == jsonData)
            return null;

        List<BengRegistered> bengs = null;
        Type listType = new TypeToken<ArrayList<BengRegistered>>() {
        }.getType();

        GsonBuilder builder=new GsonBuilder();
        builder.registerTypeAdapter(BengRegistered.class,new DateGsonDeserialize());
        Gson gson = builder.create();
        bengs = gson.fromJson(jsonData, listType);
        return bengs;
    }
    public List<>
}
