package vn.loitp.app.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.photos.GalleryPhotosActivity;
import vn.loitp.app.activity.view.AlbumItem;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LActivityUtil;
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

public class FrmPhotoManga extends BaseFragment {
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
        mGalleryView = (PlaceHolderView) view.findViewById(R.id.galleryView);
        mGalleryView.getBuilder().setLayoutManager(new GridLayoutManager(getActivity(), 2));
        LUIUtil.setPullLikeIOSVertical(mGalleryView);
        photosetsGetList();
        return view;
    }

    private List<Photoset> photosetList = new ArrayList<>();

    private void photosetsGetList() {
        avi.smoothToShow();
        if (AlbumData.getInstance().getPhotosetListManga() != null && !AlbumData.getInstance().getPhotosetListManga().isEmpty()) {
            photosetList = AlbumData.getInstance().getPhotosetListManga();
            setup();
            return;
        }
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
                photosetList.addAll(wrapperPhotosetGetlist.getPhotosets().getPhotoset());
                setup();
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                avi.smoothToHide();
            }
        });
    }

    private void setup() {
        fillList(photosetList);
        for (int i = 0; i < photosetList.size(); i++) {
            //LLog.d(TAG, photosetList.get(i).getTitle().getContent() + " " + photosetList.get(i).getId());
            mGalleryView.addView(new AlbumItem(getActivity(), photosetList.get(i), i, new AlbumItem.Callback() {
                @Override
                public void onClick(Photoset photoset, int position) {
                    AlbumData.getInstance().setUseStrechImageView(true);
                    Intent intent = new Intent(getActivity(), GalleryPhotosActivity.class);
                    intent.putExtra(Constants.PHOTOSET_ID, photoset.getId());
                    intent.putExtra(Constants.NUMBER_OF_PHOTO, photoset.getPhotos());
                    startActivity(intent);
                    LActivityUtil.tranIn(getActivity());
                }
            }));
        }
        mGalleryView.refresh();
        avi.smoothToHide();
        AlbumData.getInstance().setPhotosetListManga(photosetList);
    }

    private void fillList(List<Photoset> photosetList) {
        List<Photoset> photosets = new ArrayList<>();
        for (int i = 0; i < photosetList.size(); i++) {
            if (photosetList.get(i).getId().contains(Constants.ID_COUPLE_MANGA)
                    || photosetList.get(i).getId().contains(Constants.ID_ANIME)
                    || photosetList.get(i).getId().contains(Constants.ID_MANGA)
                    || photosetList.get(i).getId().contains(Constants.ID_CHIBI)
                    || photosetList.get(i).getId().contains(Constants.ID_FUNNY_MANGA)
                    || photosetList.get(i).getId().contains(Constants.ID_FAMOUS_MANGA)
                    || photosetList.get(i).getId().contains(Constants.ID_AVATAR)
                    || photosetList.get(i).getId().contains(Constants.ID_COSPLAY)
                    ) {
                photosets.add(photosetList.get(i));
                //LLog.d(TAG, "remove " + photosetList.get(i).getTitle().getContent() + " " + photosetList.get(i).getId());
            }
        }
        if (!photosets.isEmpty()) {
            photosetList.clear();
            photosetList.addAll(photosets);
        }
        sort(photosetList);
    }

    private void sort(List<Photoset> photosetList) {
        Collections.sort(photosetList, new Comparator<Photoset>() {
            public int compare(Photoset obj1, Photoset obj2) {
                // ## Ascending order
                return obj1.getTitle().getContent().compareToIgnoreCase(obj2.getTitle().getContent()); // To compare string values
                //return Integer.valueOf(obj1.getWeight()).compareTo(obj2.getWeight()); // To compare integer values

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId); // To compare integer values
            }
        });
    }
}