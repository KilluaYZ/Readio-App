package cn.ruc.readio.userPageActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ruc.readio.R;

import cn.ruc.readio.databinding.ActivityChangeAvatorBinding;
import cn.ruc.readio.util.BitmapBase64;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class changeAvatorActivity extends AppCompatActivity {

    private ActivityChangeAvatorBinding binding;

    private ArrayList<String> resourceArrayList = new ArrayList<>();

    private ArrayList<String> resourceIdArrayList = new ArrayList<>();

    private ImageView avatorImageView = null;
    private ImageView addImageView = null;
    private Bitmap dataBitMap = null;

    private Spinner spinner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAvatorBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_change_avator);
        binding.getRoot();
        addImageView = (ImageView) findViewById(R.id.imageView2);
        avatorImageView = (ImageView) findViewById(R.id.imageView);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, resourceArrayList);
        spinner = (Spinner) findViewById(R.id.spinnerView);
        spinner.setAdapter(adapter);

        refreshSpinnerData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("天啦噜！","出问题辣！");
                String res = parent.getItemAtPosition(position).toString();
                Toast.makeText(changeAvatorActivity.this,"正在从服务器获取:"+res,Toast.LENGTH_LONG).show();
                ArrayList<Pair<String,String>> queryParam = new ArrayList<>();
                queryParam.add(Pair.create("fileId", resourceIdArrayList.get(position)));
                HttpUtil.getRequest("/file/downloadBinary", queryParam, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mtoast("从服务器获取图片失败，请检查网络连接");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string()).getJSONObject("data");
                            String base64Image = jsonObject.getString("fileContent");
                            Bitmap pic = BitmapBase64.base64ToBitmap(base64Image);
//                            byte[] decode = Base64.decode(base64Image, Base64.DEFAULT);
//                            Bitmap pic = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    avatorImageView.setImageBitmap(pic);
                                }
                            });
                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
                            e.printStackTrace();
                            mtoast("解析服务器图片失败");
                        }

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });

        Button button = (Button) findViewById(R.id.uploadButton);
        EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        EditText typeEdit = (EditText) findViewById(R.id.typeEdit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtoast("图片上传中，请稍等");

                JSONObject postBody = new JSONObject();
                try {
                    postBody.put("fileName", nameEdit.getText());
                    postBody.put("fileType", typeEdit.getText());
                    postBody.put("fileContent", BitmapBase64.bitmapToBase64(dataBitMap));
                } catch (JSONException e) {
                    e.printStackTrace();
                    mtoast("信息填写不正确！");
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

                HttpUtil.postRequestJson("/file/uploadBinary", postBody.toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mtoast("上传失败，请检查网络连接");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.code() == 200){
                            mtoast("上传成功");
                            refreshSpinnerData();

                        }else{
                            mtoast(new String(response.body().string().getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                });

            }
        });
    }


    private void refreshSpinnerData(){
        HttpUtil.getRequest("/file/getResInfo", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"无法从服务器获取文件资源，请检查网络连接", Toast.LENGTH_LONG);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray responseJsonArray = new JSONObject(response.body().string()).getJSONArray("data");

                    for(int i = 0;i < responseJsonArray.length();++i){
                        String name = responseJsonArray.getJSONObject(i).getString("fileName");
                        resourceArrayList.add(name);
                        resourceIdArrayList.add(responseJsonArray.getJSONObject(i).getString("fileId"));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //获取server数据
                            ArrayAdapter<String> adapter =  (ArrayAdapter<String>) spinner.getAdapter();
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
//                            throw new RuntimeException(e);
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),"解析服务器数据失败", Toast.LENGTH_LONG);
                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            if(data != null){
                Log.d(this.toString(),"成功从图库取到data");
                Uri uri = data.getData();
                addImageView.setImageURI(uri);
                try {
                    dataBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void  mtoast(String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}