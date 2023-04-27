package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.userpage.serialNameAdapter;

public class addTagActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        final List<String> list = new ArrayList<>();
        list.add("灵媒向");

        EditText tagName = findViewById(R.id.tagName);
        ListView tagList = findViewById(R.id.tagList);
        LinearLayout addTagTitleBar = (LinearLayout) findViewById(R.id.addTag_titleBar);
        ImageButton addTag = (ImageButton) findViewById(R.id.confirmTagButton);

        tagAdapter adapter = new tagAdapter(this, list, new tagAdapter.onItemViewClickListener(){
            @Override
            public void onItemViewClick(String name) {
                tagName.setText(name);
                tagName.setSelection(name.length());
                tagList.setVisibility(View.GONE);
            }
        });
        tagList.setAdapter(adapter);
        tagList.setVisibility(View.GONE);

        tagName.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tagList.setVisibility(View.VISIBLE);
                return false;
            }
        });

        tagName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                SerialNameList.setVisibility(TextUtils.isEmpty(s.toString()) ? View.VISIBLE:View.GONE);
                tagList.setVisibility(View.GONE);
            }
        });

        addTagTitleBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tagList.setVisibility(View.GONE);
                return false;
            }
        });

        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText tag_name = (EditText) findViewById(R.id.tagName);
                if(TextUtils.isEmpty(tag_name.getText())){
                    Toast.makeText(addTagActivity.this, "请输入tag", Toast.LENGTH_SHORT).show();
                }
                else {
                    /*
                    上传至服务器，跟新数据库
                     */
                    Toast.makeText(addTagActivity.this, "成功更新tag", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}