package vn.loitp.app.activity.demo.gallery;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LUIUtil;
import com.restapi.flickr.FlickrConst;
import com.restapi.flickr.model.photosetgetlist.Photoset;
import com.restapi.flickr.service.FlickrService;
import com.restapi.restclient.RestClient;
import com.views.placeholderview.PlaceHolderView;
import com.views.progressloadingview.avl.LAVLoadingIndicatorView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.basemaster.R;

public class GalleryDemoAlbumActivity extends BaseFontActivity {
    private LAVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avi = findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        LUIUtil.INSTANCE.setPullLikeIOSVertical(mGalleryView);
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

        getCompositeDisposable().add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetlist -> {
                    final List<Photoset> photosetList = wrapperPhotosetGetlist.getPhotosets().getPhotoset();
                    for (int i = 0; i < photosetList.size(); i++) {
                        mGalleryView.addView(new AlbumItem(getActivity(), photosetList.get(i), i, new AlbumItem.Callback() {
                            @Override
                            public void onClick(Photoset photoset, int position) {
                                final Intent intent = new Intent(getActivity(), GalleryDemoPhotosActivity.class);
                                intent.putExtra("photosetID", photoset.getId());
                                startActivity(intent);
                                LActivityUtil.tranIn(getActivity());
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
