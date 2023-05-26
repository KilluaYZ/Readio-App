package cn.ruc.readio.ui.shelf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import cn.ruc.readio.MainActivity;
import cn.ruc.readio.R;
import cn.ruc.readio.bookReadActivity.bookDetailActivity;
import cn.ruc.readio.bookReadActivity.readBookActivity;
import cn.ruc.readio.databinding.FragmentShelfBinding;
import cn.ruc.readio.ui.userpage.login.LoginActivity;
import cn.ruc.readio.util.Auth;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class shelfFragment extends Fragment {

    private FragmentShelfBinding binding;
    private GridView grid_view;
    private int if_empty=0;

    private List<BookItem> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShelfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lists = new ArrayList<>();
        Auth.Token token = new Auth.Token(getActivity());
        if(token.isEmpty())
        {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        refreshData();
        if(if_empty==1)
        {
            if(getActivity()!=null)
            {
                Toast.makeText(getActivity(),"您的书架里还没有书呐！\n快往里头添加一些吧！",Toast.LENGTH_SHORT).show();
            }
        }
        //lists=getData();
        grid_view = binding.bookGridview;

        bookAdapter myAdapter = new bookAdapter(getContext(),lists);
        grid_view.setAdapter(myAdapter);

        return root;
    }
    private List<BookItem> getData()
    {
        List<BookItem> data = new ArrayList<>();
        data.add(new BookItem("三体","刘慈欣"));
        data.add(new BookItem("挪威的森林","村上春树"));
        data.add(new BookItem("活着","余华"));
        data.add(new BookItem("红楼梦","曹雪芹"));
        data.add(new BookItem("百年孤独","马尔克斯"));
        data.add(new BookItem("哈姆雷特","莎士比亚"));
        data.add(new BookItem("月亮与六便士","毛姆"));
        data.add(new BookItem("复活","马尔克斯"));
        data.add(new BookItem("平凡的世界","路遥"));
        data.add(new BookItem("局外人","加缪"));
        return data;
    }


    public void refreshData(){
        HttpUtil.getRequestWithTokenAsyn(getActivity(),"/app/books/list",new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");
                    int mybook_num=data.getInt("size");
                    JSONArray mybook_list = data.getJSONArray("data");
                    if(mybook_num == 0){
                        if_empty=1;
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),"您的书架里还没有书呐！\n快往里头添加一些吧！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject mybook = mybook_list.getJSONObject(i);
                            BookItem book = new BookItem(mybook.getString("bookName"), mybook.optString("authorID"));
                            lists.add(book);
                        }
                    }
                    /*获取封面*/
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0;i < lists.size(); ++i){
                                Bitmap pic = HttpUtil.getAvaSyn(lists.get(i).getCoverID());
                                lists.get(i).setCover(pic);
                                Log.d("bookadpter","需要更新");
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.bookGridview.getAdapter().notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                    }).run();*/

                    /*getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GridView gridView = getActivity().findViewById(R.id.book_gridview);
                            //gridView.getAdapter().getItem().
                        }
                    });*/
                } catch (JSONException e) {
                    Tools.my_toast(Objects.requireNonNull(getActivity()),"加载失败");
                }
            }
        });
    }


    private void  mtoast(String msg){
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show());
    }
}