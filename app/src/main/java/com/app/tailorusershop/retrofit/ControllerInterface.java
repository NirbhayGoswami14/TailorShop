package com.app.tailorusershop.retrofit;

public interface ControllerInterface {

    <T> void onSuccess(T response,String status);
    void onFail(String error);
}
