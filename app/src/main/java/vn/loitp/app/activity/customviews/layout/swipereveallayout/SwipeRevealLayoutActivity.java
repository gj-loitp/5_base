package vn.loitp.app.activity.customviews.layout.swipereveallayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.grid.SwipeRevealLayoutGridActivity;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.list.SwipeRevealLayoutListActivity;
import vn.loitp.app.activity.customviews.layout.swipereveallayout.recycler.SwipeRevealLayoutRecyclerActivity;

//https://github.com/chthai64/SwipeRevealLayout
public class SwipeRevealLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        return R.layout.activity_swipe_reveal_layout;
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
                LActivityUtil.tranIn(activity);
                return true;

            case R.id.action_list_view:
                startActivity(new Intent(this, SwipeRevealLayoutListActivity.class));
                LActivityUtil.tranIn(activity);
                return true;

            case R.id.action_grid_view:
                startActivity(new Intent(this, SwipeRevealLayoutGridActivity.class));
                LActivityUtil.tranIn(activity);
                return true;
        }

        return false;
    }

    public void layoutOneOnClick(View v) {
        showShort("Layout 1 clicked");
    }

    public void layoutTwoOnClick(View v) {
        showShort("Layout 2 clicked");
    }

    public void layoutThreeOnClick(View v) {
        showShort("Layout 3 clicked");
    }

    public void layoutFourOnClick(View v) {
        showShort("Layout 4 clicked");
    }

    public void moreOnClick(View v) {
        showShort("More clicked");
    }

    public void deleteOnClick(View v) {
        showShort("Delete clicked");
    }

    public void archiveOnClick(View v) {
        showShort("Archive clicked");
    }

    public void helpOnClick(View v) {
        showShort("Help clicked");
    }

    public void searchOnClick(View v) {
        showShort("Search clicked");
    }

    public void starOnClick(View v) {
        showShort("Star clicked");
    }
}
