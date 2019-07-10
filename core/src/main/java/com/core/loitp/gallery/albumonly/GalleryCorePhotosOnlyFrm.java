package com.core.loitp.gallery.albumonly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFragment;
import com.core.common.Constants;
import com.core.loitp.gallery.photos.PhotosDataCore;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.core.utilities.LSocialUtil;
import com.core.utilities.LUIUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.restapi.flickr.FlickrConst;
import com.restapi.flickr.model.photosetgetlist.Photoset;
import com.restapi.flickr.model.photosetgetphotos.Photo;
import com.restapi.flickr.service.FlickrService;
import com.restapi.restclient.RestClient;
import com.task.AsyncTaskDownloadImage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.core.R;
import vn.loitp.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

public class GalleryCorePhotosOnlyFrm extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView tvTitle;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 100;

    private boolean isLoading;
    private PhotosOnlyAdapter photosOnlyAdapter;

    private String photosetID;
    private int photosSize;
    private FloatingActionButton btPage;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_gallery_core_photos_only;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RestClient.init(getString(R.string.flickr_URL));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        PhotosDataCore.getInstance().clearData();
        tvTitle = view.findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle);
        avLoadingIndicatorView = view.findViewById(R.id.av);
        btPage = view.findViewById(R.id.bt_page);

        photosetID = bundle.getString(Constants.getSK_PHOTOSET_ID());
        if (photosetID == null || photosetID.isEmpty()) {
            handleException(new Exception(getString(R.string.err_unknow)));
            return;
        }
        LLog.d(TAG, "photosetID " + photosetID);
        photosSize = bundle.getInt(Constants.getSK_PHOTOSET_SIZE(), Constants.getNOT_FOUND());
        LLog.d(TAG, "photosSize " + photosSize);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true);
        photosOnlyAdapter = new PhotosOnlyAdapter(getActivity(), new PhotosOnlyAdapter.Callback() {
            @Override
            public void onClick(Photo photo, int pos) {
                //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                /*Intent intent = new Intent(activity, GalleryCoreSlideActivity.class);
                intent.putExtra(Constants.SK_PHOTO_ID, photo.getId());
                startActivity(intent);
                LActivityUtil.tranIn(activity);*/
            }

            @Override
            public void onLongClick(Photo photo, int pos) {
                //LSocialUtil.share(activity, photo.getUrlO());
            }

            @Override
            public void onClickDownload(Photo photo, int pos) {
                //LLog.d(TAG, "onClick " + PhotosDataCore.getInstance().getPhoto(viewPager.getCurrentItem()).getUrlO());
                //TODO change to rx
                new AsyncTaskDownloadImage(getActivity(), photo.getUrlO()).execute();
            }

            @Override
            public void onClickShare(Photo photo, int pos) {
                LSocialUtil.share(getActivity(), photo.getUrlO());
            }

            @Override
            public void onClickReport(Photo photo, int pos) {
                LSocialUtil.sendEmail(getActivity());
            }

            @Override
            public void onClickCmt(Photo photo, int pos) {
                LSocialUtil.openFacebookComment(getActivity(), photo.getUrlO());
            }
        });
        recyclerView.setAdapter(photosOnlyAdapter);
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(photosOnlyAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);*/

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        //LLog.d(TAG, "last item");
                        photosetsGetPhotos(photosetID);
                    }
                }
            }
        });

        btPage.setOnClickListener(v -> {
            //LLog.d(TAG, "onClick " + currentPage + "/" + totalPage);
            showListPage();
        });
    }

    private void showListPage() {
        final int size = totalPage;
        final String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Page " + (totalPage - i);
        }
        LDialogUtil.showDialogList(getActivity(), "Select page", arr, position -> {
            currentPage = totalPage - position;
            LLog.d(TAG, "showDialogList onClick position " + position + ", -> currentPage: " + currentPage);
            PhotosDataCore.getInstance().clearData();
            updateAllViews();
            photosetsGetPhotos(photosetID);
        });
    }

    private void goToHome() {
        if (photosSize == Constants.getNOT_FOUND()) {
            photosetsGetList();
        } else {
            init();
        }
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
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        final int page = 1;
        final int perPage = 500;
        //String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        final String primaryPhotoExtras = "";
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        compositeDisposable.add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetlist -> {
                    LLog.d(TAG, "photosetsGetList onSuccess " + new Gson().toJson(wrapperPhotosetGetlist));
                    for (final Photoset photoset : wrapperPhotosetGetlist.getPhotosets().getPhotoset()) {
                        if (photoset.getId().equals(photosetID)) {
                            photosSize = Integer.parseInt(photoset.getPhotos());
                            init();
                            return;
                        }
                    }
                }, e -> {
                    LLog.e(TAG, "photosetsGetList onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                }));
    }

    private void photosetsGetPhotos(@NonNull final String photosetID) {
        if (isLoading) {
            LLog.d(TAG, "photosetsGetList isLoading true -> return");
            return;
        }
        LLog.d(TAG, "is calling photosetsGetPhotos " + currentPage + "/" + totalPage);
        isLoading = true;
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        if (currentPage <= 0) {
            LLog.d(TAG, "currentPage <= 0 -> return");
            currentPage = 0;
            avLoadingIndicatorView.smoothToHide();
            return;
        }
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        compositeDisposable.add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetPhotos -> {
                    LLog.d(TAG, "photosetsGetPhotos onSuccess " + new Gson().toJson(wrapperPhotosetGetPhotos));
                    //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                    final String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                    tvTitle.setText(s);
                    final  List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                    PhotosDataCore.getInstance().addPhoto(photoList);
                    updateAllViews();

                    avLoadingIndicatorView.smoothToHide();
                    btPage.setVisibility(View.VISIBLE);
                    isLoading = false;
                    currentPage--;
                }, e -> {
                    LLog.e(TAG, "photosetsGetPhotos onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                    isLoading = true;
                }));
    }

    private void updateAllViews() {
        if (photosOnlyAdapter != null) {
            photosOnlyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    private boolean isShowDialogCheck;

    private void checkPermission() {
        isShowDialogCheck = true;
        Dexter.withActivity(getActivity())
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
        final AlertDialog alertDialog = LDialogUtil.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                checkPermission();
            }

            @Override
            public void onClick2() {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        alertDialog.setCancelable(false);
    }

    private void showSettingsDialog() {
        final AlertDialog alertDialog = LDialogUtil.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                isShowDialogCheck = false;
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                final Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onClick2() {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        alertDialog.setCancelable(false);
    }
}
