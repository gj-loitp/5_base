package vn.loitp.core.loitp.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.loitp.adhelper.AdHelperActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LSocialUtil;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmMore extends BaseFragment implements View.OnClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.bt_rate_app).setOnClickListener(this);
        view.findViewById(R.id.bt_more_app).setOnClickListener(this);
        view.findViewById(R.id.bt_share_app).setOnClickListener(this);
        view.findViewById(R.id.bt_like_fb_fanpage).setOnClickListener(this);
        view.findViewById(R.id.bt_support).setOnClickListener(this);
        view.findViewById(R.id.bt_ad_helper).setOnClickListener(this);

        //NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        //LUIUtil.setPullLikeIOSVertical(nestedScrollView);
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_more;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_rate_app) {
            LSocialUtil.rateApp(getActivity(), getActivity().getPackageName());
        } else if (id == R.id.bt_more_app) {
            LSocialUtil.moreApp(getActivity());
        } else if (id == R.id.bt_share_app) {
            LSocialUtil.shareApp(getActivity());
        } else if (id == R.id.bt_like_fb_fanpage) {
            LSocialUtil.likeFacebookFanpage(getActivity());
        } else if (id == R.id.bt_support) {
            LSocialUtil.chatMessenger(getActivity());
        } else if (id == R.id.bt_ad_helper) {
            Intent intent = new Intent(getActivity(), AdHelperActivity.class);
            startActivity(intent);
            LActivityUtil.tranIn(getActivity());
        }
    }
}