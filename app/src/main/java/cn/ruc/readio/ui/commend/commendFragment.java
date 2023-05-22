package cn.ruc.readio.ui.commend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private FragmentCommendBinding binding;
    private ImageView commend_pic;
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
        recommendation_lists.add(new Recommendation("章北海：自然选择！前进四！","刘慈欣","《三体》"));
        recommendation_lists.add(new Recommendation("黑，真他妈的黑啊","刘慈欣","《三体》"));
        recommendation_lists.add(new Recommendation("card3","不知道写点啥","《xx》"));
        recommendation_lists.add(new Recommendation("card4","随便看看吧","《yy》"));
    }

    public void refreshData(){
        HttpUtil.getRequestAsyn("/app/homepage", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    /*从服务器中获取书籍信息的list*/
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray commend_list=data.getJSONArray("data");
                    /*向recyclerview所需的list中加内容*/
                    for(int i = 0; i < commend_list.length(); i++){
                        JSONObject commend_item = commend_list.getJSONObject(i);
                        Recommendation recommendation = new Recommendation(commend_item.getString("content"),commend_item.getString("album"),commend_item.optString("authorID"));
                        recommendation_lists.add(recommendation);
                    }
                    /*随机获取图片（待完善）*/
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0;i < recommendation_lists.size(); ++i){
                                *//*随机生成一些picID*//*
                                String picID= String.valueOf(R.drawable.warship_natural_selection);
                                *//*从服务器中获取图片*//*
                                //Bitmap pic = HttpUtil.getAvaSyn(picID);
                                commend_pic.setImageResource(R.drawable.warship_natural_selection);
                                //commend_pic.setImageBitmap(pic);
                                Log.d("commendCardAdapter","需要更新");
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @SuppressLint("NotifyDataSetChanged")
                                    @Override
                                    public void run() {
                                        Objects.requireNonNull(binding.commendCard.getAdapter()).notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                    }).run();*/

                    if(getActivity() != null){
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void run() {
                                if(getActivity() != null) {
                                    RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.commend_card);
                                    if (recyclerView != null) {
                                        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void  mtoast(String msg){
        if(getActivity() != null){
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
        }
    }

}
