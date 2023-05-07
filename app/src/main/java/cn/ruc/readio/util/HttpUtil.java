package cn.ruc.readio.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class HttpUtil {
    private static String BASE_URL = "test.killuayz.top"; //http://killuayz.top:5000  http://127.0.0.1:5000
    private static String BASE_SCHEME = "http";
    private static int BASE_PORT = 5000;
    //get方法向服务器请求，address是api地址 /auth/app/login
    //callback是个回调函数
    public static void getRequestAsyn(String address, ArrayList<Pair<String,String>> queryParameter, Callback callback){
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

    public static Response getRequestSyn(String address, ArrayList<Pair<String,String>> queryParameter) throws IOException {
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
        return okHttpClient.newCall(request).execute();
    }

    //带令牌的get方法
    public static void getRequestWithTokenAsyn(String address, String token, ArrayList<Pair<String,String>> queryParameter, Callback callback){
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

    public static Response getRequestWithTokenSyn(String address, String token, ArrayList<Pair<String,String>> queryParameter, Callback callback) throws IOException {
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
        return okHttpClient.newCall(request).execute();
    }

    //普通的post方法
    public static void postRequestAsyn(String address, RequestBody requestBody, Callback callback){

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

    //普通的post方法
    public static Response postRequestSyn(String address, RequestBody requestBody) throws IOException {

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
        return okHttpClient.newCall(request).execute();
    }

    //带令牌的post方法
    public static void postRequestWithTokenAsyn(String address, String token, RequestBody requestBody, Callback callback){
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

    //带令牌的post方法
    public static Response postRequestWithTokenSyn(String address, String token, RequestBody requestBody) throws IOException {
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
        return okHttpClient.newCall(request).execute();
    }

    //普通的post方法，向服务器传json字符串
    public static void postRequestJsonAsyn(String address, String jsonString, Callback callback){
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

    //普通的post方法，向服务器传json字符串
    public static Response postRequestJsonSyn(String address, String jsonString) throws IOException {
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
        return okHttpClient.newCall(request).execute();
    }

    //带令牌的post方法，向服务器传json字符串
    public static void postRequestWithTokenJsonAsyn(String address, String token, String jsonString, Callback callback){
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

    public static Response postRequestWithTokenJsonSyn(String address, String token, String jsonString, Callback callback) throws IOException {
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
        return okHttpClient.newCall(request).execute();
    }


    public static void getAvaAsyn(String avaId, ImageView view, FragmentActivity avtivity){
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", avaId));
        HttpUtil.getRequestAsyn("/file/getFileBinaryById", queryParam, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.code() == 200){
                    byte[] picBytes = response.body().bytes();
                    Bitmap pic = BitmapFactory.decodeByteArray(picBytes,0,picBytes.length);
                    avtivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(pic);
                        }
                    });

                }else{

                }

            }
        });
    }


    public static Bitmap getAvaSyn(String avaId){
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", avaId));
        Bitmap pic = null;
        try{
            Response response =  HttpUtil.getRequestSyn("/file/getFileBinaryById", queryParam);
            byte[] picBytes = response.body().bytes();
            pic = BitmapFactory.decodeByteArray(picBytes,0,picBytes.length);

        }catch (IOException e){
            e.printStackTrace();
        }
        return pic;
    }
}
