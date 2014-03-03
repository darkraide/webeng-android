package database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends BaseDBHelper {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.webeng/";

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context,"User");
        this.myContext = context;
    }

    public void createTable() {
        try {
            String create = "CREATE TABLE IF NOT EXISTS User ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[userid] TEXT,[token] TEXT)";
            myDataBase.execSQL(create);
            String insert = "insert into User(id, userid,token ) values ('1', null,null ); ";
            myDataBase.execSQL(insert);
        } catch (Exception e) {
            Log.v("Create table", e.toString());
        }
    }

    public boolean updateUser(String userid, String token) {
        String update = "UPDATE User set userid = '" + userid + "',token = '"
                + token + "' where id ='1'";
        try {
            myDataBase.execSQL(update);
            return true;
        } catch (Exception e) {
            Log.v("updateUser", e.toString());
        }
        return false;
    }

    public String[] getUser() {

        String[] str = new String[2];
        String select = "Select * from User where id  ='1'";
        try {
            Cursor mycursor = myDataBase.rawQuery(select, null);
            if (mycursor != null) {
                int userid = mycursor.getColumnIndex("userid");

                int token = mycursor.getColumnIndex("token");

                mycursor.moveToNext();
                str[0] = mycursor.getString(userid);

                str[1] = mycursor.getString(token);

                return str;
            }
        } catch (Exception e) {
            Log.v("getUser", e.toString());

        }
        return null;
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }


    // Add your public helper methods to access and get content from the
    // database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd
    // be easy
    // to you to create adapters for your views.

}
