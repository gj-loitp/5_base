package vn.loitp.app.activity.demo.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GalleryDemoPhotosActivity extends BaseFontActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;
    private TextView tvTitle;

    private int currentPage = 0;
    private int totalPage = 1;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle = findViewById(R.id.tv_title);
        avi = findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        LUIUtil.setPullLikeIOSVertical(mGalleryView);
        final String photosetID = getIntent().getStringExtra("photosetID");
        photosetsGetPhotos(photosetID);

        mGalleryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
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
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        currentPage++;
        if (currentPage > totalPage) {
            LLog.d(TAG, "currentPage > totalPage -> return");
            currentPage = totalPage;
            avi.smoothToHide();
            return;
        }
        final int perPage = 100;
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        compositeDisposable.add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, perPage, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetPhotos -> {
                    totalPage = wrapperPhotosetGetPhotos.getPhotoset().getPages();
                    LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);

                    final String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                    tvTitle.setText(s);

                    final List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                    PhotosData.getInstance().addPhoto(photoList);
                    for (int i = 0; i < photoList.size(); i++) {
                        mGalleryView.addView(new PhotosItem(activity, photoList.get(i), i, (photo, position) -> {
                            //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                            final Intent intent = new Intent(activity, GalleryDemoSlideActivity.class);
                            intent.putExtra("photoID", photo.getId());
                            startActivity(intent);
                            LActivityUtil.tranIn(activity);
                        }));
                    }
                    avi.smoothToHide();
                    isLoading = false;
                }, e -> {
                    LLog.d(TAG, "onFail " + e.toString());
                    handleException(e);
                    avi.smoothToHide();
                    isLoading = true;
                }));
    }

    @Override
    public void onBackPressed() {
        PhotosData.getInstance().clearData();
        super.onBackPressed();
    }
}
