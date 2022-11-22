package com.app.tailorusershop.utlis;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public  class Util {

    public static void showSnack(String message, View view)
    {
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }
}
