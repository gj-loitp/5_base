package vn.puresolutions.livestarv3.activity.homescreen.fragment.homenew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.corev3.api.model.v3.categoryget.CategoryGet;
import vn.puresolutions.livestar.corev3.api.model.v3.followidol.FollowIdol;
import vn.puresolutions.livestar.corev3.api.model.v3.getposter.GetPoster;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.RoomFollowOrByView;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.homescreen.GoToPrivateRoomActivity;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategory;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategoryDetail;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow.ModelIdolFollowOrSuggest;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolposter.ModelIdolPoster;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.ModelIdolRoomByCategory;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.activity.notification.NotificationActivity;
import vn.puresolutions.livestarv3.activity.onair.OnAirNewActivity;
import vn.puresolutions.livestarv3.activity.room.BaseFragmentForLOnlyViewPager;
import vn.puresolutions.livestarv3.activity.room.RoomActivity;
import vn.puresolutions.livestarv3.activity.schedule.ScheduleActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.view.LCategoryView;
import vn.puresolutions.livestarv3.view.LIconView;
import vn.puresolutions.livestarv3.view.LIdolFollowOrSuggestView;
import vn.puresolutions.livestarv3.view.LPosterView;
import vn.puresolutions.livestarv3.view.LRoomByCategoryView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmHomeNew extends BaseFragmentForLOnlyViewPager {
    private final String TAG = getClass().getSimpleName();
    private LIconView lIconViewOnair;
    private LIconView lIconViewCalenadar;
    private LIconView lIconViewNotificationr;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedScroolView;
    private LPosterView lPosterView;
    private LIdolFollowOrSuggestView lIdolFollowOrSuggestView;
    private LCategoryView lCategoryView;
    private String mCurrentCategory = ModelIdolCategoryDetail.TYPE_ALL;
    private LRoomByCategoryView lRoomByCategoryView;
    private int mCurrentPage = 1;
    private int mTotalPage = 1;
    //private Dialog dialog;
    private ModelIdolFollowOrSuggest mModelIdolFollowOrSuggest;

    private boolean isCalledAPIPosterComplete;
    private boolean isCalledAPIIdolFollowOrSuggestComplete;
    private boolean isCalledAPICategoryComplete;
    private boolean isCalledAPIRoomByCategoryComplete;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dialog = LDialogUtils.loadingMultiColor(getActivity(), true);
        createData();
    }

    private void findView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        lPosterView = (LPosterView) view.findViewById(R.id.l_poster_view);
        lIdolFollowOrSuggestView = (LIdolFollowOrSuggestView) view.findViewById(R.id.l_idol_follow_or_suggest_view);
        lCategoryView = (LCategoryView) view.findViewById(R.id.l_category_view);
        lRoomByCategoryView = (LRoomByCategoryView) view.findViewById(R.id.l_room_by_category_view);
        nestedScroolView = (NestedScrollView) view.findViewById(R.id.nested_scrool_view);
    }

    @Override
    public void onDestroyView() {
        //LDialogUtils.hideDialog(dialog);
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.frm_home_new, container, false);
        findView(view);

        initHeaderView(view);

        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetData();
            }
        });

        lIdolFollowOrSuggestView.setCallback(new LIdolFollowOrSuggestView.Callback() {
            @Override
            public void onClickRootView(Room room) {
                //do nothing
            }

            @Override
            public void onClickFollow(Room room) {
                if (!LPref.isUserLoggedIn(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(getActivity());
                } else {
                    //LLog.d(TAG, "clickIdolFollowText isIsFollow " + room.isIsFollow() + ", isOnAir " + room.isOnAir());
                    if (room.isIsFollow()) {
                        unfollowIdol(room);
                    } else {
                        followIdol(room);
                    }
                }
            }
        });

        lCategoryView.setCallback(new LCategoryView.Callback() {
            @Override
            public void onClick(ModelIdolCategoryDetail modelIdolCategoryDetail) {
                //LLog.d(TAG, "lCategoryView onClick " + modelIdolCategoryDetail.getName());
                isCalledAPIRoomByCategoryComplete = false;
                updateProgressView();
                getNewListRoomByCategory(modelIdolCategoryDetail);
            }
        });

        lRoomByCategoryView.setCallback(new LRoomByCategoryView.Callback() {
            @Override
            public void onClick(Room room, int position, List<Room> roomList) {
                if (room.isOnAir()) {
                    if (room.getMode().equals(Constants.MODE_PUBLIC)) {
                        Intent intent = new Intent(getActivity(), RoomActivity.class);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                        intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                        startActivity(intent);
                        LUIUtil.transActivityFadeIn(getActivity());
                    } else {
                        boolean isBoughtSessionId = LPref.isBoughtSessionId(getActivity(), room.getSession().getId());
                        if (isBoughtSessionId) {
                            //da tung mua ve
                            AlertMessage.showSuccess(getActivity(), getString(R.string.is_bought_ticket));
                            Intent intent = new Intent(getActivity(), RoomActivity.class);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM, room);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(getActivity());
                        } else {
                            //chua mua ve
                            Intent intent = new Intent(getActivity(), GoToPrivateRoomActivity.class);
                            intent.putExtra(Constants.KEY_HOME_TO_PRIVATE_ROOM, room);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_POSITION, position);
                            intent.putExtra(Constants.KEY_HOME_TO_ROOM_LIST_ROOM, (Serializable) roomList);
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(getActivity());
                        }
                    }
                } else {
                    if (room.getUser().getIsIdol() == Constants.USER_IS_IDOL) {
                        AlertMessage.showError(getActivity(), String.format(getContext().getString(R.string.room_idol_is_not_onair), room.getTitle()));
                        //TODO mo trang Hoso.jpg
                    } else {
                        AlertMessage.showError(getActivity(), String.format(getContext().getString(R.string.room_is_not_onair), room.getTitle()));
                    }
                }
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
                    LLog.d(TAG, "BOTTOM SCROLL");
                    loadMoreNewListRoomByCategory();
                }
            }
        });

        return view;
    }

    private void initHeaderView(View view) {
        lIconViewOnair = (LIconView) view.findViewById(R.id.l_icon_onair);
        lIconViewCalenadar = (LIconView) view.findViewById(R.id.l_icon_calenadar);
        lIconViewNotificationr = (LIconView) view.findViewById(R.id.l_icon_notificationr);

        lIconViewOnair.setImage(R.drawable.livestream);
        lIconViewCalenadar.setImage(R.drawable.calendar);
        lIconViewNotificationr.setImage(R.drawable.noti_no);

        lIconViewOnair.setText(getString(R.string.on_air));
        lIconViewCalenadar.setText(getString(R.string.schedule));
        lIconViewNotificationr.setText(getString(R.string.noti));

        lIconViewOnair.setSizeIconImageView(true);
        lIconViewCalenadar.setSizeIconImageView(true);
        lIconViewNotificationr.setSizeIconImageView(true);

        lIconViewOnair.setOnItemClick(new LIconView.Callback() {
            @Override
            public void OnClickItem() {
                Intent intent = new Intent(getActivity(), OnAirNewActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
        lIconViewCalenadar.setOnItemClick(new LIconView.Callback() {
            @Override
            public void OnClickItem() {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
        lIconViewNotificationr.setOnItemClick(new LIconView.Callback() {
            @Override
            public void OnClickItem() {
                Intent intent;
                if (!LPref.isUserLoggedIn(getActivity())) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                } else {
                    intent = new Intent(getActivity(), NotificationActivity.class);
                }
                startActivity(intent);
                LUIUtil.transActivityFadeIn(getActivity());
            }
        });
    }

    private void createData() {
        //LDialogUtils.showDialog(dialog);
        mCurrentPage = 1;
        mTotalPage = 1;
        setLPoster();
        setLIdolFollowOrSuggestView();
        setlCategoryView();
        setLRoomByCategoryView();
    }

    private void setLPoster() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(3), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] getPosters) {
                //LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(getPosters));
                ModelIdolPoster modelIdolPoster = new ModelIdolPoster();
                modelIdolPoster.setType(Constants.TYPE_IDOL_POSTER);
                modelIdolPoster.setGetPosterArrayList(new ArrayList<>(Arrays.asList(getPosters)));
                lPosterView.setData(getActivity(), modelIdolPoster);

                isCalledAPIPosterComplete = true;
                updateProgressView();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isCalledAPIPosterComplete = true;
                updateProgressView();
            }
        });
    }

    private void setLIdolFollowOrSuggestView() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomGetByView(), new ApiSubscriber<RoomFollowOrByView>() {
            @Override
            public void onSuccess(RoomFollowOrByView roomFollowOrByView) {
                //LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(roomFollowOrByView));
                mModelIdolFollowOrSuggest = new ModelIdolFollowOrSuggest();
                mModelIdolFollowOrSuggest.setType(Constants.TYPE_IDOL_FOLLOW_OR_SUGGEST);
                if (roomFollowOrByView != null) {
                    mModelIdolFollowOrSuggest.setRoomFollowOrByView(roomFollowOrByView);
                }
                lIdolFollowOrSuggestView.setData(getActivity(), mModelIdolFollowOrSuggest);

                if (LPref.isUserLoggedIn(getActivity())) {
                    getListidolFollow();
                } else {
                    isCalledAPIIdolFollowOrSuggestComplete = true;
                    updateProgressView();
                }

                needToReloadFollowOrSuggestData = false;
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isCalledAPIIdolFollowOrSuggestComplete = true;
                needToReloadFollowOrSuggestData = false;
                updateProgressView();
            }
        });
    }

    private void getListidolFollow() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomFollowGetOnAir(), new ApiSubscriber<RoomFollowOrByView>() {
            @Override
            public void onSuccess(RoomFollowOrByView roomFollowOrByView) {
                //LLog.d(TAG, "getListidolFollow onSuccess " + LSApplication.getInstance().getGson().toJson(roomFollowOrByView));
                lIdolFollowOrSuggestView.addItemToList(roomFollowOrByView);
                isCalledAPIIdolFollowOrSuggestComplete = true;
                updateProgressView();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                isCalledAPIIdolFollowOrSuggestComplete = true;
                updateProgressView();
            }
        });
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
            }
        });
    }

    private void createCategory(CategoryGet[] categoryGets) {
        //LLog.d(TAG, "createCategory categoryGets.length " + categoryGets.length);
        ModelIdolCategory modelIdolCategory = new ModelIdolCategory();
        modelIdolCategory.setType(Constants.TYPE_IDOL_CATEGORY);
        ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList = new ArrayList<ModelIdolCategoryDetail>();
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
        lCategoryView.setData(getActivity(), modelIdolCategory);

        LPref.setModelIdolCatgory(getActivity(), modelIdolCategory);

        isCalledAPICategoryComplete = true;
        updateProgressView();
    }

    private void setLRoomByCategoryView() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomGetByCategory(mCurrentCategory, mCurrentPage, Constants.LIMIT_IDOL_BY_CATEGORY), new ApiSubscriber<RoomGetByCategory>() {
            @Override
            public void onSuccess(RoomGetByCategory roomGetByCategory) {
                //LLog.d(TAG, "setPoster onSuccess " + LSApplication.getInstance().getGson().toJson(roomGetByCategory));
                mTotalPage = roomGetByCategory.getAttr().getTotalPage();
                ModelIdolRoomByCategory modelIdolRoomByCategory = new ModelIdolRoomByCategory();
                modelIdolRoomByCategory.setType(Constants.TYPE_IDOL_ROOM_BY_CATEGORY);
                modelIdolRoomByCategory.setRoomGetByCategory(roomGetByCategory);
                lRoomByCategoryView.setData(getActivity(), modelIdolRoomByCategory);
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

    private void updateProgressView() {
        //LLog.d(TAG, ">>>updateProgressView " + isCalledAPIPosterComplete + " " + isCalledAPIIdolFollowOrSuggestComplete + " " + isCalledAPICategoryComplete + " " + isCalledAPIRoomByCategoryComplete);
        if (isCalledAPIPosterComplete && isCalledAPIIdolFollowOrSuggestComplete && isCalledAPICategoryComplete && isCalledAPIRoomByCategoryComplete) {
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
        if (modelIdolCategoryDetail == null) {
            return;
        }
        mCurrentCategory = modelIdolCategoryDetail.getType();
        mCurrentPage = 1;
        //LLog.d(TAG, "getNewListRoomByCategory: " + modelIdolCategoryDetail.getName());

        //start calling api to get new list room by new category
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.roomGetByCategory(mCurrentCategory, mCurrentPage, Constants.LIMIT_IDOL_BY_CATEGORY), new ApiSubscriber<RoomGetByCategory>() {
            @Override
            public void onSuccess(RoomGetByCategory roomGetByCategory) {
                //LLog.d(TAG, "getNewListRoomByCategory onSuccess " + roomGetByCategory.getCategory().getRooms().size());
                if (roomGetByCategory.getCategory().getRooms().isEmpty()) {
                    AlertMessage.showError(getActivity(), String.format(getContext().getString(R.string.room_by_category_is_empty), modelIdolCategoryDetail.getName()));
                    mTotalPage = 1;
                } else {
                    mTotalPage = roomGetByCategory.getAttr().getTotalPage();
                    ModelIdolRoomByCategory modelIdolRoomByCategory = new ModelIdolRoomByCategory();
                    modelIdolRoomByCategory.setType(Constants.TYPE_IDOL_ROOM_BY_CATEGORY);
                    modelIdolRoomByCategory.setRoomGetByCategory(roomGetByCategory);
                    lRoomByCategoryView.updateNewList(modelIdolRoomByCategory);
                }
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }

            @Override
            public void onFail(Throwable e) {
                mTotalPage = 1;
                handleException(e);
                isCalledAPIRoomByCategoryComplete = true;
                updateProgressView();
            }
        });
        //end calling api to get new list room by new category
    }

    private void loadMoreNewListRoomByCategory() {
        //LLog.d(TAG, "loadMoreNewListRoomByCategory mTotalPage: " + mTotalPage);
        if (mCurrentPage > mTotalPage) {
            //TODO this is last page
            //LLog.d(TAG, "if mCurrentPage > mTotalPage ->>> STOP load more");
        } else {
            mCurrentPage++;
            LLog.d(TAG, ">>>load page " + mCurrentPage);

            if (mCurrentPage > mTotalPage) {
                //TODO this is last page
                //LLog.d(TAG, "else mCurrentPage > mTotalPage ->>> STOP load more");
                return;
            }

            isCalledAPIRoomByCategoryComplete = false;
            updateProgressView();
            //start calling api to get new list room by new category
            LSService service = RestClient.createService(LSService.class);
            subscribe(service.roomGetByCategory(mCurrentCategory, mCurrentPage, Constants.LIMIT_IDOL_BY_CATEGORY), new ApiSubscriber<RoomGetByCategory>() {
                @Override
                public void onSuccess(RoomGetByCategory roomGetByCategory) {
                    //LLog.d(TAG, "loadMoreNewListRoomByCategory onSuccess " + roomGetByCategory.getCategory().getRooms().size());
                    lRoomByCategoryView.addItemToList(roomGetByCategory);
                    isCalledAPIRoomByCategoryComplete = true;
                    updateProgressView();
                }

                @Override
                public void onFail(Throwable e) {
                    mTotalPage = 1;
                    handleException(e);
                    isCalledAPIRoomByCategoryComplete = true;
                    updateProgressView();
                }
            });
            //end calling api to get new list room by new category
        }
    }

    //event bus listen if user login success
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginActivity.LogginMessage loginMessage) {
        if (loginMessage != null) {
            if (loginMessage.isLogged()) {
                resetData();
            }
        }
    }

    private void resetData() {
        mCurrentPage = 1;
        mTotalPage = 1;
        lIdolFollowOrSuggestView.resetData();
        lRoomByCategoryView.resetData();

        setLIdolFollowOrSuggestView();
        setLRoomByCategoryView();
    }

    //start follow and unfollow
    private void followIdol(Room room) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.followIdol(room.getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol result) {
                if (result != null && result.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), result.getMessage() + " " + room.getTitle());
                    lIdolFollowOrSuggestView.updateUIFollowOrUnfollow(room);
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void unfollowIdol(Room room) {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.unfollowIdol(room.getId()), new ApiSubscriber<FollowIdol>() {
            @Override
            public void onSuccess(FollowIdol result) {
                if (result != null && result.getMessage() != null) {
                    AlertMessage.showSuccess(getActivity(), result.getMessage() + " " + room.getTitle());
                    lIdolFollowOrSuggestView.updateUIFollowOrUnfollow(room);
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }
    //end follow and unfollow

    public static class FollowOrSuggestEvent {
        private boolean isReloadData;

        public boolean isReloadData() {
            return isReloadData;
        }

        public void setReloadData(boolean reloadData) {
            isReloadData = reloadData;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FollowOrSuggestEvent followOrSuggestEvent) {
        LLog.d(TAG, ">>>>>>>>.onMessageEvent followOrSuggestEvent");
        if (followOrSuggestEvent != null) {
            if (followOrSuggestEvent.isReloadData) {
                needToReloadFollowOrSuggestData = true;
            }
        }
    }

    private boolean needToReloadFollowOrSuggestData;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            LLog.d(TAG, "isVisibleToUser");
            checkToReloadDataFollowOrSuggest();
        } else {
            //LLog.d(TAG, "!isVisibleToUser");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d(TAG, "onResume");
        checkToReloadDataFollowOrSuggest();
    }

    private void checkToReloadDataFollowOrSuggest() {
        if (needToReloadFollowOrSuggestData) {
            LLog.d(TAG, ">>>needToReloadFollowOrSuggestData true =============>>>>>>>> reload data follow or suggest");
            lIdolFollowOrSuggestView.resetData();

            setLIdolFollowOrSuggestView();
            LLog.d(TAG, ">DONE reload data follow or suggest");
        } else {
            LLog.d(TAG, ">>>needToReloadFollowOrSuggestData false =============>>>>>>>> dont reload data follow or suggest");
        }
    }
}

