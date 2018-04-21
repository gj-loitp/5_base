package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxRecyclerView;

public class ParallaxYayandroidRecyclerViewActivity extends BaseActivity {
    private TestRecyclerAdapter testRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParallaxRecyclerView recyclerView = (ParallaxRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg");
        FakeData.getInstance().getStringList().add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg");

        testRecyclerAdapter = new TestRecyclerAdapter(this, new TestRecyclerAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                ToastUtils.showShort("onClick " + pos);
            }

            @Override
            public void onLongClick(int pos) {
                FakeData.getInstance().getStringList().remove(pos);
                testRecyclerAdapter.notifyItemRemoved(pos);
                testRecyclerAdapter.notifyItemRangeChanged(pos, FakeData.getInstance().getStringList().size());
            }
        });
        recyclerView.setAdapter(testRecyclerAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);
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
