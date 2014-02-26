package widget;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.webeng.BengSubmitDiagActivity;
import com.example.webeng.LoginActivity;
import com.example.webeng.MainActivity;
import com.example.webeng.R;
import com.example.webeng.StartupActivity;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by sangcu on 2/24/14.
 */
public class WebengBroadcastReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    SharedPreferences spr;
    String TAG="GCM";
    Context mContext;
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        spr=mContext.getSharedPreferences("webengmyprefs",Context.MODE_PRIVATE);
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        Log.d("GCM",messageType);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                sendNotification("Received: " + extras.toString());
                Log.i(TAG, "Received: " + extras.toString());
            }
        }

    }
    private void sendNotification(String msg){
        if (Build.VERSION.SDK_INT >= 11) {
            Notification(msg);
        } else {
            NotificationManager manager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = prepareNotification(msg);
            manager.notify(NOTIFICATION_ID, notification);
        }
    }
    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private Notification prepareNotification(String msg) {
        long when = System.currentTimeMillis();

        Notification notification = new Notification(R.drawable.gift, msg,
                when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Set a unique data uri for each notification to make sure the activity
        // gets updated
        intent.putExtra("a", 500);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                intent, 0);
        String title = mContext.getString(R.string.app_name).toUpperCase();

        return notification;
    }
    public void Notification(String msg) {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //notificationIntent.putExtra(ImageWareConstant.GCM_PRODUCT_ID,
        //        Integer.parseInt(productId));
        notificationIntent.putExtra("a", 500);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = mContext.getResources();
        Notification.Builder builder = new Notification.Builder(mContext);
        if (Build.VERSION.SDK_INT >= 16) {
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.gift)
                            // .setLargeIcon(BitmapFactory.decodeResource(res,
                            // R.drawable.some_big_img))
                            // .setTicker(res.getString(R.string.your_ticker))
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(
                            res.getString(R.string.app_name).toString()
                                    .toUpperCase()).setContentText(mContext.getString(R.string.gcm_title))
                    .setStyle(new Notification.BigTextStyle().bigText(msg));
        } else {
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.gift)
                            // .setLargeIcon(BitmapFactory.decodeResource(res,
                            // R.drawable.some_big_img))
                            // .setTicker(res.getString(R.string.your_ticker))
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(
                            res.getString(R.string.app_name).toString()
                                    .toUpperCase()).setContentText(msg);
        }

        Notification n = builder.build();

        nm.notify(NOTIFICATION_ID, n);
    }


}
