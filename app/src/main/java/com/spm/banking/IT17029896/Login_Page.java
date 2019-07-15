package com.spm.banking.IT17029896;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spm.banking.DBHandler;
import com.spm.banking.R;

import static com.spm.banking.MainActivity.assistance;

public class Login_Page extends AppCompatActivity {

    private DBHandler DbHelper;
    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper = new DBHandler(this.getApplicationContext());
        setContentView(R.layout.activity_login__page);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

    }


    public void registerButton(View V)
    {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_in);
        startActivity(new Intent(Login_Page.this,Register.class));
    }

    public void forgotPW(View V)
    {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_in);
        startActivity(new Intent(Login_Page.this,ResetPassword.class));
    }

    public void loginButton(View v)
    {
        if(username.length() == 0 || password.length() == 0)
        {
            assistance.AlertMessage("Please fill both username & password",Login_Page.this);
            return;
        }

        SQLiteDatabase DB = DbHelper.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT password FROM logins",null);
        cursor.moveToFirst();
        do
        {
            int temp = cursor.getInt(cursor.getColumnIndex("password"));
            if(temp == password.getText().toString().hashCode())
            {
                assistance.AlertMessage("Login Successful",Login_Page.this, "Welcome");

                cursor.close();
                return;
            }
        }while(cursor.moveToNext());

        assistance.AlertMessage("Incorrect username/password",Login_Page.this);
        cursor.close();

    }
}
