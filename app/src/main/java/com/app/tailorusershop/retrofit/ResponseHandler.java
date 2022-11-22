package com.app.tailorusershop.retrofit;

public interface ResponseHandler {

    public void onSuccess(Object response);

    public void onFailure(String message);

    public void onError(String error);
}
