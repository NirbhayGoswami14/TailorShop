package com.app.tailorusershop.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.R;
import com.app.tailorusershop.activity.GalleryImagesActvity;
import com.app.tailorusershop.databinding.ImageShowDialogBinding;
import com.app.tailorusershop.databinding.ItemGalleryImagesAdapterBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.HOLDER> {
    private Context context;
    private List<String> imgList;

    public GalleryImageAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public GalleryImageAdapter.HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HOLDER(ItemGalleryImagesAdapterBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageAdapter.HOLDER holder, int position) {

        holder.itemView.setOnClickListener(view -> {
            imageDialog(imgList.get(position));
        });
        Glide.with(context).load(imgList.get(position)).placeholder(R.drawable.order_clothes).into(holder.binding.galleryImg);
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemGalleryImagesAdapterBinding binding;
        public HOLDER(@NonNull  ItemGalleryImagesAdapterBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    private void imageDialog(String img)
    {
        ImageShowDialogBinding binding=ImageShowDialogBinding.inflate(LayoutInflater.from(context));
        Dialog dialog=new Dialog(context);
        dialog.setContentView(binding.getRoot());
        Glide.with(context).load(img).placeholder(R.drawable.order_clothes).into(binding.img);
        dialog.show();
    }
}
