package com.core.loitp.gallery.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.photos.GalleryCorePhotosActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.core.R;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;
import vn.loitp.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class GalleryCoreAlbumActivity extends BaseFontActivity {
    private AlbumAdapter albumAdapter;
    private List<Photoset> photosetList = new ArrayList<>();
    private ArrayList<String> removeAlbumList;
    private int bkgRootView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        setTransparentStatusNavigationBar();
        removeAlbumList = getIntent().getStringArrayListExtra(Constants.getKEY_REMOVE_ALBUM_FLICKR_LIST());

        avLoadingIndicatorView = findViewById(R.id.av);

        final String admobBannerUnitId = getIntent().getStringExtra(Constants.getAD_UNIT_ID_BANNER());
        LLog.d(TAG, "admobBannerUnitId " + admobBannerUnitId);
        final LinearLayout lnAdview = findViewById(R.id.ln_adview);
        if (admobBannerUnitId == null || admobBannerUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(admobBannerUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4);
        }

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        bkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), Constants.getNOT_FOUND());
        LLog.d(TAG, "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        final SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter(activity, photosetList, new AlbumAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                final Intent intent = new Intent(activity, GalleryCorePhotosActivity.class);
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), bkgRootView);
                intent.putExtra(Constants.getSK_PHOTOSET_ID(), photosetList.get(pos).getId());
                intent.putExtra(Constants.getSK_PHOTOSET_SIZE(), photosetList.get(pos).getPhotos());
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }

            @Override
            public void onLongClick(int pos) {
                /*photosetList.remove(pos);
                albumAdapter.notifyItemRemoved(pos);
                albumAdapter.notifyItemRangeChanged(pos, photosetList.size());*/
            }
        });
        //recyclerView.setAdapter(albumAdapter);
        final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(albumAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        photosetsGetList();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_core_album;
    }

    private void photosetsGetList() {
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        final int page = 1;
        final int perPage = 500;
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;


        compositeDisposable.add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetlist -> {
                    //LLog.d(TAG, "onSuccess " + new Gson().toJson(wrapperPhotosetGetlist));
                    photosetList.addAll(wrapperPhotosetGetlist.getPhotosets().getPhotoset());

                    /*String x = "";
                    for (int i = 0; i < photosetList.size(); i++) {
                        x += photosetList.get(i).getTitle().getContent() + " - " + photosetList.get(i).getId() + "\n";
                    }
                    LLog.d(TAG, "" + x);*/

                    //LLog.d(TAG, "orginal size: " + photosetList.size());
                    //LLog.d(TAG, "removeAlbumList size: " + removeAlbumList.size());
                    for (int i = removeAlbumList.size() - 1; i >= 0; i--) {
                        for (int j = photosetList.size() - 1; j >= 0; j--) {
                            if (removeAlbumList.get(i).equals(photosetList.get(j).getId())) {
                                photosetList.remove(j);
                            }
                        }
                    }
                    //LLog.d(TAG, "after size: " + photosetList.size());
                    Collections.sort(photosetList, new Comparator<Photoset>() {
                        @Override
                        public int compare(Photoset o1, Photoset o2) {
                            return Long.valueOf(o2.getDateUpdate()).compareTo(Long.valueOf(o1.getDateUpdate()));
                        }
                    });
                    updateAllViews();
                    avLoadingIndicatorView.smoothToHide();
                }, throwable -> {
                    handleException(throwable);
                    avLoadingIndicatorView.smoothToHide();
                }));
    }

    private void updateAllViews() {
        if (albumAdapter != null) {
            albumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
