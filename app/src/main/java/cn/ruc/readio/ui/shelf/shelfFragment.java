package cn.ruc.readio.ui.shelf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.util.Pair;
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


import cn.ruc.readio.MainActivity;
import cn.ruc.readio.R;
import cn.ruc.readio.bookReadActivity.readBookActivity;
import cn.ruc.readio.databinding.FragmentShelfBinding;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class shelfFragment extends Fragment {

    private FragmentShelfBinding binding;
    private GridView grid_view;

    private List<BookItem> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShelfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lists = new ArrayList<>();
        //refreshData();
        lists=getData();
        grid_view = binding.bookGridview;

        bookAdapter myAdapter = new bookAdapter(getContext(),lists);
        grid_view.setAdapter(myAdapter);

        return root;

        /*setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_shelf, container, false);
        Context context = view.getContext();

        grid_view= view.findViewById(R.id.book_gridview);
        lists=getData();

        bookAdapter myAdapter = new bookAdapter(getContext(),lists);
        grid_view.setAdapter(myAdapter);

        return view;*/

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
        String token="05b1c3dd3d048c587cf0b483814227b24c0c97ad";
        Activity activity = null;
        ArrayList<Pair<String,String>> queryParaameter = null;
        HttpUtil.getRequestWithTokenAsyn(activity,"/app/books/list",queryParaameter, new Callback() {

            //Request.Builder request = new Request.Builder().addHeader("Authorization",token);
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                mtoast("请求异常，加载不出来");
            }
            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray mybook_list = data.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject mybook = mybook_list.getJSONObject(i);
                        BookItem book = new BookItem(mybook.getString("bookName"), mybook.getString("authorID"));
                        lists.add(book);
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
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void  mtoast(String msg){
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show());
    }
}