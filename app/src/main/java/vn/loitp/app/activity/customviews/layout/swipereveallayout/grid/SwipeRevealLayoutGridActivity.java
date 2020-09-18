package vn.loitp.app.activity.customviews.layout.swipereveallayout.grid;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_swipe_reveal_layout_grid)
@LogTag("SwipeRevealLayoutGridActivity")
public class SwipeRevealLayoutGridActivity extends BaseFontActivity {
    private GridAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setupGrid();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.restoreStates(savedInstanceState);
        }
    }

    private void setupGrid() {
        final GridView gridView = findViewById(R.id.gridview);
        adapter = new GridAdapter(this, createList(20));
        gridView.setAdapter(adapter);
    }

    private List<String> createList(int n) {
        final List<String> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add("View " + i);
        }

        return list;
    }

    private void setupActionBar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(v -> finish());
        }
    }
}
