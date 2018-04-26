package vn.loitp.views.uizavideo.view.rl;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.uiza.model.v2.listallentity.Subtitle;
import vn.loitp.views.uizavideo.UizaPlayerManager;
import vn.loitp.views.uizavideo.listerner.ProgressCallback;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class SimpleUizaIMAVideo extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private Gson gson = new Gson();//TODO remove
    private PlayerView playerView;
    private UizaPlayerManager uizaPlayerManager;

    public SimpleUizaIMAVideo(Context context) {
        super(context);
        onCreate();
    }

    public SimpleUizaIMAVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate();
    }

    public SimpleUizaIMAVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate();
    }

    public SimpleUizaIMAVideo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onCreate();
    }

    private void onCreate() {
        inflate(getContext(), R.layout.uiza_simple_ima_video_core_frm, this);
        findViews();
        //UizaUtil.resizeLayout(playerView, llMid);
    }

    private void findViews() {
        playerView = findViewById(R.id.player_view);
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

        uizaPlayerManager = new UizaPlayerManager(playerView, null, null, null, linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar, subtitleList);
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
    }

    public void onDestroy() {
        if (uizaPlayerManager != null) {
            uizaPlayerManager.release();
        }
    }

    public void onResume() {
        if (uizaPlayerManager != null) {
            uizaPlayerManager.init();
        }
    }

    public void onPause() {
        if (uizaPlayerManager != null) {
            uizaPlayerManager.reset();
        }
    }
}