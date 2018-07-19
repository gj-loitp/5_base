package vn.loitp.app.activity.demo.film.group0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPage extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    public static final String BUNDLE_PAGE = "BUNDLE_PAGE";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) frmRootView.findViewById(R.id.tv);
        RelativeLayout bkg = (RelativeLayout) frmRootView.findViewById(R.id.bkg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Page page = (Page) bundle.getSerializable(BUNDLE_PAGE);
            if (page != null) {
                bkg.setBackgroundColor(page.getColor());
                tv.setText(page.getName());
            }
        }
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.item_page;
    }
}