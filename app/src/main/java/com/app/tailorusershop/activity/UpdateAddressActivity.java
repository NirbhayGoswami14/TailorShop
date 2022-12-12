package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityAddAddressBinding;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdateAddressActivity extends AppCompatActivity {

    private ActivityAddAddressBinding binding;
    private Context context=UpdateAddressActivity.this;
    private String add_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Edit Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);


        add_id=getIntent().getStringExtra(WebServiceConstants.WebServiceParams.ID);
        binding.etState.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.STATE));
        binding.etPostalCode.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.PINCODE));
        binding.etAddress1.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.ADDRESS1));
        binding.etAddress2.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.ADDRESS2));
        binding.etCity.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.CITY));

        binding.btnSaveAddress.setOnClickListener(view -> {
            if (validation())
            {
                callUpdateAddressApi(add_id,binding.etAddress1.getText().toString(),binding.etAddress2.getText().toString(),binding.etPostalCode.getText().toString(),binding.etCity.getText().toString(),binding.etState.getText().toString());
            }
        });
    }

    private boolean validation()
    {
        if(binding.etAddress1.getText().toString().equals(""))
        {
            Toast.makeText(context, "Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.etCity.getText().toString().equals(""))
        {
            Toast.makeText(context, "Enter city", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.etPostalCode.getText().equals(""))
        {
            Toast.makeText(context, "Enter PostalCode", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.etState.getText().equals(""))
        {
            Toast.makeText(context, "Enter state", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void callUpdateAddressApi(String id, String address1, String address2, String pincode,String city,String state) {

        JSONObject object = new JSONObject();
        try {
            //object.put(WebServiceConstants.WebServiceParams.ID, PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.ID, id);
            object.put(WebServiceConstants.WebServiceParams.NAME, "Arun");
            object.put(WebServiceConstants.WebServiceParams.MOBILE, "9873820091");
            object.put(WebServiceConstants.WebServiceParams.ADDRESS1,address1);
            object.put(WebServiceConstants.WebServiceParams.ADDRESS2,address2);
            object.put(WebServiceConstants.WebServiceParams.CITY,city);
            object.put(WebServiceConstants.WebServiceParams.PINCODE, pincode);
            object.put(WebServiceConstants.WebServiceParams.STATE,state);
            object.put("default_addr","1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.EDIT_ADDRESSES, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse) response;
                Util.showSnack(profileResponse.getMessage(),binding.getRoot());
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