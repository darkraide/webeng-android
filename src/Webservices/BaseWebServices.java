package Webservices;

import android.util.Log;

import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sangcu on 2/14/14.
 */
public class BaseWebServices {
    protected String _host="";

    public BaseWebServices(String host){
        _host=host;
    }
    public String getRequest(String uri,String userId,String token){
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpGet httpGet = new HttpGet(uri);
        if(userId!=null)
            httpGet.addHeader("userid",userId);

        if(token!=null)
            httpGet.addHeader("token",token);

        String responseData=null;
        // Making HTTP Request
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK) {
                throw new HttpException(String.format("status code: %d",response.getStatusLine().getStatusCode()));
            }
            responseData = getResponse(response);

        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        }finally {
            return responseData;
        }
    }

    private String getResponse(HttpResponse response) throws IOException {
        String responseData;
        // writing response to log
        HttpEntity getResponseEntity = response.getEntity();
        BufferedHttpEntity buf = new BufferedHttpEntity(getResponseEntity);

        InputStream is = buf.getContent();
        Reader inputStreamReader = new InputStreamReader(is, "UTF-8");
        responseData=streamToString(inputStreamReader);
        Log.d("response", responseData);
        return responseData;
    }

    public String getRequest(String uri){
        return getRequest(uri,null,null);
    }
    public String postRequest(String uri, String jsonBody,List<NameValuePair> body,String userId,String token){
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost(uri);

        if(userId!=null)
            httpPost.addHeader("userid",userId);

        if(token!=null)
            httpPost.addHeader("token",token);
        if(jsonBody!=null)
            httpPost.addHeader("Content-Type","application/json");

        String responseData=null;
        // Making HTTP Request
        try {
            if(jsonBody!=null)
                httpPost.setEntity(new StringEntity(jsonBody));
            else if(body!=null)
                httpPost.setEntity(new UrlEncodedFormEntity(body));

            HttpResponse response = httpClient.execute(httpPost);

            // writing response to log
            responseData = getResponse(response);
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        }finally {
            return responseData;
        }
    }
    public String postRequest(String uri,String jsonBody){
        return postRequest(uri,jsonBody,null,null,null);
    }
    public String postRequest(String uri,List<NameValuePair> body){
        return postRequest(uri,null,body,null,null);
    }

    private String streamToString(Reader reader) {
        int inChar;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            while ((inChar = reader.read()) != -1) {
                stringBuffer.append((char) inChar);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

        return stringBuffer.toString();
    }
}
