package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.app.tailorusershop.R;
import com.app.tailorusershop.adapter.PatternImageAdapter;
import com.app.tailorusershop.databinding.ActivityAddOrderBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddOrderActivity extends AppCompatActivity {
    private ActivityAddOrderBinding binding;
    private Context context = AddOrderActivity.this;
    private ArrayList<String> imgList;
    private final String TAG = "AddOrderActivity";
    private String date = "";
    private String categoryId = "";
    private String categoryName = "";
    private long trailDate = 0;
    private Calendar c;
    int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        imgList = new ArrayList<>(5);

        final Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        binding.imgAddPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                   /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");*/
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(intent, 100);

                }
            }
        });
        binding.etTrialDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    binding.etTrialDate.setText(i + "-" + (i1 + 1) + "-" + i2);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(binding.etTrialDate.getText().toString());
                        trailDate = date.getTime();
                        Log.d(TAG, "onDateSet:" + trailDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        binding.etDeliveryDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    binding.etDeliveryDate.setText(i + "-" + (i1 + 1) + "-" + i2);

                }
            }, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(trailDate + 1);
            datePickerDialog.show();
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 201);
            return false;
        }
        return true;
    }


    private void setRecyclerView(ArrayList<String> imgList) {
        PatternImageAdapter patternImageAdapter = new PatternImageAdapter(context, imgList);
        binding.rcPatternImg.setAdapter(patternImageAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getClipData()==null) {
                    String res = getRealPathFromURI(data.getData());
                    Log.d(TAG, "onActivityResult:"+res);
                    imgList.add(res);
                    setRecyclerView(imgList);
                } else if (data.getClipData() != null) {
                    for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                        String res = getRealPathFromURI(data.getClipData().getItemAt(i).getUri());
                        imgList.add(res);

                    }
                    setRecyclerView(imgList);
                }


            }
        }
    }

    public String getRealPathFromURI(Uri uri) {

        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

        return cursor.getString(idx);

    }
}