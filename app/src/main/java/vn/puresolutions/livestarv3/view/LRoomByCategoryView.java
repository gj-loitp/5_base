package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.IdolRoomByCategoryAdapter;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.ModelIdolRoomByCategory;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LRoomByCategoryView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private Activity mActivity;
    private ModelIdolRoomByCategory mModelIdolRoomByCategory;
    private List<Room> roomList;
    private IdolRoomByCategoryAdapter idolRoomByCategoryAdapter;

    public LRoomByCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LRoomByCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_room_by_category, this);

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
    }

    public void setData(Activity activity, ModelIdolRoomByCategory modelIdolRoomByCategory) {
        mActivity = activity;
        mModelIdolRoomByCategory = modelIdolRoomByCategory;
        if (mModelIdolRoomByCategory.getRoomGetByCategory() != null
                && mModelIdolRoomByCategory.getRoomGetByCategory().getCategory() != null
                && mModelIdolRoomByCategory.getRoomGetByCategory().getCategory().getRooms() != null) {
            roomList = mModelIdolRoomByCategory.getRoomGetByCategory().getCategory().getRooms();
            if (roomList != null) {
                idolRoomByCategoryAdapter = new IdolRoomByCategoryAdapter(mActivity, roomList, new IdolRoomByCategoryAdapter.Callback() {
                    @Override
                    public void onClick(Room room, int position, List<Room> roomList) {
                        if (callback != null) {
                            callback.onClick(room, position, roomList);
                        }
                    }
                });
                recyclerView.setHasFixedSize(true);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                //linearLayoutManager.setAutoMeasureEnabled(true);

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(idolRoomByCategoryAdapter);
                recyclerView.setNestedScrollingEnabled(false);
            }
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

    public void addItemToList(RoomGetByCategory roomGetByCategory) {
        if (mActivity == null || roomList == null || roomGetByCategory == null) {
            return;
        }
        if (roomGetByCategory.getCategory() != null) {
            roomList.addAll(roomGetByCategory.getCategory().getRooms());
            notifyData();
        }
    }

    public void resetData() {
        if (roomList != null) {
            roomList.clear();
            notifyData();
        }
    }
}