package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;

public class ScrollView2DAdvanceActivity extends BaseFontActivity {
    private final int WIDTH_PX = 300;
    private final int HEIGHT_PX = 150;
    private final int MATCH_PX = -1;
    private LinearLayout ll;
    private TextView tvInfo;
    private ProgressBar pb;
    private LinearLayout vg1;
    private LHorizontalScrollView vg2;
    private LinearLayout ll2;
    private ScrollView vg3;
    private LinearLayout ll3;
    private TwoDScrollView vg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll = findViewById(R.id.ll);
        ll2 = findViewById(R.id.ll_2);
        ll3 = findViewById(R.id.ll_3);
        tvInfo = findViewById(R.id.tv_info);
        pb = findViewById(R.id.pb);
        vg1 = findViewById(R.id.vg_1);
        vg2 = findViewById(R.id.vg_2);
        vg3 = findViewById(R.id.vg_3);
        vg4 = findViewById(R.id.vg_4);

        vg4.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            tvInfo.setText("setScrollChangeListner " + x + " - " + y);
        });

        vg2.setOnScrollListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            vg4.scrollTo(scrollX, vg4.getScrollY());
        });

        final Button btGenLine = findViewById(R.id.bt_gen_line);
        btGenLine.setOnClickListener(view -> {
            if (btGenLine.isClickable()) {
                btGenLine.setClickable(false);
                btGenLine.setTextColor(Color.GRAY);

                setSize(vg1, WIDTH_PX, HEIGHT_PX);
                setSize(vg2, MATCH_PX, HEIGHT_PX);
                setSize(vg3, WIDTH_PX, MATCH_PX);
                setSize(vg4, MATCH_PX, MATCH_PX);

                genLine(7, 20);
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
        for (int i = 0; i < row; i++) {
            final LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < column; j++) {
                final Button button = new Button(activity);
                button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
                button.setText("Button " + i + " - " + j);
                button.setOnClickListener(view1 -> {
                    LToast.showShort(activity, "Click " + button.getText().toString(), R.drawable.bkg_horizontal);
                });
                linearLayout.addView(button);
            }
            ll.addView(linearLayout);
        }
        pb.setVisibility(View.GONE);
    }

    private void setSize(@NonNull View view, final int w, final int h) {
        if (w == MATCH_PX) {
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            view.getLayoutParams().width = w;
        }
        if (h == MATCH_PX) {
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            view.getLayoutParams().height = h;
        }
        view.requestLayout();
    }
}
