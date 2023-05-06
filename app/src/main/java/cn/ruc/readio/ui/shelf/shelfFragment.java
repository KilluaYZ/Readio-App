package cn.ruc.readio.ui.shelf;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentShelfBinding;
import cn.ruc.readio.databinding.FragmentWorksBinding;
import cn.ruc.readio.ui.commend.Recommendation;
import cn.ruc.readio.ui.commend.commendCardAdapter;
import cn.ruc.readio.util.HttpUtil;
import okhttp3.Response;

public class shelfFragment extends Fragment {

    //private FragmentShelfBinding binding;
    private GridView grid_view;

    private View view;

    private List<BookItem> lists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*binding = FragmentShelfBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //refreshData();
        lists=getData();
        grid_view = binding.bookGridview;

        bookAdapter myAdapter = new bookAdapter(getContext(),lists);
        grid_view.setAdapter(myAdapter);
        return root;*/

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_shelf, container, false);
        Context context = view.getContext();

        grid_view= view.findViewById(R.id.book_gridview);
        lists=getData();

        bookAdapter myAdapter = new bookAdapter(getContext(),lists);
        grid_view.setAdapter(myAdapter);

        return view;

    }
    private List<BookItem> getData()
    {
        List<BookItem> data = new ArrayList<>();
        data.add(new BookItem("三体",R.drawable.bookcover1));
        data.add(new BookItem("挪威的森林",R.drawable.bookcover2));
        data.add(new BookItem("活着",R.drawable.bookcover3));
        data.add(new BookItem("红楼梦",R.drawable.bookcover4));
        data.add(new BookItem("百年孤独",R.drawable.bookcover5));
        data.add(new BookItem("哈姆雷特",R.drawable.bookcover6));
        data.add(new BookItem("月亮与六便士",R.drawable.bookcover7));
        data.add(new BookItem("复活",R.drawable.bookcover8));
        data.add(new BookItem("平凡的世界",R.drawable.bookcover9));
        data.add(new BookItem("局外人",R.drawable.bookcover10));
        return data;
    }


    /*public void refreshData(){
        HttpUtil.getRequestAsyn("/app/books/list", new ArrayList<>(), new Callback() {

            public void onFailure(Call call, IOException e) {
                mtoast("请求异常，加载不出来");
            }


            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray data = jsonObject.getJSONArray("data");
                    for(int i = 0; i < data.length(); i++){
                        JSONObject datai = data.getJSONObject(i);
                        JSONObject booki = datai.getJSONObject("data");
                        BookItem book = new BookItem();
                        book.setTitle(booki.getString("bookName"));
                        book.setCoverID(booki.getString("bookID"));

                        lists.add(book);

                    }

                    new Thread(new Runnable() {
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
                    }).run();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.works_column);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }*/

    /*private void  mtoast(String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }*/

}