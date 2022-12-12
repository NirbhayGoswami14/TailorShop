package com.app.tailorusershop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ItemCategoriesAdapterBinding;
import com.app.tailorusershop.responses.GetCategoriesResponse;
import com.app.tailorusershop.responses.GetOrdersResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.HOLDER> implements Filterable {
    private Context context;
    private List<GetCategoriesResponse.CategoryData> categoryData;
    private List<GetCategoriesResponse.CategoryData> filterlist;
    private ItemFilter itemFilter=new ItemFilter();
    private OnCategoriesClickListener onCategoriesClickListener;
    private String cat_gender;

    public CategoriesAdapter(Context context, List<GetCategoriesResponse.CategoryData> categoryData) {
        this.context = context;
        this.categoryData = categoryData;
        filterlist=categoryData;

    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HOLDER(ItemCategoriesAdapterBinding.inflate(LayoutInflater.from(context),parent,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, int position) {
        holder.binding.txtCatNm.setText(categoryData.get(position).getName());
        if (categoryData.get(position).getGender().equals("M"))
        {
            holder.binding.txtMF.setText("Male");
        }
        else if (categoryData.get(position).getGender().equals("F"))
        {
            holder.binding.txtMF.setText("Female");
        }

        holder.itemView.setOnClickListener(view -> {
            onCategoriesClickListener.onCategoryClick(categoryData.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }



    public class HOLDER extends RecyclerView.ViewHolder {
        private ItemCategoriesAdapterBinding binding;
        public HOLDER(@NonNull ItemCategoriesAdapterBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
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

            final List<GetCategoriesResponse.CategoryData> list = filterlist;

            int count = list.size();
            final List<GetCategoriesResponse.CategoryData> newList = new ArrayList<>(count);

            GetCategoriesResponse.CategoryData catData;

            for (int i = 0; i < count; i++) {
                catData = list.get(i);
                if (catData.getName().toLowerCase().contains(filterString)) {
                    newList.add(catData);
                }
            }

            results.values = newList;
            results.count = newList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            categoryData = (List<GetCategoriesResponse.CategoryData>)filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void setOnCategoriesClickListener(OnCategoriesClickListener onCategoriesClickListener)
    {
        this.onCategoriesClickListener=onCategoriesClickListener;
    }

    public interface OnCategoriesClickListener
    {
        public void onCategoryClick(GetCategoriesResponse.CategoryData categoryData);
    }
}
