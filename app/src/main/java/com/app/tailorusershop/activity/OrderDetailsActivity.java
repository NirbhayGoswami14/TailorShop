package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityOrderDetailsBinding;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.UserDialogLayoutBinding;
import com.app.tailorusershop.responses.GetReviewListResponse;
import com.app.tailorusershop.responses.OrderDetailsResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {

    private ActivityOrderDetailsBinding binding;
    private Context context = OrderDetailsActivity.this;
    private final String TAG = "OrderDetails";
    private String oid = "0";
    private OrderDetailsResponse orderDetailsResponse;
    private String email = "";
    private String mo_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Order Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        oid = getIntent().getStringExtra("o_id");

        binding.imgUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDialog();
            }
        });
        binding.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + mo_number)));
            }
        });

        binding.imgDel.setOnClickListener(view -> {
            deleteProDialog();
        });

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.GET_ORDER_DETAILS + "/" + oid, new HashMap<String, Object>(), OrderDetailsResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Object response) {

                orderDetailsResponse = (OrderDetailsResponse) response;
                binding.txtOId.setText("Order #" + orderDetailsResponse.getOrders().get(0).getVendorOrderId());
                binding.txtOdName.setText("" + orderDetailsResponse.getOrders().get(0).getName());
                binding.txtOStatus.setText("" + orderDetailsResponse.getOrders().get(0).getOrderStatus());
                mo_number = orderDetailsResponse.getOrders().get(0).getPhoneNo();
                email = orderDetailsResponse.getOrders().get(0).getEmail();
                //product details
                if (orderDetailsResponse.getProducts().isEmpty()) {
                    binding.constraintHolder.setVisibility(View.GONE);
                } else {
                    String[] deliveryDate = orderDetailsResponse.getProducts().get(0).getDeliveryDate().split(" ");
                    binding.txtOdDate.setText("Delivery Date :" + deliveryDate[0]);
                    binding.txtOrderTitle.setText("" + orderDetailsResponse.getProducts().get(0).getProdname());
                    binding.txtOPrice.setText("Price:" + orderDetailsResponse.getProducts().get(0).getPrice());
                    binding.txtProductStatus.setText("" + orderDetailsResponse.getProducts().get(0).getProdstatus());

                }

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


    private void userDialog() {
        UserDialogLayoutBinding binding = UserDialogLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        binding.txtMobileNo.setText(mo_number);
        binding.txtEmail.setText(email);
        dialog.create();
        dialog.show();

    }

    private void deleteProDialog() {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }


}