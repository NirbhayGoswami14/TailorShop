package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.app.tailorusershop.R;
import com.app.tailorusershop.adapter.CategoriesAdapter;
import com.app.tailorusershop.databinding.ActivityCategoriesBinding;
import com.app.tailorusershop.responses.GetCategoriesResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements CategoriesAdapter.OnCategoriesClickListener {
    private ActivityCategoriesBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private List<GetCategoriesResponse.CategoryData> categoryData;
    private List<GetCategoriesResponse.CategoryData> tempData;
    private Context context=CategoriesActivity.this;
    private final String TAG="CategoriesActivity";
    private String cat_gender="M";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        callCategoriesApi();


        binding.txtMale.setBackgroundResource(R.drawable.black_bg);
        binding.txtMale.setTextColor(Color.WHITE);

        binding.txtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtMale.setBackgroundResource(R.drawable.black_bg);
                binding.txtMale.setTextColor(Color.WHITE);
                binding.txtFemale.setBackgroundResource(R.drawable.rounded_edit_text);
                binding.txtFemale.setTextColor(Color.BLACK);
                filterCategoryData("M");
                if (tempData!=null)
                {

                    setRecyclerView(tempData);
                }
            }
        });

        binding.txtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtFemale.setBackgroundResource(R.drawable.black_bg);
                binding.txtFemale.setTextColor(Color.WHITE);
                binding.txtMale.setBackgroundResource(R.drawable.rounded_edit_text);
                binding.txtMale.setTextColor(Color.BLACK);
                filterCategoryData("F");
                if (tempData!=null)
                {

                    setRecyclerView(tempData);
                }

            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(categoriesAdapter!=null)
                {

                    categoriesAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void callCategoriesApi() {
        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.GET_CATEGORIES, new HashMap<String, Object>(), GetCategoriesResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {

            @Override
            public void onSuccess(Object response) {
                GetCategoriesResponse categoriesResponse=(GetCategoriesResponse) response;
                categoryData=categoriesResponse.getCatData();
                filterCategoryData("M");
                if (tempData!=null)
                {
                    setRecyclerView(tempData);
                }
            }

            @Override
            public void onFailure(String message) {
                Util.showSnack(message,binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Util.showSnack(error,binding.getRoot());
            }
        });
    }

    private void setRecyclerView(List<GetCategoriesResponse.CategoryData> catData)
    {
        categoriesAdapter=new CategoriesAdapter(context,catData);
        categoriesAdapter.setOnCategoriesClickListener(this);
        binding.rcCategories.setLayoutManager(new LinearLayoutManager(context));
        binding.rcCategories.setAdapter(categoriesAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategoryClick(GetCategoriesResponse.CategoryData categoryData) {
        startActivity(new Intent(context,AddOrderActivity.class));
    }

    private void filterCategoryData(String m)
    {

        if(categoryData!=null)
        {
            tempData=new ArrayList<>();
            Log.d(TAG, "filterCategoryData:==>"+categoryData.size());
            for (int i=0;i<categoryData.size();i++)
            {
                if (categoryData.get(i).getGender().equals(m))
                {
                    tempData.add(categoryData.get(i));
                }
                else if(categoryData.get(i).getGender().equals(m))
                {
                    tempData.add(categoryData.get(i));
                }
            }
            cat_gender=m;

        }

    }


}