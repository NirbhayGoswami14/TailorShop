package com.app.tailorusershop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ItemOrderStatusBinding;

import java.util.ArrayList;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.HOLDER> {
    private Context context;
    private ArrayList<String> statusList;
    private int tempIndex=0;
    private OnStatusListener onStatusListener;
    public OrderStatusAdapter(Context context, ArrayList<String> statusList) {
        this.context = context;
        this.statusList=statusList;
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemOrderStatusBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, @SuppressLint("RecyclerView") int position) {
        if (tempIndex==position)
        {
            holder.binding.txtOStatus.setText(statusList.get(position));
            holder.binding.txtOStatus.setBackgroundResource(R.drawable.black_bg);
            holder.binding.txtOStatus.setTextColor(Color.WHITE);
        }
        else
        {
            holder.binding.txtOStatus.setText(statusList.get(position));
            holder.binding.txtOStatus.setBackgroundResource(R.drawable.rounded_edit_text);
            holder.binding.txtOStatus.setTextColor(Color.BLACK);
        }



        holder.itemView.setOnClickListener(view -> {
            tempIndex=position;
            onStatusListener.currentStatus(statusList.get(position));
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemOrderStatusBinding binding;
        public HOLDER(@NonNull ItemOrderStatusBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;

        }
    }

    public void onSetStatusListener(OnStatusListener onStatusListener)
    {
        this.onStatusListener=onStatusListener;
    }

    public interface OnStatusListener
    {
        void currentStatus(String status);
    }
}
