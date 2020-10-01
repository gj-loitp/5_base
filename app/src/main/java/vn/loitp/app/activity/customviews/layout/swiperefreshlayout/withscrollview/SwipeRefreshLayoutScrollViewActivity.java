package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_swipe_refresh_scroll_view_layout)
@LogTag("SwipeRefreshLayoutScrollViewActivity")
@IsFullScreen(false)
public class SwipeRefreshLayoutScrollViewActivity extends BaseFontActivity {
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::doTask);
        LUIUtil.Companion.setColorForSwipeRefreshLayout(swipeRefreshLayout);

        TextView textView = findViewById(R.id.textView);
        String poem = LStoreUtil.Companion.readTxtFromRawFolder(R.raw.loitp);
        textView.setText(poem);
    }

    private void doTask() {
        LUIUtil.Companion.setDelay(5000, () -> {
            swipeRefreshLayout.setRefreshing(false);
            showShort("Finish");
        });
    }
}
