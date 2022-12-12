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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityOrderDetailsBinding;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.UserDialogLayoutBinding;
import com.app.tailorusershop.responses.GetReviewListResponse;
import com.app.tailorusershop.responses.OrderDetailsResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
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
    private String pdfUrl;
    private String isInvoiceShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Order Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        oid = getIntent().getStringExtra("o_id");

        callGetOrdersApi();

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
            if (orderDetailsResponse!=null)
            {
                deleteProDialog(orderDetailsResponse.getOrders().get(0).getId());
            }
        });



        binding.btnBill.setOnClickListener(view -> {
            Log.d(TAG, "onCreate===>:"+pdfUrl);
            if(isInvoiceShow.equals("Y"))
            {
                binding.btnBill.setVisibility(View.VISIBLE);
                startActivity(new Intent(context,InvoiceActivity.class).putExtra("url",pdfUrl));
            }
            else
            {
                Util.showSnack("Invoice not generated of this order",binding.getRoot());
            }
        });
    }

    private void callGetOrdersApi()
    {
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
                pdfUrl=orderDetailsResponse.getOrders().get(0).getInvoiceUrl();
                Log.d(TAG, "onSuccess:===>"+pdfUrl);

                //product details
                if (orderDetailsResponse.getProducts().isEmpty()) {
                    binding.constraintHolder.setVisibility(View.GONE);
                } else {
                    String[] deliveryDate = orderDetailsResponse.getProducts().get(0).getDeliveryDate().split(" ");
                    binding.txtOdDate.setText("Delivery Date :" + deliveryDate[0]);
                    binding.txtOrderTitle.setText("" + orderDetailsResponse.getProducts().get(0).getProdname());
                    binding.txtOPrice.setText("Price:" + orderDetailsResponse.getProducts().get(0).getPrice());
                    binding.txtProductStatus.setText("" + orderDetailsResponse.getProducts().get(0).getProdstatus());
                    isInvoiceShow= orderDetailsResponse.getProducts().get(0).getIsInvoiceShow();
                    Log.d(TAG, "onSuccess:"+isInvoiceShow);

                }
                if(isInvoiceShow.equals("Y"))
                {
                    binding.btnBill.setVisibility(View.VISIBLE);

                }
                else
                {
                    binding.btnBill.setVisibility(View.GONE);
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
        binding.txtMobileNo.setText("+91"+mo_number);
        binding.txtEmail.setText(email);
        binding.txtMobileNo.setOnClickListener(view ->{
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:"+mo_number)));});
        binding.imgWp.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://api.whatsapp.com/send?phone=+91"+mo_number));
            context.startActivity(i);
        });
        try {
        binding.txtEmail.setOnClickListener(view -> {
            Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+email));
            startActivity(intent);
        });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        dialog.create();
        dialog.show();

    }

    private void deleteProDialog(String id) {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callDeleteOrderApi(id);
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

    private void callDeleteOrderApi(String id) {
        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.DEL_ORDERS + "/" + id, new HashMap<String, Object>(), UpdateProfileResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse) response;
                Util.showSnack(profileResponse.getMessage(),binding.getRoot());
                callGetOrdersApi();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if (item.getItemId() == R.id.view_review) {
            startActivity(new Intent(context, ReviewListActivity.class));
        }

        return true;
    }



}