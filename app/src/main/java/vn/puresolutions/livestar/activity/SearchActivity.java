package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.SearchResultAdapter;
import vn.puresolutions.livestar.core.api.model.Broadcasters;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.BroadcasterService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.utilities.old.TextUtils;
import vn.puresolutions.livestarv3.base.BaseActivity;


public class SearchActivity extends BaseActivity {
    public interface SearchLoadMoreListener {
        void onLoadingMore();
    }

    @BindView(R.id.searchActivity_imgBackground)
    SimpleDraweeView imgBackground;
    private PipelineDraweeControllerBuilder controller;
    private ControllerListener controllerListener = new BaseControllerListener() {
        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(SearchActivity.this, R.anim.fade_in);
            imgBackground.startAnimation(fadeInAnimation);
        }
    };
    private String TAG = getClass().getSimpleName();
    private SearchResultAdapter searchResultAdapter;
    private int page = 0;
    private SearchActivity.SearchLoadMoreListener loadMoreListener = this::loadResult;
    private boolean stopscroll = false;
    private boolean isLoading = true;
    private int currentPosition = -1;

    @BindView(R.id.searchActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvResultSearch)
    RecyclerView rvResultSearch;
    @BindView(R.id.searActivity_prgLoading)
    ProgressBar prgLoading;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.tvSearchOops)
    TextView tvSearchOops;
    @BindView(R.id.flContainer)
    FrameLayout flContainer;
    @BindView(R.id.lnlContainer)
    LinearLayout lnlContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener);
        imgBackground.setController(controller.build());

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvSearchOops.setText(Html.fromHtml(this.getString(R.string.search_not_result)));

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResultSearch.setLayoutManager(layout);
        rvResultSearch.setHasFixedSize(true);
        rvResultSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(TAG, "onScrollStateChanged ");
                try {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        loadBackgroundImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i(TAG, "onScrolled ");
                int pastVisiblesItems, visibleItemCount, totalItemCount;
                visibleItemCount = layout.getChildCount();
                totalItemCount = layout.getItemCount();
                pastVisiblesItems = layout.findLastVisibleItemPosition();
                Log.i(TAG, "visibleItemCount: " + visibleItemCount);
                Log.i(TAG, "totalItemCount: " + totalItemCount);
                Log.i(TAG, "pastVisiblesItems: " + pastVisiblesItems);
                Log.i(TAG, "isLoading: " + isLoading);
                if (isLoading) {
                    Log.i(TAG, "onScrolled ----------------------------------------> page: " + page);
                    if (pastVisiblesItems + visibleItemCount >= totalItemCount) {
                        isLoading = false;
                        loadResult();
                    }
                }
            }
        });
        edtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.i(TAG, "onEditorAction");
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    page = 0;
                    edtSearch.clearFocus();
                    TextUtils.hideKeyboardWhenNotFocus(SearchActivity.this);
                    flContainer.post(() -> {
                        searchResultAdapter = new SearchResultAdapter();
                        searchResultAdapter.setListener(loadMoreListener);
                        //roomsAdapter.setBaseWidth(rltContainer.getWidth());
                        rvResultSearch.setAdapter(searchResultAdapter);
                        loadResult();
                    });
                    return true;
                }
                return false;
            }
        });
    }

    void loadResult() {
        prgLoading.setVisibility(View.VISIBLE);
        BroadcasterService broadcasterService = RestClient.createService(BroadcasterService.class);
        subscribe(broadcasterService.search(edtSearch.getText().toString().trim(), page), new ApiSubscriber<Broadcasters>() {
            @Override
            public void onSuccess(Broadcasters result) {
                Log.i(TAG, "onSuccess: " + result.getBroadcasters().size());
                //Log.i(TAG, "onSuccess -> totalPage: -> " + result.getTotalPage());
                if (result.getTotalPage() == 0) {
                    Log.i(TAG, "onSuccess -> if ->: " + result.getBroadcasters().size());
                    tvSearchOops.setVisibility(View.VISIBLE);
                    lnlContainer.setBackgroundColor(getResources().getColor(R.color.grey_400));
                    searchResultAdapter.reachToEnd();

                } else {
                    tvSearchOops.setVisibility(View.GONE);
                    lnlContainer.setBackgroundColor(getResources().getColor(R.color.dialogRoomBackground));
                    Log.i(TAG, "onSuccess -> else ->: " + result.getBroadcasters().size());
                    Log.i(TAG, "onSuccess -> else ->: " + page);
                    searchResultAdapter.addAll(result.getBroadcasters());
                    if (page == result.getTotalPage() - 1) {
                        prgLoading.setVisibility(View.GONE);
                        searchResultAdapter.reachToEnd();
                        //rvResultSearch.setNestedScrollingEnabled(false);
                        isLoading = false;
                        return;
                    } else {
                        page++;
                        searchResultAdapter.finishLoadingMore();
                    }
                }
                if (page == 1) {
                    // first page. Selected item zero background as default
                    loadBackgroundImage(0);
                } else {
                    loadBackgroundImage();
                }
                prgLoading.setVisibility(View.GONE);
                isLoading = true;
            }

            @Override
            public void onFail(Throwable e) {
                Log.i(TAG, "onFail: " + e.toString());
                handleException(e);
                tvSearchOops.setVisibility(View.GONE);
                prgLoading.setVisibility(View.GONE);
                isLoading = true;
            }

            @Override
            protected void finalize() throws Throwable {
                prgLoading.setVisibility(View.GONE);
                isLoading = true;
            }
        });
    }

    private void loadBackgroundImage() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvResultSearch.getLayoutManager();
        loadBackgroundImage(layoutManager.findLastCompletelyVisibleItemPosition());
    }

    private void loadBackgroundImage(int position) {
        if (!searchResultAdapter.isLastPosition(position) && position != currentPosition &&
                position != RecyclerView.NO_POSITION) {
            this.currentPosition = position;
            controller.setUri(Uri.parse(searchResultAdapter.getItem(currentPosition).getRoom().getThumb()));
            Log.i("SearchActivity", "imgBack -> " + searchResultAdapter.getItem(currentPosition).getRoom().getThumb());
            imgBackground.setController(controller.build());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
        return R.layout.activity_search;
    }
}
