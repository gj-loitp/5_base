package vn.loitp.app.activity.demo.youtubeparser;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LConnectivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.function.youtubeparser.Parser;
import vn.loitp.function.youtubeparser.models.videos.Video;
import vn.loitp.views.LToast;

//https://www.slickremix.com/docs/get-api-key-for-youtube/
public class YoutubeParserActivity extends BaseFontActivity {
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private int totalElement;
    private String nextToken;
    private final String CHANNEL_ID = "UCdDnufSiFrEvS7jqcKOkN0Q";
    public static final String API_KEY = "AIzaSyBIPIyBbvDxUHPyEi9Qy-phu9VuOzy7iUI";

    private void findViews() {
        progressBar = findViewById(R.id.pb);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.rv);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.canChildScrollUp();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                videoAdapter.clearData();
                videoAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(true);
                loadVideo();
            }
        });
        if (LConnectivityUtil.isConnected(activity)) {
            loadVideo();
        } else {
            AlertDialog alertDialog = LDialogUtil.showDialog1(activity, getString(R.string.app_name), getString(R.string.check_ur_connection), "Close", new LDialogUtil.Callback1() {
                @Override
                public void onClick1() {
                    onBackPressed();
                }
            });
            alertDialog.setCancelable(false);
        }

        //show the fab on the bottom of recycler view
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parser parser = new Parser();
                if (nextToken != null) {
                    String url = parser.generateMoreDataRequest(CHANNEL_ID, 50, Parser.ORDER_DATE, API_KEY, nextToken);
                    parser.execute(url);
                    parser.onFinish(new Parser.OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                            //update the adapter with the new data
                            int indexScroll = videoAdapter.getList().size() + 1;
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
                            LToast.showShort(activity, "Error while loading data. Please retry later.");
                        }
                    });

                } else {
                    LToast.showShort(activity, "Unable to load data. Please retry later.");
                }
            }
        });
        checkToShowWarningDialog();
    }

    private void checkToShowWarningDialog() {

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
    protected int setLayoutResourceId() {
        return R.layout.activity_youtube_parser;
    }

    private void loadVideo() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        Parser parser = new Parser();
        String url = parser.generateRequest(CHANNEL_ID, 50, Parser.ORDER_DATE, API_KEY);
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Video> list, String nextPageToken) {
                //list is an ArrayList with all video's item
                //set the adapter to recycler view
                /*for (int i = 0; i < list.size(); i++) {
                    LLog.d(TAG, i + "-> " + list.get(i).getLinkYoutube());
                }*/
                videoAdapter = new VideoAdapter(list, R.layout.yt_row, activity);
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
                LToast.showShort(activity, "Error while loading data. Please retry later.");
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
    protected void onDestroy() {
        super.onDestroy();
        if (videoAdapter != null) {
            videoAdapter.clearData();
        }
    }
}
