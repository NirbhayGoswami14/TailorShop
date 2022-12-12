package com.app.tailorusershop.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.tailorusershop.activity.OrderDetailsActivity;
import com.app.tailorusershop.adapter.OrderStatusAdapter;
import com.app.tailorusershop.adapter.OrdersDataAdapter;
import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.FragmentOrderlistBinding;
import com.app.tailorusershop.responses.GetOrdersResponse;
import com.app.tailorusershop.responses.OtpResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FragmentOrderList extends Fragment implements OrderStatusAdapter.OnStatusListener, OrdersDataAdapter.OnOrderDataClickListener, OrdersDataAdapter.OnDeleteOrderListener {

    private FragmentOrderlistBinding binding;
    private Activity activity;
    private ArrayList<String> statusList;
    private OrderStatusAdapter orderStatusAdapter;
    private OrdersDataAdapter ordersDataAdapter;
    private List<GetOrdersResponse.OrderData> getOrdersList;
    private String temStatus = "A";
    private String tempS = "Active";


    public FragmentOrderList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderlistBinding.inflate(inflater, container, false);
        activity = requireActivity();
        statusList = new ArrayList<>();
        statusList.add("Active");
        statusList.add("Delivered");
        statusList.add("Cancelled");
        /*statusList.add("In Progress");
        statusList.add("Past due date");
        statusList.add("Ready to trail");
        statusList.add("Ready to pickup");*/
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderStatusAdapter = new OrderStatusAdapter(activity, statusList);
        orderStatusAdapter.onSetStatusListener(this);
        binding.rcOrderStatus.setAdapter(orderStatusAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ordersDataAdapter != null) {

                    ordersDataAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       /* ordersDataAdapter=new OrdersDataAdapter(activity);
        binding.rcOrders.setAdapter(ordersDataAdapter);*/
        getOrders("A", "Active");

    }


    public void getOrders(String status, String s) {
        JSONObject object = new JSONObject();
        try {
            //object.put(WebServiceConstants.WebServiceParams.USER_ID, PrefManager.getInstance(activity).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.USER_ID, "52");
            object.put(WebServiceConstants.WebServiceParams.STATUS, status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(activity, WebServiceConstants.BASE_URL + WebServiceConstants.GET_ORDERS, requestBody, new HashMap<String, String>(), GetOrdersResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                GetOrdersResponse getOrdersResponse = (GetOrdersResponse) response;
                getOrdersList = getOrdersResponse.getData();
                setOrdersRecyclerView(getOrdersList, s);

            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message, binding.getRoot());
            }

            @Override
            public void onError(String error) {
               // Util.showSnack(error, binding.getRoot());
                binding.txtNoOrder.setVisibility(View.VISIBLE);
                binding.rcOrders.setVisibility(View.GONE);

            }
        });
    }

    private void setOrdersRecyclerView(List<GetOrdersResponse.OrderData> getOrdersList, String s) {
        if (getOrdersList.size() == 0) {
            binding.txtNoOrder.setVisibility(View.VISIBLE);
            binding.rcOrders.setVisibility(View.GONE);

        } else {
            binding.txtNoOrder.setVisibility(View.GONE);
            binding.rcOrders.setVisibility(View.VISIBLE);

            ordersDataAdapter = new OrdersDataAdapter(activity, getOrdersList, s);
            ordersDataAdapter.setOnOrderDataClickListener(this);
            ordersDataAdapter.setOnDeleteOrderListener(this);
            binding.rcOrders.setAdapter(ordersDataAdapter);


        }


    }

    @Override
    public void currentStatus(String status) {
        switch (status) {
            case "Active":
                getOrders("A", status);
                binding.etSearch.setText("");
                temStatus = "A";
                tempS = status;
                break;
           /* case "In Progress":
                getOrders("I",status);
                binding.etSearch.setText("");
                break;
            case "Past due date":
                getOrders("A",status);
                binding.etSearch.setText("");
                break;
            case "Ready to trail":
                getOrders("T",status);
                binding.etSearch.setText("");
                break;
            case "Ready to pickup":
                getOrders("P",status);
                binding.etSearch.setText("");
                break;
           */
            case "Delivered":
                getOrders("D", status);
                binding.etSearch.setText("");
                temStatus = "D";
                tempS = status;
                break;
            case "Cancelled":
                getOrders("C", status);
                binding.etSearch.setText("");
                temStatus = "C";
                tempS = status;
                break;
        }

    }


    @SuppressLint("SetTextI18n")
    private void deleteProDialog(String id) {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(LayoutInflater.from(activity));
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Delete Order");
        binding.txtDelMsg.setText("Are you sure you want to delete Order ?");
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
        new CallWebService(activity, WebServiceConstants.BASE_URL + WebServiceConstants.DEL_ORDERS + "/" + id, new HashMap<String, Object>(), UpdateProfileResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse = (UpdateProfileResponse) response;
                Util.showSnack(profileResponse.getMessage(), binding.getRoot());
                getOrders(temStatus,tempS);
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

    @Override
    public void onOrderDataClick(GetOrdersResponse.OrderData orderData) {
        startActivity(new Intent(activity, OrderDetailsActivity.class).putExtra("o_id", orderData.getId()));
    }


    @Override
    public void onOrderDelete(String id) {
        //Log.d("deleteOrder", "onOrderDelete:"+id);
        deleteProDialog(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders("A", "Active");

    }
}
