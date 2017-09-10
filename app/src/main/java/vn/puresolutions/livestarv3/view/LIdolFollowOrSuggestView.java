package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.RoomFollowOrByView;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow.IdolSuggestOrFollowAdapter;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow.ModelIdolFollowOrSuggest;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LIdolFollowOrSuggestView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerViewList;
    private ModelIdolFollowOrSuggest mModelIdolFollowOrSuggest;
    private Activity mActivity;
    private List<Room> roomList;
    private IdolSuggestOrFollowAdapter idolSuggestOrFollowAdapter;

    public LIdolFollowOrSuggestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LIdolFollowOrSuggestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_idol_follow_or_suggest, this);
        this.recyclerViewList = (RecyclerView) findViewById(R.id.recycler_view_list);
    }

    public void setData(Activity activity, ModelIdolFollowOrSuggest modelIdolFollowOrSuggest) {
        mActivity = activity;
        mModelIdolFollowOrSuggest = modelIdolFollowOrSuggest;

        RoomFollowOrByView roomFollowOrByView = mModelIdolFollowOrSuggest.getRoomFollowOrByView();
        if (roomFollowOrByView != null) {
            roomList = roomFollowOrByView.getRooms();
            if (roomList != null) {
                idolSuggestOrFollowAdapter = new IdolSuggestOrFollowAdapter(mActivity, roomList, new IdolSuggestOrFollowAdapter.Callback() {
                    @Override
                    public void onClickRootView(Room room) {
                        if (callback != null) {
                            callback.onClickRootView(room);
                        }
                    }

                    @Override
                    public void onClickFollow(Room room) {
                        if (callback != null) {
                            callback.onClickFollow(room);
                        }
                    }
                });
                recyclerViewList.setHasFixedSize(true);
                recyclerViewList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));

                //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                //linearLayoutManager.setAutoMeasureEnabled(true);

                recyclerViewList.setAdapter(idolSuggestOrFollowAdapter);
                recyclerViewList.setNestedScrollingEnabled(false);
            }
        }
    }

    public interface Callback {
        public void onClickRootView(Room room);

        public void onClickFollow(Room room);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void addItemToList(RoomFollowOrByView roomFollowOrByView) {
        //LLog.d(TAG, "addItemToList old size: " + roomList.size());
        if (mModelIdolFollowOrSuggest != null) {
            if (roomList != null) {
                List<Room> newRoomList = roomFollowOrByView.getRooms();

                //check duplicate
                for (int i = 0; i < roomList.size(); i++) {
                    for (int j = 0; j < newRoomList.size(); j++) {
                        if (roomList.get(i).getId().equals(newRoomList.get(j).getId())) {
                            newRoomList.remove(j);
                        }
                    }
                }
                //end check duplicate

                boolean addSuccess = roomList.addAll(newRoomList);
                LLog.d(TAG, "addSuccess");
                if (addSuccess) {
                    notifyData();
                }
            }
        }
        LLog.d(TAG, "addItemToList new size: " + roomList.size());
    }

    private void notifyData() {
        if (idolSuggestOrFollowAdapter != null) {
            idolSuggestOrFollowAdapter.notifyDataSetChanged();
        }
    }

    public void resetData() {
        if (roomList != null) {
            roomList.clear();
            //notifyData();
        }
    }

    public void updateUIFollowOrUnfollow(Room room) {
        if (room == null) {
            return;
        }
        room.setIsFollow(!room.isIsFollow());
        notifyData();
    }
}