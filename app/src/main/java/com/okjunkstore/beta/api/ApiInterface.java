package com.okjunkstore.beta.api;

import static com.okjunkstore.beta.model.Constants.CONTENT_TYPE;
import static com.okjunkstore.beta.model.Constants.SERVER_KEY;

import com.okjunkstore.beta.model.FcmHttpV1Request;
import com.okjunkstore.beta.model.PushNotification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: key="+ SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);

    @POST("v1/projects/beta-6165e/messages:send")
    Call<ResponseBody> sendNotificationNew(@Body FcmHttpV1Request request);
}
