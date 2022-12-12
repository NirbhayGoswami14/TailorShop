package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityLoginBinding;
import com.app.tailorusershop.responses.LoginResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Context context=LoginActivity.this;
    private final String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
       /* getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);*/


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etLoginNum.getText().toString().equals(""))
                {
                    Snackbar.make(binding.getRoot(),"Please Enter Mobile Number",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    calLoginApi(binding.etLoginNum.getText().toString());
                    //startActivity(new Intent(context,OtpVerificationActivity.class));
                }
            }
        });



    }



    private void calLoginApi(String mob_no)
    {
        JSONObject object=new JSONObject();
        try {
            object.put(WebServiceConstants.WebServiceParams.MOBILE,mob_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.API_LOGIN, requestBody, new HashMap<String, String>(), LoginResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
            try {

                LoginResponse loginResponse=(LoginResponse) response;
                //Toast.makeText(context, loginResponse.getData().get(0).otp, Toast.LENGTH_SHORT).show();
                if(loginResponse.getStatus().equals("success"))
                {
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ID,loginResponse.getData().getId());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_NM,loginResponse.getData().getName());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_MOBILE,loginResponse.getData().getMobileNo());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_EMAIL,loginResponse.getData().getEmail());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_GENDER,loginResponse.getData().getGender());

                    /*PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ID,loginResponse.getData().get(0).id);
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_NM,loginResponse.getData().get(0).name);
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_MOBILE,loginResponse.getData().get(0).mobileNo);
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_EMAIL,loginResponse.getData().get(0).email);
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ADDRESS,loginResponse.getData().get(0).address);
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_GENDER,loginResponse.getData().get(0).gender);*/
                    startActivity(new Intent(context,OtpVerificationActivity.class).putExtra("mobile",binding.etLoginNum.getText().toString()));
                    finish();
                    //PrefManager.getInstance(context).setUpdateProfile(false);

                }

            }
            catch (Exception e)
            {
                Log.d(TAG, "onSuccess:"+e.toString());
            }

            }

            @Override
            public void onFailure(String message) {
                Log.d("---", "onFailure:"+message);
                Util.showSnack(message,binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Log.d("---", "onError:"+error);
                Util.showSnack(error,binding.getRoot());
                if (error.equals("User not exists"))
                {
                    PrefManager.getInstance(context).setUpdateProfile(true);
                    startActivity(new Intent(context,OtpVerificationActivity.class).putExtra("mobile",binding.etLoginNum.getText().toString()));
                    finish();

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}