package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import cn.ruc.readio.MainActivity;
import eightbitlab.com.blurview.BlurView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.ruc.readio.R;
import eightbitlab.com.blurview.RenderScriptBlur;
import kotlin.jvm.internal.Intrinsics;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(R.id.login_activity_view);
        fullScreen(this);
        Drawable windowBackground = decorView.getBackground();
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BlurView blurView = (BlurView) findViewById(R.id.blurView);
            blurView.setupWith(rootView, new RenderScriptBlur(this))
            .setBlurRadius(6F);

        ImageView login_to_userpage_button = (ImageView) findViewById(R.id.login_to_userpage);
        login_to_userpage_button.setOnClickListener(v -> finish());
    }

    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        }
    }

}