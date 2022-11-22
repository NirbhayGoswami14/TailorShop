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

import com.app.tailorusershop.activity.GalleryImagesActvity;
import com.app.tailorusershop.databinding.FragmentGalleryBinding;
import com.app.tailorusershop.responses.GalleryDataResponse;
import com.app.tailorusershop.responses.GetOrdersResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;

import java.util.HashMap;

public class FragmentGallery extends Fragment {
    private FragmentGalleryBinding binding;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding=FragmentGalleryBinding.inflate(inflater,container,false);
       activity=requireActivity();
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardViewKurtiHolder.setOnClickListener(view1 -> {
            startActivity(new Intent(activity, GalleryImagesActvity.class).putExtra("TAG","kurti"));
        });
        binding.cardViewBlouseHolder.setOnClickListener(view1 -> {
            startActivity(new Intent(activity, GalleryImagesActvity.class).putExtra("TAG","blouse"));
        });

    }

}
