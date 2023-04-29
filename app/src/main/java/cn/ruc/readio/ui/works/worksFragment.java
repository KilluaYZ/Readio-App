package cn.ruc.readio.ui.works;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentWorksBinding;
import cn.ruc.readio.ui.userpage.User;
import cn.ruc.readio.util.HttpUtil;
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
        HttpUtil.getRequest("/works/getPiecesBrief", new ArrayList<>(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mtoast("请求异常，加载不出来");
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
                        User user = new User(useri.getString("userName"),useri.getString("email"),useri.getString("phoneNumber"));
                        work.setUser(user);
                        if(datai.has("tag")){
                        String tagi = datai.getJSONObject("tag").getString("content");}
                        works.add(work);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.works_column);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void  mtoast(String msg){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }
        });
    }
}