package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestarv3.activity.WebActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LBottomLiveStream extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private LinearLayout viewGroupChat;
    private LinearLayout viewGroupChatBox;
    private LinearLayout viewGroupController;
    private ImageButton bt0;
    private ImageButton bt1;
    private ImageButton bt2;
    private ImageButton bt3;
    private EditText etChat;
    private TextView tvSendChat;
    private RelativeLayout rlGuideRestrictPublicLivestream;
    private LinearLayout viewGroupBottomMain;

    public final static String STATE_VIEW_CONTROLLER = "STATE_VIEW_CONTROLLER";
    public final static String STATE_VIEW_CHAT = "STATE_VIEW_CHAT";
    public final static String STATE_VIEW_SWITCH_MODE_RESTRICT_PUBLIC = "STATE_VIEW_SWITCH_MODE_RESTRICT_PUBLIC";
    private String stateView = STATE_VIEW_CONTROLLER;

    private boolean isBlueButtonFacebook;
    private boolean isBlueButtonLighting;
    private boolean isBlueButtonCamera;

    private String mModeRoom;

    private UserProfile mUserProfile;
    private OnChatSend mOnChatSend;

    public void setData(UserProfile userProfile) {
        this.mUserProfile = userProfile;
        mModeRoom = userProfile.getRoom().getMode();
        //LLog.d(TAG, "setUserProfile mModeRoom " + mModeRoom);
        updateUIMode(mModeRoom);
    }

    public interface ModeCallback {
        public void onModeChange(String mode);
    }

    public interface OnChatSend {
        public void OnSend(String text);
    }

    public void setOnChatSend(OnChatSend onChatSend) {
        mOnChatSend = onChatSend;
    }

    private ModeCallback modeCallback;

    public void setModeCallback(ModeCallback modeCallback) {
        this.modeCallback = modeCallback;
    }

    private void setModeRoom(String mode, boolean isCallback) {
        //LLog.d(TAG, "setModeRoom " + mode + ", isCallback: " + isCallback);
        mModeRoom = mode;
        if (modeCallback != null && isCallback) {
            modeCallback.onModeChange(mModeRoom);
        }
    }

    public interface StateCallback {
        public void onStateViewChange(String stateView);
    }

    private StateCallback stateCallback;

    public interface ActionCallback {
        public void onFlipCamera();
        public void onFilterMode();
        public void onShareFb();
    }

    private ActionCallback actionCallback;

    public void setActionCallback(ActionCallback callback) {
        actionCallback = callback;
    }

    public void setStateCallback(StateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    private void setStateView(String state) {
        stateView = state;
        if (stateCallback != null) {
            stateCallback.onStateViewChange(stateView);
        }
    }

    public void resetStateViewToController() {
        if (stateView.equals(STATE_VIEW_CHAT)) {
            playCloseAnimViewChat();
        } else if (stateView.equals(STATE_VIEW_SWITCH_MODE_RESTRICT_PUBLIC)) {
            playCloseAnimGuideRestrictPublicLivestream();
        }
    }

    public LBottomLiveStream(Context context) {
        super(context);
        init();
    }

    public LBottomLiveStream(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LBottomLiveStream(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public int getHeightViewGroupBottomMain() {
        int h = 0;
        viewGroupBottomMain.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //int width = viewGroupBottomMain.getMeasuredWidth();
        h = viewGroupBottomMain.getMeasuredHeight();
        return h;
    }

    private void findViews() {
        inflate(getContext(), R.layout.view_l_bottom_live_stream, this);

        viewGroupBottomMain = (LinearLayout) findViewById(R.id.view_group_bottom_main);
        rlGuideRestrictPublicLivestream = (RelativeLayout) findViewById(R.id.rl_guide_private_public_livestream);
        viewGroupChat = (LinearLayout) findViewById(R.id.view_group_chat);
        viewGroupChatBox = (LinearLayout) findViewById(R.id.view_group_chat_box);
        viewGroupController = (LinearLayout) findViewById(R.id.view_group_controller);

        bt0 = (ImageButton) findViewById(R.id.bt_0);
        bt1 = (ImageButton) findViewById(R.id.bt_1);
        bt2 = (ImageButton) findViewById(R.id.bt_2);
        bt3 = (ImageButton) findViewById(R.id.bt_3);
        etChat = (EditText) findViewById(R.id.et_chat);
        tvSendChat = (TextView) findViewById(R.id.tv_send_chat);
    }

    private void init() {
        findViews();
        onClickEvent();
    }

    private void onClickEvent() {
        viewGroupChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewGroupChatBox.getVisibility() == GONE) {
                    viewGroupChatBox.setVisibility(VISIBLE);
                    viewGroupController.setVisibility(GONE);
                    etChat.requestFocus();
                    LAnimationUtil.play(viewGroupChatBox, Techniques.SlideInUp, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            setStateView(STATE_VIEW_CHAT);
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
                }
            }
        });
        bt0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(getContext(), "click 0");
                isBlueButtonFacebook = !isBlueButtonFacebook;
                //updateUIButtonFacebook();
                actionCallback.onShareFb();
            }
        });
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(getContext(), "click 1");
                isBlueButtonLighting = !isBlueButtonLighting;
                updateUIButtonLighting();
                actionCallback.onFilterMode();
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(getContext(), "click 2");
                isBlueButtonCamera = !isBlueButtonCamera;
                updateUIButtonCamera();
                actionCallback.onFlipCamera();
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controlStateLivestream();
            }
        });
        etChat.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                setImeVisibility(hasFocus);
            }
        });
        tvSendChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etChat.getText().toString().isEmpty()) {
                    //TODO send chat
                    mOnChatSend.OnSend(etChat.getText().toString().trim());
                    etChat.setText("");
                }
                playCloseAnimViewChat();
            }
        });
    }

    public void updateUIButtonFacebook() {
        bt0.setBackgroundResource(isBlueButtonFacebook ? R.drawable.facebook_button_blue : R.drawable.facebook_button);
        bt0.setEnabled(false);
    }

    private void updateUIButtonLighting() {
        bt1.setBackgroundResource(isBlueButtonLighting ? R.drawable.lighting_blue : R.drawable.lighting);
    }

    private void updateUIButtonCamera() {
        bt2.setBackgroundResource(isBlueButtonCamera ? R.drawable.camera_blue : R.drawable.camera);
    }

    private void playCloseAnimGuideRestrictPublicLivestream() {
        LAnimationUtil.play(rlGuideRestrictPublicLivestream, Techniques.SlideOutRight, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                rlGuideRestrictPublicLivestream.setVisibility(GONE);
                setStateView(STATE_VIEW_CONTROLLER);
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
    }

    private void showDialogConfirmSwitchToRestrictLivestream() {
        LDialogUtils.showDlg2Option(getContext(), R.drawable.lock_popup, getContext().getString(R.string.switch_to_restrict_mode), getContext().getString(R.string.switch_to_restrict_mode_msg), getContext().getString(R.string.cancel), getContext().getString(R.string.confirm), new LDialogUtils.Callback2() {
            @Override
            public void onClickButton0() {
                //setModeRoom(Constants.MODE_PUBLIC);
            }

            @Override
            public void onClickButton1() {
                setModeRoom(Constants.MODE_RESTRICT, true);
            }
        });
    }

    private void controlStateLivestream() {
        if (mUserProfile.getIsIdol() == Constants.USER_IS_IDOL) {
            if (mModeRoom.equals(Constants.MODE_PUBLIC)) {
                showDialogConfirmSwitchToRestrictLivestream();
            } else {
                setModeRoom(Constants.MODE_PUBLIC, true);
            }
        } else {
            showDialogContactYUP();
        }
    }

    private void showDialogContactYUP() {
        LDialogUtils.showDlg2Option(getContext(), R.mipmap.ic_launcher, getContext().getString(R.string.notify), getContext().getString(R.string.restrict_msg_user_is_not_idol), getContext().getString(R.string.close), getContext().getString(R.string.contact), new LDialogUtils.Callback2() {
            @Override
            public void onClickButton0() {
                //do nothing
            }

            @Override
            public void onClickButton1() {
                Intent intent = new Intent(getContext(), WebActivity.class);
                //TODO change url
                intent.putExtra(Constants.URL, "http://tinnhac.com/mina-twice-nang-cong-chua-ba-le-xinh-dep-khien-fan-me-man-khong-roi-mat-94524.html");
                getContext().startActivity(intent);
                LUIUtil.transActivityFadeIn((Activity) getContext());
            }
        });
    }

    public void updateUIMode(String mode) {
        //LLog.d(TAG, "updateUIMode " + mode);
        if (mode.equals(Constants.MODE_PUBLIC)) {
            AlertMessage.showSuccess(getContext(), getContext().getString(R.string.switch_to_public_mode));
            bt3.setBackgroundResource(R.drawable.lock_button);
            playCloseAnimGuideRestrictPublicLivestream();
        } else if (mode.equals(Constants.MODE_RESTRICT)) {
            bt3.setBackgroundResource(R.drawable.lock_highlight);
            rlGuideRestrictPublicLivestream.setVisibility(VISIBLE);
            LAnimationUtil.play(rlGuideRestrictPublicLivestream, Techniques.SlideInRight, new LAnimationUtil.Callback() {
                @Override
                public void onCancel() {
                    //do nothing
                }

                @Override
                public void onEnd() {
                    setStateView(STATE_VIEW_SWITCH_MODE_RESTRICT_PUBLIC);
                    AlertMessage.showSuccess(getContext(), getContext().getString(R.string.switch_to_restrict_mode_success));
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
        }
    }

    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etChat, 0);
            }
        }
    };

    private void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

    private void playCloseAnimViewChat() {
        LAnimationUtil.play(viewGroupChatBox, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                etChat.clearFocus();
                viewGroupChatBox.setVisibility(GONE);
                viewGroupController.setVisibility(VISIBLE);
                LAnimationUtil.play(viewGroupController, Techniques.SlideInUp, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        setStateView(STATE_VIEW_CONTROLLER);
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
    }
}