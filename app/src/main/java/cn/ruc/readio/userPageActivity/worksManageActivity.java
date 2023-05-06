package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.ActivityMainBinding;
import cn.ruc.readio.databinding.ActivityWorksManageBinding;

public class worksManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works_manage);

        TextView publishedZone_button = (TextView) findViewById(R.id.publishedManageButton);
        TextView draftZone_button = (TextView) findViewById(R.id.draftManageButton);
        ImageView exitWorksManage_button = (ImageView) findViewById(R.id.exitManagementButton);

        exitWorksManage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        publishedZone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        publishedZone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.d("color","halo");
                    publishedZone_button.setTextColor(0xD378B5C6);
                    draftZone_button.setTextColor(0xD8555554);
                }
        });
        draftZone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    publishedZone_button.setTextColor(0xD8555554);
                    draftZone_button.setTextColor(0xD378B5C6);
                }

        });
    }
}