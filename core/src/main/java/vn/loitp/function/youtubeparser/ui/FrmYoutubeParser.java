package vn.loitp.function.youtubeparser.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.function.youtubeparser.Parser;
import vn.loitp.function.youtubeparser.models.videos.Video;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;

//https://www.slickremix.com/docs/get-api-key-for-youtube/
public class FrmYoutubeParser extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private int totalElement;
    private String nextToken;
    public static final String CHANNEL_ID_LOITP = "UCdDnufSiFrEvS7jqcKOkN0Q";
    public static final String API_KEY = "AIzaSyBIPIyBbvDxUHPyEi9Qy-phu9VuOzy7iUI";
    public static final String KEY_CHANNEL_ID = "KEY_CHANNEL_ID";
    public static final String KEY_IS_ENGLISH_MSG = "KEY_IS_ENGLISH_MSG";
    private boolean isEnglishMsg;
    private String channelId = CHANNEL_ID_LOITP;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_youtube_parser;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isEnglishMsg = bundle.getBoolean(KEY_IS_ENGLISH_MSG, false);
            channelId = bundle.getString(KEY_CHANNEL_ID);
        }
        findViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.canChildScrollUp();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            videoAdapter.clearData();
            videoAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(true);
            loadVideo();
        });
        if (LConnectivityUtil.isConnected(getActivity())) {
            loadVideo();
        } else {
            final AlertDialog alertDialog = LDialogUtil.showDialog1(getActivity(), getString(R.string.app_name), getString(R.string.check_ur_connection), "Close", () -> {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });
            alertDialog.setCancelable(false);
        }

        //show the fab on the bottom of recycler view
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) {
                    return;
                }
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                if (lastVisible == totalElement - 1) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //load more data
        fab.setOnClickListener(v -> {
            final Parser parser = new Parser();
            if (nextToken != null) {
                final String url = parser.generateMoreDataRequest(channelId, 50, Parser.ORDER_DATE, API_KEY, nextToken);
                parser.execute(url);
                parser.onFinish(new Parser.OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                        //update the adapter with the new data
                        final int indexScroll = videoAdapter.getList().size() + 1;
                        videoAdapter.getList().addAll(list);
                        totalElement = videoAdapter.getItemCount();
                        nextToken = nextPageToken;
                        videoAdapter.notifyDataSetChanged();
                        fab.setVisibility(View.GONE);
                        //recyclerView.scrollToPosition(indexScroll);
                        recyclerView.smoothScrollToPosition(indexScroll);
                    }

                    @Override
                    public void onError() {
                        if (getActivity() != null) {
                            LToast.showShort(getActivity(), "Error while loading data. Please retry later.");
                        }
                    }
                });

            } else {
                if (getActivity() != null) {
                    LToast.showShort(getActivity(), "Unable to load data. Please retry later.");
                }
            }
        });
        checkToShowWarningDialog();
    }

    private void findViews() {
        progressBar = frmRootView.findViewById(R.id.pb);
        fab = frmRootView.findViewById(R.id.fab);
        recyclerView = frmRootView.findViewById(R.id.rv);
        swipeRefreshLayout = frmRootView.findViewById(R.id.swipe_refresh_layout);
    }

    private void checkToShowWarningDialog() {
        if (getActivity() == null) {
            return;
        }
        final boolean isShowed = LPref.getIsShowedDlgWarningYoutubeParser(getActivity());
        if (!isShowed) {
            String tit;
            String msg;
            if (isEnglishMsg) {
                tit = "Warning";
                msg = "All of the videos below are copyrighted from Youtube, we only provide links for you to see more easily. If you have any copyright issues, please contact the video owner on YouTube. Sincerely thank you.";
            } else {
                tit = "Thông báo";
                msg = "Tất cả những video dưới đây đều có bản quyền từ Youtube, chúng tôi chỉ cung cấp link để các bạn xem dễ dàng hơn. Nếu có bất cứ vấn đề gì về bản quyền, xin vui lòng liên hệ với chủ sở hữu video đó trên Youtube. Trân trọng cảm ơn.";
            }
            LDialogUtil.showDialog1(getActivity(), tit, msg, "Okay",
                    () -> LPref.setIsShowedDlgWarningYoutubeParser(getActivity(), true));
        }
    }

    private void loadVideo() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        final Parser parser = new Parser();
        final String url = parser.generateRequest(channelId, 50, Parser.ORDER_DATE, API_KEY);
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                //list is an ArrayList with all video's item
                //set the adapter to recycler view
                /*for (int i = 0; i < list.size(); i++) {
                    LLog.d(TAG, i + "-> " + list.get(i).getLinkYoutube());
                }*/
                videoAdapter = new VideoAdapter(list, R.layout.yt_row, getActivity());
                //recyclerView.setAdapter(videoAdapter);

                final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(videoAdapter);
                scaleAdapter.setDuration(1000);
                scaleAdapter.setInterpolator(new OvershootInterpolator());
                scaleAdapter.setFirstOnly(true);
                recyclerView.setAdapter(scaleAdapter);

                recyclerView.setAdapter(videoAdapter);
                totalElement = videoAdapter.getItemCount();
                nextToken = nextPageToken;
                videoAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError() {
                LLog.d(TAG, "onError");
                if (getActivity() == null) {
                    return;
                }
                LToast.showShort(getActivity(), "Error while loading data. Please retry later.");
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoAdapter != null) {
            videoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoAdapter != null) {
            videoAdapter.clearData();
        }
    }
}
