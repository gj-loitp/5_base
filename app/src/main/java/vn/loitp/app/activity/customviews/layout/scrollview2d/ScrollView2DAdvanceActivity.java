package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
    private LinearLayout ll;
    private TextView tvInfo;
    private ProgressBar pb;
    private LinearLayout vg1;
    private HorizontalScrollView vg2;
    private ScrollView vg3;
    private TwoDScrollView vg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll = findViewById(R.id.ll);
        tvInfo = findViewById(R.id.tv_info);
        pb = findViewById(R.id.pb);
        vg1 = findViewById(R.id.vg_1);
        vg2 = findViewById(R.id.vg_2);
        vg3 = findViewById(R.id.vg_3);
        vg4 = findViewById(R.id.vg_4);

        vg4.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            tvInfo.setText("setScrollChangeListner " + x + " - " + y);
        });

        final Button btGenLine = findViewById(R.id.bt_gen_line);
        btGenLine.setOnClickListener(view -> {
            if (btGenLine.isClickable()) {
                btGenLine.setClickable(false);
                btGenLine.setTextColor(Color.GRAY);

                setSize(vg1, WIDTH_PX, HEIGHT_PX);
                setSize(vg2, ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT_PX);
                setSize(vg3, WIDTH_PX, ViewGroup.LayoutParams.MATCH_PARENT);
                setSize(vg4, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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
        for (int i = 0; i < row; i++) {
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < column; j++) {
                Button button = new Button(activity);
                button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
                button.setText("Button " + i + " - " + j);
                button.setOnClickListener(view1 -> {
                    LToast.showShort(activity, "Click " + button.getText().toString(), R.drawable.bkg_horizontal);
                });
                linearLayout.addView(button);
            }
            ll.addView(linearLayout);
            ll.invalidate();
        }
        pb.setVisibility(View.GONE);
    }

    private void setSize(@NonNull View view, final int w, final int h) {
        view.getLayoutParams().width = w;
        view.getLayoutParams().height = h;
        view.requestLayout();
    }
}
