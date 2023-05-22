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
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import cn.ruc.readio.databinding.FragmentUserpageBinding;
import cn.ruc.readio.ui.userpage.login.LoginActivity;
import cn.ruc.readio.userPageActivity.changeAvatorActivity;
import cn.ruc.readio.userPageActivity.newWorksActivity;
import cn.ruc.readio.userPageActivity.worksManageActivity;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class userPageFragment extends Fragment {

    private FragmentUserpageBinding binding;

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


        binding.myAvator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), changeAvatorActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(binding.userID.getText().length() > 0){

        }else{
            getProfile();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getProfile(){
        if(getActivity() != null) {
            HttpUtil.getRequestWithTokenAsyn(getActivity(), "/app/auth/profile", new ArrayList<>(), new Callback() {
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
//                    Bitmap my_avamap = HttpUtil.getAvaSyn(responseJsonObject.getString("avator"));
//                    HttpUtil.getAvaAsyn(responseJsonObject.getString("avator"),binding.myAvator,getActivity());
                        if(getActivity() != null && binding.myAvator != null)
                        {
                        Tools.getImageBitmapAsyn(responseJsonObject.getString("avator"), binding.myAvator, getActivity());}
                        if(getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.userID.setText("ID:" + userId);
                                    binding.userName.setText(userName);
//                            binding.myAvator.setImageBitmap(my_avamap);
                                }
                            });
                        }
                        Log.d(this.toString(), "拿到profile数据");
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                        Tools.my_toast(getActivity(),"解析profile信息失败");
                    }
                }
            });
        }
    }

}