package com.okjunkstore.beta.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static AccessToken tokenGenerator = new AccessToken();
    private static String accessToken = tokenGenerator.getAccessToken();

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request originalRequest = chain.request();
                Request.Builder builder = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + accessToken)
                        .header("Content-Type", "application/json");
                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            })
            .build();

    public static Retrofit retrofit1 = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/") // Base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

}
