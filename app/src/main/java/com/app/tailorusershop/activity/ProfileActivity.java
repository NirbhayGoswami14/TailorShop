package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityProfileBinding;
import com.app.tailorusershop.databinding.ActivityUpdateProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    //private ActivityProfileBinding binding;
    private ActivityUpdateProfileBinding binding;
    private Context context=ProfileActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        binding.imgTop.setVisibility(View.GONE);
        binding.imgLeft.setVisibility(View.GONE);
        binding.imgLogo.setVisibility(View.GONE);
        binding.btnAddress.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

