package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.livestream.model.ChatMessage;
import vn.puresolutions.livestarv3.activity.room.adapter.ChatAdapter;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LChatView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerViewChat;

    public LChatView(Context context) {
        super(context);
        init();
    }

    public LChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LChatView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_chat_view, this);

        //init list audiences horizontal
        recyclerViewChat = (RecyclerView) findViewById(R.id.recycler_view_chat);
        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewChat.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(getContext(), chatMessageArrayList, new ChatAdapter.Callback() {
            @Override
            public void onClick(ChatMessage chatMessage) {
                AlertMessage.showSuccess(getContext(), "Click dummyMessage ");
            }
        });
        recyclerViewChat.setAdapter(chatAdapter);
        scrollToBottom();
    }

    private void scrollToBottom() {
        if (recyclerViewChat != null && chatMessageArrayList.size() > 0) {
            recyclerViewChat.smoothScrollToPosition(chatMessageArrayList.size() - 1);
        }
    }

    public void addMessage(ChatMessage message) {
        //LLog.d(TAG, "message: " + new Gson().toJson(message));
        if (message == null) {
            return;
        }
        if (!chatMessageArrayList.contains(message)) {
            chatMessageArrayList.add(message);
            if (chatAdapter != null) {
                chatAdapter.notifyDataSetChanged();
                scrollToBottom();
            }
        }
    }
}