package vn.loitp.function.youtubeparser.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.function.youtubeparser.models.utubechannel.UItem;
import vn.loitp.function.youtubeparser.models.utubechannel.UtubeChannel;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class FrmYoutubeChannel extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private List<UItem> uItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView tvMsg;
    private UtubeChannelAdapter utubeChannelAdapter;
    private ProgressBar progressBar;
    public static final String KEY_WATCHER_ACTIVITY = "KEY_WATCHER_ACTIVITY";
    private String watcherActivity = null;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_youtube_channel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            watcherActivity = bundle.getString(KEY_WATCHER_ACTIVITY);
        }
        LLog.d(TAG, "watcherActivity " + watcherActivity);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        progressBar = (ProgressBar) view.findViewById(R.id.pb);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        utubeChannelAdapter = new UtubeChannelAdapter(getActivity(), uItemList, new UtubeChannelAdapter.Callback() {
            @Override
            public void onClick(UItem uItem, int position) {
                if (watcherActivity == null) {
                    return;
                }
                try {
                    Class aClass = Class.forName(watcherActivity);
                    Intent intent = new Intent(getActivity(), aClass);
                    intent.putExtra(FrmYoutubeParser.KEY_CHANNEL_ID, uItem.getId());
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    LLog.e(TAG, "ClassNotFoundException " + e.toString());
                }
            }

            @Override
            public void onLongClick(UItem uItem, int position) {
            }

            @Override
            public void onLoadMore() {
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setAdapter(mAdapter);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(utubeChannelAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);
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
            int range = Constants.IS_DEBUG ? 1 : 15;
            if (durationS > range) {
                LLog.d(TAG, "neu durationS >" + range + " phut -> se call gg drive de lay list moi");
            } else {
                LLog.d(TAG, "do durationS <=" + range + " phut nen se lay list cu da luu");
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
                    UtubeChannel utubeChannel = new Gson().fromJson(response.body().string(), UtubeChannel.class);
                    //LLog.d(TAG, "onResponse " + new Gson().toJson(utubeChannel));
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvMsg != null && tvMsg.getVisibility() != View.VISIBLE) {
                    tvMsg.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getListYoutubeChannelSuccess(UtubeChannel utubeChannel) {
        if (getActivity() == null) {
            return;
        }
        //LLog.d(TAG, "getListYoutubeChannelSuccess " + new Gson().toJson(utubeChannel));
        uItemList.addAll(utubeChannel.getList());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tvMsg != null && tvMsg.getVisibility() != View.GONE) {
                    tvMsg.setVisibility(View.GONE);
                }
                utubeChannelAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
