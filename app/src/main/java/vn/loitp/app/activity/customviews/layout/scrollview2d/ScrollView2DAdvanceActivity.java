package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;

public class ScrollView2DAdvanceActivity extends BaseFontActivity {
    private LinearLayout ll;
    private TextView tvInfo;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwoDScrollView twoDScrollView = findViewById(R.id.sv);
        ll = findViewById(R.id.ll);
        tvInfo = findViewById(R.id.tv_info);
        pb = findViewById(R.id.pb);
        twoDScrollView.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            tvInfo.setText("setScrollChangeListner " + x + " - " + y);
        });

        final Button btGenLine = findViewById(R.id.bt_gen_line);
        btGenLine.setOnClickListener(view -> {
            if (btGenLine.isClickable()) {
                btGenLine.setClickable(false);
                btGenLine.setTextColor(Color.GRAY);
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
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < column; j++) {
                Button button = new Button(activity);
                button.setLayoutParams(new LinearLayout.LayoutParams(500, 300));
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
}
