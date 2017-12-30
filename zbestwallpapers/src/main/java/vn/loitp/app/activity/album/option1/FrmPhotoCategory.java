package vn.loitp.app.activity.album.option1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.photos.GalleryPhotosActivity;
import vn.loitp.app.activity.view.AlbumItem;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPhotoCategory extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private AVLoadingIndicatorView avi;
    private PlaceHolderView mGalleryView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_photo_category, container, false);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        avi.smoothToHide();
        mGalleryView = (PlaceHolderView) view.findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(getActivity(), 2));
        LUIUtil.setPullLikeIOSVertical(mGalleryView);
        photosetsGetList();
        return view;
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
                fillList(photosetList);
                for (int i = 0; i < photosetList.size(); i++) {
                    //LLog.d(TAG, photosetList.get(i).getTitle().getContent() + " " + photosetList.get(i).getId());
                    mGalleryView.addView(new AlbumItem(getActivity(), photosetList.get(i), i, new AlbumItem.Callback() {
                        @Override
                        public void onClick(Photoset photoset, int position) {
                            Intent intent = new Intent(getActivity(), GalleryPhotosActivity.class);
                            intent.putExtra("photosetID", photoset.getId());
                            startActivity(intent);
                            LUIUtil.transActivityFadeIn(getActivity());
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

    private void fillList(List<Photoset> photosetList) {
        for (int i = 0; i < photosetList.size(); i++) {
            if (photosetList.get(i).getId().equals(Constants.ID_STT_TRUYENNGAN)
                    || photosetList.get(i).getId().equals(Constants.ID_DEVVUI)
                    || photosetList.get(i).getId().equals(Constants.ID_DOCDAOTHUVI)
                    || photosetList.get(i).getId().equals(Constants.ID_HAIHUOC)
                    || photosetList.get(i).getId().equals(Constants.ID_HAINAO)
                    || photosetList.get(i).getId().equals(Constants.ID_FUNNYMANHINH)
                    || photosetList.get(i).getId().equals(Constants.ID_FUNNYTHETHAO)
                    || photosetList.get(i).getId().equals(Constants.ID_TROLL)
                    || photosetList.get(i).getId().equals(Constants.ID_TRUYENBUA)
                    || photosetList.get(i).getId().equals(Constants.ID_TRUYENNGAN)
                    || photosetList.get(i).getId().equals(Constants.ID_ANHTHEOTEN)
                    || photosetList.get(i).getId().equals(Constants.ID_ANHCHESGK)
                    || photosetList.get(i).getId().equals(Constants.ID_TUOITHODUDOI)
                    || photosetList.get(i).getId().equals(Constants.ID_CUNGHOANGDAOHEHEHORO)
                    || photosetList.get(i).getId().equals(Constants.ID_CUNGHOANGDAOFUNTFACT)
                    || photosetList.get(i).getId().equals(Constants.ID_BANCOBIET)
                    || photosetList.get(i).getId().equals(Constants.ID_QUOTEVIET)
                    || photosetList.get(i).getId().equals(Constants.ID_TRIETLY)
                    || photosetList.get(i).getId().equals(Constants.ID_KIEMHIEP)
                    || photosetList.get(i).getId().equals(Constants.ID_STTTAMTRANG)
                    || photosetList.get(i).getId().equals(Constants.ID_STTDEUCHAT)
                    || photosetList.get(i).getId().equals(Constants.ID_STTBUON)
                    || photosetList.get(i).getId().equals(Constants.ID_STTVUI)
                    || photosetList.get(i).getId().equals(Constants.ID_THO)
                    || photosetList.get(i).getId().equals(Constants.ID_NGAY83)) {
                LLog.d(TAG, "remove " + photosetList.get(i).getTitle().getContent() + " " + photosetList.get(i).getId());
                photosetList.remove(i);
            }
        }
    }
}