package vn.puresolutions.livestar.adapter.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.base.RecyclerAdapter;
import vn.puresolutions.livestar.core.api.model.GiftMessage;
import vn.puresolutions.livestar.core.api.model.HeartMessage;
import vn.puresolutions.livestar.core.api.model.Message;
import vn.puresolutions.livestar.core.api.model.VipComingMessage;
import vn.puresolutions.livestar.custom.view.ImageSpan;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.old.VipUtils;

/**
 * Created by khanh on 8/21/16.
 */
public class ChatAdapter extends RecyclerAdapter<Message, ChatAdapter.ViewHolder> {

    private Context context;
    private HashMap<Integer, Bitmap> mapGifts;

    public ChatAdapter(Context context) {
        this.context = context;
        this.mapGifts = new HashMap<>();
    }

    @Override
    protected int getLayoutItem() {
        return R.layout.item_chat;
    }

    @Override
    protected void updateView(Message message, ViewHolder holder, int position) {
        holder.avatarContainer.setVisibility(View.GONE);
        String msg;
        if (message instanceof GiftMessage) {
            switch ( message.getVip().getVip()){
                case 0:
                    holder.imgVipType.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1));
                    }
                    break;
                case 2:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip2);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2));
                    }
                    break;
                case 3:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip3);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3));
                    }
                    break;
                case 4:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip4);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4));
                    }
                    break;
                case 5:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip5);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5));
                    }
                    break;
            }
            GiftMessage giftMessage = (GiftMessage) message;
            msg = context.getString(R.string.giftMessageFormat,
                    message.getSender().getName(), String.valueOf(giftMessage.getQuantity()),
                    giftMessage.getGift().getName(), RoomCenter.getInstance().idolName);
        } else if (message instanceof HeartMessage) {
            holder.imgVipType.setVisibility(View.GONE);
            HeartMessage heartMessage = (HeartMessage) message;
            msg = context.getString(R.string.heartMessageFormat,
                    message.getSender().getName(), String.valueOf(heartMessage.getBctHearts()), RoomCenter.getInstance().idolName);
        } else if (message instanceof VipComingMessage) {
            msg = context.getString(R.string.vipCommingMessageFormat,
                    String.valueOf(message.getVip().getVip()), message.getSender().getName());
            holder.imgVipType.setVisibility(View.GONE);
            holder.avatarContainer.setVisibility(View.VISIBLE);
            // load user avatar and vip icon
            LImageUtils.loadAvatar(holder.imgAvatar, message.getSender().getId());
            //VipUtils.setIconVip(holder.imgVip, message.getVip().getVip());
            switch ( message.getVip().getVip()){
                case 0:
                    holder.imgVip.setImageDrawable(null);
                    break;
                case 1:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //holder.imgVip.setImageResource(R.drawable.ic_vip1);
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1,context.getTheme()));
                    } else {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1));
                    }
                    break;
                case 2:
                    //holder.imgVip.setImageResource(R.drawable.ic_vip2);
                    //holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2,context.getTheme()));
                    } else {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2));
                    }
                    break;
                case 3:
                    //holder.imgVip.setImageResource(R.drawable.ic_vip3);
                    //holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3,context.getTheme()));
                    } else {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3));
                    }
                    break;
                case 4:
                    //holder.imgVip.setImageResource(R.drawable.ic_vip4);
                    //holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4,context.getTheme()));
                    } else {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4));
                    }
                    break;
                case 5:
                    //holder.imgVip.setImageResource(R.drawable.ic_vip5);
                    //holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5,context.getTheme()));
                    } else {
                        holder.imgVip.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5));
                    }
                    break;
            }
        } else {
            Log.i("ChatAdapter","else");
            switch ( message.getVip().getVip()){
                case 0:
                    holder.imgVipType.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip1));
                    }
                    break;
                case 2:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip2);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip2));
                    }
                    break;
                case 3:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip3);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip3));
                    }
                    break;
                case 4:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip4);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip4));
                    }
                    break;
                case 5:
                    holder.imgVipType.setVisibility(View.VISIBLE);
                    //holder.imgVipType.setImageResource(R.drawable.ic_vip5);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5,context.getTheme()));
                    } else {
                        holder.imgVipType.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_vip5));
                    }
                    break;
            }
            msg = String.format("%s: %s", message.getSender().getName(), message.getMessage()); ;
        }

        Spannable text = highlightNameInMessage(msg, message.getSender().getName(), message.getVip().getVip());
        if (message instanceof VipComingMessage) {
            String vipLevel = "VIP " + message.getVip().getVip();
            int index = msg.indexOf(vipLevel);
            int color = VipUtils.getVipColor(message.getVip().getVip(), R.color.defaultNameColor);
            text.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), index, index + vipLevel.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        if (message instanceof GiftMessage) {
            GiftMessage giftMessage = (GiftMessage) message;
            Bitmap bitmap = mapGifts.get(((GiftMessage) message).getGift().getId());
            int size = (int) (holder.tvMessage.getTextSize() * 1.5);
            if (bitmap != null) {
                ImageSpan imageSpan = new ImageSpan(bitmap, size, size);
                int index = msg.indexOf(giftMessage.getGift().getName());
                text.setSpan(imageSpan, index, index + giftMessage.getGift().getName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                LImageUtils.loadBitmap(context, ((GiftMessage) message).getGift().getImage(), size, size, bitmap1 -> {
                    Message msg1 = items.get(position);
                    if (msg1 instanceof GiftMessage) {
                        mapGifts.put(((GiftMessage) msg1).getGift().getId(), bitmap1);
                        new Handler().post(() -> notifyItemChanged(position));
                    }
                });
            }
        }

        holder.tvMessage.setText(text);
//        if (message.getVip().getVip() > 0) {
//            holder.imgVip.setVisibility(View.VISIBLE);
//            VipUtils.setIconVip(holder.imgVip, message.getVip().getVip());
//        } else {
//            holder.imgVip.setVisibility(View.GONE);
//        }


    }

    private Spannable highlightNameInMessage(String msg, String name, int vip) {
        Spannable text = new SpannableString(msg);
        int color = VipUtils.getVipColor(vip, R.color.defaultNameColor);
        int index = msg.indexOf(name);
        text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), index, index + name.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        int indexOfIdolName = msg.indexOf(RoomCenter.getInstance().idolName);
        if (indexOfIdolName > 0) {
            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.idolNameColor)),
                    indexOfIdolName, indexOfIdolName + RoomCenter.getInstance().idolName.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return text;
    }

    @Override
    protected ViewHolder createViewHolder(View container) {
        return new ViewHolder(container);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chatItem_tvMessage)
        TextView tvMessage;
        @BindView(R.id.chatItem_lnlAvatar)
        View avatarContainer;
        @BindView(R.id.chatItem_imgAvatar)
        SimpleDraweeView imgAvatar;
        @BindView(R.id.chatItem_imgVip)
        ImageView imgVip;
        @BindView(R.id.Chatitem_imgVipType)
        ImageView imgVipType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
