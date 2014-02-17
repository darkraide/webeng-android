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
    String Uri="/beng/dashboard/";
    public BengWebServices(String host) {
        super(host);
    }
    public List<BengModelItem> getBengs(String index,String userid,String token){
        String jsonData = getRequest(_host+Uri+index,userid,token);
        if(null == jsonData)
            return  null;


        List<BengModelItem> bengs=null;
        Type listType = new TypeToken< ArrayList<BengModelItem>>() {}.getType();
        Gson gson=new Gson();
        bengs=gson.fromJson(jsonData,listType);

        return  bengs;
    }
}
