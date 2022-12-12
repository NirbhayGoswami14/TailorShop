package com.app.tailorusershop.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.ItemOrdersDataBinding;
import com.app.tailorusershop.responses.GetOrdersResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrdersDataAdapter extends RecyclerView.Adapter<OrdersDataAdapter.HOLDER> implements Filterable {
    private Context context;
    private List<GetOrdersResponse.OrderData> orderDataList;
    private List<GetOrdersResponse.OrderData> filterlist;
    private String status;
    private ItemFilter itemFilter=new ItemFilter();
    private OnOrderDataClickListener onOrderDataClickListener;
    private OnDeleteOrderListener onDeleteOrderListener;
    public OrdersDataAdapter(Context context, List<GetOrdersResponse.OrderData> getOrdersList, String s) {
        this.context = context;
        this.orderDataList = getOrdersList;
        filterlist=getOrdersList;
        status = s;
    }

    @NonNull
    @Override
    public OrdersDataAdapter.HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemOrdersDataBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrdersDataAdapter.HOLDER holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.txtOStatus.setText("" + status);
        holder.binding.txtOId.setText("#" + orderDataList.get(position).getVendorOrderId());
        holder.binding.txtOPrice.setText(PrefManager.getInstance(context).getRupeeUnicode() + orderDataList.get(position).getTotAmount());
        String[] deliveryDate = orderDataList.get(position).getDeliveryDate().split(" ");
        String[] trialDate = orderDataList.get(position).getDeliveryDate().split(" ");
        holder.binding.txtODate.setText("" + deliveryDate[0]);
        holder.binding.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteOrderListener.onOrderDelete(orderDataList.get(position).getId());
                //Toast.makeText(context, ""+orderDataList.get(position).getId()+"\t\t"+orderDataList.get(position).getVendorOrderId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderDataClickListener.onOrderDataClick(orderDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }



    public class HOLDER extends RecyclerView.ViewHolder {
        ItemOrdersDataBinding binding;

        public HOLDER(@NonNull ItemOrdersDataBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }


    public void setOnOrderDataClickListener(OnOrderDataClickListener onOrderDataClickListener) {
        this.onOrderDataClickListener = onOrderDataClickListener;
    }
    public void setOnDeleteOrderListener(OnDeleteOrderListener onOnDeleteOrderListener)
    {
        this.onDeleteOrderListener=onOnDeleteOrderListener;
    }

    public interface OnOrderDataClickListener {
        void onOrderDataClick(GetOrdersResponse.OrderData orderData);
    }
    public interface OnDeleteOrderListener
    {
             void onOrderDelete(String id);
    }


    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private class ItemFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filterString = charSequence.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<GetOrdersResponse.OrderData> list = filterlist;

            int count = list.size();
            final List<GetOrdersResponse.OrderData> newList = new ArrayList<>(count);

            GetOrdersResponse.OrderData orderData;

            for (int i = 0; i < count; i++) {
                orderData = list.get(i);
                if (orderData.getVendorOrderId().toLowerCase().contains(filterString)) {
                    newList.add(orderData);
                }
            }

            results.values = newList;
            results.count = newList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            orderDataList = (List<GetOrdersResponse.OrderData>)filterResults.values;
            notifyDataSetChanged();
        }
    }
}
