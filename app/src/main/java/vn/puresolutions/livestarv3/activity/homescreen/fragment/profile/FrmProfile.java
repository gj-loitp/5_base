package vn.puresolutions.livestarv3.activity.homescreen.fragment.profile;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign.GetCoinPagerActivity;
import vn.puresolutions.livestarv3.activity.bank.manager.BankHistoryActivity;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.profile.view.LRowProflieAction;
import vn.puresolutions.livestarv3.activity.room.BaseFragmentForLOnlyViewPager;
import vn.puresolutions.livestarv3.activity.userprofile.EditInfoActivity;
import vn.puresolutions.livestarv3.activity.userprofile.EditInfoNotifyActivity;
import vn.puresolutions.livestarv3.activity.userprofile.FollowerActivity;
import vn.puresolutions.livestarv3.activity.userprofile.FollowingActivity;
import vn.puresolutions.livestarv3.activity.userprofile.ProfileLevelActivity;
import vn.puresolutions.livestarv3.activity.userprofile.RoomManagerActivity;
import vn.puresolutions.livestarv3.activity.userprofile.VideoManagerActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmProfile extends BaseFragmentForLOnlyViewPager {
    private final String TAG = getClass().getSimpleName();
    private Dialog dialog;
    @BindView(R.id.rpaProperty)
    LRowProflieAction rpaProperty;
    @BindView(R.id.rpaRoomManager)
    LRowProflieAction rpaRoomManager;
    @BindView(R.id.rpaSettingNoti)
    LRowProflieAction rpaSettingNoti;
    @BindView(R.id.sdvProfileScreen_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.tvProfileScreen_Logout)
    TextView tvLogout;
    @BindView(R.id.tvProfileScreen_Name)
    TextView tvProfileScreen_Name;
    @BindView(R.id.lnlProfileScreen_Following)
    LinearLayout lnlProfileScreen_Following;
    @BindView(R.id.lnlProfileScreen_Follower)
    LinearLayout lnlProfileScreen_Follower;
    @BindView(R.id.lnlProfileScreen_Video)
    LinearLayout lnlProfileScreen_Video;
    @BindView(R.id.lnlLogout)
    LinearLayout lnlLogout;
    @BindView(R.id.tvProfileScreen_Title)
    LActionBar labHeader;
    @BindView(R.id.lthProfileScreen_Level)
    LTextViewHorizontal lthLevelInfo;
    @BindView(R.id.tvProfileScreen_Follower)
    TextView tvFollower;
    @BindView(R.id.tvProfileScreen_Following)
    TextView tvFollowing;
    @BindView(R.id.tvProfileScreen_Video)
    TextView tvVideo;
    @BindView(R.id.line_2)
    View line2;

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
        View view = inflater.inflate(R.layout.frm_profile_base, container, false);
        LUIUtil.setMarquee((TextView) view.findViewById(R.id.tv_follower));
        LUIUtil.setMarquee((TextView) view.findViewById(R.id.tv_following));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        LUIUtil.setTextBold(tvFollower);
        LUIUtil.setTextBold(tvFollowing);
        LUIUtil.setTextBold(tvVideo);
        UserProfile userProfile = LPref.getUser(getActivity());

        //hide video view if user is not idol
        if (userProfile != null) {
            lnlProfileScreen_Video.setVisibility(userProfile.getIsIdol() == Constants.USER_IS_IDOL ? View.VISIBLE : View.GONE);
            line2.setVisibility(userProfile.getIsIdol() == Constants.USER_IS_IDOL ? View.VISIBLE : View.GONE);
        }

        dialog = LDialogUtils.loadingMultiColor(getActivity(), true);
        labHeader.setTvTitle(getString(R.string.profilescreen_title));
        labHeader.getIvIconMenu().setVisibility(View.VISIBLE);
        labHeader.getIvIconMenu().setImageResource(R.drawable.editblue);
        labHeader.inviBackIcon();
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                //do nothing
            }

            @Override
            public void onClickMenu() {
                Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
        rpaProperty.setMainTitle(getString(R.string.coin_manager));
        if (userProfile != null) {
            rpaProperty.setSubTitle(String.format(getString(R.string.coin_amount), userProfile.getMoney()));
        }
        rpaProperty.setIcon(R.drawable.taisan);
        rpaProperty.setOnClickItem(new LRowProflieAction.Callback() {
            @Override
            public void OnClickContainer() {
                Intent intent = new Intent(getActivity(), BankHistoryActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }

            @Override
            public void OnClickButton() {
                Intent intent = new Intent(getActivity(), GetCoinPagerActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });

        rpaRoomManager.setMainTitle(getString(R.string.roommanagerscreen_title));
        rpaRoomManager.setInvisibleButton();
        rpaRoomManager.setSubTitle("Quản lý ảnh, quà tặng của room");
        rpaRoomManager.setIcon(R.drawable.phongdien);
        rpaRoomManager.setOnClickItem(new LRowProflieAction.Callback() {
            @Override
            public void OnClickContainer() {
                Intent intent = new Intent(getActivity(), RoomManagerActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }

            @Override
            public void OnClickButton() {
                //do nothing
            }
        });

        rpaSettingNoti.setMainTitle(getString(R.string.edit_profilescreen_title_notify));
        rpaSettingNoti.setInvisibleButton();
        rpaSettingNoti.setSubTitle("Cài đặt để nhận thông báo mới nhất");
        rpaSettingNoti.setIcon(R.drawable.thongbao);
        rpaSettingNoti.setOnClickItem(new LRowProflieAction.Callback() {
            @Override
            public void OnClickContainer() {
                Intent intent = new Intent(getActivity(), EditInfoNotifyActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }

            @Override
            public void OnClickButton() {
                //do nothing
            }
        });
        lthLevelInfo.setOnItemClick(new LTextViewHorizontal.Callback() {
            @Override
            public void OnClickItem() {
                Intent intent = new Intent(getActivity(), ProfileLevelActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
    }

    private void updateUi(UserProfile userProfile) {
        if (userProfile != null) {
            if (userProfile.getAvatarsPath() != null) {
                if ((String) userProfile.getAvatarsPath().getAvatar() != null) {
                    LImageUtils.loadImage(sdvAvatar, (String) userProfile.getAvatarsPath().getW512h512());
                }
            }
            tvProfileScreen_Name.setText(userProfile.getName());
            tvVideo.setText("0");
            int lv = (Integer) userProfile.getLevel().getLevel();
            lthLevelInfo.setImageSize(20);
            lthLevelInfo.setText(String.format(getString(R.string.profilescreen_level_label), lv));
            lthLevelInfo.setBackground(R.drawable.bt_violet);
            if (lv == 0) {
                lthLevelInfo.getIvIcon().setVisibility(View.GONE);
            } else if (lv >= 1 && lv <= 3) {
                lthLevelInfo.setImage(R.drawable.chick_green);
            } else if (lv >= 4 && lv <= 7) {
                lthLevelInfo.setImage(R.drawable.chick_orange);
            } else if (lv >= 8 && lv <= 10) {
                lthLevelInfo.setImage(R.drawable.chick_pink);
            }
            tvFollowing.setText(String.valueOf(userProfile.getFollowings()));
            if (userProfile.getRoom() != null) {
                tvFollower.setText(String.valueOf(userProfile.getRoom().getFollow()));
            }
            rpaProperty.setSubTitle(String.format(getString(R.string.coin_amount), userProfile.getMoney()));
        }
    }

    @OnClick(R.id.lnlLogout)
    void doLogout() {
        LPref.removeAll(getActivity());
        //restart app
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        LUIUtil.transActivityNoAniamtion(getActivity());
        AlertMessage.showSuccess(getActivity(), getString(R.string.logout_success));
        System.exit(0);
        //end restart app
    }

    @OnClick(R.id.lnlProfileScreen_Following)
    void goFollowingScreen() {
        UserProfile userProfile = LPref.getUser(getActivity());
        if (userProfile != null) {
            Intent intent = new Intent(getActivity(), FollowingActivity.class);
            intent.putExtra(Constants.KEY_USER_ID, userProfile.getId());
            intent.putExtra(Constants.KEY_USER_NAME, userProfile.getName());
            startActivity(intent);
            LUIUtil.transActivityFadeIn(getActivity());
        }
    }


    @OnClick(R.id.lnlProfileScreen_Follower)
    void goFollowerScreen() {
        UserProfile userProfile = LPref.getUser(getActivity());
        if (userProfile != null) {
            Intent intent = new Intent(getActivity(), FollowerActivity.class);
            intent.putExtra(Constants.KEY_USER_ID, userProfile.getId());
            intent.putExtra(Constants.KEY_USER_NAME, userProfile.getName());
            startActivity(intent);
            LUIUtil.transActivityFadeIn(getActivity());
        }
    }

    @OnClick(R.id.lnlProfileScreen_Video)
    void goVideoScreen() {
        Intent intent = new Intent(getActivity(), VideoManagerActivity.class);
        startActivity(intent);
        LUIUtil.transActivityFadeIn(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LLog.d(TAG, "isVisibleToUser");
            reloadData();
        } else {
            LLog.d(TAG, "!isVisibleToUser");
        }
    }

    private void reloadData() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getProfile(), new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile result) {
                LPref.setUser(getActivity().getBaseContext(), result);
                UserProfile userProfile = LPref.getUser(getActivity());
                updateUi(userProfile);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
}

