package com.core.loitp.gallery.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.slide.GalleryCoreSlideActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.core.utilities.LSocialUtil;
import com.core.utilities.LUIUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.restapi.flickr.FlickrConst;
import com.restapi.flickr.model.photosetgetphotos.Photo;
import com.restapi.flickr.service.FlickrService;
import com.restapi.restclient.RestClient;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import com.views.recyclerview.animator.animators.SlideInRightAnimator;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GalleryCorePhotosActivity extends BaseFontActivity {
    private TextView tvTitle;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 100;

    private boolean isLoading;
    private PhotosAdapter photosAdapter;
    private FloatingActionButton btPage;
    private String photosetID;

    private int bkgRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowAdWhenExit(false);
        setTransparentStatusNavigationBar();
        PhotosDataCore.getInstance().clearData();
        btPage = findViewById(R.id.bt_page);
        tvTitle = findViewById(R.id.tv_title);
        LUIUtil.INSTANCE.setTextShadow(tvTitle);

        avLoadingIndicatorView = findViewById(R.id.av);
        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        photosetID = getIntent().getStringExtra(Constants.getSK_PHOTOSET_ID());
        final String photosSize = getIntent().getStringExtra(Constants.getSK_PHOTOSET_SIZE());
        LLog.d(getTAG(), "photosetID " + photosetID);
        LLog.d(getTAG(), "photosSize " + photosSize);

        bkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), Constants.getNOT_FOUND());
        LLog.d(getTAG(), "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        int totalPhotos;
        try {
            totalPhotos = Integer.parseInt(photosSize);
        } catch (final Exception e) {
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

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        final SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);

        final int column = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), column));
        recyclerView.setHasFixedSize(true);
        photosAdapter = new PhotosAdapter(getActivity(), column, new PhotosAdapter.Callback() {
            @Override
            public void onClick(final Photo photo, final int pos) {
                //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                final Intent intent = new Intent(getActivity(), GalleryCoreSlideActivity.class);
                intent.putExtra(Constants.INSTANCE.getSK_PHOTO_ID(), photo.getId());
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), bkgRootView);
                startActivity(intent);
                LActivityUtil.tranIn(getActivity());
            }

            @Override
            public void onLongClick(final Photo photo, final int pos) {
                LSocialUtil.INSTANCE.share(getActivity(), photo.getUrlO());
            }
        });
        //recyclerView.setAdapter(albumAdapter);
        final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(photosAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.INSTANCE.setPullLikeIOSVertical(recyclerView);

        photosetsGetPhotos(photosetID);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        currentPage--;
                        LLog.d(getTAG(), "last item ->>> ");
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
        int size = totalPage;
        final String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Page " + (totalPage - i);
        }
        LDialogUtil.INSTANCE.showDialogList(getActivity(), "Select page", arr, position -> {
            currentPage = totalPage - position;
            LLog.d(getTAG(), "showDialogList onClick position " + position + ", -> currentPage: " + currentPage);
            PhotosDataCore.getInstance().clearData();
            updateAllViews();
            photosetsGetPhotos(photosetID);
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
        return R.layout.l_activity_gallery_core_photos;
    }

    private void photosetsGetPhotos(@NonNull final String photosetID) {
        if (isLoading) {
            LLog.d(getTAG(), "isLoading true -> return");
            return;
        }
        LLog.d(getTAG(), "is calling photosetsGetPhotos " + currentPage + "/" + totalPage);
        isLoading = true;
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        if (currentPage <= 0) {
            LLog.d(getTAG(), "currentPage <= 0 -> return");
            currentPage = 0;
            avLoadingIndicatorView.smoothToHide();
            isLoading = false;
            return;
        }
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        getCompositeDisposable().add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetPhotos -> {
                    //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);
                    final String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                    tvTitle.setText(s);
                    final List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                    PhotosDataCore.getInstance().addPhoto(photoList);
                    updateAllViews();
                    avLoadingIndicatorView.smoothToHide();
                    btPage.setVisibility(View.VISIBLE);
                    isLoading = false;
                }, e -> {
                    LLog.e(getTAG(), "onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                    isLoading = true;
                }));
    }

    private void updateAllViews() {
        if (photosAdapter != null) {
            photosAdapter.notifyDataSetChanged();
        }
    }
}
