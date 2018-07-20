package vn.loitp.app.activity.demo.film.groupviewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.LToast;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPage extends BaseFragment implements OnClickListener {
    private final String TAG = getClass().getSimpleName();
    public static final String BUNDLE_PAGE = "BUNDLE_PAGE";
    private Page page;
    private RelativeLayout bkg;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        bkg = (RelativeLayout) view.findViewById(R.id.bkg);
        Bundle bundle = getArguments();
        if (bundle != null) {
            page = (Page) bundle.getSerializable(BUNDLE_PAGE);
            if (page != null) {
                LLog.d(TAG, "bundle !null: " + page.getName());
                bkg.setBackgroundColor(page.getColor());
                tv.setText(page.getName());
                LImageUtil.load(getActivity(), page.getUrlImg(), iv);
            }
        } else {
            LLog.d(TAG, "bundle null -> do nothing");
        }
        //event click
        bkg.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.item_page;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bkg:
                LToast.show(getActivity(), "Click " + page.getName());
                break;
        }
    }
}