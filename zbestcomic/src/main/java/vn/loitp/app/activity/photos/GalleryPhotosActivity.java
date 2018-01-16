package vn.loitp.app.activity.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.slide.GallerySlideActivity;
import vn.loitp.app.activity.view.PhotosItem;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.PhotosData;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.LAppBarLayout;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GalleryPhotosActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;
    private TextView tvTitle;

    private int currentPage;
    private int totalPage;
    private int perPage = 100;

    private final int REQUEST_CODE = 6969;

    private boolean isLoading;
    private ImageView toolbarImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        AppUtil.loadBackground(activity, toolbarImage);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                    AppUtil.loadBackground(activity, toolbarImage);
                } else {
                    //IDLE appBarLayout not min not max
                    LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });

        tvTitle = (TextView) findViewById(R.id.tv_title);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        //LUIUtil.setPullLikeIOSVertical(mGalleryView);
        String photosetID = getIntent().getStringExtra(Constants.PHOTOSET_ID);

        String strNumberOfPhoto = getIntent().getStringExtra(Constants.NUMBER_OF_PHOTO);
        long numberOfPhoto = 0;
        try {
            numberOfPhoto = Long.parseLong(strNumberOfPhoto);
        } catch (NullPointerException e) {
            showDialogError(e.toString());
        }

        if (numberOfPhoto == 0) {
            showDialogError("numberOfPhoto == 0");
        }

        totalPage = (int) (numberOfPhoto / perPage) + 1;
        currentPage = totalPage;
        LLog.d(TAG, "onCreate: " + currentPage + "/" + totalPage);

        photosetsGetPhotos(photosetID);

        mGalleryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        //LLog.d(TAG, "last item");
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
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_photos;
    }

    private void photosetsGetPhotos(String photosetID) {
        if (isLoading) {
            //LLog.d(TAG, "isLoading true -> return");
            return;
        }
        //LLog.d(TAG, "is calling photosetsGetPhotos");
        isLoading = true;
        avi.smoothToShow();
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        //LLog.d(TAG, "photosetsGetPhotos currentPage " + currentPage);
        if (currentPage <= 0) {
            //LLog.d(TAG, "currentPage <=0 -> return");
            currentPage = 0;
            avi.smoothToHide();
            return;
        }
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, perPage, currentPage, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetPhotos>() {
            @Override
            public void onSuccess(WrapperPhotosetGetPhotos wrapperPhotosetGetPhotos) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(wrapperPhotosetGetPhotos));

                totalPage = wrapperPhotosetGetPhotos.getPhotoset().getPages();
                //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                tvTitle.setText(s);

                List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                PhotosData.getInstance().addPhoto(photoList);
                for (int i = 0; i < photoList.size(); i++) {
                    mGalleryView.addView(new PhotosItem(activity, photoList.get(i), i, new PhotosItem.Callback() {
                        @Override
                        public void onClick(Photo photo, int position) {
                            //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                            Intent intent = new Intent(activity, GallerySlideActivity.class);
                            intent.putExtra(Constants.PHOTO_ID, photo.getId());
                            startActivityForResult(intent, REQUEST_CODE);
                            LActivityUtil.tranIn(activity);
                        }
                    }));
                }
                avi.smoothToHide();
                isLoading = false;
                currentPage--;
            }

            @Override
            public void onFail(Throwable e) {
                //LLog.d(TAG, "onFail " + e.toString());
                handleException(e);
                avi.smoothToHide();
                isLoading = true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        PhotosData.getInstance().clearData();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            int pos = data.getIntExtra(Constants.POSITION_OF_SILIDE, vn.loitp.core.common.Constants.NOT_FOUND);
            if (pos != vn.loitp.core.common.Constants.NOT_FOUND) {
                //LLog.d(TAG, "onActivityResult pos " + pos);
                if (pos >= 0) {
                    mGalleryView.smoothScrollToPosition(pos);
                }
            }
        }
    }
}
