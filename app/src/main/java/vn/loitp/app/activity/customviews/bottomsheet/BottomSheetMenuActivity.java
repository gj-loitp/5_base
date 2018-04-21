package vn.loitp.app.activity.customviews.bottomsheet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;

public class BottomSheetMenuActivity extends BaseActivity {
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout layoutBottomSheet;
    private Button bt0;
    private Button bt1;
    private Button bt2;

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
        bt0 = (Button) findViewById(R.id.bt_0);
        layoutBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        layoutBottomSheet.findViewById(R.id.bt_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LToast.show(activity, "Click layoutBottomSheet R.id.bt_payment");
            }
        });

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

        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bt0.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bt0.setText("Expand sheet");
                }
            }
        });
    }

    private void click1() {
        bt1 = (Button) findViewById(R.id.bt_1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);
                BottomSheetDialog dialog = new BottomSheetDialog(activity);
                dialog.setContentView(view);
                dialog.show();
            }
        });
    }

    private void click2() {
        bt2 = (Button) findViewById(R.id.bt_2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }
}
