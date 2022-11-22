package com.app.tailorusershop.retrofit;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<String> callGetService(@Url String url);

    @GET
    Call<String> callGetService(@HeaderMap HashMap<String, String> headers, @Url String url, @QueryMap HashMap<String, Object> params);


    @FormUrlEncoded
    @POST
    Call<String> callPostService(@Url String url, @FieldMap HashMap<String, Object> params);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @FormUrlEncoded
    @POST
    Call<String> callPostService(@HeaderMap HashMap<String, String> headers, @Url String url, @FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> callPostServiceNew(@HeaderMap HashMap<String, String> headers, @Url String url, @FieldMap HashMap<String, Object> params);


    @POST
    Call<String> callPostServiceWithQuery(@Url String url, @QueryMap HashMap<String, Object> params);

    @POST
    Call<String> callPostServiceWithQuery(@HeaderMap HashMap<String, String> headers, @Url String url, @QueryMap HashMap<String, Object> params);


    @Multipart
    @POST
    Call<String> callMultipartService(@Url String url, @PartMap HashMap<String, RequestBody> params, @Part ArrayList<MultipartBody.Part> images);

    @Multipart
    @POST
    Call<String> callMultipartServiceHeader(@HeaderMap HashMap<String, String> headers, @Url String url, @PartMap HashMap<String, RequestBody> params, @Part ArrayList<MultipartBody.Part> images);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @Multipart
    @POST
    Call<String> callPostServiceWithRequest(@HeaderMap HashMap<String, String> headers, @Url String url, @PartMap HashMap<String, RequestBody> params);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST
    Call<String> callPostServiceWithBody(@HeaderMap HashMap<String, String> headers, @Url String url, @Body RequestBody object);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT
    Call<String> callPutServiceWithHeader(@HeaderMap HashMap<String, String> headers, @Url String url, @Body RequestBody object);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE
    Call<String> callDeleteServiceWithHeader(@HeaderMap HashMap<String, String> headers, @Url String url);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST
    Call<String> callPostServiceWithoutHeader(@Url String url, @Body RequestBody object);

    @DELETE
    Call<String> callDeleteService(@Url String url);

    @PUT
    Call<String> callPutService(@Url String url, @Body RequestBody object);
}

