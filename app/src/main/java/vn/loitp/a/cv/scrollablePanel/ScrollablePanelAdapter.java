package vn.loitp.a.cv.scrollablePanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.loitp.views.scrollablePanel.PanelAdapter;
import com.loitp.views.toast.LToast;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.R;

public class ScrollablePanelAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int ROOM_TYPE = 0;
    private static final int DATE_TYPE = 1;
    private static final int ORDER_TYPE = 2;

    private List<RoomInfo> roomInfoList = new ArrayList<>();
    private List<DateInfo> dateInfoList = new ArrayList<>();
    private List<List<OrderInfo>> ordersList = new ArrayList<>();

    @Override
    public int getRowCount() {
        return roomInfoList.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return dateInfoList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        final int viewType = getItemViewType(row, column);
        switch (viewType) {
            case DATE_TYPE:
                setDateView(column, (DateViewHolder) holder);
                break;
            case ROOM_TYPE:
                setRoomView(row, (RoomViewHolder) holder);
                break;
            case TITLE_TYPE:
                break;
            default:
                setOrderView(row, column, (OrderViewHolder) holder);
        }
    }

    public int getItemViewType(final int row, final int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return ROOM_TYPE;
        }
        if (row == 0) {
            return DATE_TYPE;
        }
        return ORDER_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATE_TYPE:
                return new DateViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.i_date_info, parent, false));
            case ROOM_TYPE:
                return new RoomViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.i_room_info, parent, false));
            case ORDER_TYPE:
                return new OrderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.i_order_info, parent, false));
            case TITLE_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.i_title, parent, false));
            default:
                break;
        }
        return new OrderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.i_order_info, parent, false));
    }


    private void setDateView(final int pos, final DateViewHolder viewHolder) {
        final DateInfo dateInfo = dateInfoList.get(pos - 1);
        if (dateInfo != null && pos > 0) {
            viewHolder.dateTextView.setText(dateInfo.getDate());
            viewHolder.weekTextView.setText(dateInfo.getWeek());
        }
    }

    private void setRoomView(final int pos, final RoomViewHolder viewHolder) {
        final RoomInfo roomInfo = roomInfoList.get(pos - 1);
        if (roomInfo != null && pos > 0) {
            viewHolder.roomTypeTextView.setText(roomInfo.getRoomType());
            viewHolder.roomNameTextView.setText(roomInfo.getRoomName());
        }
    }

    private void setOrderView(final int row, final int column, final OrderViewHolder viewHolder) {
        final OrderInfo orderInfo = ordersList.get(row - 1).get(column - 1);
        if (orderInfo != null) {
            if (orderInfo.getStatus() == OrderInfo.Status.BLANK) {
                viewHolder.view.setBackgroundResource(R.drawable.bg_white_gray_stroke);
                viewHolder.nameTextView.setText("");
                viewHolder.statusTextView.setText("");
            } else if (orderInfo.getStatus() == OrderInfo.Status.CHECK_IN) {
                viewHolder.nameTextView.setText(orderInfo.isBegin() ? orderInfo.getGuestName() : "");
                viewHolder.statusTextView.setText(orderInfo.isBegin() ? "check in" : "");
                viewHolder.view.setBackgroundResource(orderInfo.isBegin() ? R.drawable.bg_room_red_begin_with_stroke : R.drawable.bg_room_red_with_stroke);
            } else if (orderInfo.getStatus() == OrderInfo.Status.REVERSE) {
                viewHolder.nameTextView.setText(orderInfo.isBegin() ? orderInfo.getGuestName() : "");
                viewHolder.statusTextView.setText(orderInfo.isBegin() ? "reverse" : "");
                viewHolder.view.setBackgroundResource(orderInfo.isBegin() ? R.drawable.bg_room_blue_begin_with_stroke : R.mipmap.bg_room_blue_middle);
            }
            if (orderInfo.getStatus() != OrderInfo.Status.BLANK) {
                viewHolder.itemView.setClickable(true);
                viewHolder.itemView.setOnClickListener(v -> {
                    if (orderInfo.isBegin()) {
                        LToast.INSTANCE.showShortInformation("name:" + orderInfo.getGuestName(), true);
                    } else {
                        int i = 2;
                        while (column - i >= 0 && ordersList.get(row - 1).get(column - i).getId() == orderInfo.getId()) {
                            i++;
                        }
                        final OrderInfo info = ordersList.get(row - 1).get(column - i + 1);
                        LToast.INSTANCE.showShortInformation("name:" + info.getGuestName(), true);
                    }
                });
            } else {
                viewHolder.itemView.setClickable(false);
            }
        }
    }

    private static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView weekTextView;

        DateViewHolder(View itemView) {
            super(itemView);
            this.dateTextView = itemView.findViewById(R.id.date);
            this.weekTextView = itemView.findViewById(R.id.week);
        }

    }

    private static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomTypeTextView;
        TextView roomNameTextView;

        RoomViewHolder(View view) {
            super(view);
            this.roomTypeTextView = view.findViewById(R.id.roomType);
            this.roomNameTextView = view.findViewById(R.id.roomName);
        }
    }

    private static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView statusTextView;
        public View view;

        OrderViewHolder(View view) {
            super(view);
            this.view = view;
            this.statusTextView = view.findViewById(R.id.status);
            this.nameTextView = view.findViewById(R.id.guestName);
        }
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        TitleViewHolder(View view) {
            super(view);
            this.titleTextView = view.findViewById(R.id.title);
        }
    }


    void setRoomInfoList(final List<RoomInfo> roomInfoList) {
        this.roomInfoList = roomInfoList;
    }

    void setDateInfoList(final List<DateInfo> dateInfoList) {
        this.dateInfoList = dateInfoList;
    }

    void setOrdersList(final List<List<OrderInfo>> ordersList) {
        this.ordersList = ordersList;
    }
}
