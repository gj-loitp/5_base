package vn.puresolutions.livestarv3.activity.onair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.GoToPrivateRoomActivity;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategory;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategoryDetail;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.ModelIdolRoomByCategory;
import vn.puresolutions.livestarv3.activity.room.RoomActivity;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LCategoryView;
import vn.puresolutions.livestarv3.view.LOnAirRoomByCategoryView;

public class OnAirNewActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    private Button btExit;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LCategoryView lCategoryView;
    private boolean isCalledAPIRoomByCategoryComplete;
    private boolean isCalledAPICategoryComplete;
    private String mCurrentCategory = ModelIdolCategoryDetail.TYPE_ALL;
    private int mCurrentPage = 1;
    private int mTotalPage = 1;
    private LOnAirRoomByCategoryView lOnAirRoomByCategoryView;
    private NestedScrollView nestedScroolView;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        createData();
    }

    private void initView() {
        nestedScroolView = (NestedScrollView) findViewById(R.id.nested_scrool_view);
        btExit = (Button) findViewById(R.id.bt_exit);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        lCategoryView = (LCategoryView) findViewById(R.id.l_category_view);
        lOnAirRoomByCategoryView = (LOnAirRoomByCategoryView) findViewById(R.id.l_on_air_room_by_category_view);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        //LUIUtil.setTextShadow(tvMsg);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mTotalPage = 1;
                lOnAirRoomByCategoryView.resetData();

                setlOnAirRoomByCategoryView();
            }
        });

        lCategoryView.setCallback(new LCategoryView.Callback() {
            @Override
            public void onClick(ModelIdolCategoryDetail modelIdolCategoryDetail) {
                getNewListRoomByCategory(modelIdolCategoryDetail);
            }
        });

        nestedScroolView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //important, dont remove
                /*if (scrollY > oldScrollY) {
                    LLog.d(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    LLog.d(TAG, "Scroll UP");
                }
                if (scrollY == 0) {
                    LLog.d(TAG, "TOP SCROLL");
                }*/
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    //LLog.d(TAG, "BOTTOM SCROLL");
                    loadMoreNewListRoomByCategory();
                }
            }
        });
        lOnAirRoomByCategoryView.setCallback(new LOnAirRoomByCategoryView.Callback() {
            @Override
            public void onClick(Room room, int position, List<Room> roomList) {
                if (room.isOnAir()) {
                    if (room.getMode().equals(Constants.MODE_PUBLIC)) {
                        Intent intent = new Intent(activity, RoomActivity.class);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                        startActivity(intent);
                        LUIUtil.transActivityFadeIn(activity);
                    } else {
                        boolean isBoughtSessionId = LPref.isBoughtSessionId(activity, room.getSession().getId());
                        if (isBoughtSessionId) {
                            //da tung mua ve
                            AlertMessage.showSuccess(activity, getString(R.string.is_bought_ticket));
                            Intent intent = new Intent(activity, RoomActivity.class);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(activity);
                        } else {
                            //chua mua ve
                            Intent intent = new Intent(activity, GoToPrivateRoomActivity.class);
                            intent.putExtra(Constants.KEY_HOME_TO_PRIVATE_ROOM, room);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(activity);
                        }
                    }
                } else {
                    if (room.getUser().getIsIdol() == Constants.USER_IS_IDOL) {
                        AlertMessage.showError(activity, String.format(activity.getString(R.string.room_idol_is_not_onair), room.getTitle()));
                        //TODO mo trang Hoso.jpg
                    } else {
                        AlertMessage.showError(activity, String.format(activity.getString(R.string.room_is_not_onair), room.getTitle()));
                    }
                }
            }
        });
    }

    private void createData() {
        updateProgressView();
        mCurrentPage = 1;
        mTotalPage = 1;
        setlCategoryView();
        setlOnAirRoomByCategoryView();
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
        return R.layout.activity_on_air_new;
    }

    private void updateProgressView() {
        //LLog.d(TAG, ">>>updateProgressView " + isCalledAPICategoryComplete + " " + isCalledAPIRoomByCategoryComplete);
        if (isCalledAPICategoryComplete && isCalledAPIRoomByCategoryComplete) {
            LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    swipeRefreshLayout.setRefreshing(false);
                    //LDialogUtils.hideDialog(dialog);
                }
            });
        } else {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
            //LDialogUtils.showDialog(dialog);
        }
    }

    private void getNewListRoomByCategory(ModelIdolCategoryDetail modelIdolCategoryDetail) {
        //LLog.d(TAG, "getNewListRoomByCategory " + modelIdolCategoryDetail.getName());
        mCurrentCategory = modelIdolCategoryDetail.getType();
        mCurrentPage = 1;
        isCalledAPIRoomByCategoryComplete = false;
        updateProgressView();

        lOnAirRoomByCategoryView.resetData();

        //start calling api to get new list room by new category
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomGetByCategory(mCurrentCategory, false, mCurrentPage), new ApiSubscriber<RoomGetByCategory>() {
            @Override
            public void onSuccess(RoomGetByCategory roomGetByCategory) {
                //LLog.d(TAG, "getNewListRoomByCategory onSuccess " + LSApplication.getInstance().getGson().toJson(roomGetByCategory));
                if (roomGetByCategory.getCategory().getRooms().isEmpty()) {
                    //AlertMessage.showError(activity, String.format(getString(R.string.room_by_category_is_empty), modelIdolCategoryDetail.getName()));
                    tvMsg.setText(String.format(getString(R.string.room_by_category_is_empty), modelIdolCategoryDetail.getName()));
                    LAnimationUtil.play(tvMsg, Techniques.FlipInY);
                    if (tvMsg.getVisibility() != View.VISIBLE) {
                        tvMsg.setVisibility(View.VISIBLE);
                    }
                    lOnAirRoomByCategoryView.hideAllViews();
                    mTotalPage = 1;
                } else {
                    mTotalPage = roomGetByCategory.getAttr().getTotalPage();
                    ModelIdolRoomByCategory modelIdolRoomByCategory = new ModelIdolRoomByCategory();
                    modelIdolRoomByCategory.setType(Constants.TYPE_IDOL_ROOM_BY_CATEGORY);
                    modelIdolRoomByCategory.setRoomGetByCategory(roomGetByCategory);
                    lOnAirRoomByCategoryView.updateNewList(modelIdolRoomByCategory);
                    lOnAirRoomByCategoryView.showAllViews();
                    if (tvMsg.getVisibility() != View.INVISIBLE) {
                        tvMsg.setVisibility(View.INVISIBLE);
                    }
                }
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                mTotalPage = 1;
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }
        });
        //end calling api to get new list room by new category
    }

    private void setlCategoryView() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.categoryGet(), new ApiSubscriber<CategoryGet[]>() {
            @Override
            public void onSuccess(CategoryGet[] categoryGets) {
                //LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(categoryGets));
                createCategory(categoryGets);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isCalledAPICategoryComplete = true;
                updateProgressView();
                updateProgressView();
            }
        });
    }

    private ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList = new ArrayList<ModelIdolCategoryDetail>();

    private void createCategory(CategoryGet[] categoryGets) {
        //LLog.d(TAG, "createCategory categoryGets.length " + categoryGets.length);
        ModelIdolCategory modelIdolCategory = new ModelIdolCategory();
        modelIdolCategory.setType(Constants.TYPE_IDOL_CATEGORY);
        for (int i = 0; i < categoryGets.length; i++) {
            switch (categoryGets[i].getId()) {
                case ModelIdolCategoryDetail.TYPE_ALL:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_ALL, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_DJ:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_DJ, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_BOY:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_HOT_BOY, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_GIRL:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_HOT_GIRL, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_NGHESY:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_NGHESY, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_CARTOON:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_CARTOON, categoryGets[i].getTitle(), false));
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_IDOL:
                    modelIdolCategoryDetailArrayList.add(new ModelIdolCategoryDetail(ModelIdolCategoryDetail.TYPE_HOT_IDOL, categoryGets[i].getTitle(), false));
                    break;
                default:
                    break;
            }
        }
        for (int i = 0; i < modelIdolCategoryDetailArrayList.size(); i++) {
            if (modelIdolCategoryDetailArrayList.get(i).getType().equals(mCurrentCategory)) {
                modelIdolCategoryDetailArrayList.get(i).setChecked(true);
                break;
            }
        }
        modelIdolCategory.setModelIdolCategoryDetailArrayList(modelIdolCategoryDetailArrayList);
        lCategoryView.setData(activity, modelIdolCategory);

        LPref.setModelIdolCatgory(activity, modelIdolCategory);

        isCalledAPICategoryComplete = true;
        updateProgressView();
    }

    private void setlOnAirRoomByCategoryView() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomGetByCategory(mCurrentCategory, false, mCurrentPage), new ApiSubscriber<RoomGetByCategory>() {
            @Override
            public void onSuccess(RoomGetByCategory roomGetByCategory) {
                //LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(roomGetByCategory));
                mTotalPage = roomGetByCategory.getAttr().getTotalPage();
                ModelIdolRoomByCategory modelIdolRoomByCategory = new ModelIdolRoomByCategory();
                modelIdolRoomByCategory.setType(Constants.TYPE_IDOL_ROOM_BY_CATEGORY);
                modelIdolRoomByCategory.setRoomGetByCategory(roomGetByCategory);
                if (!modelIdolRoomByCategory.getRoomGetByCategory().getCategory().getRooms().isEmpty()) {
                    lOnAirRoomByCategoryView.setData(activity, modelIdolRoomByCategory);
                } else {
                    tvMsg.setText(String.format(getString(R.string.room_by_category_is_empty), modelIdolCategoryDetailArrayList.get(0).getName()));
                    LAnimationUtil.play(tvMsg, Techniques.FlipInY);
                    if (tvMsg.getVisibility() != View.VISIBLE) {
                        tvMsg.setVisibility(View.VISIBLE);
                    }
                }
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }
        });
    }

    private void loadMoreNewListRoomByCategory() {
        isCalledAPIRoomByCategoryComplete = false;
        updateProgressView();
        //LLog.d(TAG, "loadMoreNewListRoomByCategory mTotalPage: " + mTotalPage);

        if (mCurrentPage > mTotalPage) {
            //TODO this is last page
            //LLog.d(TAG, "if mCurrentPage > mTotalPage ->>> STOP load more");
            isCalledAPIRoomByCategoryComplete = true;
            updateProgressView();
        } else {
            mCurrentPage++;
            //LLog.d(TAG, ">>>load page " + mCurrentPage);

            if (mCurrentPage > mTotalPage) {
                //TODO this is last page
                //LLog.d(TAG, "else mCurrentPage > mTotalPage ->>> STOP load more");
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
                return;
            }

            //start calling api to get new list room by new category
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.roomGetByCategory(mCurrentCategory, false, mCurrentPage), new ApiSubscriber<RoomGetByCategory>() {
                @Override
                public void onSuccess(RoomGetByCategory roomGetByCategory) {
                    //LLog.d(TAG, "getNewListRoomByCategory onSuccess " + LSApplication.getInstance().getGson().toJson(roomGetByCategory));
                    //LLog.d(TAG, "loadMoreNewListRoomByCategory onSuccess " + roomGetByCategory.getCategory().getRooms().size());
                    boolean addSuccess = lOnAirRoomByCategoryView.addItemToList(roomGetByCategory);
                    //LLog.d(TAG, "addSuccess " + addSuccess);
                    isCalledAPIRoomByCategoryComplete = true;
                    updateProgressView();
                }

                @Override
                public void onFail(Throwable e) {
                    isCalledAPIRoomByCategoryComplete = true;
                    updateProgressView();
                    mTotalPage = 1;
                    handleException(e);
                }
            });
            //end calling api to get new list room by new category
        }
    }
}
