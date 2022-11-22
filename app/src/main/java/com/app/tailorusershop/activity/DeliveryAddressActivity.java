package com.app.tailorusershop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.tailorusershop.databinding.ActivityDeliveryAddressBinding;

public class DeliveryAddressActivity extends AppCompatActivity {

    private ActivityDeliveryAddressBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}