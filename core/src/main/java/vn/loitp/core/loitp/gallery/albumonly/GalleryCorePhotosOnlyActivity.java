package vn.loitp.core.loitp.gallery.albumonly;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.task.AsyncTaskDownloadImage;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;

public class GalleryCorePhotosOnlyActivity extends BaseFontActivity {
    private ProgressBar progressBar;
    private TextView tvTitle;

    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 100;

    private boolean isLoading;
    private PhotosOnlyAdapter photosOnlyAdapter;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.init(getString(R.string.flickr_URL));
        setTransparentStatusNavigationBar();
        PhotosDataCore.getInstance().clearData();

        String adUnitId = getIntent().getStringExtra(Constants.AD_UNIT_ID_BANNER);
        LLog.d(TAG, "adUnitId " + adUnitId);
        LinearLayout lnAdview = (LinearLayout) findViewById(R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 3);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle, Color.WHITE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(activity, R.color.White));

        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        final String photosetID = getIntent().getStringExtra(Constants.SK_PHOTOSET_ID);
        final String photosSize = getIntent().getStringExtra(Constants.SK_PHOTOSET_SIZE);
        LLog.d(TAG, "photosetID " + photosetID);
        LLog.d(TAG, "photosSize " + photosSize);

        int totalPhotos = 0;
        try {
            totalPhotos = Integer.parseInt(photosSize);
        } catch (Exception e) {
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        if (totalPhotos == 0) {
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        if (totalPhotos % PER_PAGE_SIZE == 0) {
            totalPage = totalPhotos / PER_PAGE_SIZE;
        } else {
            totalPage = totalPhotos / PER_PAGE_SIZE + 1;
        }

        currentPage = totalPage;
        //LLog.d(TAG, "total page " + totalPage);
        //LLog.d(TAG, "currentPage " + currentPage);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);
        photosOnlyAdapter = new PhotosOnlyAdapter(activity, new PhotosOnlyAdapter.Callback() {
            @Override
            public void onClick(Photo photo, int pos) {
                //do nothing

                //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                /*Intent intent = new Intent(activity, GalleryCoreSlideActivity.class);
                intent.putExtra(Constants.SK_PHOTO_ID, photo.getId());
                startActivity(intent);
                LActivityUtil.tranIn(activity);*/
            }

            @Override
            public void onLongClick(Photo photo, int pos) {
                ////do nothing
                //LSocialUtil.share(activity, photo.getUrlO());
            }

            @Override
            public void onClickDownload(Photo photo, int pos) {
                //LLog.d(TAG, "onClick " + PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO());
                new AsyncTaskDownloadImage(getApplicationContext(), photo.getUrlO()).execute();
            }

            @Override
            public void onClickShare(Photo photo, int pos) {
                LSocialUtil.share(activity, photo.getUrlO());
            }

            @Override
            public void onClickReport(Photo photo, int pos) {
                LSocialUtil.sendEmail(activity);
            }

            @Override
            public void onClickCmt(Photo photo, int pos) {
                LSocialUtil.openFacebookComment(activity, photo.getUrlO());
            }
        });
        recyclerView.setAdapter(photosOnlyAdapter);
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(photosOnlyAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);*/

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        photosetsGetPhotos(photosetID);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        LLog.d(TAG, "last item");
                        photosetsGetPhotos(photosetID);
                    }
                }
            }
        });
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
        return R.layout.activity_gallery_core_photos_only;
    }

    private void photosetsGetPhotos(String photosetID) {
        if (isLoading) {
            LLog.d(TAG, "isLoading true -> return");
            return;
        }
        LLog.d(TAG, "is calling photosetsGetPhotos " + currentPage + "/" + totalPage);
        isLoading = true;
        LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE);
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        if (currentPage <= 0) {
            LLog.d(TAG, "currentPage <= 0 -> return");
            currentPage = 0;
            LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
            return;
        }
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetPhotos>() {
            @Override
            public void onSuccess(WrapperPhotosetGetPhotos wrapperPhotosetGetPhotos) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(wrapperPhotosetGetPhotos));

                //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                tvTitle.setText(s);
                List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                PhotosDataCore.getInstance().addPhoto(photoList);
                updateAllViews();

                LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                isLoading = false;
                currentPage--;
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "onFail " + e.toString());
                handleException(e);
                LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                isLoading = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //PhotosDataCore.getInstance().clearData();
        super.onBackPressed();
    }

    private void updateAllViews() {
        if (photosOnlyAdapter != null) {
            photosOnlyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
