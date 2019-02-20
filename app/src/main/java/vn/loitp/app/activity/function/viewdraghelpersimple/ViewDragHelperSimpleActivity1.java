package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.views.LToast;

public class ViewDragHelperSimpleActivity1 extends BaseFontActivity {
    private VDHView vdhv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vdhv = (VDHView) findViewById(R.id.vdhv);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(activity, "Click");
            }
        });
        findViewById(R.id.bt_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vdhv.setAutoBackToOriginalPosition(!vdhv.isAutoBackToOriginalPosition());
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
        return R.layout.activity_view_draghelper_simple_1;
    }
}
