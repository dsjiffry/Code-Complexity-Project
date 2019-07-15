package com.spm.banking.IT17029896;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spm.banking.R;

import java.util.ArrayList;

import static com.spm.banking.MainActivity.assistance;


public class Register extends AppCompatActivity {

    private EditText fullName;
    private EditText contactNo;
    private EditText cardNumber;
    private EditText email;
    private EditText NIC;

    ArrayList<EditText> list = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fullName = findViewById(R.id.fullName);
        contactNo = findViewById(R.id.contactNo);
        cardNumber = findViewById(R.id.cardNumber);
        email = findViewById(R.id.email);
        NIC = findViewById(R.id.NIC);

        list.add(fullName);
        list.add(contactNo);
        list.add(cardNumber);
        list.add(email);
        list.add(NIC);

    }

    public void backButton(View v)
    {
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.fade_in);
        this.onBackPressed();
    }

    public void registerButton(View v)
    {
        //Making sure no fields are empty
        for(EditText ET : list)
        {
            if(ET.getText().toString().length() == 0)
            {
                assistance.AlertMessage("Please Fill all Fields",Register.this);
                return;
            }
        }

        //Checking length and last Character of NIC
        if(NIC.getText().length() != 10 || NIC.getText().toString().charAt(9) != 'v' && NIC.getText().toString().charAt(9) !='V')
        {
            assistance.AlertMessage("Please Check NIC number",Register.this);
            return;
        }

        //Checking Email Format
        if(! assistance.isEmailValid(email.getText().toString()))
        {
            assistance.AlertMessage("Please Check email address",Register.this);
            return;
        }

        //Checking contact Number length and starting with 0
        if(contactNo.getText().length() != 10 || contactNo.getText().toString().charAt(0) != '0')
        {
            assistance.AlertMessage("Please Check Contact Number",Register.this);
            return;
        }

        //checking if card number has 12 digits
        if(cardNumber.getText().length() != 12)
        {
            assistance.AlertMessage("Please Check Card Number",Register.this);
            return;
        }

        if(! assistance.InternetConnectionCheck(Register.this))
        {
            assistance.AlertMessage("Please connect to the Internet to submit details",Register.this);
            return;
        }

        assistance.AlertMessage("Your details have been sent to the bank. They will email you your username and Password",Register.this,"Request Sent");



    }


















}


