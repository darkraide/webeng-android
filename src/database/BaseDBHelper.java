package database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sangcu on 3/3/14.
 */
public abstract class BaseDBHelper extends SQLiteOpenHelper {
    // The Android's default system path of your application database.
    protected static String DB_PATH = "/data/data/com.example.webeng/";
    protected static String DB_NAME;
    static  final int DATABASE_VERSION=2;

    protected SQLiteDatabase myDataBase;

    protected Context myContext;

    public BaseDBHelper(Context context,String DB_NAME) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.DB_NAME=DB_NAME;
        this.myContext=context;

    }


}
