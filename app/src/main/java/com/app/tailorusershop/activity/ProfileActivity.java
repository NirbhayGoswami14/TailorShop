package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityProfileBinding;
import com.app.tailorusershop.databinding.ActivityUpdateProfileBinding;
import com.app.tailorusershop.responses.GetProfileResponse;
import com.app.tailorusershop.responses.OrderDetailsResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity {

    //private ActivityProfileBinding binding;
    private ActivityUpdateProfileBinding binding;
    private Context context=ProfileActivity.this;
    private String gender="";
    private String userId="";
    private final String TAG="ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        binding.imgTop.setVisibility(View.GONE);
        binding.imgLeft.setVisibility(View.GONE);
        binding.imgLogo.setVisibility(View.GONE);
        binding.btnAddress.setVisibility(View.VISIBLE);
        userId=PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID);
        Log.d(TAG, "onCreate:===>null"+userId);
        callGetProfileApi();

        binding.radioMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gender="M";
                Log.d(TAG, "onClick:"+gender);
            }
        });
        binding.radioFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="F";
                Log.d(TAG, "onClick:"+gender);
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation())
                {

                    callUpdateProfileApi(binding.etFullName.getText().toString(),binding.etSignUpNum.getText().toString(),binding.etEmail.getText().toString(),gender);
                }
            }
        });

        binding.btnAddress.setOnClickListener(view -> { startActivity(new Intent(context,DeliveryAddressActivity.class));});

    }

    private void callGetProfileApi()
    {
        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.GET_PROFILE + "/" +userId, new HashMap<String, Object>(), GetProfileResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                GetProfileResponse profileResponse=(GetProfileResponse) response;
                binding.etFullName.setText(profileResponse.data.get(0).getName());
                binding.etEmail.setText(profileResponse.data.get(0).getEmail());
                binding.etSignUpNum.setText(profileResponse.data.get(0).getMobileNo());
                if(profileResponse.data.get(0).getGender() != null)
                {
                    if (profileResponse.data.get(0).getGender().equals("M"))
                    {

                        binding.radiogrpGender.check(R.id.radio_male);
                        gender="M";
                    }
                    else
                    {
                        binding.radiogrpGender.check(R.id.radio_female);
                        gender="F";
                    }
                }


                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_ID,profileResponse.getData().get(0).getId());
                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_NM,profileResponse.getData().get(0).getName());
                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_MOBILE,profileResponse.getData().get(0).getMobileNo());
                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_EMAIL,profileResponse.getData().get(0).getEmail());
                PrefManager.getInstance(context).setUserDetails(PrefManager.USER_GENDER,profileResponse.getData().get(0).getGender());

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }


    private boolean validation()
    {
        if(binding.etFullName.getText().toString().equals(""))
        {
            Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etSignUpNum.getText().toString().equals(""))
        {
            Toast.makeText(context, "Enter mobile No", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.etSignUpNum.getText().length()<10)
        {
            Toast.makeText(context, "Enter valid mobile no", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(gender.equals(""))
        {
            Toast.makeText(context, "Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private void callUpdateProfileApi(String name,String mobile,String email,String gender)
    {
        JSONObject object=new JSONObject();
        try {
            //object.put(WebServiceConstants.WebServiceParams.ID, PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.ID, userId);
            object.put(WebServiceConstants.WebServiceParams.NAME,name);
            object.put(WebServiceConstants.WebServiceParams.MOBILE,mobile);
            object.put(WebServiceConstants.WebServiceParams.EMAIL,email);
            object.put(WebServiceConstants.WebServiceParams.GENDER,gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.UPDATE_PROFILE, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response)
            {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse)response;
                if(profileResponse.getStatus().equals("success"))
                {

                    Util.showSnack("Profile Updated",binding.getRoot());

                    callGetProfileApi();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

