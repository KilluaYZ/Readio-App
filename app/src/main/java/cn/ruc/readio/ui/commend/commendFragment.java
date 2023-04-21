package cn.ruc.readio.ui.commend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;

public class commendFragment extends Fragment {

    private Toolbar toolbar1;
    private RecyclerView recycler_view;
    private TextView tv1,tv2;
    private View view;
    private List<DataBean> lists;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_commend, container, false);

        initView();
        initData();
        LinearLayoutManager m=new LinearLayoutManager(getContext());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        recyclerViewadapter adapter=new recyclerViewadapter(lists,getContext());
        recycler_view.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initData() {
        lists=new ArrayList<>();
        lists.add(new DataBean("章北海：自然选择！前进四！","——刘慈欣《三体》"));
        lists.add(new DataBean("黑，真他妈的黑啊","——刘慈欣《三体》"));
        lists.add(new DataBean("card3","不知道写点啥"));
        lists.add(new DataBean("card4","随便看看吧"));

    }

    private void initView() {
        recycler_view= (RecyclerView) view.findViewById(R.id.recycler_view);
        tv1= (TextView) view.findViewById(R.id.quote);
        tv2= (TextView) view.findViewById(R.id.source);


    }
}


