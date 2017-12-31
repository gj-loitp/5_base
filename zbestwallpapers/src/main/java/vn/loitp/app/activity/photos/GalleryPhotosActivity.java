package vn.loitp.app.activity.photos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.slide.GallerySlideActivity;
import vn.loitp.app.activity.view.PhotosItem;
import vn.loitp.app.common.Constants;
import vn.loitp.app.model.PhotosData;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GalleryPhotosActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;
    private TextView tvTitle;

    private int currentPage;
    private int totalPage;
    private int perPage = 100;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        LUIUtil.setPullLikeIOSVertical(mGalleryView);
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
        LLog.d(TAG, "photosetsGetPhotos currentPage " + currentPage);
        if (currentPage <= 0) {
            LLog.d(TAG, "currentPage <=0 -> return");
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
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(activity);
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
}
