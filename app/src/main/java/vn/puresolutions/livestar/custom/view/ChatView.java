package vn.puresolutions.livestar.custom.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.ChatAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Message;
import vn.puresolutions.livestar.core.api.model.param.MessageParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.LiveService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.old.ScreenUtils;

/**
 * Created by khanh on 8/20/16.
 */
public class ChatView extends BaseView implements NotificationCenter.NotificationCenterListener {

    private static final int MAX_CHAT_ITEM = 50;

    @BindView(R.id.chatView_rcvMessages)
    RecyclerView rcvMessages;
    @BindView(R.id.chatView_edtInput)
    EditText edtInput;
    @BindView(R.id.chatView_imgSend)
    ImageView chatView_imgSend;

    private ChatAdapter adapter;
    private LiveService service;
    private int screenHeight = -1;
    private LinearLayoutManager layoutManager;

    public ChatView(Context context) {
        this(context, null, 0);
    }

    public ChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addView(R.layout.view_chat);
        ButterKnife.bind(this);

        rcvMessages.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvMessages.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(getContext());
        rcvMessages.setAdapter(adapter);

        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.addObserver(this, NotificationCenter.ChatReceivedNewMessage);
        notificationCenter.addObserver(this, NotificationCenter.KeyboardShow);
        notificationCenter.addObserver(this, NotificationCenter.KeyboardHide);

        edtInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (edtInput.getText().toString().trim().length()>0){

                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (UserInfo.checkLoginAndShowDialog(getContext())) {
                            String msg = edtInput.getText().toString();
                            if (!TextUtils.isEmpty(msg)) {
                                if (service == null) {
                                    service = RestClient.createService(LiveService.class);
                                }
                                subscribe(service.sendMessage(RoomCenter.getInstance().roomId, new MessageParam(msg)), new ApiSubscriber() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        // no result
                                    }

                                    @Override
                                    public void onFail(Throwable e) {
                                        handleException(e);
                                    }
                                });
                                edtInput.setText("");
                            }

                        }
                        //return true;
                    }
                }else{
                    edtInput.setText("");
                }

                return false;
            }
        });
    }


    public void onResume() {
        resetView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.removeObserver(this, NotificationCenter.ChatReceivedNewMessage);
        notificationCenter.removeObserver(this, NotificationCenter.KeyboardShow);
        notificationCenter.removeObserver(this, NotificationCenter.KeyboardHide);
    }
    @OnClick(R.id.chatView_imgSend)
    void onSendMessage() {
            if (UserInfo.checkLoginAndShowDialog(getContext())) {
                String msg = edtInput.getText().toString();
                if (!TextUtils.isEmpty(msg)) {
                    if (service == null) {
                        service = RestClient.createService(LiveService.class);
                    }
                    subscribe(service.sendMessage(RoomCenter.getInstance().roomId, new MessageParam(msg)), new ApiSubscriber() {
                        @Override
                        public void onSuccess(Object result) {
                        }

                        @Override
                        public void onFail(Throwable e) {
                            handleException(e);
                        }
                    });
                    edtInput.setText("");
                }
            }

    }

    private void moveViewAboveKeyboard(int keyboardHeight) {
        if (screenHeight < 0) {
            screenHeight = ScreenUtils.getHeightScreen((Activity) rcvMessages.getContext());
        }

        if (keyboardHeight > screenHeight * 0.75) {
            keyboardHeight = (int) (screenHeight * 0.52);
        }

        setPadding(0, 0, 0, keyboardHeight);
    }

    private void resetView() {
        setPadding(0, 0, 0, 0);
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.ChatReceivedNewMessage) {

            Message message = (Message) args[0];
            Log.i("ChatView","Mess: "+ new Gson().toJson(message));
            adapter.add(message);
            if (adapter.getItems().size() - 1>=3){
                layoutManager.setStackFromEnd(true);
            }
            // remove if message have more than 50
            if (adapter.getItems().size() > MAX_CHAT_ITEM) {
                adapter.getItems().remove(0);
            }
            adapter.notifyDataSetChanged();
            rcvMessages.postDelayed(() -> rcvMessages.scrollToPosition(adapter.getItems().size()-1), 200);
        } else if (id == NotificationCenter.KeyboardShow) {
            moveViewAboveKeyboard((int) args[0]);

        } else if (id == NotificationCenter.KeyboardHide){
            resetView();
        }
    }
}
