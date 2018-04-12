/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.loitp.app.activity.customviews.videoview.uizavideowithima;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.base.PreviewLoader;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.glide.GlideApp;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.glide.GlideThumbnailTransformationPB;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.ProgressCallback;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.VideoAdPlayerListerner;
import vn.loitp.core.utilities.LLog;

/**
 * Manages the {@link ExoPlayer}, the IMA plugin and all video playback.
 */
/* package */ final class UizaPlayerManager implements AdsMediaSource.MediaSourceFactory, PreviewLoader {
    private final String TAG = getClass().getSimpleName();
    private ImaAdsLoader adsLoader = null;
    private final DataSource.Factory manifestDataSourceFactory;
    private final DataSource.Factory mediaDataSourceFactory;

    private SimpleExoPlayer player;
    private long contentPosition;

    private String linkPlay;

    private VideoAdPlayerListerner videoAdPlayerListerner = new VideoAdPlayerListerner();

    private PreviewTimeBarLayout previewTimeBarLayout;
    private String thumbnailsUrl;
    private ImageView imageView;
    private Player.EventListener eventListener = new Player.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY && playWhenReady) {
                if (previewTimeBarLayout != null) {
                    previewTimeBarLayout.hidePreview();
                }
            }
        }
    };

    private Handler handler;
    private Runnable runnable;

    private ProgressCallback progressCallback;

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public UizaPlayerManager(Context context, PreviewTimeBarLayout previewTimeBarLayout, ImageView imageView, String linkPlay, String urlIMAAd, String thumbnailsUrl) {
        this.linkPlay = linkPlay;
        if (urlIMAAd == null || urlIMAAd.isEmpty()) {
            LLog.d(TAG, "UizaPlayerManager urlIMAAd == null || urlIMAAd.isEmpty()");
        } else {
            adsLoader = new ImaAdsLoader(context, Uri.parse(urlIMAAd));
        }
        String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
        manifestDataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
        mediaDataSourceFactory = new DefaultDataSourceFactory(
                context,
                userAgent,
                new DefaultBandwidthMeter());
        this.imageView = imageView;
        this.previewTimeBarLayout = previewTimeBarLayout;
        this.thumbnailsUrl = thumbnailsUrl;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    boolean isPlayingAd = videoAdPlayerListerner.isPlayingAd();
                    //LLog.d(TAG, "isPlayingAd " + isPlayingAd);
                    if (isPlayingAd) {
                        if (progressCallback != null) {
                            VideoProgressUpdate videoProgressUpdate = adsLoader.getAdProgress();
                            float mls = videoProgressUpdate.getCurrentTime();
                            float duration = videoProgressUpdate.getDuration();
                            int percent = (int) (mls * 100 / duration);
                            //LLog.d(TAG, "ad progress: " + mls + "/" + duration + " -> " + percent + "%");
                            progressCallback.onAdProgress(mls, duration, percent);
                        }
                    } else {
                        if (progressCallback != null) {
                            float mls = player.getCurrentPosition();
                            float duration = player.getDuration();
                            int percent = (int) (mls * 100 / duration);
                            //LLog.d(TAG, "video progress: " + mls + "/" + duration + " -> " + percent + "%");
                            progressCallback.onVideoProgress(mls, duration, percent);
                        }
                    }
                    handler.postDelayed(runnable, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    public void init(Context context, PlayerView playerView) {
        /*// Create a default track selector.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // Create a player instance.
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        playerView.setPlayer(player);

        // This is the MediaSource representing the content media (i.e. not the ad).

        String contentUrl = linkPlay;
        //String contentUrl = context.getString(R.string.content_url);
        //String contentUrl = context.getString(R.string.url_dash);
        //String contentUrl = context.getString(R.string.url_hls);
        //String contentUrl = context.getString(R.string.url_smooth);
        MediaSource contentMediaSource = buildMediaSource(Uri.parse(contentUrl), *//* handler= *//* null, *//* listener= *//* null);

        // Compose the content media source into a new AdsMediaSource with both ads and content.
        MediaSource mediaSourceWithAds = new AdsMediaSource(
                contentMediaSource,
            *//* adMediaSourceFactory= *//* this,
                adsLoader,
                playerView.getOverlayFrameLayout(),
            *//* eventHandler= *//* null,
            *//* eventListener= *//* null);

        // Prepare the player with the source.
        player.seekTo(contentPosition);

        player.addListener(eventListener);

        player.addListener(new PlayerEventListener());
        player.addAudioDebugListener(new AudioEventListener());
        player.addVideoDebugListener(new VideoEventListener());
        player.addMetadataOutput(new MetadataOutputListener());
        player.addTextOutput(new TextOutputListener());

        if (adsLoader != null) {
            adsLoader.addCallback(videoAdPlayerListerner);
        }
        player.prepare(mediaSourceWithAds);

        *//*Format textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "en", null);
        String urlSubtitle = "https://s3-ap-southeast-1.amazonaws.com/58aa3a0eb555420a945a27b47ce9ef2f-data/static/type_caption__entityId_81__language_en.vtt";
        MediaSource textMediaSource = new SingleSampleMediaSource.Factory(
                mediaDataSourceFactory)
                .createMediaSource(Uri.parse(urlSubtitle), textFormat, C.TIME_UNSET);
        MediaSource mediaSourceWithText = new MergingMediaSource(contentMediaSource, textMediaSource);
        //player.prepare(mediaSourceWithText, false, false);
        player.prepare(mediaSourceWithText);*//*

        player.setPlayWhenReady(true);*/


        //Exo Player Initialization
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        playerView.setPlayer(player);

        DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "iWatch"), bandwidthMeter2);


        //SUBTITLE
        //Text Format Initialization
        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, null, Format.NO_VALUE, Format.NO_VALUE, "ar", null, Format.OFFSET_SAMPLE_RELATIVE);
        //Video Source
        //MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkPlay));
        MediaSource videoSource = buildMediaSource(Uri.parse(linkPlay),  null,  null);

        String linkSub = "https://dev-static.uiza.io/subtitle_56a4f990-17e6-473c-8434-ef6c7e40bba1_vi_1522812445904.vtt";
        //String linkSub = "https://s3-ap-southeast-1.amazonaws.com/58aa3a0eb555420a945a27b47ce9ef2f-data/static/type_caption__entityId_81__language_en.vtt";
        //Arabic Subtitles
        SingleSampleMediaSource textMediaSourceAr = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);
        //English Subtitles
        SingleSampleMediaSource textMediaSourceEn = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);
        //French Subtitles
        SingleSampleMediaSource textMediaSourceFr = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);

        //Final MediaSource
        MediaSource mediaSource = new MergingMediaSource(videoSource, textMediaSourceAr, textMediaSourceEn, textMediaSourceFr);
        //Preparing The Player
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    public void resumeVideo() {
        player.setPlayWhenReady(true);
    }

    public void pauseVideo() {
        player.setPlayWhenReady(false);
    }

    public void reset() {
        if (player != null) {
            contentPosition = player.getContentPosition();
            player.release();
            player = null;

            handler = null;
            runnable = null;
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;

            handler = null;
            runnable = null;
        }
        if (adsLoader != null) {
            adsLoader.release();
        }
    }

    // AdsMediaSource.MediaSourceFactory implementation.

    @Override
    public MediaSource createMediaSource(Uri uri, @Nullable Handler handler, @Nullable MediaSourceEventListener listener) {
        return buildMediaSource(uri, handler, listener);
    }

    @Override
    public int[] getSupportedTypes() {
        // IMA does not support Smooth Streaming ads.
        return new int[]{C.TYPE_DASH, C.TYPE_HLS, C.TYPE_OTHER};
    }

    // Internal methods.

    private MediaSource buildMediaSource(Uri uri, @Nullable Handler handler, @Nullable MediaSourceEventListener listener) {
        @ContentType int type = Util.inferContentType(uri);
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        manifestDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory), manifestDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                        .createMediaSource(uri, handler, listener);
            default:
                throw new IllegalStateException("Unsupported type: " + type);
        }
    }

    @Override
    public void loadPreview(long currentPosition, long max) {
        player.setPlayWhenReady(false);
        GlideApp.with(imageView)
                .load(thumbnailsUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new GlideThumbnailTransformationPB(currentPosition))
                .into(imageView);

    }
}
