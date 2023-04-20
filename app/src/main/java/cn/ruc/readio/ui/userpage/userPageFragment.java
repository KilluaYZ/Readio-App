package cn.ruc.readio.ui.userpage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

import cn.ruc.readio.databinding.FragmentUserpageBinding;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class userPageFragment extends Fragment {

    private FragmentUserpageBinding binding;
    private String token = "";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.d(this.toString(),"修改userID");
                    binding.userID.setText((String)msg.obj);
                    break;
                case 2:
                    Log.d(this.toString(),"修改userName");
                    binding.userName.setText((String)msg.obj);
                    break;
                default:
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserpageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if(token.isEmpty()){
//                        login();
//                    }
//                    getProfile();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();

        try {
            if(token.isEmpty()){
                login();
            }else{
                Log.d(this.toString(),"token = "+token);
                getProfile();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void login() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber","18314266702");
        jsonObject.put("passWord","123456");

        HttpUtil.postRequestJson("/auth/app/login", jsonObject.toString(), new Callback() {
                @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"登录失败！请检查网络连接",Toast.LENGTH_LONG).show();
                Looper.loop();
                Log.e(this.toString(), "登录数据获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJsonObject = new JSONObject(response.body().string());
                    token = responseJsonObject.getString("token");
                    Log.d(this.toString(), "获取到登录数据");
                    Log.d(this.toString(),"token = "+token);
                    getProfile();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void getProfile(){
        HttpUtil.getRequestWithToken("/auth/app/profile", token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(),"获取信息失败！请检查网络连接",Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJsonObject = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("data");
                    String userId = String.valueOf(responseJsonObject.getInt("userId"));
                    String userName = responseJsonObject.getString("userName");
//                    handler.obtainMessage(1,userId);
//                    handler.obtainMessage(2,userName);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.userID.setText("ID:"+userId);
                            binding.userName.setText(userName);
                        }
                    });
                    Log.d(this.toString(),"拿到profile数据");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}