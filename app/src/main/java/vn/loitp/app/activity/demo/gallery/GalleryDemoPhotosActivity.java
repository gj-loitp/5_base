package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.PlaceHolderView;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.flickr.FlickrConst;
import vn.loitp.flickr.model.photosetgetphotos.Photo;
import vn.loitp.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.flickr.service.FlickrService;
import vn.loitp.livestar.R;
import vn.loitp.restclient.RestClient;

public class GalleryDemoPhotosActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;
    private TextView tvTitle;

    private int currentPage = 0;
    private int totalPage = 1;

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
        String photosetID = getIntent().getStringExtra("photosetID");
        photosetsGetPhotos(photosetID);

        mGalleryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_demo_photos;
    }

    private void photosetsGetPhotos(String photosetID) {
        if (isLoading) {
            LLog.d(TAG, "isLoading true -> return");
            return;
        }
        LLog.d(TAG, "is calling photosetsGetPhotos");
        isLoading = true;
        avi.smoothToShow();
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        currentPage++;
        if (currentPage > totalPage) {
            LLog.d(TAG, "currentPage > totalPage -> return");
            currentPage = totalPage;
            avi.smoothToHide();
            return;
        }
        int perPage = 100;
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, perPage, currentPage, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetPhotos>() {
            @Override
            public void onSuccess(WrapperPhotosetGetPhotos wrapperPhotosetGetPhotos) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(wrapperPhotosetGetPhotos));

                totalPage = wrapperPhotosetGetPhotos.getPhotoset().getPages();
                LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                tvTitle.setText(s);

                List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                PhotosData.getInstance().addPhoto(photoList);
                for (int i = 0; i < photoList.size(); i++) {
                    mGalleryView.addView(new PhotosItem(activity, photoList.get(i), i, new PhotosItem.Callback() {
                        @Override
                        public void onClick(Photo photo, int position) {
                            //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                            Intent intent = new Intent(activity, GalleryDemoSlideActivity.class);
                            intent.putExtra("photoID", photo.getId());
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(activity);
                        }
                    }));
                }
                avi.smoothToHide();
                isLoading = false;
            }

            @Override
            public void onFail(Throwable e) {
                LLog.d(TAG, "onFail " + e.toString());
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
