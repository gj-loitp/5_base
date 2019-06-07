package vn.loitp.core.loitp.gallery.album;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.GalleryCorePhotosActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class GalleryCoreAlbumActivity extends BaseFontActivity {
    //private Gson gson = new Gson();
    private AlbumAdapter albumAdapter;
    private List<Photoset> photosetList = new ArrayList<>();
    private ArrayList<String> removeAlbumList;
    private int bkgRootView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private AdView adView;
    private String admobBannerUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        setTransparentStatusNavigationBar();
        removeAlbumList = getIntent().getStringArrayListExtra(Constants.INSTANCE.getKEY_REMOVE_ALBUM_FLICKR_LIST());

        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.av);
        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        admobBannerUnitId = getIntent().getStringExtra(Constants.INSTANCE.getAD_UNIT_ID_BANNER());
        LLog.INSTANCE.d(TAG, "admobBannerUnitId " + admobBannerUnitId);
        LinearLayout lnAdview = (LinearLayout) findViewById(R.id.ln_adview);
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        bkgRootView = getIntent().getIntExtra(Constants.INSTANCE.getBKG_ROOT_VIEW(), Constants.INSTANCE.getNOT_FOUND());
        LLog.INSTANCE.d(TAG, "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.INSTANCE.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter(activity, photosetList, new AlbumAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(activity, GalleryCorePhotosActivity.class);
                intent.putExtra(Constants.INSTANCE.getBKG_ROOT_VIEW(), bkgRootView);
                intent.putExtra(Constants.INSTANCE.getSK_PHOTOSET_ID(), photosetList.get(pos).getId());
                intent.putExtra(Constants.INSTANCE.getSK_PHOTOSET_SIZE(), photosetList.get(pos).getPhotos());
                startActivity(intent);
                LActivityUtil.INSTANCE.tranIn(activity);
            }

            @Override
            public void onLongClick(int pos) {
                /*photosetList.remove(pos);
                albumAdapter.notifyItemRemoved(pos);
                albumAdapter.notifyItemRangeChanged(pos, photosetList.size());*/
            }
        });
        //recyclerView.setAdapter(albumAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(albumAdapter);
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
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        int page = 1;
        int perPage = 500;
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetlist>() {
            @Override
            public void onSuccess(WrapperPhotosetGetlist wrapperPhotosetGetlist) {
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
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avLoadingIndicatorView.smoothToHide();
            }
        });
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
