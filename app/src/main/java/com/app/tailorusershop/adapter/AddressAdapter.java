package com.app.tailorusershop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.activity.MapActivity;
import com.app.tailorusershop.databinding.ItemAddressesAdapterBinding;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.HOLDER> {
    private Context context;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemAddressesAdapterBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, int position) {
        holder.binding.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MapActivity.class));
            }
        });
        holder.binding.imgCall.setOnClickListener(view -> {context.startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:9586226356")));});

        holder.binding.imgWp.setOnClickListener(view -> {
            //String url = "https://api.whatsapp.com/send?phone="+number;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://api.whatsapp.com/send?phone=+917567306898"));
            context.startActivity(i);});
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemAddressesAdapterBinding binding;
        public HOLDER(@NonNull ItemAddressesAdapterBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
