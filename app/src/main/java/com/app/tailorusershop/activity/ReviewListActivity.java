package com.app.tailorusershop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.tailorusershop.R;
import com.app.tailorusershop.adapter.GetReviewsAdapter;
import com.app.tailorusershop.databinding.ActivityReviewListBinding;
import com.app.tailorusershop.responses.GalleryDataResponse;
import com.app.tailorusershop.responses.GetReviewListResponse;
import com.app.tailorusershop.responses.UpdateProfileResponse;
import com.app.tailorusershop.retrofit.CallWebService;
import com.app.tailorusershop.retrofit.ResponseHandler;
import com.app.tailorusershop.retrofit.WebServiceConstants;
import com.app.tailorusershop.utlis.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReviewListActivity extends AppCompatActivity implements GetReviewsAdapter.EditReviewListener {
    private ActivityReviewListBinding binding;
    private Context context=ReviewListActivity.this;
    private final String TAG="ReviewListActivity";
    private List<GetReviewListResponse.ReviewData> reviewData;
    private GetReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getReviews();
    }

    private void getReviews()
    {
        new CallWebService(this,WebServiceConstants.BASE_URL + WebServiceConstants.GET_REVIEWS+"/52", new HashMap<String, Object>(), GetReviewListResponse.class, CallWebService.APIType.GET, null, null, true, new ResponseHandler()  {
            @Override
            public void onSuccess(Object response) {
                GetReviewListResponse getReviewListResponse=(GetReviewListResponse) response;
                setUpRecyclerView(getReviewListResponse.getData());
            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void setUpRecyclerView(List<GetReviewListResponse.ReviewData> reviewData)
    {
        reviewsAdapter=new GetReviewsAdapter(context,reviewData);
        reviewsAdapter.setOnEditReviewListener(this);
        binding.rcReviewList.setAdapter(reviewsAdapter);
    }


    public void deleteReview(String reviewId)
    {
        JSONObject object=new JSONObject();
        try {
            object.put(WebServiceConstants.WebServiceParams.REVIEW_ID,reviewId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),object.toString());

        new CallWebService(this, WebServiceConstants.BASE_URL + WebServiceConstants.DELETE_REVIEWS, requestBody, new HashMap<String, String>(), UpdateProfileResponse.class, CallWebService.APIType.POST_WITH_BODY_NO_HEADER, null, null, true, new ResponseHandler() {
            @Override
            public void onSuccess(Object response) {
                UpdateProfileResponse profileResponse=(UpdateProfileResponse) response;
                Util.showSnack(profileResponse.getMessage(),binding.getRoot());
                getReviews();
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
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }


    @Override
    public void editReView(GetReviewListResponse.ReviewData reviewData) {
        startActivity(new Intent(context,EditReviewActivity.class)
                .putExtra(WebServiceConstants.WebServiceParams.T_RATING,reviewData.getTimelyOrderCompletionRating())
        .putExtra(WebServiceConstants.WebServiceParams.O_RATING,reviewData.getOrderCreationRating())
        .putExtra(WebServiceConstants.WebServiceParams.F_RATING,reviewData.getProductFittingRating())
        .putExtra(WebServiceConstants.WebServiceParams.REVIEW_ID,"8")
                .putExtra(WebServiceConstants.WebServiceParams.COMMENT,reviewData.getComments())
        .putExtra("review_tag","update"));
    }
}