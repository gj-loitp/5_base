package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.recyclerview.parallaxyay.ParallaxRecyclerView;

import loitp.basemaster.R;

public class ParallaxYayandroidRecyclerViewActivity extends BaseFontActivity {
    private TestRecyclerAdapter testRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.Companion.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");

        testRecyclerAdapter = new TestRecyclerAdapter(this, new TestRecyclerAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                showShort("onClick " + pos);
            }

            @Override
            public void onLongClick(int pos) {
                FakeData.Companion.getInstance().getStringList().remove(pos);
                testRecyclerAdapter.notifyItemRemoved(pos);
                testRecyclerAdapter.notifyItemRangeChanged(pos, FakeData.Companion.getInstance().getStringList().size());
            }
        });
        recyclerView.setAdapter(testRecyclerAdapter);

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
        return R.layout.activity_parallax_recycler_view_yayandroid;
    }
}
