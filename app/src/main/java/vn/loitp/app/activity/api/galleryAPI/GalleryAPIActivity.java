package vn.loitp.app.activity.api.galleryAPI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import vn.loitp.app.app.LSApplication;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.rxandroid.ApiSubscriber;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.flickr.FlickrConst;
import vn.loitp.flickr.model.Photoset;
import vn.loitp.flickr.model.WrapperPhotosetGetlist;
import vn.loitp.flickr.service.FlickrService;
import vn.loitp.livestar.R;
import vn.loitp.restclient.RestClient;

public class GalleryAPIActivity extends BaseActivity {
    private AVLoadingIndicatorView avi;
    private TextView tv;
    private Button bt1;
    private Button bt2;

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
        String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS;
        String format = FlickrConst.FORMAT;
        int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;
        subscribe(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback), new ApiSubscriber<WrapperPhotosetGetlist>() {
            @Override
            public void onSuccess(WrapperPhotosetGetlist result) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
                avi.smoothToHide();
                bt2.setVisibility(View.VISIBLE);

                List<Photoset> photosetList = result.getPhotosets().getPhotoset();
                for (int i = 0; i < photosetList.size(); i++) {
                    LLog.d(TAG, i + " : " + photosetList.get(i).getTitle().getContent());
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }
}
