package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.model.photosetgetphotos.WrapperPhotosetGetPhotos;
import vn.loitp.restapi.flickr.service.FlickrService;
import loitp.basemaster.R;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

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
                            LActivityUtil.tranIn(activity);                        }
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
