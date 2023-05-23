package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.ruc.readio.MainActivity;
import cn.ruc.readio.R;
import cn.ruc.readio.util.Auth;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class mySettingsActivity extends AppCompatActivity {
    private Activity settingAct = this;
    String oldMail;
    String oldName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getInfo();
        EditText newNameText = (EditText) findViewById(R.id.setNameText);
        EditText oldPwdText = (EditText) findViewById(R.id.oldPwdText);
        EditText newPwdText = (EditText) findViewById(R.id.newPwdText);
        EditText newPwdText2 = (EditText) findViewById(R.id.newPwdText2);
        TextView confirmName = (TextView) findViewById(R.id.confirmChangeName);
        TextView confirmPwd = (TextView) findViewById(R.id.confirmChangePwd);
        LinearLayout ReadioInfo = (LinearLayout) findViewById(R.id.ReadioInfo);
        LinearLayout changeAccount = (LinearLayout) findViewById(R.id.changeAccountBar);
        LinearLayout Logout = (LinearLayout) findViewById(R.id.Logout);
        ImageView exit = (ImageView) findViewById(R.id.exitSetting);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = String.valueOf(newNameText.getText());
                if (!newName.equals(oldName)) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("email", oldMail);
                    } catch (JSONException e) {
                        Tools.my_toast(settingAct,"修改失败，请检查网络");
                    }
                    try {
                        jsonObject.put("userName", newName);
                    } catch (JSONException e) {
                        Tools.my_toast(settingAct,"修改失败，请检查网络");
                    }
                    String json = jsonObject.toString();
                    HttpUtil.postRequestWithTokenJsonAsyn(settingAct, "/app/auth/profile", json, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Tools.my_toast(settingAct, "修改失败，请检查网络");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.code()==200) {
                                Tools.my_toast(settingAct, "修改成功！");
                            }
                            else{
                                JSONObject responseJsonObject = null;
                                try {
                                    responseJsonObject = new JSONObject(response.body().string());
                                } catch (JSONException e) {
                                    Tools.my_toast(settingAct, "出错了，请检查网络");
                                }
                                try {
                                    Tools.my_toast(settingAct,responseJsonObject.getString("msg"));
                                } catch (JSONException e) {
                                    Tools.my_toast(settingAct, "出错了，请检查网络");
                                }
                            }
                        }
                    });
                }
            }
        });
        confirmPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPwd;
                String newPwd;
                String newPwd2;
                oldPwd = String.valueOf(oldPwdText.getText());
                newPwd = String.valueOf(newPwdText.getText());
                newPwd2 = String.valueOf(newPwdText2.getText());
                if(newPwd.equals(newPwd2))
                {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("oldPassword",oldPwd );
                    } catch (JSONException e) {
                        Tools.my_toast(settingAct,"修改失败，请检查网络");
                    }
                    try {
                        jsonObject.put("newPassword", newPwd);
                    } catch (JSONException e) {
                        Tools.my_toast(settingAct,"修改失败，请检查网络");
                    }
                    String json = jsonObject.toString();
                    HttpUtil.postRequestWithTokenJsonAsyn(settingAct, "/app/auth/profile/updatePwd", json, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Tools.my_toast(settingAct, "修改失败，请检查网络");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.code()==200) {
                                Tools.my_toast(settingAct, "修改成功！");
                            }
                            else{
                                JSONObject responseJsonObject = null;
                                try {
                                    responseJsonObject = new JSONObject(response.body().string());
                                } catch (JSONException e) {
                                    Tools.my_toast(settingAct, "出错了，请检查网络");
                                }
                                try {
                                    Tools.my_toast(settingAct,responseJsonObject.getString("msg"));
                                } catch (JSONException e) {
                                    Tools.my_toast(settingAct, "出错了，请检查网络");
                                }
                            }
                        }
                    });

                }else{
                    Tools.my_toast(settingAct,"新密码不一致，请检查");
                }
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth auth = new Auth();
                auth.logout(settingAct);
                finish();
            }
        });


    }

    public void getInfo()
    {
        final String[] userName = new String[1];
        final String[] Tele = new String[1];
        ArrayList<String> list = new ArrayList<>();
        HttpUtil.getRequestWithTokenAsyn(settingAct,"/app/auth/profile",new ArrayList<>(),new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.my_toast(settingAct,"获取信息失败，请检查网络");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if(response.code()==200) {
                        JSONObject responseJsonObject = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("userInfo");
                        userName[0] = responseJsonObject.getString("userName");
                        oldName = responseJsonObject.getString("userName");
                        Tele[0] = responseJsonObject.getString("phoneNumber");
                        oldMail = responseJsonObject.getString("email");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EditText nameText = (EditText) findViewById(R.id.setNameText);
                                nameText.setText(userName[0]);
                            }
                        });
                    } else{
                        JSONObject responseJsonObject = new JSONObject(response.body().string());
                        Tools.my_toast(settingAct,responseJsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    Tools.my_toast(settingAct,"获取信息失败，请检查网络");
                }
            }
        });
    }

}