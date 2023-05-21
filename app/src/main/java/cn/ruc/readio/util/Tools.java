package cn.ruc.readio.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.ruc.readio.entity.FileInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Tools {
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void my_toast(Activity activity, String text){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void getImageBitmapAsyn(String fileId, ImageView view, Activity activity) throws IOException, ParseException {
        Bitmap pic = getImageBitmapFromLocal(activity, fileId);
        if(pic == null){
            //从服务器异步获取
            getImageBitmapFromServerAsyn(fileId, view, activity);
        }else{
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(pic);
                }
            });
        }
    }

    public static Bitmap getImageBitmapSyn(Activity activity, String fileId) throws IOException, JSONException, ParseException {
        Bitmap pic = getImageBitmapFromLocal(activity, fileId);
        if(pic == null){
            //本地妹有，从云端拿
            Bitmap pic_server = getImageBitmapFromServerSyn(fileId);
            FileInfo serverFileInfo = getFileInfoByFileId(fileId);
            FileReader reader = new FileReader(activity);
            reader.insert(serverFileInfo);
            return pic_server;
        }
        return pic;
    }

    public static boolean localDatabaseHasFile(Activity activity, String fileId) throws IOException, ParseException {
        FileReader fileReader = new FileReader(activity);
        FileInfo fileInfo = fileReader.getFileInfoByFileId(fileId);
        if(fileInfo == null){
            return false;
        }
        return true;
    }

    public static Bitmap getImageBitmapFromLocal(Activity activity,String fileId) throws IOException, ParseException {
        FileReader fileReader = new FileReader(activity);
        FileInfo fileInfo = fileReader.getFileInfoByFileId(fileId);
        if(fileInfo != null){
            Bitmap pic = BitmapFactory.decodeByteArray(fileInfo.getContent(), 0, fileInfo.getContent().length);
            return pic;
        }
        return null;
    }

    public static void getImageBitmapFromServerAsyn(String fileId, ImageView view, Activity activity){
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", fileId));
        Log.d("avatorload","进入函数");
        HttpUtil.getRequestAsyn("/file/getFileBinaryById", queryParam, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.my_toast(activity, "获取图片失败，请检查网络连接");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.code() == 200){
                    Log.d("avatorload","hello");
                    byte[] picBytes = response.body().bytes();
                    Bitmap pic = BitmapFactory.decodeByteArray(picBytes,0,picBytes.length);
                    try {
                        FileInfo serverFileInfo = getFileInfoByFileId(fileId);
                        FileReader reader = new FileReader(activity);
                        reader.insert(serverFileInfo);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("avatorload","即将设置");
                            view.setImageBitmap(pic);
                            Log.d("avatorload","设置完毕");
                        }
                    });

                }else{
                    Log.d("avatorload","出问题啦");
                    try {
                        Tools.my_toast(activity, new JSONObject(response.body().string()).getString("msg"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    public static Bitmap getImageBitmapFromServerSyn(String fileId){
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", fileId));
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

    public static byte[] getImageByteFromServerSyn(String fileId){
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", fileId));
        byte[] picBytes = null;
        try{
            Response response =  HttpUtil.getRequestSyn("/file/getFileBinaryById", queryParam);
            picBytes = response.body().bytes();
        }catch (IOException e){
            e.printStackTrace();
        }
        return picBytes;
    }

    public static FileInfo getFileInfoByFileId(String fileId) throws IOException, JSONException, ParseException {
        ArrayList<Pair<String, String>> queryParam = new ArrayList<>();
        queryParam.add(Pair.create("fileId", fileId));
        Response response = HttpUtil.getRequestSyn("/file/getFileInfo", queryParam);
        if(response.code() != 200){
            return null;
        }
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if(dataArray.length() > 0){
            JSONObject fileInfoObject = (JSONObject) jsonObject.getJSONArray("data").get(0);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(fileInfoObject.getString("fileId"));
            fileInfo.setFileName(fileInfoObject.getString("fileName"));
            fileInfo.setFileType(fileInfoObject.getString("fileType"));
            fileInfo.setFilePath(fileInfoObject.getString("filePath"));
//            Log.d("createTime", fileInfoObject.getString("createTime"));
//            fileInfo.setCreateTime(formatter.parse(fileInfoObject.getString("createTime")));
//            fileInfo.setVisitTime(formatter.parse(fileInfoObject.getString("visitTime")));
            fileInfo.setContent(getImageByteFromServerSyn(fileId));
            return fileInfo;
        }

        return null;
    }
}
