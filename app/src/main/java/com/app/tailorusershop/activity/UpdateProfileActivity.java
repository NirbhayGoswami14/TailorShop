package com.app.tailorusershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.tailorusershop.databinding.ActivityUpdateProfileBinding;
import com.app.tailorusershop.responses.LoginResponse;
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

public class UpdateProfileActivity extends AppCompatActivity {

    private ActivityUpdateProfileBinding binding;
    private int selectedId;
    private Context context=UpdateProfileActivity.this;
    private String gender="";
    private String TAG="UpdateProfile";
    private String mobile_no="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mobile_no = getIntent().getStringExtra("mobile");
      /*  binding.radiogrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=radioGroup.findViewById(i);
            }
        });*/

        binding.etSignUpNum.setText(mobile_no);

        binding.radioMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId=binding.radiogrpGender.getCheckedRadioButtonId();
                gender="M";
                Log.d(TAG, "onClick:"+gender);
            }
        });
        binding.radioFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId=binding.radiogrpGender.getCheckedRadioButtonId();
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
            object.put(WebServiceConstants.WebServiceParams.ID,PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.NAME,name);
            object.put(WebServiceConstants.WebServiceParams.MOBILE,mobile);
            object.put(WebServiceConstants.WebServiceParams.EMAIL,email);
            object.put(WebServiceConstants.WebServiceParams.GENDER,gender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.UPDATE, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response)
            {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse)response;
                if(profileResponse.getStatus().equals("success"))
                {

                    Util.showSnack("Profile Updated",binding.getRoot());
                    startActivity(new Intent(context,HomeActivity.class));
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

}