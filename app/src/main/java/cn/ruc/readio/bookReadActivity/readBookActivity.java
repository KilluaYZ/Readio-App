package cn.ruc.readio.bookReadActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ruc.readio.R;

public class readBookActivity extends Activity {
    ViewPager pager = null;
    PagerTabStrip tabStrip = null;
    ArrayList<View> viewContainer = new ArrayList<View>();
    private int nPosition;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }  //用于调整状态栏为透明色

        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        String BookName = intent.getStringExtra("BookName");
        String Author = intent.getStringExtra("Author");

        pager = (ViewPager) this.findViewById(R.id.viewpager);
        View view1 = LayoutInflater.from(this).inflate(R.layout.item_bookview,null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_bookview,null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.item_bookview,null);
        viewContainer.add(view1);
        viewContainer.add(view2);
        viewContainer.add(view3);
        bookReadAdapter myAdapter = new bookReadAdapter();
        pager.setAdapter(myAdapter);
        pager.setCurrentItem(1,false);
        myAdapter.setNewViewList(viewContainer);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                nPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != pager.SCROLL_STATE_IDLE) return;
                if (nPosition == viewContainer.size() - 1) {
                    Log.d("mybug", toString().valueOf(nPosition));
                    addPage("this is new page");
                    pager.setCurrentItem(nPosition - 1, false);
                    delFirstPage();
                }
            }

            public void addPage(String text) {
                LayoutInflater inflater = LayoutInflater.from(readBookActivity.this);
                View view = inflater.inflate(R.layout.item_bookview, null);
                TextView content = (TextView) view.findViewById(R.id.content);
                content.setText(text);
                viewContainer.add(view);
                myAdapter.notifyDataSetChanged();

            }

            public void delFirstPage() {
                viewContainer.remove(0);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}