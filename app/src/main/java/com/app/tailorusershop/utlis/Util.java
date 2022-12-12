package com.app.tailorusershop.utlis;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.google.android.material.snackbar.Snackbar;

public  class Util {

    private static Dialog mDialog;
    public static void showSnack(String message, View view)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    public static void showDialog(Context context)
    {
        mDialog = new Dialog(context);
        mDialog.setTitle("");
        mDialog.setCancelable(false);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(R.layout.dialog_progress);
        mDialog.show();
    }

    public static void dismissDialog()
    {
        if(mDialog!=null)
        {
            mDialog.dismiss();
        }

    }
}
