package vn.loitp.app.activity.animation.lottie;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class MenuLottieActivity extends BaseFontActivity {
    private List<String> stringList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LottieAdapter lottieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        lottieAdapter = new LottieAdapter(activity, stringList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(lottieAdapter);
        prepareData();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_lottie;
    }

    private void prepareData() {
        String cover;
        for (int i = 0; i < 5; i++) {
            stringList.add("Loitp " + i);
        }
        lottieAdapter.notifyDataSetChanged();
    }
}
