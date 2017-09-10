package vn.puresolutions.livestarv3.activity.room.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.livestream.model.ChatMessage;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<ChatMessage> chatMessageArrayList;
    private Context context;
    //private final int NUMBER_DISPLAY = 2;
    private int height;

    public interface Callback {
        public void onClick(ChatMessage chatMessage);
    }

    private Callback callback;

    public ChatAdapter(Context context, ArrayList<ChatMessage> chatMessageArrayList, Callback callback) {
        this.chatMessageArrayList = chatMessageArrayList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_chat_msg, null);
        //height = viewGroup.getMeasuredHeight() / NUMBER_DISPLAY;
        //v.setMinimumHeight(height);
        //LLog.d(TAG, "height " + height);
        return new MsgChatHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        ChatMessage chatMessage = chatMessageArrayList.get(position);
        if (baseHolder instanceof MsgChatHolder) {
            MsgChatHolder msgChatHolder = (MsgChatHolder) baseHolder;
            LImageUtils.loadImage(msgChatHolder.ivAvatar, R.drawable.chick_orange);
            //msgChatHolder.viewGroupMain.getLayoutParams().height = height;
            msgChatHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClick(chatMessage);
                    }
                }
            });
            msgChatHolder.tvLevelUser.setText(String.valueOf(chatMessage.getUser().getLevel().getLevel()));
            msgChatHolder.tvNameUser.setText(chatMessage.getUser().getName());
            msgChatHolder.tvMsg.setText(chatMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        LLog.d(TAG, "List Chat Size: " + chatMessageArrayList.size());
        return (null != chatMessageArrayList ? chatMessageArrayList.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class MsgChatHolder extends BaseHolder {
        private LinearLayout viewGroupMain;
        private SimpleDraweeView ivAvatar;
        private TextView tvLevelUser;
        private TextView tvNameUser;
        private TextView tvMsg;

        public MsgChatHolder(View view) {
            super(view);
            this.viewGroupMain = (LinearLayout) view.findViewById(R.id.view_group_main);
            this.ivAvatar = (SimpleDraweeView) view.findViewById(R.id.iv_avatar);
            this.tvLevelUser = (TextView) view.findViewById(R.id.tv_level_user);
            this.tvNameUser = (TextView) view.findViewById(R.id.tv_name_user);
            this.tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        }
    }
}