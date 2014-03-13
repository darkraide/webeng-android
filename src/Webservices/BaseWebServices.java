package Webservices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

import models.ResponseErrorMessage;


/**
 * Created by sangcu on 2/14/14.
 */
public class BaseWebServices {
    private final String POST="POST";
    private final String PUT="PUT";
    protected String _host = "";
    protected Integer errorCode;
    private String errorMessage;
    private Integer responseCode;
    public BaseWebServices(String host) {
        _host = host;
        errorCode = 0;
    }

    public String getRequest(String uri, String userId, String token) {
        resetLastResultCode();
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpGet httpGet = new HttpGet(uri);
        if (userId != null)
            httpGet.addHeader("userid", userId);

        if (token != null)
            httpGet.addHeader("token", token);

        String responseData = null;
        // Making HTTP Request
        try {
            HttpResponse response = httpClient.execute(httpGet);
            responseCode=response.getStatusLine().getStatusCode();
            if (responseCode != HttpStatus.SC_OK) {
                errorCode = response.getStatusLine().getStatusCode();
                errorMessage=getResponse(response);
                return null;
            }
            responseData = getResponse(response);

        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        } finally {
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
        responseData = streamToString(inputStreamReader);
        Log.d("response", responseData);
        return responseData;
    }

    public String getRequest(String uri) {
        return getRequest(uri, null, null);
    }
    public String putRequest(String uri,String jsonBody,String userId,String token){
        return putRequest(uri,jsonBody,null,userId,token);
    }
    public String putRequest(String uri, String jsonBody, List<NameValuePair> body, String userId, String token){
        return makeRequest(PUT,uri, jsonBody, body, userId, token);
    }
    public String postRequest(String uri, String jsonBody, List<NameValuePair> body, String userId, String token) {
        return makeRequest(POST,uri, jsonBody, body, userId, token);
    }
    private String makeRequest(String method,String uri, String jsonBody, List<NameValuePair> body, String userId, String token) {
        resetLastResultCode();
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpEntityEnclosingRequestBase httpPostOrPut = null;
        if(method.equals(POST))
            httpPostOrPut= new HttpPost(uri);
        else if(method.equals(PUT))
            httpPostOrPut=new HttpPut(uri);

        if (userId != null)
            httpPostOrPut.addHeader("userid", userId);

        if (token != null)
            httpPostOrPut.addHeader("token", token);
        if (jsonBody != null)
            httpPostOrPut.addHeader("Content-Type", "application/json");

        String responseData = null;
        // Making HTTP Request
        try {
            if (jsonBody != null)
                httpPostOrPut.setEntity(new StringEntity(jsonBody));
            else if (body != null)
                httpPostOrPut.setEntity(new UrlEncodedFormEntity(body));

            HttpResponse response = httpClient.execute(httpPostOrPut);
            responseCode=response.getStatusLine().getStatusCode();
            if (responseCode != HttpStatus.SC_OK&&responseCode!=HttpStatus.SC_CREATED) {
                errorCode = response.getStatusLine().getStatusCode();
                errorMessage=getResponse(response);
                return null;
            }
            // writing response to log
            responseData = getResponse(response);
        } catch (ClientProtocolException e) {
            // writing exception to log
            e.printStackTrace();

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();

        } finally {
            return responseData;
        }
    }

    public String postRequest(String uri, String jsonBody) {
        return postRequest(uri, jsonBody, null, null, null);
    }

    public String postRequest(String uri, String jsonBody,String userid,String token) {
        return postRequest(uri, jsonBody, null, userid, token);
    }

    public String postRequest(String uri, List<NameValuePair> body) {
        return postRequest(uri, null, body, null, null);
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

    public Integer getErrorCode() {
        return errorCode;
    }
    public void resetLastResultCode() {
        errorCode=0;
        errorMessage=null;
        responseCode=0;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public ResponseErrorMessage getResponseErrorMessage(){
        if(getErrorMessage()==null)
            return null;
        return new Gson().fromJson(getErrorMessage(),ResponseErrorMessage.class);
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
