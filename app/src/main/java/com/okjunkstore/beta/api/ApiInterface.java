package com.okjunkstore.beta.api;

import com.okjunkstore.beta.model.FcmHttpV1Request;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("v1/projects/beta-6165e/messages:send")
    Call<ResponseBody> sendNotificationNew(@Body FcmHttpV1Request request);
}
