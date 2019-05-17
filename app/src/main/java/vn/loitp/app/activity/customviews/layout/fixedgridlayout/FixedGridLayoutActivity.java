package vn.loitp.app.activity.customviews.layout.fixedgridlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.layout.fixedgridlayout.FixedGridLayout;

public class FixedGridLayoutActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FixedGridLayout fixedGridLayout = (FixedGridLayout) findViewById(R.id.fgl);
        for (int i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(activity);
            imageView.setTag("Item " + i);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (i % 2 == 0) {
                imageView.setBackgroundColor(Color.BLUE);
            } else {
                imageView.setBackgroundColor(Color.BLACK);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LToast.showShort(activity, "Touch " + imageView.getTag(), R.drawable.bkg_horizontal);
                }
            });
            fixedGridLayout.addView(imageView);
        }
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int row = LDeviceUtil.getRandomNumber(7) + 1;//+1 make sure value != 0
                int col = LDeviceUtil.getRandomNumber(10) + 1;//+1 make sure value != 0
                fixedGridLayout.setGridSize(row, col);
                LToast.showShort(activity, row + "x" + col, R.drawable.bkg_horizontal);
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
        return R.layout.activity_fixed_grid_layout;
    }
}
