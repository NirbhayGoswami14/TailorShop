package com.app.tailorusershop.utlis;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static PrefManager prefManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


   public static String USER_NM="name";
   public static String USER_ID="id";
   public static String USER_EMAIL="email";
   public static String USER_MOBILE="mobile_no";
   public static String USER_GENDER="gender";
   public static String USER_ADDRESS="address";
   public static String USER_VID="vid";
   public static String IS_LOGIN="islogin";
   public static String UPDATE_PROFILE="update_profile";


    public PrefManager(Context context) {
        sharedPreferences=context.getSharedPreferences("tailorshop",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }

    public static PrefManager getInstance(Context context)
    {
       if(prefManager==null)
       {
           prefManager=new PrefManager(context);
       }
       return prefManager;
    }

    public void setUserDetails(String key,String values)
    {
        editor.putString(key,values).commit();
    }

    public void setIsLogin(Boolean isLogin)
    {
        editor.putBoolean(IS_LOGIN,isLogin).commit();
    }

    public void setUpdateProfile(Boolean updateProfile)
    {
        editor.putBoolean(UPDATE_PROFILE,updateProfile).commit();
    }
    public String getUserDetails(String key)
    {
        return sharedPreferences.getString(key,"");
    }

    public boolean isLogin()
    {
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }
    public boolean isUpdate(){return sharedPreferences.getBoolean(UPDATE_PROFILE,false);}


    public void clearPref()
    {
        editor.clear();
        editor.commit();
    }
}
