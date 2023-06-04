package cn.ruc.readio.bookReadActivity;

import static android.util.Pair.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import cn.ruc.readio.R;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class readBookActivity extends Activity {
    private String BookID,BookName,Author;
    ViewPager pager = null;
    PagerTabStrip tabStrip = null;
    ArrayList<View> viewContainer = new ArrayList<View>();
    ArrayList<String> contentList = new ArrayList<String>();
    private int nPosition;
    private book my_book=new book();
    private TextView content;
    private TextView readPage;
    private TextView BookInfo;
    private View view1;
    private View view2;
    int Page = 0;
    private ArrayList<Integer> ifLoad;
    private int lastPosition = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contentList = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        if (Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }  //用于调整状态栏为透明色
        readPage = findViewById(R.id.pageBar);
        BookInfo = findViewById(R.id.book_nameBar);
        ifLoad = new ArrayList<>();
        InitifLoad();
        /*接受传递的消息*/
        Intent intent = getIntent();

        /*获取内容*/
        BookName = intent.getStringExtra("BookName");
        Author = intent.getStringExtra("Author");
        BookID = intent.getStringExtra("BookID");

        getBook();


        pager = (ViewPager) this.findViewById(R.id.viewpager);
        view1 = LayoutInflater.from(this).inflate(R.layout.item_bookview,null);
        view2 = LayoutInflater.from(this).inflate(R.layout.item_bookview,null);
        content = view1.findViewById(R.id.content);
        content.setText("内容正在加载中，请稍后...");
        viewContainer.add(view1);
        viewContainer.add(view2);
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
                if(nPosition > lastPosition){
                    Page++;
                }
                if(nPosition < lastPosition){
                    Page--;
                }
                String wholePage = toString().valueOf(contentList.size());
                String nowPage = toString().valueOf(Page+1);
                readPage.setText(nowPage+"/"+wholePage);
                if (nPosition == viewContainer.size() - 1) {
                    if(contentList == null)
                    {
                        Tools.my_toast(readBookActivity.this,"内容仍在加载中，请稍等一下~");
                    }
                    else{
                        if(Page < contentList.size()-1) {
                            if(ifLoad.get(Page+1) == 0) {
                                addPage(contentList.get(Page+1));
                                Loaded(Page+1);
                            }
                        }
                        else{
                            Tools.my_toast(readBookActivity.this,"这是最后一页啦~");
                        }

                    }
//                    pager.setCurrentItem(nPosition, false);
                }

                if (nPosition == 0) {
                    if(contentList == null)
                    {
                        Tools.my_toast(readBookActivity.this,"内容仍在加载中，请稍等一下~");
                    }
                    else {
                        if(Page>=1) {
                            if(ifLoad.get(Page-1) == 0) {
                                addPageFront(contentList.get(Page-1));
                                pager.setCurrentItem(nPosition+1, false);
                                Loaded(Page-1);
                            }
                        }
                        else{
                            Tools.my_toast(readBookActivity.this,"已经是第一页啦~");
                        }
                    }
                }
                lastPosition = nPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != pager.SCROLL_STATE_IDLE) return;
                pager.setCurrentItem(nPosition, false);
            }

            public void addPage(String text) {
                LayoutInflater inflater = LayoutInflater.from(readBookActivity.this);
                View view = inflater.inflate(R.layout.item_bookview, null);
                TextView content = (TextView) view.findViewById(R.id.content);
                content.setText(text);
                viewContainer.add(view);
                myAdapter.notifyDataSetChanged();

            }

            public void addPageFront(String text){
                LayoutInflater inflater = LayoutInflater.from(readBookActivity.this);
                View view = inflater.inflate(R.layout.item_bookview, null);
                TextView content = (TextView) view.findViewById(R.id.content);
                content.setText(text);
                viewContainer.add(0,view);
                myAdapter.notifyDataSetChanged();
            }

            public void delFirstPage() {
                viewContainer.remove(0);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArrayList<Pair<String,String>> queryParam = new ArrayList<>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("bookId",my_book.getBookId());
            jsonObject.put("progress",toString().valueOf(getProgress()));
        } catch (JSONException e) {
            Tools.my_toast(readBookActivity.this,"进度上传失败");
        }
        String json = jsonObject.toString();
        HttpUtil.postRequestWithTokenJsonAsyn(readBookActivity.this, "/app/books/update", json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Tools.my_toast(readBookActivity.this,"进度上传失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Tools.my_toast(readBookActivity.this,"进度保存成功~");
            }
        });
    }

    void getBook(){
        HttpUtil.getRequestWithTokenAsyn(readBookActivity.this,"/app/books/reading/"+Integer.parseInt(BookID), new ArrayList<>() ,new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Tools.my_toast(readBookActivity.this,"请求异常，加载不出来");
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject bookinfo = jsonObject.getJSONObject("data");

                    my_book.setBookId(bookinfo.getString("id"));
                    my_book.setBookAbstract(bookinfo.getString("abstract"));
                    my_book.setBookName(bookinfo.getString("bookName"));
                    my_book.setAuthorName(bookinfo.getString("authorName"));
                    my_book.setSize(bookinfo.getInt("size"));
                    my_book.setProgress(bookinfo.getInt("progress"));
                    JSONArray content=bookinfo.getJSONArray("content");
                    ArrayList<Pair<String, String>> bookcontent=new ArrayList<>();

                    for(int i = 0;i<content.length();i++){
                        JSONObject contenti= content.getJSONObject(i);
                        bookcontent.add(create(contenti.getString("ChapterName"),contenti.getString("Text")));
                    }
                    my_book.setContent(bookcontent);
                    contentList = new BookPageDivider(my_book,300).getPagesList();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BookInfo.setText(my_book.getBookName()+" "+my_book.getAuthorName());
                            TextView view1_content = view1.findViewById(R.id.content);
                            TextView view2_content = view2.findViewById(R.id.content);
                            Page = my_book.getProgress()/300;
                            String wholePage = toString().valueOf(contentList.size());
                            String nowPage = toString().valueOf(Page+1);
                            readPage.setText(nowPage+"/"+wholePage);
                            view1_content.setText(contentList.get(Page));
                            Loaded(Page);
                            if(Page < contentList.size()-1) {
                                view2_content.setText(contentList.get(Page + 1));
                                Loaded(Page + 1);
                            }
                            if(Page>=1)
                            {
                                if(ifLoad.get(Page-1) == 0) {
                                    LayoutInflater inflater = LayoutInflater.from(readBookActivity.this);
                                    View view = inflater.inflate(R.layout.item_bookview, null);
                                    TextView content = (TextView) view.findViewById(R.id.content);
                                    content.setText(contentList.get(Page-1));
                                    viewContainer.add(0,view);
                                    Loaded(Page-1);
                                    pager.getAdapter().notifyDataSetChanged();
                                    pager.setCurrentItem(nPosition+1, false);
                                    Page--;
                                }
                            }
                        }
                    });
                } catch (JSONException e) {
                    Tools.my_toast(readBookActivity.this, "内容加载失败");
                }
            }
        });
    }
    int getProgress(){
        return Page*300+1;
    }
    void InitifLoad(){
        for(int i = 0; i < 10000; i++)
        {
            ifLoad.add(0);
        }
    }
    void Loaded(int Position)
    {
        ifLoad.set(Position, 1);
    }
}