package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.tailorusershop.R;
import com.app.tailorusershop.adapter.GalleryImageAdapter;
import com.app.tailorusershop.databinding.ActivityGalleryImagesActvityBinding;
import com.app.tailorusershop.responses.GalleryDataResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import java.util.HashMap;
import java.util.List;

public class GalleryImagesActvity extends AppCompatActivity {
    private ActivityGalleryImagesActvityBinding binding;
    private Context context=GalleryImagesActvity.this;
    private final String TAG="GalleryImagesActivity";
    private String imgTag="Kurti";
    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGalleryImagesActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        imgTag=getIntent().getStringExtra("TAG");



        new CallWebService(this,WebServiceConstants.BASE_URL + WebServiceConstants.GET_GALLERY_DATA, new HashMap<String, Object>(), GalleryDataResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                GalleryDataResponse dataResponse=(GalleryDataResponse) response;
                if(imgTag.equals("kurti"))
                {
                    getSupportActionBar().setTitle("Kurti");
                    imageList=dataResponse.getData().getKurti().getImages();
                    binding.rcGalleryImage.setAdapter(new GalleryImageAdapter(context, imageList));
                }
                else if (imgTag.equals("blouse"))
                {
                    getSupportActionBar().setTitle("Blouse");
                    imageList=dataResponse.getData().getBlouse().getImages();
                    binding.rcGalleryImage.setAdapter(new GalleryImageAdapter(context, imageList));
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




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}