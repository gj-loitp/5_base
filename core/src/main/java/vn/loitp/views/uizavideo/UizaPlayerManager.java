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
package vn.loitp.views.uizavideo;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Surface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.base.PreviewLoader;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.C.ContentType;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.uiza.model.v2.listallentity.Subtitle;
import vn.loitp.views.uizavideo.glide.GlideApp;
import vn.loitp.views.uizavideo.glide.GlideThumbnailTransformationPB;
import vn.loitp.views.uizavideo.listerner.ProgressCallback;
import vn.loitp.views.uizavideo.listerner.VideoAdPlayerListerner;

/**
 * Manages the {@link ExoPlayer}, the IMA plugin and all video playback.
 */
/* package */ public final class UizaPlayerManager implements AdsMediaSource.MediaSourceFactory, PreviewLoader {
    private final String TAG = getClass().getSimpleName();
    private Gson gson = new Gson();//TODO remove later
    private Context context;
    private PlayerView playerView;
    private ImaAdsLoader adsLoader = null;
    private final DataSource.Factory manifestDataSourceFactory;
    private final DataSource.Factory mediaDataSourceFactory;
    private ProgressBar progressBar;
    private SimpleExoPlayer player;
    private long contentPosition;

    private String userAgent;
    private String linkPlay;
    private List<Subtitle> subtitleList;

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

    public UizaPlayerManager(PlayerView playerView, ProgressBar progressBar, PreviewTimeBarLayout previewTimeBarLayout, ImageView imageView, String linkPlay, String urlIMAAd, String thumbnailsUrl, List<Subtitle> subtitleList) {
        this.context = playerView.getContext();
        this.playerView = playerView;
        this.linkPlay = linkPlay;
        this.subtitleList = subtitleList;
        if (urlIMAAd == null || urlIMAAd.isEmpty()) {
            LLog.d(TAG, "UizaPlayerManager urlIMAAd == null || urlIMAAd.isEmpty()");
        } else {
            adsLoader = new ImaAdsLoader(context, Uri.parse(urlIMAAd));
        }
        this.progressBar = progressBar;
        userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
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
                        hideProgress();
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
                    if (handler != null && runnable != null) {
                        handler.postDelayed(runnable, 1000);
                    }
                }
            }
        };
        handler.postDelayed(runnable, 0);

        //playerView.setControllerAutoShow(false);
        playerView.setControllerShowTimeoutMs(0);
    }

    private DefaultTrackSelector trackSelector;

    public DefaultTrackSelector getTrackSelector() {
        return trackSelector;
    }

    private TrackSelectionHelper trackSelectionHelper;

    public TrackSelectionHelper getTrackSelectionHelper() {
        return trackSelectionHelper;
    }

    public void init() {
        //Exo Player Initialization
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        trackSelectionHelper = new TrackSelectionHelper(trackSelector, videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        playerView.setPlayer(player);

        MediaSource mediaSourceVideo = createMediaSourceVideo();

        //merge title to media source video
        //SUBTITLE
        MediaSource mediaSourceWithSubtitle = createMediaSourceWithSubtitle(mediaSourceVideo);

        //merge ads to media source subtitle
        //IMA ADS
        // Compose the content media source into a new AdsMediaSource with both ads and content.
        MediaSource mediaSourceWithAds = createMediaSourceWithAds(mediaSourceWithSubtitle);

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
        player.setPlayWhenReady(true);

        if (debugCallback != null) {
            debugCallback.onUpdateButtonVisibilities();
        }
    }

    private MediaSource createMediaSourceVideo() {
        //Video Source
        //MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkPlay));
        MediaSource mediaSourceVideo = buildMediaSource(Uri.parse(linkPlay), null, null);
        return mediaSourceVideo;
    }

    private MediaSource createMediaSourceWithSubtitle(MediaSource mediaSource) {
        if (subtitleList == null || subtitleList.isEmpty()) {
            return mediaSource;
        }
        LLog.d(TAG, "createMediaSourceWithSubtitle " + gson.toJson(subtitleList));

        List<SingleSampleMediaSource> singleSampleMediaSourceList = new ArrayList<>();
        for (int i = 0; i < subtitleList.size(); i++) {
            Subtitle subtitle = subtitleList.get(i);
            if (subtitle == null || subtitle.getLanguage() == null || subtitle.getUrl() == null || subtitle.getUrl().isEmpty()) {
                continue;
            }
            DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent, bandwidthMeter2);
            //Text Format Initialization
            Format textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, null, Format.NO_VALUE, Format.NO_VALUE, subtitle.getLanguage(), null, Format.OFFSET_SAMPLE_RELATIVE);
            SingleSampleMediaSource textMediaSource = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(subtitle.getUrl()), textFormat, C.TIME_UNSET);
            singleSampleMediaSourceList.add(textMediaSource);
        }
        MediaSource mediaSourceWithSubtitle = null;
        for (int i = 0; i < singleSampleMediaSourceList.size(); i++) {
            SingleSampleMediaSource singleSampleMediaSource = singleSampleMediaSourceList.get(i);
            if (i == 0) {
                mediaSourceWithSubtitle = new MergingMediaSource(mediaSource, singleSampleMediaSource);
            } else {
                mediaSourceWithSubtitle = new MergingMediaSource(mediaSourceWithSubtitle, singleSampleMediaSource);
            }
        }
        return mediaSourceWithSubtitle;


        //ADD SUBTITLE MANUAL -> WORK PERFECTLY
        /*DefaultBandwidthMeter bandwidthMeter2 = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent, bandwidthMeter2);
        //Text Format Initialization
        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, null, Format.NO_VALUE, Format.NO_VALUE, "ar", null, Format.OFFSET_SAMPLE_RELATIVE);

        String linkSub = "https://dev-static.uiza.io/subtitle_56a4f990-17e6-473c-8434-ef6c7e40bba1_vi_1522812445904.vtt";
        //String linkSub = "https://s3-ap-southeast-1.amazonaws.com/58aa3a0eb555420a945a27b47ce9ef2f-data/static/type_caption__entityId_81__language_en.vtt";
        //Arabic Subtitles
        SingleSampleMediaSource textMediaSourceAr = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);
        //English Subtitles
        SingleSampleMediaSource textMediaSourceEn = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);
        //French Subtitles
        SingleSampleMediaSource textMediaSourceFr = new SingleSampleMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(linkSub), textFormat, C.TIME_UNSET);

        //Final MediaSource
        MediaSource mediaSourceWithSubtitle = new MergingMediaSource(mediaSource, textMediaSourceAr, textMediaSourceEn, textMediaSourceFr);
        //player.prepare(mediaSource);
        //player.setPlayWhenReady(true);

        return mediaSourceWithSubtitle;*/
    }

    private MediaSource createMediaSourceWithAds(MediaSource mediaSource) {
        if (adsLoader == null) {
            return mediaSource;
        }
        MediaSource mediaSourceWithAds = new AdsMediaSource(
                mediaSource,
                this,
                adsLoader,
                playerView.getOverlayFrameLayout(),
                null,
                null);
        return mediaSourceWithAds;
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

            trackSelectionHelper = null;
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;

            handler = null;
            runnable = null;

            trackSelectionHelper = null;
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

    private void hideProgress() {
        if (progressBar != null && progressBar.getVisibility() != View.GONE) {
            progressBar.setVisibility(View.GONE);
            LLog.d(TAG, "hideProgress !null");
        } else {
            LLog.d(TAG, "hideProgress null");
        }
    }

    private void showProgress() {
        if (progressBar != null && progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
            LLog.d(TAG, "showProgress !null");
        } else {
            LLog.d(TAG, "showProgress null");
        }
    }

    public class PlayerEventListener implements Player.EventListener {
        private final String TAG = Constants.LOITP;

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            LLog.d(TAG, "onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            LLog.d(TAG, "onTracksChanged");
            if (debugCallback != null) {
                debugCallback.onUpdateButtonVisibilities();
            }
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            LLog.d(TAG, "onLoadingChanged isLoading " + isLoading);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            LLog.d(TAG, "onPlayerStateChanged playWhenReady: " + playWhenReady);
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    showProgress();
                    break;
                case Player.STATE_ENDED:
                    hideProgress();
                    break;
                case Player.STATE_IDLE:
                    showProgress();
                    break;
                case Player.STATE_READY:
                    hideProgress();
                    break;
            }
            if (debugCallback != null) {
                debugCallback.onUpdateButtonVisibilities();
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            LLog.d(TAG, "onRepeatModeChanged repeatMode: " + repeatMode);
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            LLog.d(TAG, "onShuffleModeEnabledChanged shuffleModeEnabled: " + shuffleModeEnabled);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            LLog.d(TAG, "onPlayerError " + error.toString());
            if (debugCallback != null) {
                debugCallback.onUpdateButtonVisibilities();
            }
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            LLog.d(TAG, "onPositionDiscontinuity");
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            LLog.d(TAG, "onPlaybackParametersChanged");
        }

        @Override
        public void onSeekProcessed() {
            LLog.d(TAG, "onSeekProcessed");
        }
    }

    public class AudioEventListener implements AudioRendererEventListener {
        private final String TAG = Constants.LOITP;

        @Override
        public void onAudioEnabled(DecoderCounters counters) {
            LLog.d(TAG, "onAudioEnabled");
        }

        @Override
        public void onAudioSessionId(int audioSessionId) {
            LLog.d(TAG, "onAudioSessionId audioSessionId: " + audioSessionId);
        }

        @Override
        public void onAudioDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            LLog.d(TAG, "onAudioDecoderInitialized");
        }

        @Override
        public void onAudioInputFormatChanged(Format format) {
            LLog.d(TAG, "onAudioInputFormatChanged");
        }

        @Override
        public void onAudioSinkUnderrun(int bufferSize, long bufferSizeMs, long elapsedSinceLastFeedMs) {
            LLog.d(TAG, "onAudioSinkUnderrun");
        }

        @Override
        public void onAudioDisabled(DecoderCounters counters) {
            LLog.d(TAG, "onAudioDisabled");
        }
    }

    public class VideoEventListener implements VideoRendererEventListener {
        private final String TAG = Constants.LOITP;

        @Override
        public void onVideoEnabled(DecoderCounters counters) {
            LLog.d(TAG, "onVideoEnabled");
        }

        @Override
        public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {
            LLog.d(TAG, "onVideoDecoderInitialized decoderName: " + decoderName + ", initializedTimestampMs " + initializedTimestampMs + ", initializationDurationMs " + initializationDurationMs);
        }

        @Override
        public void onVideoInputFormatChanged(Format format) {
            LLog.d(TAG, "onVideoInputFormatChanged");
        }

        @Override
        public void onDroppedFrames(int count, long elapsedMs) {
            LLog.d(TAG, "onDroppedFrames count " + count + ",elapsedMs " + elapsedMs);
        }

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            LLog.d(TAG, "onVideoSizeChanged " + width + "x" + height + ", pixelWidthHeightRatio " + pixelWidthHeightRatio);
        }

        @Override
        public void onRenderedFirstFrame(Surface surface) {
            LLog.d(TAG, "onRenderedFirstFrame");
        }

        @Override
        public void onVideoDisabled(DecoderCounters counters) {
            LLog.d(TAG, "onVideoDisabled");
        }
    }

    public class MetadataOutputListener implements MetadataOutput {
        private final String TAG = Constants.LOITP;

        @Override
        public void onMetadata(Metadata metadata) {
            LLog.d(TAG, "onMetadata " + metadata.length());
        }
    }

    public class TextOutputListener implements TextOutput {
        private final String TAG = Constants.LOITP;

        @Override
        public void onCues(List<Cue> cues) {
            LLog.d(TAG, "onCues " + cues.size());
        }
    }

    private float currentVolume;

    public void toggleVolumeMute(ImageButton exoVolume) {
        if (player == null || exoVolume == null) {
            return;
        }
        if (player.getVolume() == 0f) {
            //off -> on
            setVolume(currentVolume);
            exoVolume.setImageResource(R.drawable.ic_volume_up_black_48dp);
        } else {
            //on -> off
            currentVolume = player.getVolume();
            setVolume(0f);
            exoVolume.setImageResource(R.drawable.ic_volume_off_black_48dp);
        }
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }

    public void setVolume(float volume) {
        if (player != null) {
            //LLog.d(TAG, "volume " + volume);
            player.setVolume(volume);
            //LLog.d(TAG, "-> setVolume done: " + player.getVolume());
        }
    }

    public float getVolume() {
        if (player != null) {
            return player.getVolume();
        }
        return 0;
    }

    public void seekTo(long positionMs) {
        if (player != null) {
            player.seekTo(positionMs);
        }
    }

    public interface DebugCallback {
        public void onUpdateButtonVisibilities();
    }

    private DebugCallback debugCallback;

    public void setDebugCallback(DebugCallback debugCallback) {
        this.debugCallback = debugCallback;
    }
}
