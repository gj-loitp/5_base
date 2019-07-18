package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;

import loitp.basemaster.R;

public class ParallaxRecyclerViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(new ParallaxAdapter(this));

        LUIUtil.INSTANCE.setPullLikeIOSVertical(recyclerView);
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
        return R.layout.activity_parallax_recycler_view;
    }
}
