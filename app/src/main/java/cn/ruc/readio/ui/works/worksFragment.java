package cn.ruc.readio.ui.works;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentWorksBinding;
import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.util.HttpUtil;
import cn.ruc.readio.util.Tools;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class worksFragment extends Fragment {

    private FragmentWorksBinding binding;
    User user = new User("zyy","123456","123456");
    ArrayList<Works> works = new ArrayList<Works>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        refreshData();
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerView = binding.worksColumn;
        recyclerView.setLayoutManager(layoutManager);
        WorkAdapter workAdapter = new WorkAdapter(getContext(),works);
        recyclerView.setAdapter(workAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void refreshData(){
        HttpUtil.getRequestAsyn("/works/getPiecesBrief", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.my_toast(getActivity(),"请求异常，加载不出来");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray data = jsonObject.getJSONArray("data");
                    for(int i = 0; i < data.length(); i++){
                        JSONObject datai = data.getJSONObject(i);
                        JSONObject useri = datai.getJSONObject("user");
                        Works work = new Works();
                        work.setPieceTitle(datai.getString("title"));
                        work.setContent(datai.getString("content"));
                        work.setLikesNum(datai.getInt("likes"));
                        work.setWorkID(datai.getInt("piecesId"));
                        User user = new User(useri.getString("userName"),useri.getString("email"),useri.getString("phoneNumber"));
                        user.setAvaID(useri.getString("avator"));
                        tags tagi = new tags();
                        if(datai.has("tag")){
                            String tagConent = datai.getJSONObject("tag").getString("content");
                            int tagID = datai.getJSONObject("tag").getInt("tagId");
                            int tagLinkedTimes = datai.getJSONObject("tag").getInt("linkedTimes");
                            tagi.setContent(tagConent);
                            tagi.setLinkedTimes(tagLinkedTimes);
                            tagi.setTagId(tagID);
                            work.setTag(tagi);
                        }
                        work.setUser(user);
                        works.add(work);

                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0;i < works.size(); ++i){
//                                Bitmap pic = HttpUtil.getAvaSyn(works.get(i).getUser().getAvaID());
                                Bitmap pic = null;
                                try {
                                    if(getActivity() != null) {
                                        pic = Tools.getImageBitmapSyn(getActivity(), works.get(i).getUser().getAvaID());
                                    }
                                    } catch (IOException | JSONException | ParseException e) {
                                    Tools.my_toast(getActivity(),"图片加载出错啦！");
                                }
                                works.get(i).getUser().setAvator(pic);
                                Log.d("workadpter","需要更新");
                                if(getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.worksColumn.getAdapter().notifyDataSetChanged();
                                        }
                                    });
                                }
                            }

                        }
                    }).run();
                    if(getActivity() != null)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.works_column);
                                if(recyclerView != null) {
                                    recyclerView.getAdapter().notifyDataSetChanged();
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

}