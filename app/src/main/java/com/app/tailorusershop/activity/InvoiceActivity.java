package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityInvoiceBinding;
import com.app.tailorusershop.retrofit.APIClient;
import com.app.tailorusershop.utlis.Util;
import com.google.android.datatransport.backend.cct.BuildConfig;

public class InvoiceActivity extends AppCompatActivity {
    private ActivityInvoiceBinding binding;
    private Context context=InvoiceActivity.this;
    private final String TAG="InvoiceActivity";
    private String url="";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Invoice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        url=getIntent().getStringExtra("url");

        Log.d(TAG, "onCreate:1"+APIClient.BASE_URL+url);
    binding.webPdf.getSettings().setBuiltInZoomControls(true);
        binding.webPdf.getSettings().setDisplayZoomControls(false);
        binding.webPdf.getSettings().setJavaScriptEnabled(true);
        binding.webPdf.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                binding.progressPdf.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Util.showSnack("Failed to load try again",binding.getRoot());
                super.onReceivedError(view, request, error);
            }
        });
        binding.webPdf.loadUrl("http://docs.google.com/gview?embedded=true&url="+APIClient.BASE_URL+url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invoice_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        else if(item.getItemId()==R.id.share_pdf)
        {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = APIClient.BASE_URL+url;
            //shareMessage = shareMessage + "VGreen Under Development Stay Tuned\t\t" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }

        return true;
    }
}