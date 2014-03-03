package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.webeng.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sangcu on 3/3/14.
 */
public class BengRegisteredHelper extends BaseDBHelper {

    private SQLiteDatabase myDataBase;

    private Context myContext;

    public BengRegisteredHelper(Context context) {
        super(context,"BengRegistered");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
    public String[] getBengs(){
        ArrayList<String> result= new ArrayList<String>();
        String insertSql="SELECT bengid FROM BengRegistered";
        Cursor query= myDataBase.query(true, DB_NAME, new String[]{"bengid"}, null, null, null, null, null, null);
        while(query.moveToNext()){
            result.add(query.getString(0));
        }
        return result.toArray(new String[]{});
    }
    private void createTable() {
        try {
            String create = "CREATE TABLE IF NOT EXISTS BengRegistered ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[bengid] TEXT NOT NULL PRIMARY KEY,[registered] DateTime)";
            myDataBase.execSQL(create);

        } catch (Exception e) {
            Log.v("Create table", e.toString());
        }
    }
    public void addRegistered(String bengid,Date registered){
    openDataBase();
        String insertSql="INSERT INTO BengRegistered(bengid,registered) VALUES('"+bengid+"','"+registered+"')";
        myDataBase.execSQL(insertSql);
    }


}
