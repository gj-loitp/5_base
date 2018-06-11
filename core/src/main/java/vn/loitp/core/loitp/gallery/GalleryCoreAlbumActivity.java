package vn.loitp.core.loitp.gallery;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.album.AlbumAdapter;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.parallaxrecyclerviewyayandroid.ParallaxRecyclerView;

public class GalleryCoreAlbumActivity extends BaseFontActivity {
    //private Gson gson = new Gson();
    private AVLoadingIndicatorView avi;
    private AlbumAdapter albumAdapter;
    private List<Photoset> photosetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToHide();

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        photosetsGetList();
        ParallaxRecyclerView recyclerView = (ParallaxRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter(activity, photosetList, new AlbumAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                /*Intent intent = new Intent(activity, GalleryDemoPhotosActivity.class);
                intent.putExtra("photosetID", photoset.getId());
                startActivity(intent);
                LActivityUtil.tranIn(activity);*/
            }

            @Override
            public void onLongClick(int pos) {
                /*FakeData.getInstance().getStringList().remove(pos);
                albumAdapter.notifyItemRemoved(pos);
                albumAdapter.notifyItemRangeChanged(pos, FakeData.getInstance().getStringList().size());*/
            }
        });
        recyclerView.setAdapter(albumAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);
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
        return R.layout.activity_gallery_core_album;
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
                //LLog.d(TAG, "onSuccess " + gson.toJson(wrapperPhotosetGetlist));
                photosetList.addAll(wrapperPhotosetGetlist.getPhotosets().getPhotoset());
                updateAllViews();
                avi.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }

    private void updateAllViews() {
        if (albumAdapter != null) {
            albumAdapter.notifyDataSetChanged();
        }
    }
}
