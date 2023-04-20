package cn.ruc.readio.ui.works;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Date;

import cn.ruc.readio.R;
import cn.ruc.readio.databinding.FragmentWorksBinding;
import cn.ruc.readio.ui.userpage.User;

public class worksFragment extends Fragment {

    private FragmentWorksBinding binding;
    User user = new User("zyy","123456","123456");
    ArrayList<Works> works = new ArrayList<Works>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        for(int i = 0;i < 10;++i){
            works.add(new Works("hellohelhellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello",15, "呆头鹅的幸福生活","遇到小青蛙遇到小青蛙遇到小青蛙遇到小青蛙遇到小青蛙",user));
        }
        works.add(new Works("hellohellohellohelellohellohelellohellohelellohellohelellohellohelellohellohelellohellohelellohellohelellohellohelellolohello",15, "呆头鹅的幸福生活","遇到小青蛙遇到小青蛙",user));
        works.add(new Works("hellohellolohello",15, "呆头鹅的幸福生活呆头鹅的幸福生活","遇到小青蛙",user));
        works.add(new Works("hellohellolohello",15, "呆头鹅的幸福生活","遇到小青蛙",user));
        for(int i = 0;i < 10;++i){
            works.add(new Works("hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello",15, "呆头鹅的幸福生活","遇到小青蛙",user));
        }
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
}