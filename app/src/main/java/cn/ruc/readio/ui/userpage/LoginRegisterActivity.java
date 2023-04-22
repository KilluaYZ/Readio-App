package cn.ruc.readio.ui.userpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.userpage.ui.login.LoginFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }
    }
}