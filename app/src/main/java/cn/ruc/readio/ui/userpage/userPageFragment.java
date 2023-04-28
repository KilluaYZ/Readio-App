package cn.ruc.readio.ui.userpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import cn.ruc.readio.MainActivity;
import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentUserpageBinding;
import cn.ruc.readio.userPageActivity.LoginActivity;
import cn.ruc.readio.userPageActivity.changeAvatorActivity;
import cn.ruc.readio.userPageActivity.newWorksActivity;
import cn.ruc.readio.userPageActivity.worksManageActivity;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class userPageFragment extends Fragment {

    private FragmentUserpageBinding binding;
    private String token = "";

    public ImageButton new_work_button;


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

        ImageButton touxiang = binding.mySettingsButton;

        ImageButton manage_button = binding.workManageButton;
        touxiang.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.newWorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(this.toString(),"点击了 新建作品");
                Intent intent = new Intent(getContext(), newWorksActivity.class);
                startActivity(intent);
            }
        });

        manage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), worksManageActivity.class);
                startActivity(intent);
            }
        });

        try {
            if(token.isEmpty()){
                login();
            }else{
                Log.d(this.toString(),"token = "+token);
                getProfile();
            }

        } catch (JSONException e) {
            Toast.makeText(getContext(), "访问服务器错误", Toast.LENGTH_LONG).show();
        }

        binding.iconImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), changeAvatorActivity.class);
                startActivity(intent);
            }
        });

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

        HttpUtil.postRequestJson("/app/auth/login", jsonObject.toString(), new Callback() {
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
                    token = responseJsonObject.getJSONObject("data").getString("token");
                    Log.d(this.toString(), "获取到登录数据");
                    Log.d(this.toString(),"token = "+token);
                    getProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mtoast("解析登录信息失败");
                }
            }
        });
    }

    private void getProfile(){
        HttpUtil.getRequestWithToken("/app/auth/profile", token, new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "获取信息失败！请检查网络连接", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject responseJsonObject = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("userInfo");
                    String userId = String.valueOf(responseJsonObject.getInt("userId"));
                    String userName = responseJsonObject.getString("userName");
//                    handler.obtainMessage(1,userId);
//                    handler.obtainMessage(2,userName);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.userID.setText("ID:" + userId);
                            binding.userName.setText(userName);
                        }
                    });
                    Log.d(this.toString(), "拿到profile数据");
                } catch (JSONException e) {
                    e.printStackTrace();
                    mtoast("解析profile信息失败");
                }
            }
        });
    }

    private void  mtoast(String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}