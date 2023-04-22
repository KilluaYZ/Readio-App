package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;
import cn.ruc.readio.ui.userpage.serialNameAdapter;

public class newWorksActivity extends Activity {
    private int touch_num = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_works);

        final List<String> list = new ArrayList<>();
        list.add("恶煞");
        list.add("viko通讯录");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");
        list.add("年下不叫哥，心思有点多");

        EditText SerialName = findViewById(R.id.serialName);
        ListView SerialNameList = findViewById(R.id.serialNameList);
        serialNameAdapter adapter = new serialNameAdapter(this, list, new serialNameAdapter.onItemViewClickListener(){
            @Override
            public void onItemViewClick(String name) {
                SerialName.setText(name);
                SerialName.setSelection(name.length());
                SerialNameList.setVisibility(View.GONE);
            }
        });
        SerialNameList.setAdapter(adapter);
        SerialNameList.setVisibility(View.GONE);

        SerialName.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SerialNameList.setVisibility(View.VISIBLE);
                return false;
            }
        });

        SerialName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SerialNameList.setVisibility(TextUtils.isEmpty(s.toString()) ? View.VISIBLE:View.GONE);
            }
        });
    }
}