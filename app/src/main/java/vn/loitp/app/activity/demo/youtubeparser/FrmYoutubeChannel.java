package vn.loitp.app.activity.demo.youtubeparser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.IOException;

import loitp.basemaster.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.function.youtubeparser.models.utubechannel.UtubeChannel;

public class FrmYoutubeChannel extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_youtube_channel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListChannel();
    }

    private void getListChannel() {
        final String LINK_GG_DRIVE_CHECK_READY = "https://drive.google.com/uc?export=download&id=1gLzUZcd3GjV3M5Aw2ynx-7hNpg-gAuUB";
        Request request = new Request.Builder().url(LINK_GG_DRIVE_CHECK_READY).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LLog.d(TAG, "onFailure " + e.toString());
                getListYoutubeChannelFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //LLog.d(TAG, "onResponse " + LSApplication.getInstance().getGson().toJson(response.body().string()));
                    UtubeChannel utubeChannel = LSApplication.getInstance().getGson().fromJson(response.body().string(), UtubeChannel.class);
                    LLog.d(TAG, "onResponse " + LSApplication.getInstance().getGson().toJson(utubeChannel));
                } else {
                    LLog.d(TAG, "onResponse !isSuccessful: " + response.toString());
                    getListYoutubeChannelFailed();
                }
            }
        });
    }

    private void getListYoutubeChannelFailed() {
        //TODO
    }
}
