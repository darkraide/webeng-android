package Webservices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.BengModelItem;

/**
 * Created by sangcu on 2/15/14.
 */
public class BengWebServices extends BaseWebServices {
    String UriBoard = "/beng/dashboard/";
    String UriBeng="/beng/";
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
}
