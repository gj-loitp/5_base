package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.search.RoomResult;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.room.RoomActivity;
import vn.puresolutions.livestarv3.activity.userprofile.adapter.FollowAdapter;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LActionBar;

import static android.view.View.GONE;

public class FollowingActivity extends BaseActivity {
    private FollowAdapter followAdapter;
    private String TAG = getClass().getSimpleName();
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;
    private ArrayList<Room> lstUserFollow = new ArrayList<>();
    private boolean isLoading = true;
    @BindView(R.id.laFollowingScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.rvFollowingScreen)
    RecyclerView rvFollowing;
    @BindView(R.id.tvFollowingScreen_NoResult)
    TextView tvNoResult;
    private String mUserId;
    private String mUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mUserId = getIntent().getStringExtra(Constants.KEY_USER_ID);
        mUserName = getIntent().getStringExtra(Constants.KEY_USER_NAME);
        LLog.d(TAG, "~~~userId " + mUserId + ", mUserName " + mUserName);
        loadData();
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        rvFollowing.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFollowing.setLayoutManager(linearLayoutManager);
        followAdapter = new FollowAdapter(this, lstUserFollow, new FollowAdapter.Callback() {
            @Override
            public void onClick(Room room) {
                if (room.isOnAir()) {
                    ArrayList<Room> rooms = new ArrayList<>();
                    rooms.add(room);
                    Intent intent = new Intent(activity, RoomActivity.class);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, 0);
                    intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) rooms);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                } else {
                    LLog.d(TAG, "~~~FollowingActivity KEY_USER_TO_PROFILE " + room.getUserId());
                    Intent intent = new Intent(activity, LiveUserProfileActivity.class);
                    intent.putExtra(Constants.KEY_USER_TO_PROFILE, room.getUserId());
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                }
            }
        });
        rvFollowing.setAdapter(followAdapter);
        rvFollowing.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pastVisiblesItems, visibleItemCount, totalItemCount;
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findLastVisibleItemPosition();
                //LLog.d(TAG, "visibleItemCount: " + visibleItemCount);
                //LLog.d(TAG, "totalItemCount: " + totalItemCount);
                //LLog.d(TAG, "pastVisiblesItems: " + pastVisiblesItems);
                if (isLoading) {
                    if (pastVisiblesItems + visibleItemCount >= totalItemCount) {
                        isLoading = false;
                        loadData();
                        //LLog.d(TAG, "onScrolled-page: " + page);
                    }
                }
            }
        });
    }

    private void loadData() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getFollowing(page, mUserId), new ApiSubscriber<RoomResult>() {
            @Override
            public void onSuccess(RoomResult result) {
                LLog.d("FrmFollow", "size: " + result.getRooms().size());
                labHeader.setTvTitle(String.format(getString(R.string.following_title), result.getAttr().getTotalItem()));
                if (result.getAttr().getPage() > 0) {
                    tvNoResult.setVisibility(GONE);
                    page++;
                    if (lstUserFollow.size() >= result.getAttr().getTotalItem()) {
                        return;
                    } else {
                        lstUserFollow.addAll(result.getRooms());
                        LLog.d("FrmFollow", "size: " + lstUserFollow.size());
                        followAdapter.notifyDataSetChanged();
                    }
                } else {
                    tvNoResult.setVisibility(View.VISIBLE);
                    tvNoResult.setText(String.format(getString(R.string.following_no_result), mUserName));
                    lstUserFollow.clear();
                    followAdapter.notifyDataSetChanged();
                }
                isLoading = true;
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_following;
    }
}
