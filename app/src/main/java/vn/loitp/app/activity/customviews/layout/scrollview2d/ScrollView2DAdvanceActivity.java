package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;

public class ScrollView2DAdvanceActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwoDScrollView twoDScrollView = findViewById(R.id.sv);
        final LinearLayout ll = findViewById(R.id.ll);
        twoDScrollView.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            LLog.d(TAG, "setScrollChangeListner " + x + " - " + y);
        });

        findViewById(R.id.bt_add).setOnClickListener(view -> {
            for (int i = 0; i < 50; i++) {
                LinearLayout linearLayout = new LinearLayout(activity);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < 20; j++) {
                    Button button = new Button(activity);
                    button.setLayoutParams(new LinearLayout.LayoutParams(500, 300));
                    button.setText("Button " + i + " - " + j);
                    button.setOnClickListener(view1 -> {
                        LToast.showShort(activity, "Click " + button.getText().toString(), R.drawable.bkg_horizontal);
                    });
                    linearLayout.addView(button);
                }
                ll.addView(linearLayout);
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
}
