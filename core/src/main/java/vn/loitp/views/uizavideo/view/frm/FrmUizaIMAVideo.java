package vn.loitp.views.uizavideo.view.frm;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.model.v2.listallentity.Subtitle;
import vn.loitp.views.LToast;
import vn.loitp.views.seekbar.verticalseekbar.VerticalSeekBar;
import vn.loitp.views.uizavideo.UizaPlayerManager;
import vn.loitp.views.uizavideo.view.floatview.FloatingUizaVideoService;
import vn.loitp.views.uizavideo.view.util.UizaUtil;
import vn.loitp.views.uizavideo.listerner.ProgressCallback;

import static android.app.Activity.RESULT_OK;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmUizaIMAVideo extends BaseFragment implements PreviewView.OnPreviewChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private final String TAG = getClass().getSimpleName();
    private Gson gson = new Gson();//TODO remove
    private PlayerView playerView;
    private UizaPlayerManager uizaPlayerManager;

    //play controller
    private RelativeLayout llMid;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    private ImageButton exoFullscreenIcon;
    private ImageView ivThumbnail;
    private ProgressBar progressBar;
    private TextView tvTitle;
    private ImageButton exoBackScreen;
    private ImageButton exoVolume;
    private ImageButton exoSetting;
    private ImageButton exoCc;
    private ImageButton exoPlaylist;
    private ImageButton exoHearing;
    private ImageButton exoPictureInPicture;
    private VerticalSeekBar seekbarVolume;
    private ImageView ivVolumeSeekbar;
    private VerticalSeekBar seekbarBirghtness;
    private ImageView ivBirghtnessSeekbar;

    private LinearLayout debugRootView;
    private int firstBrightness;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void findViews(View view) {
        llMid = (RelativeLayout) view.findViewById(R.id.ll_mid);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(progressBar.getContext(), R.color.White));
        playerView = view.findViewById(R.id.player_view);
        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = playerView.findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);
        ivThumbnail = (ImageView) playerView.findViewById(R.id.image_view_thumnail);
        exoFullscreenIcon = (ImageButton) playerView.findViewById(R.id.exo_fullscreen_toggle_icon);
        tvTitle = (TextView) playerView.findViewById(R.id.tv_title);
        exoBackScreen = (ImageButton) playerView.findViewById(R.id.exo_back_screen);
        exoVolume = (ImageButton) playerView.findViewById(R.id.exo_volume);
        exoSetting = (ImageButton) playerView.findViewById(R.id.exo_setting);
        exoCc = (ImageButton) playerView.findViewById(R.id.exo_cc);
        exoPlaylist = (ImageButton) playerView.findViewById(R.id.exo_playlist);
        exoHearing = (ImageButton) playerView.findViewById(R.id.exo_hearing);
        exoPictureInPicture = (ImageButton) playerView.findViewById(R.id.exo_picture_in_picture);

        seekbarVolume = (VerticalSeekBar) playerView.findViewById(R.id.seekbar_volume);
        seekbarBirghtness = (VerticalSeekBar) playerView.findViewById(R.id.seekbar_birghtness);
        LUIUtil.setColorSeekBar(seekbarVolume, ContextCompat.getColor(getActivity(), R.color.White));
        LUIUtil.setColorSeekBar(seekbarBirghtness, ContextCompat.getColor(getActivity(), R.color.White));
        ivVolumeSeekbar = (ImageView) playerView.findViewById(R.id.exo_volume_seekbar);
        ivBirghtnessSeekbar = (ImageView) playerView.findViewById(R.id.exo_birghtness_seekbar);

        debugRootView = view.findViewById(R.id.controls_root);
        if (Constants.IS_DEBUG) {
            debugRootView.setVisibility(View.GONE);
        } else {
            debugRootView.setVisibility(View.GONE);
        }

        //onclick
        exoFullscreenIcon.setOnClickListener(this);
        exoBackScreen.setOnClickListener(this);
        exoVolume.setOnClickListener(this);
        exoSetting.setOnClickListener(this);
        exoCc.setOnClickListener(this);
        exoPlaylist.setOnClickListener(this);
        exoHearing.setOnClickListener(this);
        exoPictureInPicture.setOnClickListener(this);

        //seekbar change
        seekbarVolume.setOnSeekBarChangeListener(this);
        seekbarBirghtness.setOnSeekBarChangeListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uiza_ima_video_core_frm, container, false);
        findViews(view);
        UizaUtil.resizeLayout(playerView, llMid);

        initUI();
        return view;
    }

    private List<Subtitle> createDummySubtitle() {
        String json = "[\n" +
                "                {\n" +
                "                    \"id\": \"18414566-c0c8-4a51-9d60-03f825bb64a9\",\n" +
                "                    \"name\": \"\",\n" +
                "                    \"type\": \"subtitle\",\n" +
                "                    \"url\": \"//dev-static.uiza.io/subtitle_56a4f990-17e6-473c-8434-ef6c7e40bba1_en_1522812430080.vtt\",\n" +
                "                    \"mine\": \"vtt\",\n" +
                "                    \"language\": \"en\",\n" +
                "                    \"isDefault\": \"0\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\": \"271787a0-5d23-4a35-a10a-5c43fdcb71a8\",\n" +
                "                    \"name\": \"\",\n" +
                "                    \"type\": \"subtitle\",\n" +
                "                    \"url\": \"//dev-static.uiza.io/subtitle_56a4f990-17e6-473c-8434-ef6c7e40bba1_vi_1522812445904.vtt\",\n" +
                "                    \"mine\": \"vtt\",\n" +
                "                    \"language\": \"vi\",\n" +
                "                    \"isDefault\": \"0\"\n" +
                "                }\n" +
                "            ]";
        Subtitle[] subtitles = gson.fromJson(json, new TypeToken<Subtitle[]>() {
        }.getType());
        LLog.d(TAG, "createDummySubtitle subtitles " + gson.toJson(subtitles));
        List subtitleList = Arrays.asList(subtitles);
        LLog.d(TAG, "createDummySubtitle subtitleList " + gson.toJson(subtitleList));
        return subtitleList;
    }

    public void initData(String linkPlay, String urlIMAAd, String urlThumnailsPreviewSeekbar) {
        List<Subtitle> subtitleList = createDummySubtitle();

        uizaPlayerManager = new UizaPlayerManager(playerView, progressBar, previewTimeBarLayout, ivThumbnail, linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar, subtitleList);
        previewTimeBarLayout.setPreviewLoader(uizaPlayerManager);
        uizaPlayerManager.setProgressCallback(new ProgressCallback() {
            @Override
            public void onAdProgress(float currentMls, float duration, int percent) {
                LLog.d(TAG, "ad progress: " + currentMls + "/" + duration + " -> " + percent + "%");
            }

            @Override
            public void onVideoProgress(float currentMls, float duration, int percent) {
                LLog.d(TAG, "video progress: " + currentMls + "/" + duration + " -> " + percent + "%");
            }
        });
        uizaPlayerManager.setDebugCallback(new UizaPlayerManager.DebugCallback() {
            @Override
            public void onUpdateButtonVisibilities() {
                LLog.d(TAG, "onUpdateButtonVisibilities");
                updateButtonVisibilities();
            }
        });

        //set volume max in first play
        seekbarVolume.setMax(100);
        setProgressSeekbar(seekbarVolume, 100);
        uizaPlayerManager.setVolume(100f);

        //set bightness max in first play
        firstBrightness = LScreenUtil.getCurrentBrightness(getActivity()) * 100 / 255 + 1;
        LLog.d(TAG, "firstBrightness " + firstBrightness);
        seekbarBirghtness.setMax(100);
        setProgressSeekbar(seekbarBirghtness, firstBrightness);
    }

    private void setProgressSeekbar(SeekBar seekbar, int progressSeekbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seekbar.setProgress(progressSeekbar, true);
        } else {
            seekbar.setProgress(progressSeekbar);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //restore first brightness
        LScreenUtil.setBrightness(getActivity(), firstBrightness);
    }

    @Override
    public void onResume() {
        super.onResume();
        uizaPlayerManager.init();
    }

    @Override
    public void onPause() {
        super.onPause();
        uizaPlayerManager.reset();
    }

    @Override
    public void onDestroy() {
        uizaPlayerManager.release();
        super.onDestroy();
    }

    @Override
    public void onStartPreview(PreviewView previewView) {
        LLog.d(TAG, "onStartPreview");
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        LLog.d(TAG, "onStopPreview");
        uizaPlayerManager.resumeVideo();
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {
        LLog.d(TAG, "onPreview progress " + progress);
    }

    @Override
    public void onClick(View v) {
        if (v == exoFullscreenIcon) {
            UizaUtil.setUIFullScreenIcon(getActivity(), exoFullscreenIcon);
            LActivityUtil.toggleScreenOritation(getActivity());
        } else if (v == exoBackScreen) {
            if (isLandscape) {
                exoFullscreenIcon.performClick();
            } else {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        } else if (v == exoVolume) {
            uizaPlayerManager.toggleVolumeMute(exoVolume);
        } else if (v == exoSetting) {
            View view = UizaUtil.getBtVideo(debugRootView);
            if (view != null) {
                UizaUtil.getBtVideo(debugRootView).performClick();
            }
        } else if (v == exoCc) {
            View view = UizaUtil.getBtText(debugRootView);
            if (view != null) {
                UizaUtil.getBtText(debugRootView).performClick();
            }
        } else if (v == exoPlaylist) {
            //TODO
            LToast.show(getActivity(), "Click exoPlaylist");
        } else if (v == exoHearing) {
            View view = UizaUtil.getBtAudio(debugRootView);
            if (view != null) {
                UizaUtil.getBtAudio(debugRootView).performClick();
            }
        } else if (v == exoPictureInPicture) {
            clickPiP();
        } else if (v.getParent() == debugRootView) {
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = uizaPlayerManager.getTrackSelector().getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {
                uizaPlayerManager.getTrackSelectionHelper().showSelectionDialog(getActivity(), ((Button) v).getText(), mappedTrackInfo, (int) v.getTag());
            }
        }
    }

    private boolean isLandscape;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getActivity() != null) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LScreenUtil.hideDefaultControls(getActivity());
                isLandscape = true;
            } else {
                LScreenUtil.showDefaultControls(getActivity());
                isLandscape = false;
            }
        }
        UizaUtil.resizeLayout(playerView, llMid);
    }

    public void initUI() {
        String title = "Dummy Video";

        updateUIPlayController(title);
    }

    private void updateUIPlayController(String title) {
        tvTitle.setText(title);
        LUIUtil.setTextShadow(tvTitle);
    }

    public void updateButtonVisibilities() {
        debugRootView.removeAllViews();
        if (uizaPlayerManager.getPlayer() == null) {
            return;
        }
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = uizaPlayerManager.getTrackSelector().getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }
        for (int i = 0; i < mappedTrackInfo.length; i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                Button button = new Button(context);
                int label;
                switch (uizaPlayerManager.getPlayer().getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.text;
                        break;
                    default:
                        continue;
                }
                button.setText(label);
                button.setTag(i);
                button.setOnClickListener(this);
                debugRootView.addView(button);
            }
        }
    }

    //on seekbar change
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == seekbarVolume) {
            //LLog.d(TAG, "seekbarVolume onProgressChanged " + progress);
            if (progress >= 66) {
                ivVolumeSeekbar.setImageResource(R.drawable.ic_volume_up_black_48dp);
            } else if (progress >= 33) {
                ivVolumeSeekbar.setImageResource(R.drawable.ic_volume_down_black_48dp);
            } else {
                ivVolumeSeekbar.setImageResource(R.drawable.ic_volume_mute_black_48dp);
            }
            LLog.d(TAG, "seekbarVolume onProgressChanged " + progress + " -> " + ((float) progress / 100));
            uizaPlayerManager.setVolume(((float) progress / 100));
        } else if (seekBar == seekbarBirghtness) {
            LLog.d(TAG, "seekbarBirghtness onProgressChanged " + progress);
            if (progress >= 85) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_7_black_48dp);
            } else if (progress >= 71) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_6_black_48dp);
            } else if (progress >= 57) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_5_black_48dp);
            } else if (progress >= 42) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_4_black_48dp);
            } else if (progress >= 28) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_3_black_48dp);
            } else if (progress >= 14) {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_2_black_48dp);
            } else {
                ivBirghtnessSeekbar.setImageResource(R.drawable.ic_brightness_1_black_48dp);
            }
            LScreenUtil.setBrightness(getActivity(), progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        LLog.d(TAG, "onStartTrackingTouch");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LLog.d(TAG, "onStopTrackingTouch");
    }
    //end on seekbar change

    private final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 6969;

    private void clickPiP() {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializePiP();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializePiP();
            } else {
                LToast.show(getActivity(), "Draw over other app permission not available");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initializePiP() {
        if (getActivity() == null) {
            return;
        }
        getActivity().startService(new Intent(getActivity(), FloatingUizaVideoService.class));
        //getActivity().onBackPressed();
    }
}