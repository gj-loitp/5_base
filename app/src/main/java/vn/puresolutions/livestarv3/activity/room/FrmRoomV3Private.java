package vn.puresolutions.livestarv3.activity.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.activity.homescreen.GoToPrivateRoomNoCoinActivity;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 8/1/2017.
 */

public class FrmRoomV3Private extends BaseFragmentForLOnlyViewPager {
    private final String TAG = getClass().getSimpleName();
    private Room mRoom;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_room_v3_private, container, false);
        Bundle bundle = getArguments();
        if (bundle == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }
        mRoom = (Room) bundle.getSerializable(Constants.KEY_ROOM);
        if (mRoom == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return view;
        }
        view.findViewById(R.id.tv_watch_prev_live).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvWatchPrevLive();
            }
        });
        view.findViewById(R.id.tv_buy_ticket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvBuyTicket();
            }
        });

        SimpleDraweeView ivAvatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
        TextView tvWatchCount = (TextView) view.findViewById(R.id.tv_watch_count);
        TextView tvLikeCount = (TextView) view.findViewById(R.id.tv_like_count);

        LImageUtils.loadImage(ivAvatar, mRoom.getBanners().getBanner());
        //TODO set watch count
        tvWatchCount.setText(String.valueOf(mRoom.getSession().getView()));
        tvLikeCount.setText(String.valueOf(mRoom.getReceivedHeart()));

        LUIUtil.setMarquee(tvWatchCount);
        LUIUtil.setMarquee(tvLikeCount);
        return view;
    }

    private void onClickTvWatchPrevLive() {
        AlertMessage.showSuccess(getActivity(), "onClickTvWatchPrevLive");
    }

    private void onClickTvBuyTicket() {
        if (LPref.isUserLoggedIn(getActivity())) {
            UserProfile userProfile = LPref.getUser(getActivity());
            if (userProfile.getMoney() >= Constants.TICKET_PRIVATE_ROOM_PRICE) {
                AlertMessage.showSuccess(getActivity(), "buy");
            } else {
                Intent intent = new Intent(getActivity(), GoToPrivateRoomNoCoinActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            LUIUtil.transActivityFadeIn(getActivity());
        }
    }
}
