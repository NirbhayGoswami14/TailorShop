package com.app.tailorusershop.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.app.tailorusershop.R;
import com.app.tailorusershop.utlis.ConnectionCheck;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/*
import app.tailorshop.R;
import app.tailorshop.activity.HomeActivity;
import app.tailorshop.utils.CommonUtils;
import app.tailorshop.utils.ConnectionCheck;
import app.tailorshop.utils.PrefUtils;
import app.tailorshop.utils.ToastMaster;
*/
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallWebService {

    private APIType type;
    private Activity mActivity;
    private ResponseHandler responseHandler;
    private String url;
    private HashMap<String, Object> params;
    private boolean isGetMethod;
    private HashMap<String, RequestBody> fileParams;
    private HashMap<String, String> headers;
    private ArrayList<MultipartBody.Part> images;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayoutCompat progressBar;
    private boolean isShowDialog;
   private ConnectionCheck connectionCheck;
    private boolean isInternetAvailable=true;
    private Dialog mDialog;
    private Object param;
    private APIInterface apiInterface;
    private Call<String> call;
    private Callback<String> callback;
    private boolean isFileUpload = false;
    private Object model;
    private Object falseModel;
    private RequestBody requestBody;
   // private PrefUtils prefUtils;

    public enum APIType {
        GET,
        POST,
        GET_WITH_HEADER,
        POST_WITH_HEADER,
        POST_WITH_HEADER_NEW,
        MULTI_PART,
        MULTIPART_WITH_HEADER,
        POST_WITH_QUERY,
        POST_WITH_QUERY_HEADER,
        POST_WITH_BODY,
        PUT_WITH_HEADER,
        PUT,
        DELETE_WITH_HEADER,
        POST_WITH_BODY_NO_HEADER,
        DELETE
    }

    public CallWebService(Activity mActivity, HashMap<String, String> headers, String url, RequestBody param, Object model, APIType type, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
        //prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.params = null;
        this.requestBody = param;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = false;
        this.headers = headers;
        this.model = model;
        this.type = type;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();

    }

    public CallWebService(Activity mActivity, String url, HashMap<String, Object> params, Object model, APIType type, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
       // prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.params = params;
        this.isGetMethod = isGetMethod;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = false;
        this.headers = null;
        this.model = model;
        this.type = type;
        this.falseModel = null;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();

    }

    public CallWebService(Activity mActivity, String url, HashMap<String, Object> params, Object model, Object falseModel, APIType type, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
       // prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.params = params;
        this.isGetMethod = isGetMethod;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = false;
        this.headers = null;
        this.model = model;
        this.type = type;
        this.falseModel = falseModel;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();

    }

    public CallWebService(Activity mActivity, String url, HashMap<String, Object> params, HashMap<String, String> headers, Object model, APIType type, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
        //prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.params = params;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = false;
        this.headers = headers;
        this.model = model;
        this.type = type;
        this.falseModel = null;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();

    }

    public CallWebService(Activity mActivity, String url, HashMap<String, RequestBody> fileParams, HashMap<String, String> headers, Object model, APIType type, ArrayList<MultipartBody.Part> images, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
       // prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.fileParams = fileParams;
        this.images = images;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = true;
        this.model = model;
        this.headers = headers;
        this.type = type;
        this.falseModel = null;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();
    }

    public CallWebService(Activity mActivity, String url, RequestBody requestBody, HashMap<String, String> headers, Object model, APIType type, CoordinatorLayout coordinatorLayout, LinearLayoutCompat progressBar, boolean isShowDialog, ResponseHandler responseHandler) {
      //  prefUtils = new PrefUtils(mActivity);
        this.mActivity = mActivity;
        this.responseHandler = responseHandler;
        this.url = url;
        this.fileParams = null;
        this.images = null;
        this.coordinatorLayout = coordinatorLayout;
        this.progressBar = progressBar;
        this.isShowDialog = isShowDialog;
        this.isFileUpload = true;
        this.model = model;
        this.headers = null;
        this.type = type;
        this.falseModel = null;
        this.requestBody = requestBody;
        apiInterface = APIClient.getScalarAPIClient().create(APIInterface.class);
        checkInternetConnection();
    }

    private void checkInternetConnection() {
       connectionCheck = new ConnectionCheck(mActivity);
        isInternetAvailable = connectionCheck.isNetworkConnected();
        if (isInternetAvailable) {
            callAPI();
        } else {
            connectionCheck.showConnectionDialog().show();
        }
    }

    private void callAPI() {
        try {
            if (isShowDialog) {
                mDialog = new Dialog(mActivity);
                mDialog.setTitle("");
                mDialog.setCancelable(false);
                mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mDialog.setContentView(R.layout.dialog_progress);
                mDialog.show();
            } else {
                if (progressBar != null) {
                    if (progressBar.getVisibility() != View.VISIBLE)
                        progressBar.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (type) {
            case GET:
                Log.e("TAG", "callAPI: GET:: ");
                call = apiInterface.callGetService(url);
                break;

            case GET_WITH_HEADER:
                Log.e("TAG", "callAPI: GET_WITH_HEADER:: ");
                call = apiInterface.callGetService(headers, url, params);
                break;

            case POST:
                Log.e("TAG", "callAPI: POST:: ");
                call = apiInterface.callPostService(url, params);
                break;

            case POST_WITH_HEADER:
                Log.e("TAG", "callAPI: POST_WITH_HEADER:: ");
                call = apiInterface.callPostService(headers, url, params);
                break;

            case POST_WITH_HEADER_NEW:
                Log.e("TAG", "callAPI: POST_WITH_HEADER NEW :: ");
                call = apiInterface.callPostServiceNew(headers, url, params);
                break;

            case POST_WITH_QUERY_HEADER:
                call = apiInterface.callPostServiceWithQuery(headers, url, params);
                break;

            case POST_WITH_QUERY:
                call = apiInterface.callPostServiceWithQuery(url, params);
                break;

            case MULTI_PART:
                call = apiInterface.callMultipartService(url, fileParams, images);
                break;

            case MULTIPART_WITH_HEADER:
                call = apiInterface.callMultipartServiceHeader(headers, url, fileParams, images);
                break;

            case POST_WITH_BODY:
                Log.e("TAG", "callAPI: POST_WITH_BODY:: ");
                call = apiInterface.callPostServiceWithBody(headers,url, requestBody);
                break;

            case PUT_WITH_HEADER:
                Log.e("TAG", "callAPI: PUT_WITH_HEADER:: ");
                call = apiInterface.callPutServiceWithHeader(headers,url, requestBody);
                break;

            case DELETE_WITH_HEADER:
                Log.e("TAG", "callAPI: DELETE_WITH_HEADER:: ");
                call = apiInterface.callDeleteServiceWithHeader(headers,url);
                break;
            case POST_WITH_BODY_NO_HEADER:
                Log.e("TAG", "callAPI: POST_WITH_BODY_NO_HEADER::" );
                call=apiInterface.callPostServiceWithoutHeader(url,requestBody);
                break;
            case DELETE:
                Log.e("TAG", "callAPI: DELETE::" );
                call=apiInterface.callDeleteService(url);
                break;
            case PUT:
                Log.e("TAG", "callAPI: PUT :: ");
                call = apiInterface.callPutService(url, requestBody);
                break;
        }

        callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                dismissProgressDialog();

                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (object.getString("status").equalsIgnoreCase("success") ) {
                            if (model != null && model != String.class) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gsonBuilder.setDateFormat("M/d/yy hh:mm a"); //Format of our JSON dates
                                Gson gson = gsonBuilder.create();
                                responseHandler.onSuccess(gson.fromJson(response.body(), (Class) model));
                            } else {
                                responseHandler.onSuccess(response.body());
                            }
                        } else {
                            Log.e("TAG", "onResponse: message " + object.getString("message"));
                            Log.e("TAG", "onResponse: obj error" + object.toString());

                            if(object.getString("message").trim().toLowerCase().equalsIgnoreCase("token is expired")) {
                                //prefUtils.clearPrefs();
                                //CommonUtils.openActivityAndClearPreviousStack(mActivity, HomeActivity.class);
                            }else {

                                if(falseModel == null) {
                                    responseHandler.onError(object.getString("message"));
                                }else if(falseModel == String.class){
                                    responseHandler.onError(object.toString());
                                }
                            }

                        }
                    } catch (JSONException e) {
                        responseHandler.onError(mActivity.getString(R.string.error));
                        e.printStackTrace();
                    }


                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                    try {
//                        Log.e("TAG", "onResponse: error: " + response.errorBody().string());
                        String errorResponse = response.errorBody().string().trim();
                        Log.e("TAG", "onResponse: errorResponse: "+errorResponse );
                        JSONObject object = new JSONObject(errorResponse);
                        Log.e("TAG", "onResponse: "+object.toString() );
                        responseHandler.onError(object.getString("message"));
                    } catch (Exception e) {
                        responseHandler.onError(mActivity.getString(R.string.error));
                        e.printStackTrace();
                    }
                    Log.e("TAG", "onResponse: " + response.code());
                    Log.e("TAG", "onResponse: " + response.body());

                }

                Log.e("TAG", "onResponse: " + call.request().url());
            }

            @Override
            public void onFailure(final Call<String> call, Throwable t) {

                dismissProgressDialog();
                Log.e("TAG", "onResponse: " + t.getMessage());
                showSnackbar(t.getMessage());
                responseHandler.onFailure(t.getMessage());
            }
        };

        call.enqueue(callback);
    }

    private void showSnackbar(String message) {
        if (coordinatorLayout != null) {
            final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(ContextCompat.getColor(mActivity, R.color.grey_dark));
            snackbar.setAction(mActivity.getResources().getString(R.string.button_retry), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (progressBar != null) {
                        if (progressBar.getVisibility() == View.GONE)
                            progressBar.setVisibility(View.VISIBLE);
                    }
                    if (isShowDialog) {
                        try {
                            if (mDialog == null) {
                                mDialog = new Dialog(mActivity);
                                mDialog.setTitle("");
                                mDialog.setCancelable(false);
                                mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                mDialog.setContentView(R.layout.dialog_progress);
                            }
                            mDialog.show();
                        } catch (Exception e) {

                        }
                    }

                    call.clone().enqueue(callback);
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {
            //ToastMaster.showToastLong(mActivity, message);
        }
    }

    private void dismissProgressDialog() {
        try {
            if (progressBar != null) {
                if (progressBar.getVisibility() == View.VISIBLE)
                    progressBar.setVisibility(View.GONE);
            } else {
                if (mDialog != null && mDialog.isShowing())
                    mDialog.dismiss();
            }


        } catch (Exception e) {
            Log.e("TAG", "onResponse: " + e.getMessage());
            responseHandler.onError(e.getMessage());
        }
    }

}
