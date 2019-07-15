package com.spm.banking.IT17029896;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assistance
{
    /**
     * Will show a popup alert message
     * @param message The message to be shown
     * @param context needs <<ClassName>>.this context object
     */
    public void AlertMessage(String message, Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    //Can Set Message Title
    public void AlertMessage(String message, final Context context, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(title);

        builder.setNeutralButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }


    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Checks internet connection
     *  @param context needs <<ClassName>>.this context object
     * @return true if connected to internet
     */
    public boolean InternetConnectionCheck(Context context)
    {
        ConnectivityManager cMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cMan.getActiveNetworkInfo();
        return (nInfo != null && nInfo.isConnected());
    }
}
