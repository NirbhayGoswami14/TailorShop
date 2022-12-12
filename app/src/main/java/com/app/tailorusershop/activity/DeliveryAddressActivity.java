package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.app.tailorusershop.R;
import com.app.tailorusershop.adapter.AddressAdapter;
import com.app.tailorusershop.databinding.ActivityDeliveryAddressBinding;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.responses.GalleryDataResponse;
import com.app.tailorusershop.responses.GetAddressResponse;
import com.app.tailorusershop.responses.GetCategoriesResponse;
import com.app.tailorusershop.responses.GetProfileResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import java.util.HashMap;
import java.util.List;

public class DeliveryAddressActivity extends AppCompatActivity implements AddressAdapter.OnEditAddressListener, AddressAdapter.OnDeleteAddressListener {

    private ActivityDeliveryAddressBinding binding;
    private Context context = DeliveryAddressActivity.this;
    private final String TAG = "DeliveryAddress";
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Locations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        callGetAddressApi();

        binding.btnNewAddress.setOnClickListener(view -> {
            startActivity(new Intent(context,AddAddressActivity.class));
        });
    }

    private void callGetAddressApi() {
        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.GET_ADDRESSES + "/" + "52", new HashMap<String, Object>(), GetAddressResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                GetAddressResponse getAddressResponse = (GetAddressResponse) response;
                setRecyclerView(getAddressResponse.getData());
            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message, binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error, binding.getRoot());
            }
        });
    }

    private void setRecyclerView(List<GetAddressResponse.AddressData> data) {
        addressAdapter = new AddressAdapter(context, data);
        addressAdapter.setOnDeleteAddressListener(this);
        addressAdapter.setOnEditAddressListener(this);
        binding.rcAddresses.setAdapter(addressAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEdit(GetAddressResponse.AddressData addressData) {
        startActivity(new Intent(context, UpdateAddressActivity.class)
                .putExtra(WebServiceConstants.WebServiceParams.ID, addressData.getId())
                .putExtra(WebServiceConstants.WebServiceParams.ADDRESS1, addressData.getAddress1())
                .putExtra(WebServiceConstants.WebServiceParams.ADDRESS2, addressData.getAddress2())
                .putExtra(WebServiceConstants.WebServiceParams.CITY, addressData.getCity())
                .putExtra(WebServiceConstants.WebServiceParams.PINCODE, addressData.getPincode())
                .putExtra(WebServiceConstants.WebServiceParams.STATE, addressData.getState()));
    }

    @Override
    public void onDelete(GetAddressResponse.AddressData addressData) {

        deleteAddressDialog(addressData.getId());
    }

    private void deleteAddressDialog(String id) {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Delete Address");
        binding.txtDelMsg.setText("Are you sure you want to delete Address ?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new CallWebService(DeliveryAddressActivity.this, WebServiceConstants.BASE_URL + WebServiceConstants.DEL_ADDRESSES + "/" + id, new HashMap<String, Object>(), UpdateProfileResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {


                    @Override
                    public void onSuccess(Object response) {
                        UpdateProfileResponse profileResponse = (UpdateProfileResponse) response;
                        Util.showSnack(profileResponse.getMessage(), binding.getRoot());
                        callGetAddressApi();
                    }

                    @Override
                    public void onFailure(String message) {
                        Util.showSnack(message, binding.getRoot());
                    }

                    @Override
                    public void onError(String error) {
                        Util.showSnack(error, binding.getRoot());
                    }
                });


                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callGetAddressApi();
    }
}