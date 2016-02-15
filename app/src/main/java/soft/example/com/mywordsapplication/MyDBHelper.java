package soft.example.com.mywordsapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;


import java.util.HashMap;

import soft.example.com.mywordsapplication.sqliteasset.SQLiteAssetHelper;

/**
 * Created by admin on 27.12.2015.
 */
public class MyDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "words.sqlite";
    private static final int DATABASE_VERSION = 1;


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public HashMap<String, Integer>  getAllWords(){
        HashMap<String, Integer> words = new HashMap<String, Integer>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        Cursor c = db.rawQuery("SELECT UNAME, LENG FROM sg_entry", null);

        if (c.moveToFirst()) {
            while(c.moveToNext()) {
                words.put(c.getString(0),c.getInt(1));
            }
        }
        c.close();
        return words;
    }

}
