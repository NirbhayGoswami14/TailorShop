package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityAddReviewBinding;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EditReviewActivity extends AppCompatActivity {
    private ActivityAddReviewBinding binding;
    private Context context=EditReviewActivity.this;
    private final String TAG="EditReview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Edit Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        binding.btnSubmit.setText("Edit Review");

        binding.timelyRating.setRating(Float.parseFloat(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.T_RATING)));
        binding.fittingRating.setRating(Float.parseFloat(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.F_RATING)));
        binding.creationRating.setRating(Float.parseFloat(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.O_RATING)));
        binding.etReview.setText(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.COMMENT));

        binding.btnSubmit.setOnClickListener(view -> {
            callEditReviewApi(getIntent().getStringExtra(WebServiceConstants.WebServiceParams.REVIEW_ID),binding.timelyRating.getRating(),binding.fittingRating.getRating(),binding.creationRating.getRating(),binding.etReview.getText().toString());

        });
    }

    private void callEditReviewApi(String reviewId, float t_rating, float f_rating, float o_rating, String comment)
    {

        JSONObject object=new JSONObject();
        try {
            if (t_rating != 0 && f_rating != 0 && o_rating != 0 && !comment.equals("")) {
                object.put(WebServiceConstants.WebServiceParams.REVIEW_ID,reviewId);
                object.put(WebServiceConstants.WebServiceParams.T_RATING, String.valueOf(t_rating));
                object.put(WebServiceConstants.WebServiceParams.F_RATING, String.valueOf(f_rating));
                object.put(WebServiceConstants.WebServiceParams.O_RATING, String.valueOf(o_rating));
                object.put(WebServiceConstants.WebServiceParams.COMMENT, comment);

            } else {
                Util.showSnack("Give AtLeast One Rating and Comment", binding.getRoot());
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.EDIT_REVIEWS, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse)response;
                Util.showSnack(profileResponse.getMessage(), binding.getRoot());
            }

            @Override
            public void onFailure(String message) {
                Log.d(TAG, "onFailure:" + message);
                Util.showSnack(message, binding.getRoot());
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError:" + error);
                Util.showSnack(error, binding.getRoot());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

}