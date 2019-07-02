package vn.loitp.app.activity.demo.gallery;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class GalleryDemoAlbumActivity extends BaseFontActivity {
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avi = findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = findViewById(R.id.galleryView);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_demo_album;
    }

    private void photosetsGetList() {
        avi.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        final int page = 1;
        final int perPage = 500;
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        compositeDisposable.add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetlist -> {
                    final List<Photoset> photosetList = wrapperPhotosetGetlist.getPhotosets().getPhotoset();
                    for (int i = 0; i < photosetList.size(); i++) {
                        mGalleryView.addView(new AlbumItem(activity, photosetList.get(i), i, new AlbumItem.Callback() {
                            @Override
                            public void onClick(Photoset photoset, int position) {
                                final Intent intent = new Intent(activity, GalleryDemoPhotosActivity.class);
                                intent.putExtra("photosetID", photoset.getId());
                                startActivity(intent);
                                LActivityUtil.tranIn(activity);
                            }
                        }));
                    }
                    avi.smoothToHide();
                }, e -> {
                    handleException(e);
                    avi.smoothToHide();
                }));
    }
}
