package cn.ruc.readio.util;

import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.HttpUrl;

public class HttpUtil {
    private static String BASE_URL = "killuayz.top"; //http://killuayz.top:5000  http://127.0.0.1:5000
    private static String BASE_SCHEME = "http";
    private static int BASE_PORT = 5000;
    //get方法向服务器请求，address是api地址 /auth/app/login
    //callback是个回调函数
    public static void getRequest(String address, ArrayList<Pair<String,String>> queryParameter, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        queryParameter.forEach((item) -> {
            url.addQueryParameter(item.first,item.second);
        });

        Request request = new Request.Builder()
                .url(url.build())
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的get方法
    public static void getRequestWithToken(String address, String token, ArrayList<Pair<String,String>> queryParameter, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        queryParameter.forEach((item) -> {
            url.addQueryParameter(item.first,item.second);
        });

        Request request = new Request.Builder()
                .url(url.build())
                .get()
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //普通的post方法
    public static void postRequest(String address, RequestBody requestBody, Callback callback){

        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        Log.d("HttpUtil","postRequest正在访问 "+url.build().toString());
        Request request = new Request.Builder()
                .url(url.build())
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的post方法
    public static void postRequestWithToken(String address, String token, RequestBody requestBody, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        Request request = new Request.Builder()
                .url(url.build())
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //普通的post方法，向服务器传json字符串
    public static void postRequestJson(String address, String jsonString, Callback callback){
//        Log.d("HttpUtil","正在访问 "+BASE_URL + address + "\n body = "+jsonString);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        Log.d("HttpUtil","postRequestJson正在访问 "+url.build().toString() + "\n body = "+jsonString);
        Request request = new Request.Builder()
                .url(url.build())
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //带令牌的post方法，向服务器传json字符串
    public static void postRequestWithTokenJson(String address, String token, String jsonString, Callback callback){
        Log.d("HttpUtil","正在访问 "+BASE_URL + address);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        HttpUrl.Builder url = new HttpUrl.Builder()
                .host(BASE_URL)
                .port(BASE_PORT)
                .scheme(BASE_SCHEME)
                .addPathSegment(address);

        Request request = new Request.Builder()
                .url(url.build())
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

}
