//package vn.puresolutions.livestar.room;
//
//import android.net.Uri;
//import android.support.v4.app.Fragment;
//import android.view.View;
//import android.view.animation.AlphaAnimation;
//import android.widget.FrameLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.github.pedrovgs.DraggableView;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import vn.puresolutions.livestar.R;
//import vn.puresolutions.livestar.core.api.model.Room;
//import vn.puresolutions.livestar.ui.activity.MainActivity;
//import vn.puresolutions.livestar.ui.fragment.VideoFragment;
//import vn.puresolutions.livestar.ui.fragment.VideoViewerFragment;
//import vn.puresolutions.livestar.ui.fragment.home.RoomFragment;
//import vn.puresolutions.livestar.ui.fragment.room.VideosFragment;
//import vn.puresolutions.livestarv3.utilities.old.AlertMessage;
//import vn.puresolutions.livestarv3.utilities.FrescoUtils;
//import vn.puresolutions.livestarv3.utilities.old.NetworkUtils;
//import vn.puresolutions.livestarv3.utilities.old.ScreenUtils;
//
///***
// * @author Khanh Le
// * @version 1.0.0
// * @since 11/8/2015
// */
//public class RoomScreen {
//
//    private static final int DELAY_SHOW_PLAYER = 1000;
//
//    private View vwRoot;
//
//    @Bind(R.id.ls_roomView_fltContentContainer)
//    FrameLayout fltContentContainer;
//
//    @Bind(R.id.ls_roomView_lnltStatus)
//    View viewStatus;
//
//    @Bind(R.id.ls_roomView_pgbLoading)
//    ProgressBar pgbLoading;
//
//    @Bind(R.id.ls_roomView_tvMessage)
//    TextView tvMessage;
//
//    @Bind(R.id.ls_roomView_fltPlayerContainer)
//    FrameLayout fltPlayerContainer;
//
//    @Bind(R.id.ls_roomView_pgbTopLoading)
//    ProgressBar pgbTopLoading;
//
//    @Bind(R.id.ls_roomView_viewOffline)
//    View grpOffline;
//
//    @Bind(R.id.ls_roomView_fltVideoContainer)
//    FrameLayout fltVideoContainer;
//
//    private MainActivity activity;
//    private RoomCenter roomCenter;
//
//    private Fragment videoFragment;
//    private Fragment videosFragment;
//    private SimpleDraweeView imgBanner;
//
//    private long startLoadingTime;
//
//    public RoomScreen(final MainActivity activity, View root) {
//        this.activity = activity;
//        this.vwRoot = root;
//
//        initView();
//
//        roomCenter = RoomCenter.getInstance();
//        roomCenter.init(activity, this);
//    }
//
//    private void initView() {
//        ButterKnife.bind(this, vwRoot);
//
//        DraggableView draggableView = activity.getDraggableView();
//        int height = (int) (ScreenUtils.getWidthScreen(activity) * 9.0f / 16);
//        draggableView.setTopViewHeight(height);
//
//        activity.getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.ls_roomView_fltContentContainer, new RoomFragment())
//                .commit();
//    }
//
//    public void load(Room room) {
//        if (!NetworkUtils.hasConnection(activity)) {
//            AlertMessage.showError(activity, R.string.ls_cant_connect_to_internet);
//            return;
//        }
//
//        // open room view
//        activity.showDraggableView();
//
//        if (!roomCenter.loadRoom(room)) {
//            return;
//        }
//
//        startLoadingTime = System.currentTimeMillis();
//    }
//
//    public void closeRoom() {
//        roomCenter.closeRoom();
//    }
//
//    public void resetRoom() {
//        if (videoFragment != null) {
//            activity.getSupportFragmentManager()
//                    .beginTransaction()
//                    .remove(videoFragment)
//                    .commitAllowingStateLoss();
//            videoFragment = null;
//        }
//
//        if (videosFragment != null) {
//            grpOffline.setVisibility(View.GONE);
//            activity.getSupportFragmentManager()
//                    .beginTransaction()
//                    .remove(videosFragment)
//                    .commitAllowingStateLoss();
//            videosFragment = null;
//        }
//
//        if (imgBanner != null) {
//            fltPlayerContainer.removeView(imgBanner);
//            imgBanner = null;
//        }
//    }
//
//    public void openVideo(final String url) {
//        showPlayerContent(new Runnable() {
//            @Override
//            public void run() {
//                pgbTopLoading.setVisibility(View.INVISIBLE);
//                videoFragment = VideoFragment.newInstance(url);
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.ls_roomView_fltPlayerContainer, videoFragment)
//                        .commitAllowingStateLoss();
//            }
//        });
//    }
//
//    public void openYoutube(final String link) {
//        showPlayerContent(new Runnable() {
//            @Override
//            public void run() {
//                Uri uri = Uri.parse(link);
//                String youtubeId = uri.getLastPathSegment();
//                pgbTopLoading.setVisibility(View.INVISIBLE);
//                videoFragment = VideoViewerFragment.newInstance(youtubeId);
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.ls_roomView_fltPlayerContainer, videoFragment)
//                        .commitAllowingStateLoss();
//            }
//        });
//    }
//
//    public void showBanner(final String url) {
//        showPlayerContent(new Runnable() {
//            @Override
//            public void run() {
//                pgbTopLoading.setVisibility(View.INVISIBLE);
//                imgBanner = new SimpleDraweeView(activity);
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                        FrameLayout.LayoutParams.MATCH_PARENT);
//                fltPlayerContainer.addView(imgBanner, params);
//                FrescoUtils.loadImage(imgBanner, url);
//            }
//        });
//    }
//
//    private void showPlayerContent(Runnable runnable) {
//        long delayTime = Math.max(DELAY_SHOW_PLAYER - (System.currentTimeMillis() - startLoadingTime), 0);
//        fltPlayerContainer.postDelayed(runnable, delayTime);
//    }
//
//    public void showLoading() {
//        fltContentContainer.setVisibility(View.INVISIBLE);
//        viewStatus.setVisibility(View.VISIBLE);
//        pgbLoading.setVisibility(View.VISIBLE);
//        tvMessage.setText(activity.getString(R.string.ls_connectRoomLoadingMessage));
//        pgbTopLoading.setVisibility(View.VISIBLE);
//    }
//
//    public void showRoomView() {
//        activity.hideAnimationView(false);
//        long delayTime = Math.max(DELAY_SHOW_PLAYER - (System.currentTimeMillis() - startLoadingTime), 0);
//        fltContentContainer.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                viewStatus.setVisibility(View.INVISIBLE);
//                AlphaAnimation animation = new AlphaAnimation(0, 1);
//                animation.setDuration(1000);
//                fltContentContainer.setVisibility(View.VISIBLE);
//                fltContentContainer.setAnimation(animation);
//            }
//        }, delayTime);
//    }
//
//    public void showOfflineView() {
//        activity.hideAnimationView(true);
//        long delayTime = Math.max(DELAY_SHOW_PLAYER - (System.currentTimeMillis() - startLoadingTime), 0);
//        grpOffline.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                viewStatus.setVisibility(View.INVISIBLE);
//
//                videosFragment = new VideosFragment();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.ls_roomView_fltVideoContainer, videosFragment)
//                        .commitAllowingStateLoss();
//
//                AlphaAnimation animation = new AlphaAnimation(0, 1);
//                animation.setDuration(1000);
//                grpOffline.setVisibility(View.VISIBLE);
//                grpOffline.setAnimation(animation);
//            }
//        }, delayTime);
//    }
//
//    public void showError(int msgId) {
//        fltContentContainer.setVisibility(View.INVISIBLE);
//        viewStatus.setVisibility(View.VISIBLE);
//        pgbLoading.setVisibility(View.GONE);
//        tvMessage.setText(activity.getString(msgId));
//    }
//
//    public void onDestroy() {
//        resetRoom();
//        roomCenter.destroy();
//        roomCenter = null;
//        activity = null;
//    }
//}
