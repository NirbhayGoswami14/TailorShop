package com.app.tailorusershop.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.databinding.DeleteDialogLayoutBinding;
import com.app.tailorusershop.databinding.ItemOrdersDataBinding;
import com.app.tailorusershop.responses.GetOrdersResponse;

import java.util.List;

public class OrdersDataAdapter extends RecyclerView.Adapter<OrdersDataAdapter.HOLDER> {
    private Context context;
    private List<GetOrdersResponse.OrderData> orderDataList;
    private String status;
    private OnOrderDataClickListener onOrderDataClickListener;

    public OrdersDataAdapter(Context context, List<GetOrdersResponse.OrderData> getOrdersList, String s) {
        this.context = context;
        this.orderDataList = getOrdersList;
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
        holder.binding.txtOPrice.setText("" + orderDataList.get(position).getTotAmount());
        String[] deliveryDate = orderDataList.get(position).getDeliveryDate().split(" ");
        holder.binding.txtODate.setText("" + deliveryDate[0]);
        holder.binding.imgDel.setOnClickListener(view -> {
            deleteProDialog();
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

    @SuppressLint("SetTextI18n")
    private void deleteProDialog() {
        DeleteDialogLayoutBinding binding = DeleteDialogLayoutBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setView(binding.getRoot());
        binding.txtTitle.setText("Delete Order");
        binding.txtDelMsg.setText("Are you sure you want to delete Order ?");
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

    public void setOnOrderDataClickListener(OnOrderDataClickListener onOrderDataClickListener) {
        this.onOrderDataClickListener = onOrderDataClickListener;
    }

    public interface OnOrderDataClickListener {
        void onOrderDataClick(GetOrdersResponse.OrderData orderData);
    }
}
