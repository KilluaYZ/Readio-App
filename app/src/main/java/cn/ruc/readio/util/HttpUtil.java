package cn.ruc.readio.util;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static String BASE_URL = "http://127.0.0.1:5000";

    public static void getRequest(String address, Callback callback){
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void getRequestWithToken(String address, String token, Callback callback){
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest(String address, RequestBody requestBody, Callback callback){
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequestWithToken(String address, String token, RequestBody requestBody, Callback callback){
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequestJson(String address, String jsonString, Callback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequestWithTokenJson(String address, String token, String jsonString, Callback callback){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
