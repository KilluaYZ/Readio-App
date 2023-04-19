package cn.ruc.readio.util;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    private static String BASE_URL = "http://killuayz.top:5000";

    //get方法向服务器请求，address是api地址 /auth/app/login
    //callback是个回调函数
    public static void getRequest(String address, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的get方法
    public static void getRequestWithToken(String address, String token, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //普通的post方法
    public static void postRequest(String address, RequestBody requestBody, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的post方法
    public static void postRequestWithToken(String address, String token, RequestBody requestBody, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //普通的post方法，向服务器传json字符串
    public static void postRequestJson(String address, String jsonString, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address + "\n body = "+jsonString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        Request request = new Request.Builder()
                .url(BASE_URL + address)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的post方法，向服务器传json字符串
    public static void postRequestWithTokenJson(String address, String token, String jsonString, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
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
