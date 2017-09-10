package vn.puresolutions.livestarv3.activity.livestream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.endlive.EndLive;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.DateUtils;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.utilities.v3.RoomSocketHelper;
import vn.puresolutions.livestarv3.view.LActionBar;

public class SaveVideoActivity extends BaseActivity {
    private LActionBar lActionBar;
    private AppCompatEditText etNameVideo;
    private TextView tvSaveVideo;
    private LinearLayout llAskSaveVideo;
    private LinearLayout llShowDetailVideo;

    public static final int MODE_ASK_SAVE_VIDEO = 1;
    public static final int MODE_SHOW_DETAIL_VIDEO = 0;

    private int currentMode = MODE_ASK_SAVE_VIDEO;

    private TextView tvNameIdol;
    private TextView tvTime;
    private TextView tvViewCount;
    private TextView tvHeartCount;
    private TextView tvShareCount;
    private TextView tvGiftCount;
    private LinearLayout llBackToHome;
    private UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomStatusBar(false);
        mUserProfile = LPref.getUser(activity);
        if (mUserProfile == null) {
            AlertMessage.showError(activity, R.string.err_unknow);
            return;
        }
        findViews();
        setupActionBar();
        tvSaveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNameVideo.getText().toString().isEmpty()) {
                    AlertMessage.showError(activity, getString(R.string.video_name_cannot_be_empty));
                } else {
                    currentMode = MODE_SHOW_DETAIL_VIDEO;
                    showDetailVideoView(true);
                }
                sendEventSaveVideo();
            }
        });
        llBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });
        currentMode = getIntent().getIntExtra(Constants.LIVESTREAM_SAVE_VIDEO_OR_SHOW_DETAIL, Constants.NOT_FOUND);
        if (currentMode == Constants.NOT_FOUND) {
            AlertMessage.showError(activity, R.string.err_unknow);
            return;
        }
        if (currentMode == MODE_ASK_SAVE_VIDEO) {
            //do nothing
        } else {
            llAskSaveVideo.setVisibility(View.GONE);
            showDetailVideoView(false);
        }
        String currentDate = DateUtils.getCurrentDate();
        etNameVideo.setText(mUserProfile.getName() + "_" + currentDate);
        etNameVideo.setSelection(etNameVideo.length());
    }

    private void setupActionBar() {
        lActionBar.hideBlurView();
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                //do nothing
            }

            @Override
            public void onClickMenu() {
                onBackPressed();
            }
        });
        lActionBar.setTvTitle("");
        lActionBar.hideBackIcon();
        lActionBar.showMenuIcon();
        lActionBar.setImageMenuIcon(R.drawable.delete_button);
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
        return R.layout.activity_save_video;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        etNameVideo = (AppCompatEditText) findViewById(R.id.et_name_video);
        tvSaveVideo = (TextView) findViewById(R.id.tv_save_video);
        llAskSaveVideo = (LinearLayout) findViewById(R.id.ll_ask_save_video);
        llShowDetailVideo = (LinearLayout) findViewById(R.id.ll_show_detail_video);
        tvNameIdol = (TextView) findViewById(R.id.tv_name_idol);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvViewCount = (TextView) findViewById(R.id.tv_view_count);
        tvHeartCount = (TextView) findViewById(R.id.tv_heart_count);
        tvShareCount = (TextView) findViewById(R.id.tv_share_count);
        tvGiftCount = (TextView) findViewById(R.id.tv_gift_count);
        llBackToHome = (LinearLayout) findViewById(R.id.ll_back_to_home);
    }

    private void showDetailVideoView(boolean isDelay) {
        //TODO dummy data
        /*tvNameIdol.setText(LPref.getUser(activity).getName());
        tvTime.setText(endLive.getTotalTime());
        tvViewCount.setText(String.valueOf(endLive.getTotalView()));
        tvHeartCount.setText(String.valueOf(endLive.getTotalHeart()));
        tvShareCount.setText("96");
        tvGiftCount.setText("6699");*/
        if (isDelay) {
            LAnimationUtil.play(llAskSaveVideo, Techniques.SlideOutUp, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    llAskSaveVideo.setVisibility(View.GONE);
                    llShowDetailVideo.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(llShowDetailVideo, Techniques.SlideInUp);
                }

                @Override
                public void onRepeat() {
                    //do nothing
                }

                @Override
                public void onStart() {
                    //do nothing
                }
            });
        } else {
            llAskSaveVideo.setVisibility(View.GONE);
            llShowDetailVideo.setVisibility(View.VISIBLE);
            LAnimationUtil.play(llShowDetailVideo, Techniques.SlideInUp);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentMode == MODE_SHOW_DETAIL_VIDEO) {
            backToHome();
        } else {
            finish();
            LUIUtil.transActivityFadeIn(activity);
        }
    }

    private void backToHome() {
        LLog.d(TAG, "backToHome");
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.KEY_LIVESTREAM_ASK_SAVE_VIDEO, true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        LUIUtil.transActivityFadeIn(activity);
        // endLiveStream();
    }

    private void endLiveStream() {
        Dialog dialog = LDialogUtils.loadingMultiColor(activity, true);
        LDialogUtils.showDialog(dialog);
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.endLive(), new ApiSubscriber<EndLive>() {
            @Override
            public void onSuccess(EndLive endLive) {
                LLog.d(TAG, "endLiveStream onSuccess " + LSApplication.getInstance().getGson().toJson(endLive));
                LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Constants.KEY_LIVESTREAM_ASK_SAVE_VIDEO, true);
                        setResult(Activity.RESULT_OK, returnIntent);
                        LDialogUtils.hideDialog(dialog);
                        finish();
                        LUIUtil.transActivityFadeIn(activity);
                    }
                });
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LDialogUtils.hideDialog(dialog);
            }
        });
    }

    public static class SaveVideoMessage {
        private boolean isSavedClick;

        public boolean isSavedClick() {
            return isSavedClick;
        }

        public void setSavedClick(boolean savedClick) {
            isSavedClick = savedClick;
        }
    }

    private void sendEventSaveVideo() {
        //notify FrmHome user is logged in successfully
        SaveVideoMessage saveVideoMessage = new SaveVideoMessage();
        saveVideoMessage.setSavedClick(true);
        EventBus.getDefault().post(saveVideoMessage);
        //end notify
    }

    public static class EndLiveEvent {
        private EndLive endLive;

        public EndLive getEndLive() {
            return endLive;
        }

        public void setEndLive(EndLive endLive) {
            this.endLive = endLive;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EndLiveEvent endLiveEvent) {
        if (endLiveEvent != null) {
            EndLive endLive = endLiveEvent.getEndLive();
            loadData(endLive);
            //LLog.d(TAG, ">>>>>>>>>>>>>>>>>>>onMessageEvent endLiveEvent " + LSApplication.getInstance().getGson().toJson(endLive));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void loadData(EndLive endLive) {
        tvNameIdol.setText(LPref.getUser(activity).getName());
        tvTime.setText(endLive.getTotalTime());
        tvViewCount.setText(String.valueOf(endLive.getTotalView()));
        tvHeartCount.setText(String.valueOf(endLive.getTotalHeart()));
        tvShareCount.setText("96");
        tvGiftCount.setText("6699");
    }
}
