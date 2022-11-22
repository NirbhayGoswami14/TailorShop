package com.app.tailorusershop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.tailorusershop.activity.OrderDetailsActivity;
import com.app.tailorusershop.adapter.OrderStatusAdapter;
import com.app.tailorusershop.adapter.OrdersDataAdapter;
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

public class FragmentOrderList extends Fragment implements OrderStatusAdapter.OnStatusListener,OrdersDataAdapter.OnOrderDataClickListener {

    private FragmentOrderlistBinding binding;
    private Activity activity;
    private ArrayList<String> statusList;
    private OrderStatusAdapter orderStatusAdapter;
    private OrdersDataAdapter ordersDataAdapter;
    private List<GetOrdersResponse.OrderData> getOrdersList;


    public FragmentOrderList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderlistBinding.inflate(inflater, container, false);
        activity = requireActivity();
        statusList = new ArrayList<>();
        statusList.add("Active");
        statusList.add("In Progress");
        statusList.add("Past due date");
        statusList.add("Ready to trail");
        statusList.add("Ready to pickup");
        statusList.add("Delivered");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderStatusAdapter=new OrderStatusAdapter(activity,statusList);
        orderStatusAdapter.onSetStatusListener(this);
        binding.rcOrderStatus.setAdapter(orderStatusAdapter);

       /* ordersDataAdapter=new OrdersDataAdapter(activity);
        binding.rcOrders.setAdapter(ordersDataAdapter);*/
        getOrders("A","Active");

    }


    public void getOrders(String status,String s)
    {
        JSONObject object=new JSONObject();
        try {
            //object.put(WebServiceConstants.WebServiceParams.USER_ID, PrefManager.getInstance(activity).getUserDetails(PrefManager.USER_ID));
            object.put(WebServiceConstants.WebServiceParams.USER_ID,"52");
            object.put(WebServiceConstants.WebServiceParams.STATUS,status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),object.toString());

        new CallWebService(activity, WebServiceConstants.BASE_URL + WebServiceConstants.GET_ORDERS, requestBody, new HashMap<String, String>(), GetOrdersResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                GetOrdersResponse getOrdersResponse=(GetOrdersResponse) response;
                getOrdersList=getOrdersResponse.getData();
            setOrdersRecyclerView(getOrdersList,s);

            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message,binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error,binding.getRoot());
                binding.txtNoOrder.setVisibility(View.VISIBLE);
                binding.rcOrders.setVisibility(View.GONE);

            }
        });
  }

  private void setOrdersRecyclerView(List<GetOrdersResponse.OrderData> getOrdersList, String s)
  {
      if(getOrdersList.size()==0)
      {
        binding.txtNoOrder.setVisibility(View.VISIBLE);
        binding.rcOrders.setVisibility(View.GONE);

      }
      else
      {
          binding.txtNoOrder.setVisibility(View.GONE);
          binding.rcOrders.setVisibility(View.VISIBLE);

          ordersDataAdapter=new OrdersDataAdapter(activity,getOrdersList,s);
          ordersDataAdapter.setOnOrderDataClickListener(this);
          binding.rcOrders.setAdapter(ordersDataAdapter);

      }


  }

    @Override
    public void currentStatus(String status) {
        switch (status)
        {
            case "Active":
                getOrders("A",status);
                break;
            case "In Progress":
                getOrders("I",status);
                break;
            case "Past due date":
                getOrders("A",status);
                break;
            case "Ready to trail":
                getOrders("T",status);
                break;
            case "Ready to pickup":
                getOrders("P",status);
                break;
            case "Delivered":
                getOrders("D",status);
                break;
        }

    }

    @Override
    public void onOrderDataClick(GetOrdersResponse.OrderData orderData) {
        startActivity(new Intent(activity, OrderDetailsActivity.class).putExtra("o_id",orderData.getId()));
    }
}
