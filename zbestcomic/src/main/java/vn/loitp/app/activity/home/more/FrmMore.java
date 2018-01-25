package vn.loitp.app.activity.home.more;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmMore extends BaseFragment implements View.OnClickListener {
    private AdView adView;

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
        View view = inflater.inflate(R.layout.frm_photo_more, container, false);
        view.findViewById(R.id.bt_rate_app).setOnClickListener(this);
        view.findViewById(R.id.bt_more_app).setOnClickListener(this);
        view.findViewById(R.id.bt_share_app).setOnClickListener(this);
        view.findViewById(R.id.bt_like_fb_fanpage).setOnClickListener(this);
        view.findViewById(R.id.bt_support).setOnClickListener(this);

        //NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        //LUIUtil.setPullLikeIOSVertical(nestedScrollView);

        adView = (AdView) view.findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rate_app:
                LSocialUtil.rateApp(getActivity(), getActivity().getPackageName());
                break;
            case R.id.bt_more_app:
                LSocialUtil.moreApp(getActivity());
                break;
            case R.id.bt_share_app:
                LSocialUtil.shareApp(getActivity());
                break;
            case R.id.bt_like_fb_fanpage:
                LSocialUtil.likeFacebookFanpage(getActivity());
                break;
            case R.id.bt_support:
                LSocialUtil.chatMessenger(getActivity());
                break;
        }
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