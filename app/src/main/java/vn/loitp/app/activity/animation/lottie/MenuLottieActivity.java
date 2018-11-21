package vn.loitp.app.activity.animation.lottie;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

//https://www.lottiefiles.com/?page=1
public class MenuLottieActivity extends BaseFontActivity {
    private List<LottieItem> lottieItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LottieAdapter lottieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        lottieAdapter = new LottieAdapter(activity, lottieItemList);
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
        getListAssetFiles("lottie");
        for (int i = 0; i < stringList.size(); i++) {
            lottieItemList.add(new LottieItem(i + " - " + stringList.get(i), "lottie/" + stringList.get(i)));
        }
        lottieAdapter.notifyDataSetChanged();
    }

    private List<String> stringList = new ArrayList<>();

    private boolean getListAssetFiles(String path) {
        String[] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!getListAssetFiles(path + "/" + file))
                        return false;
                    else {
                        // This is a file
                        stringList.add(file);
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
