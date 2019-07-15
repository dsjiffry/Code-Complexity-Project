package com.spm.banking.IT17029896;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spm.banking.R;

import static com.spm.banking.MainActivity.assistance;

public class ResetPassword extends AppCompatActivity {

    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email2);
    }







    public void BackButton(View v)
    {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_in);
        this.onBackPressed();
    }

    public void SubmitButton(View V)
    {
        //Checking Email Format
        if(! assistance.isEmailValid(email.getText().toString()))
        {
            assistance.AlertMessage("Please Check email address",ResetPassword.this);
            return;
        }

        assistance.AlertMessage("An email has been sent.",ResetPassword.this,"Check Email");
    }

}
