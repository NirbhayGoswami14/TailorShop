package com.app.tailorusershop.adapter;

import android.annotation.SuppressLint;
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
import com.app.tailorusershop.responses.GetAddressResponse;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.HOLDER> {
    private Context context;
    private List<GetAddressResponse.AddressData> addressData;
    private OnEditAddressListener onEditAddressListener;
    private OnDeleteAddressListener onDeleteAddressListener;


    public AddressAdapter(Context context, List<GetAddressResponse.AddressData> addressData) {
        this.context = context;
        this.addressData = addressData;
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemAddressesAdapterBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, int position) {
       holder.binding.txtAddress.setText(addressData.get(position).getAddress1()+","+addressData.get(position).getAddress2()
               +",\n"+addressData.get(position).getCity()+","+addressData.get(position).getPincode()+","+addressData.get(position).getState());
       holder.binding.imgEdit.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               onEditAddressListener.onEdit(addressData.get(position));
           }
       });

       holder.binding.imgDel.setOnClickListener(view -> {
           onDeleteAddressListener.onDelete(addressData.get(position));
       });
    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemAddressesAdapterBinding binding;
        public HOLDER(@NonNull ItemAddressesAdapterBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    public void setOnEditAddressListener(OnEditAddressListener onEditAddressListener)
    {
        this.onEditAddressListener=onEditAddressListener;
    }
    public void setOnDeleteAddressListener(OnDeleteAddressListener onDeleteAddressListener)
    {
        this.onDeleteAddressListener=onDeleteAddressListener;
    }


    public interface OnEditAddressListener
    {
        public void onEdit(GetAddressResponse.AddressData addressData);
    }

    public interface OnDeleteAddressListener
    {
        public void onDelete(GetAddressResponse.AddressData addressData);
    }
}


/* holder.binding.imgLocation.setOnClickListener(new View.OnClickListener() {
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
            context.startActivity(i);});*/
