package vn.loitp.app.activity.customviews.bottomsheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.views.LToast;

import loitp.basemaster.R;

public class BottomSheetMenuActivity extends BaseFontActivity {
    private BottomSheetBehavior sheetBehavior;
    private Button bt0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        click0();
        click1();
        click2();
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
        return R.layout.activity_bottomsheet_menu;
    }

    private void click0() {
        bt0 = findViewById(R.id.bt_0);
        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
        layoutBottomSheet.findViewById(R.id.bt_payment).setOnClickListener(v -> LToast.show(activity, "Click layoutBottomSheet R.id.bt_payment"));

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        LLog.d(TAG, "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        LLog.d(TAG, "STATE_HIDDEN");
                        bt0.setText("Close Sheet");
                        break;
                    }
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        LLog.d(TAG, "STATE_COLLAPSED");
                        bt0.setText("Expand Sheet");
                        break;
                    }
                    case BottomSheetBehavior.STATE_DRAGGING:
                        LLog.d(TAG, "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        LLog.d(TAG, "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                LLog.d(TAG, "onSlide " + slideOffset);
            }
        });

        bt0.setOnClickListener(v -> {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bt0.setText("Close sheet");
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bt0.setText("Expand sheet");
            }
        });
    }

    private void click1() {
        final Button bt1 = findViewById(R.id.bt_1);
        bt1.setOnClickListener(v -> {
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);
            BottomSheetDialog dialog = new BottomSheetDialog(activity);
            dialog.setContentView(view);
            dialog.show();
        });
    }

    private void click2() {
        final Button bt2 = findViewById(R.id.bt_2);
        bt2.setOnClickListener(v -> {
            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        });
    }
}
