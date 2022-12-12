package com.app.tailorusershop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.databinding.ItemPatternImageBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PatternImageAdapter extends RecyclerView.Adapter<PatternImageAdapter.HOLDER> {
    private Context context;
    private ArrayList<String> imgList;

    public PatternImageAdapter(Context context, ArrayList<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemPatternImageBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(imgList.get(position)).into(holder.binding.imgAddPattern);
        holder.binding.cardRemoveImg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                imgList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(),imgList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemPatternImageBinding binding;
        public HOLDER(@NonNull ItemPatternImageBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
