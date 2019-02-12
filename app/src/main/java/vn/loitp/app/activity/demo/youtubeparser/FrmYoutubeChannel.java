package vn.loitp.app.activity.demo.youtubeparser;

import android.app.AlertDialog;
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
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
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
        long lastTime = LPref.getTimeGetYoutubeChannelListSuccess(getActivity());
        LLog.d(TAG, "lastTime " + lastTime);
        if (lastTime == 0) {
            LLog.d(TAG, "lastTime == 0 -> day la lan dau -> se call gg drive de lay list moi");
        } else {
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - lastTime;
            int durationS = (int) (duration / (60 * 1000));
            if (durationS > 15) {
                LLog.d(TAG, "neu durationS > 15 phut -> se call gg drive de lay list moi");
            } else {
                LLog.d(TAG, "do durationS <=15 phut nen se lay list cu da luu");
                UtubeChannel utubeChannel = LPref.getYoutubeChannelList(getActivity());
                getListYoutubeChannelSuccess(utubeChannel);
                return;
            }
        }
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
                    if (response == null || response.body() == null || getActivity() == null) {
                        getListYoutubeChannelFailed();
                        return;
                    }
                    UtubeChannel utubeChannel = LSApplication.getInstance().getGson().fromJson(response.body().string(), UtubeChannel.class);
                    LLog.d(TAG, "onResponse " + LSApplication.getInstance().getGson().toJson(utubeChannel));
                    if (utubeChannel == null) {
                        getListYoutubeChannelFailed();
                        return;
                    }
                    LPref.setTimeGetYoutubeChannelListSuccess(getActivity(), System.currentTimeMillis());
                    LPref.setYoutubeChannelList(getActivity(), utubeChannel);
                    getListYoutubeChannelSuccess(utubeChannel);
                } else {
                    LLog.d(TAG, "onResponse !isSuccessful: " + response.toString());
                    getListYoutubeChannelFailed();
                }
            }
        });
    }

    private void getListYoutubeChannelFailed() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = LDialogUtil.showDialog1(getActivity(), getString(R.string.err), getString(R.string.err_unknow_en), getString(R.string.ok), new LDialogUtil.Callback1() {
            @Override
            public void onClick1() {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        alertDialog.setCancelable(false);
    }

    private void getListYoutubeChannelSuccess(UtubeChannel utubeChannel) {
        LLog.d(TAG, "getListYoutubeChannelSuccess " + LSApplication.getInstance().getGson().toJson(utubeChannel));
    }
}
