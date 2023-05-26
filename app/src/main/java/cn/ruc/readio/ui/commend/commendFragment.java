package cn.ruc.readio.ui.commend;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentCommendBinding;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class commendFragment extends Fragment {
    private RecyclerView recycler_view;

    private String bookName,author;
    private FragmentCommendBinding binding;
    private List<Recommendation> recommendation_lists;

    public commendFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        binding = FragmentCommendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //commend_pic=binding.picture;

        recommendation_lists =new ArrayList<>();
        recycler_view= binding.commendCard;

        /*从服务器获取内容*/
        refreshData();

        /*设置recyclerview的卡片*/
        LinearLayoutManager m=new LinearLayoutManager(getContext());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        commendCardAdapter myAdapter = new commendCardAdapter(recommendation_lists, getContext());
        recycler_view.setAdapter(myAdapter);

        return root;
    }

    /*初始化函数，用于test*/
    private void initData() {
        recommendation_lists.add(new Recommendation("章北海：自然选择！前进四！","刘慈欣","《三体》",0));
        recommendation_lists.add(new Recommendation("黑，真他妈的黑啊","刘慈欣","《三体》",0));
        recommendation_lists.add(new Recommendation("card3","不知道写点啥","《xx》",0));
        recommendation_lists.add(new Recommendation("card4","随便看看吧","《yy》",0));
    }

    public void refreshData(){
        HttpUtil.getRequestAsyn("/app/homepage", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mtoast();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    /*从服务器中获取书籍信息的list*/
                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray commend_list=data.getJSONArray("data");
                    /*向recyclerview所需的list中加内容*/
                    for(int i = 0; i < commend_list.length(); i++){
                        JSONObject commend_item = commend_list.getJSONObject(i);
                        String bookID=commend_item.optString("bookId");
                        int BookID=0;
                        if(!bookID.equals("null"))  BookID=Integer.parseInt(bookID);
                        Recommendation recommendation = new Recommendation(commend_item.getString("content"),commend_item.getString("album"),commend_item.optString("authorId"),BookID);
                        /*get_bookinfo(BookID);
                        Recommendation recommendation = new Recommendation(commend_item.getString("content"),bookName,author,BookID);*/
                        recommendation_lists.add(recommendation);
                    }
                    /*随机获取图片（待完善）*/
                    new Thread(() -> {
                        for(int i = 0;i < recommendation_lists.size(); ++i){
                            Bitmap pic = null;
                            try {
                                String picId=get_randomPicId();
                                //String picId="6e6f1de8ac743c762a4fcd9ecd83c576addf657f88e69c2ba94e2e99d23399d8";
                                recommendation_lists.get(i).setPicId(picId);
                                pic = Tools.getImageBitmapSyn(getActivity(), recommendation_lists.get(i).getPicId());
                            } catch (IOException | JSONException | ParseException e) {
                                Tools.my_toast(Objects.requireNonNull(getActivity()),"图片加载出错啦！");
                            }
                            recommendation_lists.get(i).setPic(pic);
                            Log.d("commendCardAdapter","需要更新");
                            Objects.requireNonNull(getActivity()).runOnUiThread(() -> Objects.requireNonNull(binding.commendCard.getAdapter()).notifyDataSetChanged());
                        }

                    }).start();

                    Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                        if(getActivity() != null)
                        {
                        RecyclerView recyclerView = getActivity().findViewById(R.id.commend_card);
                            if(recyclerView != null)
                            {
                                Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*获取书籍名称和作者名称的函数，等待api中...*/
    /*public void get_bookinfo(int BookID) {
        HttpUtil.getRequestAsyn("/app/book/" + BookID, new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {

                    assert response.body() != null;
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
    /*获取随机图片id的函数，等待api中...*/
    public String get_randomPicId(){
        String picId="6e6f1de8ac743c762a4fcd9ecd83c576addf657f88e69c2ba94e2e99d23399d8";
        return picId;
    }

    private void  mtoast(){
        Objects.requireNonNull(getActivity()).runOnUiThread(() -> Toast.makeText(getActivity(), "请求异常，加载不出来",Toast.LENGTH_LONG).show());
    }

}
