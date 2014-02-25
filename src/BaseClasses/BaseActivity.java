package BaseClasses;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.webeng.LoginActivity;
import com.example.webeng.R;
import com.example.webeng.WebengApplication;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

/**
 * Created by sangcu on 2/15/14.
 */
public class BaseActivity extends Activity {

    //Uses the tag from derived classes
    private static String tag = null;
    //To turn/off progress dialogs
    private ProgressDialog pd = null;
    private String screenName=null;
    private Tracker tracker;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tracker = GoogleAnalytics.getInstance(this).getTracker(getResources().getString(R.string.ga_trackingId));


    }
    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
        HashMap<String, String> hitParameters = new HashMap<String, String>();
        hitParameters.put(Fields.SCREEN_NAME, getScreenName());

        tracker.send(hitParameters);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
    protected SharedPreferences getSharedPreferences() {
        SharedPreferences sp
                = WebengApplication
                .s_applicationContext
                .getSharedPreferences("webengmyprefs", Context.MODE_PRIVATE);
        return sp;
    }

    //we often need to transfer to other activities
    public void gotoActivity(Class activityClassReference) {
        Intent i = new Intent(this, activityClassReference);
        startActivity(i);
    }

    //On callbacks turn on/off progress bars
    public void turnOnProgressDialog(String title, String message) {
        pd = ProgressDialog.show(this, title, message);
    }

    public void turnOffProgressDialog() {
        pd.cancel();
    }

    //Sometimes you need an explicit alert
    public void alert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }

    //Report it using a toast
    public void reportTransient(String tag, String message) {
        String s = tag + ":" + message;
        Toast mToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        mToast.show();
        Log.d(tag, message);
    }

    public Boolean handleHttpCode(Integer code) {
        if (code == 401) {
            SharedPreferences sp = getSharedPreferences();
            SharedPreferences.Editor spe = sp.edit();

            spe.putString("userid", null);
            spe.putString("token", null);
            gotoActivity(LoginActivity.class);
            finish();
            return false;
        }
        return true;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}