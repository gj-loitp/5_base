package vn.puresolutions.livestarv3.activity.homescreen.fragment.livestream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.room.BaseFragmentForLOnlyViewPager;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmLivestream extends BaseFragmentForLOnlyViewPager {

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
        View view = inflater.inflate(R.layout.frm_live_stream, container, false);
        return view;

    }

    /*@Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        //user no setup his room before
        //TODO
        *//*LDialogUtils.showDlg1Option(getActivity(), 0, "Chú ý", "Bạn vui lòng cập nhập thông tin phòng diễn trước khi livestream", "Xác nhận", new LDialogUtils.Callback1() {
            @Override
            public void onClickButton0() {
                Intent intent = new Intent(getActivity(), RoomManagerActivity.class);
                startActivity(intent);
                LUIUtil.transActivityLeftToRightAniamtion(getActivity());
            }
        });*//*
        //user setup his room okay
        Intent intent = new Intent(getActivity(), LivestreamActivity.class);
        startActivity(intent);
        LUIUtil.transActivityNoAniamtion(getActivity());

        //this line is very important
        *//*LUIUtil.setDelay(500, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                HomeMainActivity homeMainActivity = ((HomeMainActivity) getActivity());
                homeMainActivity.scrollToPage(HomeMainActivity.PAGE_HOME);//scroll to home page
            }
        });*//*
    }*/
}