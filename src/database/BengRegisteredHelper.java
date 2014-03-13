package database;

import android.content.ContentValues;
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
    public void onCreate(SQLiteDatabase db) {
        try {
            String create = "CREATE TABLE IF NOT EXISTS BengRegistered ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[bengid] TEXT,[registered] INTEGER)";
            db.execSQL(create);

        } catch (Exception e) {
            Log.v("Create table", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);

        // Create tables again
        onCreate(db);
    }
    public String[] getBengs(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> result= new ArrayList<String>();
        Cursor query= db.query(true, DB_NAME, new String[]{"bengid"}, null, null, null, null, null, null);
        while(query.moveToNext()){
            result.add(query.getString(0));
        }
        return result.toArray(new String[]{});
    }
    public void addRegistered(String bengid,Date registered){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("bengid",bengid);
        values.put("registered", registered.getTime());
        assert db != null;
        Cursor cur=db.query(true,DB_NAME,new String[]{"bengid"},"bengid=?",new String[]{bengid},null,null,null,null);
        if(cur.getCount()==0){
            db.insert(DB_NAME,null,values);
        }
        cur.close();
        db.close(); 
        String[] b=getBengs();

    }


}
