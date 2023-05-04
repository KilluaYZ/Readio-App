package cn.ruc.readio.userPageActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.ruc.readio.R;

public class editWorkActivity extends AppCompatActivity {
    static public editWorkActivity editworkActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_edit_work);
        editworkActivity = this;
        newWorksActivity.new_workActivity.finish();
        TextView publish_button = (TextView) findViewById(R.id.publishButton);
        TextView exitEdit_button = (TextView) findViewById(R.id.editExitButton);
        TextView saveDraft_button = (TextView) findViewById(R.id.saveDraftButton);
        TextView addTag_button = (TextView) findViewById(R.id.addTagButton);
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editContent = (EditText) findViewById(R.id.editPiece);
                if(!TextUtils.isEmpty(editContent.getText())){
                Toast.makeText(editWorkActivity.this, "您编辑的内容已发布（⌯'▾'⌯）", Toast.LENGTH_SHORT).show();
                finish();}
                else{
                    Toast.makeText(editWorkActivity.this, "所以你写了什么？(´◔ ‸◔`)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exitEdit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editContent = (EditText) findViewById(R.id.editPiece);
                if(!TextUtils.isEmpty(editContent.getText())){
                    Intent intent = new Intent(editWorkActivity.this, exitEditActivity.class);
                    startActivity(intent);
                }else{
                    finish();
                }
            }
        });
        saveDraft_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                上传至服务器，更新数据库
                 */
                Toast.makeText(editWorkActivity.this, "已存入草稿箱", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        addTag_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editWorkActivity.this,addTagActivity.class);
                startActivity(intent);
            }
        });
    }
}
