package com.spm.banking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.spm.banking.IT17029896.Assistance;
import com.spm.banking.IT17029896.Login_Page;

public class MainActivity extends AppCompatActivity {

    public static Assistance assistance = new Assistance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SplashScreen setter, Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler DbHelper = new DBHandler(this.getApplicationContext()); // Creation of Database in storage
        SQLiteDatabase DB = DbHelper.getWritableDatabase();

        String temp = "admin";

        //Using shared preferences value to identify first run.
        SharedPreferences prefs = getSharedPreferences("Banking", MODE_PRIVATE);
        if (prefs.getInt("FirstTime", 0)!=1)    //The app has not been run before
        {
            DB.execSQL("INSERT INTO logins VALUES (1,'bob',"+temp.hashCode()+")");


            SharedPreferences.Editor editor = getSharedPreferences("Banking", MODE_PRIVATE).edit();
            editor.putInt("FirstTime", 1);
            editor.apply();
        }

        startActivity(new Intent(MainActivity.this,Login_Page.class));
    }
}
