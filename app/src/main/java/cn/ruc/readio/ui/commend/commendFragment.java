package cn.ruc.readio.ui.commend;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ruc.readio.R;
public class commendFragment extends Fragment {
    private RecyclerView recycler_view;
    private Context context;
    private View view;
    private List<Recommendation> lists;

    private recyclerViewAdapter myadapter;
    public commendFragment() {
        // Required empty public constructor
    }
    /*@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_commend, container, false);
        context = view.getContext();

        initView();
        initData();
        LinearLayoutManager m=new LinearLayoutManager(context);
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        myadapter =new recyclerViewAdapter(lists,context);
        recycler_view.setAdapter(myadapter);

        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();

    }*/

    private void initData() {
        lists=new ArrayList<>();
        lists.add(new Recommendation("章北海：自然选择！前进四！","——刘慈欣《三体》"));
        lists.add(new Recommendation("黑，真他妈的黑啊","——刘慈欣《三体》"));
        lists.add(new Recommendation("card3","不知道写点啥"));
        lists.add(new Recommendation("card4","随便看看吧"));
    }

    private void initView() {
        TextView tv1,tv2;
        recycler_view= (RecyclerView) view.findViewById(R.id.recycler_view);
        tv1= (TextView) view.findViewById(R.id.quote);
        tv2= (TextView) view.findViewById(R.id.source);

    }
}
