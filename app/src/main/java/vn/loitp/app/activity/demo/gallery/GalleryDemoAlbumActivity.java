package vn.loitp.app.activity.demo.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.PlaceHolderView;
import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.flickr.FlickrConst;
import vn.loitp.flickr.model.photosetgetlist.Photoset;
import vn.loitp.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.flickr.service.FlickrService;
import vn.loitp.livestar.R;
import vn.loitp.restclient.RestClient;

public class GalleryDemoAlbumActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = (PlaceHolderView) findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        LUIUtil.setPullLikeIOSVertical(mGalleryView);
        photosetsGetList();
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
        return R.layout.activity_gallery_demo_album;
    }

    private void photosetsGetList() {
        avi.smoothToShow();
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
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                List<Photoset> photosetList = wrapperPhotosetGetlist.getPhotosets().getPhotoset();
                for (int i = 0; i < photosetList.size(); i++) {
                    mGalleryView.addView(new AlbumItem(activity, photosetList.get(i), i, new AlbumItem.Callback() {
                        @Override
                        public void onClick(Photoset photoset, int position) {
                            Intent intent = new Intent(activity, GalleryDemoPhotosActivity.class);
                            intent.putExtra("photosetID", photoset.getId());
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(activity);
                        }
                    }));
                }
                avi.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }
}
