package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.ModelIdolRoomByCategory;
import vn.puresolutions.livestarv3.activity.onair.adapter.IdolSquareRoomByCategoryAdapter;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LOnAirRoomByCategoryView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private Activity mActivity;
    private ModelIdolRoomByCategory mModelIdolRoomByCategory;
    private List<Room> roomList;
    private IdolSquareRoomByCategoryAdapter idolRoomByCategoryAdapter;

    public LOnAirRoomByCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LOnAirRoomByCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_room_by_category_onair, this);

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
    }

    public void setData(Activity activity, ModelIdolRoomByCategory modelIdolRoomByCategory) {
        mActivity = activity;
        mModelIdolRoomByCategory = modelIdolRoomByCategory;
        roomList = modelIdolRoomByCategory.getRoomGetByCategory().getCategory().getRooms();
        if (roomList != null) {
            idolRoomByCategoryAdapter = new IdolSquareRoomByCategoryAdapter(activity, roomList, new IdolSquareRoomByCategoryAdapter.Callback() {
                @Override
                public void onClick(Room room, int position, List<Room> roomList) {
                    if (callback != null) {
                        callback.onClick(room, position, roomList);
                    }
                }
            });
            recyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(idolRoomByCategoryAdapter);
            recyclerView.setNestedScrollingEnabled(false);
        }
    }

    public interface Callback {
        public void onClick(Room room, int position, List<Room> roomList);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void notifyData() {
        if (idolRoomByCategoryAdapter != null) {
            idolRoomByCategoryAdapter.notifyDataSetChanged();
        }
    }

    public void updateNewList(ModelIdolRoomByCategory modelIdolRoomByCategory) {
        if (mActivity == null || roomList == null) {
            return;
        }
        roomList.clear();
        //notifyData();
        roomList.addAll(modelIdolRoomByCategory.getRoomGetByCategory().getCategory().getRooms());
        notifyData();
    }

    public boolean addItemToList(RoomGetByCategory roomGetByCategory) {
        LLog.d(TAG, "old size: " + roomList.size());
        if (mActivity == null || roomList == null || roomGetByCategory == null) {
            //LLog.d(TAG, mActivity == null ? "mActivity == null" : "mActivity != null");
            //LLog.d(TAG, roomList == null ? "roomList == null" : "roomList != null");
            //LLog.d(TAG, roomGetByCategory == null ? "roomGetByCategory == null" : "roomGetByCategory != null");
            return false;
        }
        if (roomGetByCategory.getCategory() != null) {
            roomList.addAll(roomGetByCategory.getCategory().getRooms());
            notifyData();
        }
        //LLog.d(TAG, "new size: " + roomList.size());
        return true;
    }

    public void resetData() {
        if (roomList != null) {
            roomList.clear();
            //notifyData();
        }
        //LLog.d(TAG, "resetData");
    }

    public void showAllViews() {
        if (recyclerView != null && recyclerView.getVisibility() != VISIBLE) {
            recyclerView.setVisibility(VISIBLE);
        }
    }

    public void hideAllViews() {
        if (recyclerView != null && recyclerView.getVisibility() != INVISIBLE) {
            recyclerView.setVisibility(INVISIBLE);
        }
    }
}