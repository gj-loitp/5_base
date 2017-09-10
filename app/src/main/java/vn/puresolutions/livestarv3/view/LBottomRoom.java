package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.Item;
import vn.puresolutions.livestar.corev3.api.model.v3.listgift.ListGift;
import vn.puresolutions.livestar.corev3.api.model.v3.roomfindbyID.RoomFindByID;
import vn.puresolutions.livestarv3.activity.room.adapter.GiftsAdapter;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LBottomRoom extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private LinearLayout viewGroupChat;
    private LinearLayout viewGroupChatBox;
    private LinearLayout viewGroupController;
    private LinearLayout viewGroupMore;
    private LinearLayout viewGroupGifts;
    private LinearLayout llControl;
    private Button bt0;
    private Button bt1;
    private Button bt2;
    private EditText etChat;
    private TextView tvSendChat;
    private LTextViewVertical lTextViewShareFb;
    private LTextViewVertical lTextViewCalendar;
    private RecyclerView recyclerViewGifts;

    public final static String STATE_VIEW_CONTROLLER = "STATE_VIEW_CONTROLLER";
    public final static String STATE_VIEW_CHAT = "STATE_VIEW_CHAT";
    public final static String STATE_VIEW_GIFTS = "STATE_VIEW_GIFTS";
    public final static String STATE_VIEW_HEART = "STATE_VIEW_HEART";
    public final static String STATE_VIEW_MORE = "STATE_VIEW_MORE";
    private String stateView = STATE_VIEW_CONTROLLER;

    private Point pointHeartButton;

    private RoomFindByID mRoomFindByID;

    public interface StateCallback {
        public void onStateViewChange(String stateView);
    }

    private StateCallback stateCallback;
    private OnChatSend mOnChatSend;

    public void setStateCallback(StateCallback stateCallback) {
        this.stateCallback = stateCallback;
    }

    public void setOnChatSend(OnChatSend onChatSend) {
        mOnChatSend = onChatSend;
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
        } else if (stateView.equals(STATE_VIEW_GIFTS)) {
            playCloseAnimViewGifts();
        } else if (stateView.equals(STATE_VIEW_HEART)) {
            //TODO
        } else if (stateView.equals(STATE_VIEW_MORE)) {
            playCloseAnimViewMore();
        }
    }

    public interface CallbackGift {
        public void onClickGift(Item item);
    }

    public interface OnChatSend {
        public void OnSend(String text);
    }

    private CallbackGift callbackGift;

    public void setCallbackGift(CallbackGift callbackGift) {
        this.callbackGift = callbackGift;
    }

    public LBottomRoom(Context context) {
        super(context);
        init();
    }

    public LBottomRoom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LBottomRoom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void findViews() {
        inflate(getContext(), R.layout.view_l_bottom_room, this);

        llControl = (LinearLayout) findViewById(R.id.ll_control);
        viewGroupChat = (LinearLayout) findViewById(R.id.view_group_chat);
        viewGroupChatBox = (LinearLayout) findViewById(R.id.view_group_chat_box);
        viewGroupController = (LinearLayout) findViewById(R.id.view_group_controller);
        viewGroupMore = (LinearLayout) findViewById(R.id.view_group_more);
        viewGroupGifts = (LinearLayout) findViewById(R.id.view_group_gifts);
        bt0 = (Button) findViewById(R.id.bt_0);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        etChat = (EditText) findViewById(R.id.et_chat);
        tvSendChat = (TextView) findViewById(R.id.tv_send_chat);
        lTextViewShareFb = (LTextViewVertical) findViewById(R.id.l_text_view_share_fb);
        lTextViewCalendar = (LTextViewVertical) findViewById(R.id.l_text_view_calendar);
        recyclerViewGifts = (RecyclerView) findViewById(R.id.recycler_view_gifts);
    }

    private void init() {
        findViews();
        setupViewOpen();
        setupViewGifts();
        onClickEvent();
    }

    public void setData(RoomFindByID roomFindByID) {
        mRoomFindByID = roomFindByID;
    }

    private Point getPointOfView(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return new Point(location[0], location[1]);
    }

    public interface CallbackOnClick {
        public void onClickHeartButton(int posX, int posY, int widthHeartButton, int heightHeartButton);
    }

    private CallbackOnClick callbackOnClick;

    public void setCallbackOnClick(CallbackOnClick callbackOnClick) {
        this.callbackOnClick = callbackOnClick;
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
                if (viewGroupGifts.getVisibility() == GONE) {
                    LAnimationUtil.play(viewGroupController, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            setStateView(STATE_VIEW_GIFTS);
                            viewGroupGifts.setVisibility(VISIBLE);
                            LAnimationUtil.play(viewGroupGifts, Techniques.SlideInUp);
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
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointHeartButton == null) {
                    pointHeartButton = getPointOfView(bt1);
                }
                //LLog.d(TAG, "getHeartButtonPosition view point x,y (" + point.x + ", " + point.y + ")");
                if (callbackOnClick != null && pointHeartButton != null) {
                    callbackOnClick.onClickHeartButton(pointHeartButton.x, pointHeartButton.y, bt1.getWidth(), bt1.getHeight());
                }
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewGroupMore.getVisibility() == GONE) {
                    LAnimationUtil.play(viewGroupController, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            setStateView(STATE_VIEW_MORE);
                            viewGroupMore.setVisibility(VISIBLE);
                            LAnimationUtil.play(viewGroupMore, Techniques.SlideInUp);
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
        etChat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                setImeVisibility(hasFocus);
            }
        });
        tvSendChat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etChat.getText().toString().isEmpty()) {
                    mOnChatSend.OnSend(etChat.getText().toString().trim());
                    etChat.setText("");
                }
                playCloseAnimViewChat();
            }
        });
        viewGroupGifts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playCloseAnimViewGifts();
            }
        });
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

    private void playCloseAnimViewMore() {
        LAnimationUtil.play(viewGroupMore, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                viewGroupMore.setVisibility(GONE);
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

    public interface ViewMoreListener {
        public void viewMoreShareFb();

        public void viewMoreSchedule();
    }

    private ViewMoreListener viewMoreListener;

    public void setViewMoreListener(ViewMoreListener mViewMoreListener) {
        this.viewMoreListener = mViewMoreListener;
    }

    private void setupViewOpen() {
        viewGroupMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playCloseAnimViewMore();
            }
        });
        lTextViewShareFb.setImage(R.drawable.facebook);
        lTextViewShareFb.setTintImage(ContextCompat.getColor(getContext(), R.color.Blue));
        lTextViewShareFb.setText(getContext().getString(R.string.share_fb));
        lTextViewShareFb.setOnItemClick(new LTextViewVertical.Callback() {
            @Override
            public void OnClickItem() {
                playCloseAnimViewMore();
                AlertMessage.showSuccess(getContext(), "Click lTextViewShareFb");
                viewMoreListener.viewMoreShareFb();
            }
        });
        lTextViewCalendar.setImage(R.drawable.lichdien_white);
        lTextViewCalendar.setTintImage(ContextCompat.getColor(getContext(), R.color.Black));
        lTextViewCalendar.setText(getContext().getString(R.string.schedule));
        lTextViewCalendar.setOnItemClick(new LTextViewVertical.Callback() {
            @Override
            public void OnClickItem() {
                playCloseAnimViewMore();
                AlertMessage.showSuccess(getContext(), "Click lTextViewCalendar");
                viewMoreListener.viewMoreSchedule();
            }
        });
    }

    private void setupViewGifts() {
        ListGift listGift = LPref.getListGift(getContext());
        if (listGift == null) {
            AlertMessage.showError(getContext(), R.string.err_get_giif);
        } else {
            List<Item> itemList = listGift.getItems();
            recyclerViewGifts.setHasFixedSize(true);
            recyclerViewGifts.setLayoutManager(new GridLayoutManager(getContext(), 4));
            GiftsAdapter giftsAdapter = new GiftsAdapter(getContext(), itemList, new GiftsAdapter.Callback() {
                @Override
                public void onClick(Item item) {
                    if (callbackGift != null) {
                        callbackGift.onClickGift(item);
                    }
                }
            });
            recyclerViewGifts.setAdapter(giftsAdapter);
        }
    }

    private void playCloseAnimViewGifts() {
        LAnimationUtil.play(viewGroupGifts, Techniques.SlideOutDown, new LAnimationUtil.Callback() {
            @Override
            public void onCancel() {
                //do nothing
            }

            @Override
            public void onEnd() {
                viewGroupGifts.setVisibility(GONE);
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

    public void hideAllView() {
        llControl.setVisibility(GONE);
    }
}