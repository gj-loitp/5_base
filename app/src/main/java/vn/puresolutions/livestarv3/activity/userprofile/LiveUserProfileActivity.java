package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.userprofile.adapter.VideoManagerAdapter;
import vn.puresolutions.livestarv3.activity.userprofile.model.SectionVideo;
import vn.puresolutions.livestarv3.activity.userprofile.model.SingleVideo;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LHomeUtil;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.utilities.v3.VipUtil;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;

public class LiveUserProfileActivity extends BaseActivity implements VideoManagerAdapter.Callback {
    private ArrayList<SectionVideo> lstSectionVideo;
    @BindView(R.id.labLiveProfileScreen_Header)
    LActionBar labHeader;
    @BindView(R.id.lthLiveProfileScreen_Level)
    LTextViewHorizontal lthLevel;
    @BindView(R.id.lthLiveProfileScreen_isFollow)
    LTextViewHorizontal lthIsFollow;
    @BindView(R.id.lthLiveProfileScreen_Schedule)
    LTextViewHorizontal lthSchedule;
    @BindView(R.id.sdvLiveProfileScreen_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.rvLiveProfileScreen_VideoList)
    RecyclerView rvLiveVideoManager;
    @BindView(R.id.tvLiveProfileScreen_Name)
    TextView tvName;
    @BindView(R.id.tvLiveProfileScreen_Following)
    TextView tvFollowing;
    @BindView(R.id.tvLiveProfileScreen_Follower)
    TextView tvFollower;
    @BindView(R.id.lnlProfileScreen_Video)
    LinearLayout lnlProfileScreenVideo;
    @BindView(R.id.tvProfileScreen_Video)
    TextView tvProfileScreenVideo;
    @BindView(R.id.lnlProfileScreen_Following)
    LinearLayout lnlProfileScreenFollowing;
    @BindView(R.id.lnlProfileScreen_Follower)
    LinearLayout lnlProfileScreen_Follower;

    private String mUserID;
    private UserProfile userProfile;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = LDialogUtils.loadingMultiColor(activity, true);
        ButterKnife.bind(this);
        mUserID = getIntent().getStringExtra(Constants.KEY_USER_TO_PROFILE);
        LLog.d(TAG, "~~~~~~~~~~~~LiveUserProfileActivity mUserID " + mUserID);
        loadProfile(mUserID);
    }

    private void loadProfile(String userID) {
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getProfile(userID), new ApiSubscriber<UserProfile>() {
            @Override
            public void onSuccess(UserProfile up) {
                LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        LDialogUtils.hideDialog(dialog);
                    }
                });
                userProfile = up;
                if (userProfile != null) {
                    LLog.d(TAG, ">>>up " + LSApplication.getInstance().getGson().toJson(up));
                    LLog.d(TAG, ">>>userProfile " + LSApplication.getInstance().getGson().toJson(userProfile));
                    init();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LDialogUtils.hideDialog(dialog);
            }
        });
    }

    private void init() {
        createDummyData();
        rvLiveVideoManager.setHasFixedSize(true);
        rvLiveVideoManager.setNestedScrollingEnabled(false);
        rvLiveVideoManager.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        VideoManagerAdapter sectionVideoAdapter = new VideoManagerAdapter(activity, lstSectionVideo, this);
        rvLiveVideoManager.setAdapter(sectionVideoAdapter);

        LImageUtils.loadImage(sdvAvatar, userProfile.getAvatarsPath().getW512h512());
        tvName.setText(userProfile.getName());
        lthLevel.setImageSize(15);
        lthLevel.setText(String.format(getString(R.string.profilescreen_level_label), userProfile.getLevel().getLevel()));
        lthLevel.setBackground(R.drawable.bt_violet);
        if (VipUtil.getLevelIcon(userProfile.getLevel().getLevel()) != 0) {
            lthLevel.setImage(VipUtil.getLevelIcon(userProfile.getLevel().getLevel()));
        }

        updateUIFollowView();
        lthIsFollow.setImageSize(15);

        tvFollowing.setText(String.valueOf(userProfile.getFollow()));
        if (userProfile != null) {
            tvFollower.setText(String.valueOf(userProfile.getRoom().getFollow()));
        }

        if (userProfile.getIsIdol() == Constants.USER_IS_IDOL) {
            lthSchedule.setVisibility(View.VISIBLE);
            lthSchedule.setText(getString(R.string.schedule));
            lthSchedule.setImageSize(15);
            lthSchedule.setImage(R.drawable.ic_schedule);
            lthSchedule.setBackground(R.drawable.bt_red);
            lthSchedule.setImagePadding(2);
            lthSchedule.setOnItemClick(new LTextViewHorizontal.Callback() {
                @Override
                public void OnClickItem() {
                    //TODO
                    AlertMessage.showSuccess(activity, "Dang iplm");
                }
            });
            lnlProfileScreenVideo.setVisibility(View.VISIBLE);
            tvProfileScreenVideo.setText(userProfile.getRoom().getVideos() + "");
        } else {
            lthSchedule.setVisibility(View.GONE);
            lnlProfileScreenVideo.setVisibility(View.GONE);
        }

        labHeader.setTvTitle(getString(R.string.profilescreen_live_title));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lthIsFollow.setOnItemClick(new LTextViewHorizontal.Callback() {
            @Override
            public void OnClickItem() {
                LAnimationUtil.play(lthIsFollow, Techniques.Flash);
                if (!LPref.isUserLoggedIn(activity)) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    intent.putExtra(Constants.LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM, true);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                } else {
                    if (userProfile.getRoom().isIsFollow()) {
                        unfollowIdol();
                    } else {
                        followIdol();
                    }
                }
            }
        });
    }

    //TODO
    public void createDummyData() {
        lstSectionVideo = new ArrayList<SectionVideo>();
        for (int i = 1; i <= 5; i++) {
            SectionVideo sv = new SectionVideo();
            sv.setDate("11/11/2017");
            ArrayList<SingleVideo> singleItem = new ArrayList<SingleVideo>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleVideo("https://www.allkpop.com/upload/2017/06/af_org/13040634/Minah-twice.jpg", "XXXX", 1233, 14234));
            }
            sv.setLstVideo(singleItem);
            lstSectionVideo.add(sv);
        }
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_live_user_profile;
    }

    @Override
    public void onClick() {
        //TODO?
        LLog.d(TAG, "onClick");
    }

    //start follow and unfollow
    private void followIdol() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.followIdol(userProfile.getRoom().getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                if (followIdol != null && followIdol.getMessage() != null) {
                    userProfile.getRoom().setIsFollow(!userProfile.getRoom().isIsFollow());
                    updateUIFollowView();
                    LHomeUtil.reloadDataFollowOrSuggest();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void unfollowIdol() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.unfollowIdol(userProfile.getRoom().getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol followIdol) {
                if (followIdol != null && followIdol.getMessage() != null) {
                    userProfile.getRoom().setIsFollow(!userProfile.getRoom().isIsFollow());
                    updateUIFollowView();
                    LHomeUtil.reloadDataFollowOrSuggest();
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
    //end follow and unfollow

    private void updateUIFollowView() {
        if (userProfile.getRoom().isIsFollow()) {
            lthIsFollow.setText(activity.getString(R.string.followed));
            lthIsFollow.setBackground(R.drawable.bt_followed);
            lthIsFollow.setImage(R.drawable.tick);
        } else {
            lthIsFollow.setText(activity.getString(R.string.follow));
            lthIsFollow.setBackground(R.drawable.bt_follow);
            lthIsFollow.setImage(R.drawable.ic_add_white_48dp);
        }
    }

    @Override
    protected void onDestroy() {
        LDialogUtils.hideDialog(dialog);
        super.onDestroy();
    }

    @OnClick(R.id.lnlProfileScreen_Following)
    void onClickFollowing() {
        //LLog.d(TAG, "~~~onClickFollowing " + mUserID + ", " + userProfile.getName());
        Intent intent = new Intent(activity, FollowingActivity.class);
        intent.putExtra(Constants.KEY_USER_ID, mUserID);
        intent.putExtra(Constants.KEY_USER_NAME, userProfile.getName());
        startActivity(intent);
        LUIUtil.transActivityFadeIn(activity);
    }

    @OnClick(R.id.lnlProfileScreen_Follower)
    void onClickFollower() {
        //LLog.d(TAG, "~~~onClickFollower " + mUserID + ", " + userProfile.getName());
        Intent intent = new Intent(activity, FollowerActivity.class);
        intent.putExtra(Constants.KEY_USER_ID, mUserID);
        intent.putExtra(Constants.KEY_USER_NAME, userProfile.getName());
        startActivity(intent);
        LUIUtil.transActivityFadeIn(activity);
    }
}
