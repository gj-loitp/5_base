package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.scrollview.LHorizontalScrollView;
import vn.loitp.views.scrollview.LScrollView;
import vn.loitp.views.scrollview.TwoDScrollView;

public class ScrollView2DAdvanceActivity extends BaseFontActivity {
    private final int WIDTH_PX = 300;
    private final int HEIGHT_PX = 150;
    private TextView tvInfo;
    private ProgressBar pb;
    private LinearLayout vg1;
    private LHorizontalScrollView vg2;
    private LinearLayout ll2;
    private LScrollView vg3;
    private LinearLayout ll3;
    private TwoDScrollView vg4;
    private RelativeLayout rl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rl4 = findViewById(R.id.rl_4);
        ll2 = findViewById(R.id.ll_2);
        ll3 = findViewById(R.id.ll_3);
        tvInfo = findViewById(R.id.tv_info);
        pb = findViewById(R.id.pb);
        vg1 = findViewById(R.id.vg_1);
        vg2 = findViewById(R.id.vg_2);
        vg3 = findViewById(R.id.vg_3);
        vg4 = findViewById(R.id.vg_4);

        vg2.setOnScrollListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LLog.d(TAG, "vg2 setOnScrollListener " + scrollX);
            vg4.scrollTo(scrollX, vg4.getScrollY());
        });

        vg3.setOnScrollListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LLog.d(TAG, "vg3 setOnScrollListener " + scrollX);
            vg4.scrollTo(vg4.getScrollX(), scrollY);
        });

        vg4.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            tvInfo.setText("setScrollChangeListner " + x + " - " + y);
            LLog.d(TAG, "vg4 setOnScrollListener " + x);
            vg2.scrollTo(x, vg2.getScrollY());
            vg3.scrollTo(vg3.getScrollX(), y);
        });

        final Button btGenLine = findViewById(R.id.bt_gen_line);
        btGenLine.setOnClickListener(view -> {
            if (btGenLine.isClickable()) {
                btGenLine.setClickable(false);
                btGenLine.setTextColor(Color.GRAY);

                LUIUtil.setSize(vg1, WIDTH_PX, HEIGHT_PX);
                LUIUtil.setSize(vg2, ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT_PX);
                LUIUtil.setSize(vg3, WIDTH_PX, ViewGroup.LayoutParams.MATCH_PARENT);
                LUIUtil.setSize(vg4, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                genLine(30, 24);
            }
        });
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
        return R.layout.activity_scrollview_2d_advance;
    }

    private void genLine(final int column, final int row) {
        //gen view group 2
        for (int i = 0; i < column; i++) {
            final Button button = new Button(activity);
            button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
            button.setText("Date " + i);
            button.setOnClickListener(view1 -> {
                LToast.showShort(activity, "Click " + button.getText().toString(), R.drawable.bkg_horizontal);
            });
            ll2.addView(button);
        }

        //gen view group 3
        for (int i = 0; i < row; i++) {
            final Button button = new Button(activity);
            button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
            button.setText(i + ":00:00");
            button.setOnClickListener(view1 -> {
                LToast.showShort(activity, "Click " + button.getText().toString(), R.drawable.bkg_horizontal);
            });
            ll3.addView(button);
        }

        //gen view group 4
        final List<Square> squareList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                final Square square = new Square();
                square.setName(i + "x" + j);
                squareList.add(square);
            }
        }

        final SquareAdapter squareAdapter = new SquareAdapter(activity, squareList, WIDTH_PX, HEIGHT_PX, new SquareAdapter.Callback() {
            @Override
            public void onClick(Square square, int position) {
                LToast.show(activity, "onClick " + square.getName(), R.drawable.bkg_horizontal);
            }

            @Override
            public void onLongClick(Square square, int position) {
                LToast.show(activity, "onLongClick " + square.getName(), R.drawable.bkg_horizontal);
            }
        });
        final RecyclerView recyclerView = new RecyclerView(activity);
        rl4.addView(recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(activity, column));
        recyclerView.setAdapter(squareAdapter);

        //add sticker img
        final ImageView sticker0 = new ImageView(activity);
        sticker0.setImageResource(R.drawable.loitp);
        sticker0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final RelativeLayout.LayoutParams rl0 = new RelativeLayout.LayoutParams(WIDTH_PX, HEIGHT_PX);
        rl0.setMargins(WIDTH_PX, HEIGHT_PX * 7, 0, 0);
        sticker0.setLayoutParams(rl0);
        rl4.addView(sticker0);

        final ImageView sticker1 = new ImageView(activity);
        sticker1.setImageResource(R.drawable.loitp);
        sticker1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final RelativeLayout.LayoutParams rl1 = new RelativeLayout.LayoutParams((int) (WIDTH_PX * 2.5), (int) (HEIGHT_PX * 2.5));
        rl1.setMargins((int) (WIDTH_PX * 1.5), HEIGHT_PX * 2, 0, 0);
        sticker1.setLayoutParams(rl1);
        rl4.addView(sticker1);

        pb.setVisibility(View.GONE);
    }
}
