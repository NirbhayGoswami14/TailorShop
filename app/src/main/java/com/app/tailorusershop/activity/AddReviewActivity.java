package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.app.tailorusershop.R;
import com.app.tailorusershop.databinding.ActivityAddReviewBinding;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.PrefManager;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddReviewActivity extends AppCompatActivity {

    private ActivityAddReviewBinding binding;
    private Context context = AddReviewActivity.this;
    private final String TAG = "AddReview";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);





        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAddReviewApi(binding.timelyRating.getRating(), binding.fittingRating.getRating(), binding.creationRating.getRating(), binding.etReview.getText().toString());
                //Log.d(TAG, "onClick:"+binding.rating.getRating());
            }
        });
    }

    private void callAddReviewApi(float t_rating, float f_rating, float o_rating, String comment) {
        JSONObject object = new JSONObject();
        try {
            if (t_rating != 0 && f_rating != 0 && o_rating != 0 && !comment.equals("")) {
                object.put(WebServiceConstants.WebServiceParams.USER_ID, PrefManager.getInstance(context).getUserDetails(PrefManager.USER_ID));
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

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.API_ADDREVIEW, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {

                Util.showSnack("Thank you for Rating", binding.getRoot());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_review_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.view_review) {
            startActivity(new Intent(context, ReviewListActivity.class));
        }
        return true;
    }


}