package vn.loitp.app.activity.customviews.layout.swipereveallayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.grid.SwipeRevealLayoutGridActivity;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.list.SwipeRevealLayoutListActivity;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.recycler.SwipeRevealLayoutRecyclerActivity;

//https://github.com/chthai64/SwipeRevealLayout
@LayoutId(R.layout.activity_swipe_reveal_layout)
@LogTag("SwipeRevealLayoutActivity")
@IsFullScreen(false)
public class SwipeRevealLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_swipe_reveal_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_recycler_view:
                startActivity(new Intent(this, SwipeRevealLayoutRecyclerActivity.class));
                LActivityUtil.tranIn(this);
                return true;

            case R.id.action_list_view:
                startActivity(new Intent(this, SwipeRevealLayoutListActivity.class));
                LActivityUtil.tranIn(this);
                return true;

            case R.id.action_grid_view:
                startActivity(new Intent(this, SwipeRevealLayoutGridActivity.class));
                LActivityUtil.tranIn(this);
                return true;
        }

        return false;
    }

    public void layoutOneOnClick(View v) {
        showShortInformation("Layout 1 clicked", true);
    }

    public void layoutTwoOnClick(View v) {
        showShortInformation("Layout 2 clicked", true);
    }

    public void layoutThreeOnClick(View v) {
        showShortInformation("Layout 3 clicked", true);
    }

    public void layoutFourOnClick(View v) {
        showShortInformation("Layout 4 clicked", true);
    }

    public void moreOnClick(View v) {
        showShortInformation("More clicked", true);
    }

    public void deleteOnClick(View v) {
        showShortInformation("Delete clicked", true);
    }

    public void archiveOnClick(View v) {
        showShortInformation("Archive clicked", true);
    }

    public void helpOnClick(View v) {
        showShortInformation("Help clicked", true);
    }

    public void searchOnClick(View v) {
        showShortInformation("Search clicked", true);
    }

    public void starOnClick(View v) {
        showShortInformation("Star clicked", true);
    }
}
