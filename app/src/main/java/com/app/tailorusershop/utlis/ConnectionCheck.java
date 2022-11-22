package com.app.tailorusershop.utlis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionCheck {

    private Context context;
    public ConnectionCheck(Context context) {

        this.context=context;
    }

    public  boolean isNetworkConnected() {
        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }

    public AlertDialog.Builder showConnectionDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage("Please Connect InternetConnection");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok",(dialogInterface, i) -> {

            dialogInterface.dismiss();
        });
        builder.create();


        return builder;
    }
}
