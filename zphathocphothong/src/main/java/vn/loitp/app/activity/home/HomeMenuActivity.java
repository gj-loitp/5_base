package vn.loitp.app.activity.home;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.ad.FrmGift;
import vn.loitp.app.activity.home.alllist.FrmItem;
import vn.loitp.app.activity.home.more.FrmMore;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.DataManager;
import vn.loitp.app.model.Chap;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LAppBarLayout;

public class HomeMenuActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<Chap> chapList = new ArrayList<>();
    private ImageView toolbarImage;
    private DataManager dataManager;
    //private Handler handlerSearch = new Handler();

    private final int POS_PAGE_GIFT = 3;

    public DataManager getDataManager() {
        return dataManager;
    }

    //private final String STATE_RESUME_WINDOW = "resumeWindow";
    //private final String STATE_RESUME_POSITION = "resumePosition";
    //private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    //private boolean mExoPlayerFullscreen = false;
    //private FrameLayout mFullScreenButton;
    //private ImageView mFullScreenIcon;
    //private Dialog mFullScreenDialog;

    //private int mResumeWindow;
    //private long mResumePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = new DataManager(activity);
        try {
            dataManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (IOException e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        isShowAdWhenExist = false;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);

        AppUtil.loadBackground(activity, toolbarImage);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    //LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    //LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                    AppUtil.loadBackground(activity, toolbarImage);
                } else {
                    //IDLE appBarLayout not min not max
                    //LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });

        chapList = dataManager.getAllChap(DataManager.TABLE_NAME);
        //LLog.d(TAG, "chapList " + LSApplication.getInstance().getGson().toJson(chapList));

        //add item gift and more
        Chap chapGift = new Chap();
        chapGift.setTitle(Constants.MENU_GIFT);
        chapList.add(POS_PAGE_GIFT, chapGift);

        Chap chapMore = new Chap();
        chapMore.setTitle(Constants.MENU_MORE);
        chapList.add(chapMore);

        findViewById(R.id.bt_menu).setOnClickListener(this);

        /*if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }*/

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                LLog.d(TAG, "onPageSelected " + position);
                initPlayerAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });

        int index = LPref.getIndex(activity);
        if (index != vn.loitp.core.common.Constants.NOT_FOUND && index >= 0 && index < chapList.size()) {
            viewPager.setCurrentItem(index);
            initPlayerAtPosition(index);
        } else {
            initPlayerAtPosition(0);
        }
    }

    private void initPlayerAtPosition(int position) {
        LPref.setIndex(activity, position);
        releaseVideo();
        String linkMp3 = chapList.get(position).getLinkMp3();
        if (linkMp3 == null) {
            if (mExoPlayerView.getVisibility() != View.GONE) {
                LAnimationUtil.play(mExoPlayerView, Techniques.FadeOut, new LAnimationUtil.Callback() {
                    @Override
                    public void onCancel() {
                        //do nothing
                    }

                    @Override
                    public void onEnd() {
                        mExoPlayerView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRepeat() {
                        //do nothing
                    }

                    @Override
                    public void onStart() {
                        //do nothing
                    }
                });
            }
            return;
        }
        if (mExoPlayerView.getVisibility() != View.VISIBLE) {
            mExoPlayerView.setVisibility(View.VISIBLE);
            LAnimationUtil.play(mExoPlayerView, Techniques.FadeIn);
        }
        playMp3(linkMp3);
        initExoPlayer();
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
        return R.layout.activity_home_menu;
    }

    private FrmItem genFrmAllList(int pos) {
        FrmItem frm = new FrmItem();
        Bundle bundle = new Bundle();
        Chap chap = chapList.get(pos);
        bundle.putSerializable(Constants.MENU_CATEGORY, chap);
        frm.setArguments(bundle);
        return frm;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == POS_PAGE_GIFT) {
                return new FrmGift();
            } else if (position == chapList.size() - 1) {
                return new FrmMore();
            } else {
                return genFrmAllList(position);
            }
        }

        @Override
        public int getCount() {
            return chapList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return chapList.get(position).getTitle();
        }
    }

    private boolean isExit;

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        } else {
            LDialogUtil.showDialog3(activity, getString(R.string.app_name), getString(R.string.msg_exit_app), getString(R.string.yes), getString(R.string.no), getString(R.string.rate), new LDialogUtil.Callback3() {
                @Override
                public void onClick1() {
                    isExit = true;
                    onBackPressed();
                }

                @Override
                public void onClick2() {
                    //do nothing
                }

                @Override
                public void onClick3() {
                    LSocialUtil.rateApp(activity, getPackageName());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                LPopupMenu.show(activity, v, R.menu.menu_popup, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.rate_app:
                                LSocialUtil.rateApp(activity, getPackageName());
                                break;
                            case R.id.more_app:
                                LSocialUtil.moreApp(activity);
                                break;
                            case R.id.share_app:
                                LSocialUtil.shareApp(activity);
                                break;
                            case R.id.like_facebook_fanpage:
                                LSocialUtil.likeFacebookFanpage(activity);
                                break;
                            case R.id.support_24_7:
                                LSocialUtil.chatMessenger(activity);
                                break;
                        }
                    }
                });
                break;
        }
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }*/

    /*private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen) {
                    closeFullscreenDialog();
                }
                super.onBackPressed();
            }
        };
    }*/

    /*private void openFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }*/


    /*private void closeFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_expand));
    }*/

    /*private void initFullscreenButton() {
        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }*/

    private void initExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        mExoPlayerView.setPlayer(player);

        //boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        //if (haveResumePosition) {
        //    mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        //}
        mExoPlayerView.setControllerHideOnTouch(false);
        mExoPlayerView.setControllerShowTimeoutMs(-1);
        mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(false);

        mExoPlayerView.getPlayer().addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                //do nothing
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                //do nothing
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                //do nothing
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        LLog.d(TAG, "onPlayerStateChanged STATE_BUFFERING");
                        break;
                    case Player.STATE_ENDED:
                        LLog.d(TAG, "onPlayerStateChanged STATE_ENDED");
                        break;
                    case Player.STATE_IDLE:
                        LLog.d(TAG, "onPlayerStateChanged STATE_IDLE");
                        break;
                    case Player.STATE_READY:
                        LLog.d(TAG, "onPlayerStateChanged STATE_READY");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {
                //do nothing
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                //do nothing
            }

            @Override
            public void onPositionDiscontinuity() {
                //do nothing
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                //do nothing
            }
        });
    }


    /*@Override
    protected void onResume() {
        super.onResume();
        if (mExoPlayerView == null) {
            mExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);

            initFullscreenDialog();
            initFullscreenButton();

            playM3u8();
            playMp3();
        }

        initExoPlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }*/

    /*private void playM3u8() {
        String streamUrl = "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
        String userAgent = Util.getUserAgent(activity, getApplicationContext().getApplicationInfo().packageName);
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(activity, null, httpDataSourceFactory);
        Uri daUri = Uri.parse(streamUrl);
        mVideoSource = new HlsMediaSource(daUri, dataSourceFactory, 1, null, null);
    }*/

    /*private void playMp3() {
        Handler mHandler = new Handler();
        String url = "http://www.hoahaomedia.org/hoahao/_media/PGHH/Audio/Sam_Giang/Hue_Duyen/001_Sam%20Giang%20Khuyen%20Nguoi%20Doi%20Tu%20Niem%20Hue%20Duyen%20-%20Tri%20Tung.mp3";
        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(activity, getApplicationContext().getApplicationInfo().packageName);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);
        mVideoSource = new ExtractorMediaSource(uri, dataSourceFactory, Mp3Extractor.FACTORY, mHandler, null);
    }*/

    private void playMp3(String url) {
        Handler mHandler = new Handler();
        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(activity, getApplicationContext().getApplicationInfo().packageName);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent, null,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);
        mVideoSource = new ExtractorMediaSource(uri, dataSourceFactory, Mp3Extractor.FACTORY, mHandler, null);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
        mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

        releaseVideo();

        if (mFullScreenDialog != null) {
            mFullScreenDialog.dismiss();
        }
    }*/

    private void releaseVideo() {
        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mExoPlayerView.getPlayer().release();
        }
    }

    @Override
    protected void onDestroy() {
        releaseVideo();
        super.onDestroy();
    }
}
