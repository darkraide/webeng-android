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

public class DataBaseHelper extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.webeng/";

    private static String DB_NAME = "User";

    private SQLiteDatabase myDataBase;

    private Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /*
     * public DataBaseHelper() { super(myContext, DB_NAME, null, 1); myContext =
     * mycontextholder.mcontext;
     *
     * // this.myContext = mycontextholder.getInstance().mcontext; } *? /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            openDataBase();
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();
                Log.v("<coppy database>", "coppy xong!");

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

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

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the
    // database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd
    // be easy
    // to you to create adapters for your views.

}
