package vn.loitp.core.loitp.gallery.member;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.layout.floatdraglayout.DisplayUtil;

public class GalleryMemberActivity extends BaseFontActivity {
    private ProgressBar progressBar;
    private TextView tvTitle;

    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 50;

    private boolean isLoading;
    private MemberAdapter memberAdapter;
    private AdView adView;

    private String photosetID;
    private int photosSize;
    private int resBkgRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        RestClient.init(getString(R.string.flickr_URL));
        setTransparentStatusNavigationBar();
        PhotosDataCore.getInstance().clearData();

        resBkgRootView = getIntent().getIntExtra(Constants.BKG_ROOT_VIEW, R.color.colorPrimary);
        getRootView().setBackgroundResource(resBkgRootView);

        final String adUnitId = getIntent().getStringExtra(Constants.AD_UNIT_ID_BANNER);
        LLog.d(TAG, "adUnitId " + adUnitId);
        LinearLayout lnAdview = (LinearLayout) findViewById(R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle, Color.WHITE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(activity, R.color.White));

        photosetID = Constants.FLICKR_ID_MEMBERS;
        if (photosetID == null || photosetID.isEmpty()) {
            handleException(new Exception(getString(R.string.err_unknow)));
            return;
        }
        LLog.d(TAG, "photosetID " + photosetID);
        photosSize = getIntent().getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND);
        LLog.d(TAG, "photosSize " + photosSize);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        int numCount = LDeviceUtil.isTablet(activity) ? 3 : 2;

        recyclerView.setLayoutManager(new GridLayoutManager(activity, numCount));
        recyclerView.setHasFixedSize(true);
        memberAdapter = new MemberAdapter(activity, numCount, new MemberAdapter.Callback() {
            @Override
            public void onClick(Photo photo, int pos, ImageView imageView, TextView textView) {
                Intent intent = new Intent(activity, GalleryMemberDetailActivity.class);
                intent.putExtra(GalleryMemberDetailActivity.PHOTO, photo);
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        new Pair<View, String>(imageView, GalleryMemberDetailActivity.IV),
                        new Pair<View, String>(textView, GalleryMemberDetailActivity.TV));
                ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
            }

            @Override
            public void onLongClick(Photo photo, int pos) {
                ////do nothing
                //LSocialUtil.share(activity, photo.getUrlO());
            }
        });
        recyclerView.setAdapter(memberAdapter);
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(memberAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);*/

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

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

    private void goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            photosetsGetList();
        } else {
            init();
        }
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

    private void init() {
        LLog.d(TAG, "init photosSize " + photosSize);

        if (photosSize % PER_PAGE_SIZE == 0) {
            totalPage = photosSize / PER_PAGE_SIZE;
        } else {
            totalPage = photosSize / PER_PAGE_SIZE + 1;
        }

        currentPage = totalPage;
        //LLog.d(TAG, "total page " + totalPage);
        //LLog.d(TAG, "currentPage " + currentPage);

        photosetsGetPhotos(photosetID);
    }

    private void photosetsGetList() {
        LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE);
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        int page = 1;
        int perPage = 500;
        //String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        String primaryPhotoExtras = "";
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetlist>() {
            @Override
            public void onSuccess(WrapperPhotosetGetlist wrapperPhotosetGetlist) {
                LLog.d(TAG, "photosetsGetList onSuccess " + new Gson().toJson(wrapperPhotosetGetlist));
                for (Photoset photoset : wrapperPhotosetGetlist.getPhotosets().getPhotoset()) {
                    if (photoset.getId().equals(photosetID)) {
                        photosSize = Integer.parseInt(photoset.getPhotos());
                        init();
                        return;
                    }
                }
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "photosetsGetList onFail " + e.toString());
                handleException(e);
                LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
            }
        });
    }

    private void photosetsGetPhotos(String photosetID) {
        if (isLoading) {
            LLog.d(TAG, "photosetsGetList isLoading true -> return");
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
                //LLog.d(TAG, "photosetsGetPhotos onSuccess " + new Gson().toJson(wrapperPhotosetGetPhotos));
                LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

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
                LLog.e(TAG, "photosetsGetPhotos onFail " + e.toString());
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
        if (memberAdapter != null) {
            memberAdapter.notifyDataSetChanged();
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
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private boolean isShowDialogCheck;

    private void checkPermission() {
        isShowDialogCheck = true;
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            LLog.d(TAG, "onPermissionsChecked do you work now");
                            goToHome();
                        } else {
                            LLog.d(TAG, "!areAllPermissionsGranted");
                            showShouldAcceptPermission();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            LLog.d(TAG, "onPermissionsChecked permission is denied permenantly, navigate user to app settings");
                            showSettingsDialog();
                        }
                        isShowDialogCheck = true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        LLog.d(TAG, "onPermissionRationaleShouldBeShown");
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showShouldAcceptPermission() {
        AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                checkPermission();
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }

    private void showSettingsDialog() {
        AlertDialog alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                isShowDialogCheck = false;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }
}
