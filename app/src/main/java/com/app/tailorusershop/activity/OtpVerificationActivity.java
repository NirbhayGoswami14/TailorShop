package com.app.tailorusershop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityOtpVerificationBinding;
import com.app.tailorusershop.responses.OtpResponse;
import com.app.tailorusershop.responses.VerifyOtpResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OtpVerificationActivity extends AppCompatActivity implements TextWatcher {

    private ActivityOtpVerificationBinding binding;
    private Context context = OtpVerificationActivity.this;
    private String otpCode = "";
    private String resOtp = "";
    private String mobile_no = "";
    private boolean resend=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mobile_no = getIntent().getStringExtra("mobile");

        binding.etOtp1.addTextChangedListener(this);
        binding.etOtp2.addTextChangedListener(this);
        binding.etOtp3.addTextChangedListener(this);
        binding.etOtp4.addTextChangedListener(this);


        binding.linearProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpCode = binding.etOtp1.getText().toString() + binding.etOtp2.getText().toString() + binding.etOtp3.getText().toString() + binding.etOtp4.getText().toString();
                if(!otpCode.equals(""))
                {
                    verifyOtp(mobile_no,otpCode);
                /*if (resOtp.equals(otpCode)) {

                    PrefManager.getInstance(context).setIsLogin(true);
                    if (PrefManager.getInstance(context).isUpdate()) {
                        startActivity(new Intent(context, UpdateProfileActivity.class).putExtra("mobile",mobile_no));
                        finish();
                    } else {
                        startActivity(new Intent(context, HomeActivity.class));
                        finish();
                    }*/

                } else {
                    Log.d("", "onClick:" + otpCode);
                    Snackbar.make(binding.getRoot(), "Enter Valid Otp", Snackbar.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(context,HomeActivity.class));
            }
        });
        //getOTP(mobile_no);

        reSendOtp();
        binding.txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resend)
                {
                    sendOTP(mobile_no);
                    resend=false;
                    reSendOtp();
                }
            }
        });
    }


    private void verifyOtp(String mobile_no,String otp)
    {
        JSONObject object = new JSONObject();
        try {
            object.put(WebServiceConstants.WebServiceParams.MOBILE, mobile_no);
            object.put("otp",otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.VERIFY_OTP, requestBody, new HashMap<String, String>(), VerifyOtpResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                VerifyOtpResponse verifyOtpResponse=(VerifyOtpResponse) response;
                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ID,verifyOtpResponse.getData().getId());
                if(verifyOtpResponse.getMessage().equals("Otp verified")&&PrefManager.getInstance(context).getUserDetails(PrefManager.USER_NM).equals(""))
                {
                    startActivity(new Intent(context,UpdateProfileActivity.class).putExtra("mobile",mobile_no));
                    finish();
                }
                else if (verifyOtpResponse.getMessage().equals("Otp verified"))
                {
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ID,verifyOtpResponse.getData().getId());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_NM,verifyOtpResponse.getData().getName());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_MOBILE,verifyOtpResponse.getData().getMobileNo());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_EMAIL,verifyOtpResponse.getData().getEmail());
                    PrefManager.getInstance(context).setUserDetails(PrefManager.USER_GENDER,verifyOtpResponse.getData().getGender());
                    startActivity(new Intent(context, HomeActivity.class));
                    finish();
                    PrefManager.getInstance(context).setIsLogin(true);
                }

            }

            @Override
            public void onFailure(String message) {
            Util.showSnack(message,binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error,binding.getRoot());
            }
        });
        }

    void sendOTP(String mobile_no) {
        if(resend)
        {
            Util.showSnack("Otp Resend",binding.getRoot());
        }
        JSONObject object = new JSONObject();
        try {
            object.put(WebServiceConstants.WebServiceParams.MOBILE, mobile_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.SEND_OTP, requestBody, new HashMap<String, String>(), OtpResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                OtpResponse response1 = (OtpResponse) response;
                // Toast.makeText(context, response1.getData().getOtp(), Toast.LENGTH_SHORT).show();
                resOtp = response1.getData().getOtp();


            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message,binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error,binding.getRoot());
            }
        });

    }




    void reSendOtp() {
        binding.txtResend.setTextColor(Color.BLACK);
        new CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                long sec = TimeUnit.MILLISECONDS.toSeconds(l);
                binding.txtResend.setText("Resend otp in " + sec);
            }


            @Override
            public void onFinish() {
                resend=true;
                binding.txtResend.setText("Resend Otp");
                binding.txtResend.setTextColor(getResources().getColor(R.color.purple_500));
            }
        }.start();
    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.length() == 1) {
            if (binding.etOtp1.length() == 1) {
                binding.etOtp2.requestFocus();
            }
            if (binding.etOtp2.length() == 1) {
                binding.etOtp3.requestFocus();
            }
            if (binding.etOtp3.length() == 1) {
                binding.etOtp4.requestFocus();
            }
        } else if (editable.length() == 0) {
            if (binding.etOtp4.length() == 0) {
                binding.etOtp3.requestFocus();
            }
            if (binding.etOtp3.length() == 0) {
                binding.etOtp2.requestFocus();
            }
            if (binding.etOtp2.length() == 0) {
                binding.etOtp1.requestFocus();
            }
        }

    }
}