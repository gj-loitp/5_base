package vn.loitp.app.activity.home.alllist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.Chap;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmItem extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AdView adView;
    private TextView tv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_item, container, false);
        adView = (AdView) view.findViewById(R.id.adView);
        tv = (TextView) view.findViewById(R.id.tv);
        LUIUtil.createAdBanner(adView);

        Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.d(TAG, "bundle == null");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }

        Chap chap = (Chap) bundle.getSerializable(Constants.MENU_CATEGORY);
        if (chap == null) {
            LLog.d(TAG, "chap == null");
            showDialogError(getString(R.string.err_unknow));
            return view;
        }
        tv.setText(chap.getContent());
        return view;
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}
