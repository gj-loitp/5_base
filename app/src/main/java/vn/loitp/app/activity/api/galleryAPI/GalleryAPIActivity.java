package vn.loitp.app.activity.api.galleryAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.loitp.app.activity.customviews.progress_loadingview.avloading_indicator_view._lib.avi.AVLoadingIndicatorView;
import vn.loitp.app.activity.demo.gallery.GalleryDemoSplashActivity;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.flickr.FlickrConst;
import vn.loitp.flickr.model.photosetgetlist.Photoset;
import vn.loitp.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.flickr.service.FlickrService;
import vn.loitp.livestar.R;
import vn.loitp.restclient.RestClient;

public class GalleryAPIActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private TextView tv;
    private Button bt1;
    private Button bt2;

    private WrapperPhotosetGetlist mWrapperPhotosetGetlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.smoothToHide();
        tv = (TextView) findViewById(R.id.tv);
        bt1 = (Button) findViewById(R.id.bt_1);
        bt2 = (Button) findViewById(R.id.bt_2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photosetsGetList();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelectPhotoset();
            }
        });
        findViewById(R.id.bt_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GalleryDemoSplashActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
        return R.layout.activity_gallery_api;
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
                mWrapperPhotosetGetlist = wrapperPhotosetGetlist;
                LUIUtil.printBeautyJson(wrapperPhotosetGetlist, tv);
                avi.smoothToHide();
                bt2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }

    private void showDialogSelectPhotoset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose:");

        List<Photoset> photosetList = mWrapperPhotosetGetlist.getPhotosets().getPhotoset();
        String[] items = new String[photosetList.size()];
        for (int i = 0; i < photosetList.size(); i++) {
            //LLog.d(TAG, i + " : " + photosetList.get(i).getTitle().getContent());
            items[i] = photosetList.get(i).getTitle().getContent();
        }

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                LLog.d(TAG, "onClick " + position);
                photosetsGetPhotos(photosetList.get(position).getId());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void photosetsGetPhotos(String photosetID) {
        tv.setText("");
        avi.smoothToShow();
        FlickrService service = RestClient.createService(FlickrService.class);
        String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        String apiKey = FlickrConst.API_KEY;
        String userID = FlickrConst.USER_KEY;
        int page = 1;
        int perPage = 500;
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, perPage, page, format, nojsoncallback), new ApiSubscriber<Object>() {
            @Override
            public void onSuccess(Object wrapperPhotosetGetlist) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(wrapperPhotosetGetlist, tv);
                avi.smoothToHide();
                bt2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }
}
