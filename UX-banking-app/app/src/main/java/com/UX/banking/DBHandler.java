package com.UX.banking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper
{
    public DBHandler(Context context) {
        super(context, "userInfo.db", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {

        //Create all required Tables Here
        db.execSQL("CREATE TABLE IF NOT EXISTS logins (" +
                "ID INTEGER PRIMARY KEY, " +
                "username VARCHAR(100) NOT NULL UNIQUE, " +
                "password INTEGER NOT NULL " +
                ")");


    }









    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //  db.execSQL("DROP TABLE IF EXISTS online");
        //  db.execSQL("DROP TABLE IF EXISTS settings");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}