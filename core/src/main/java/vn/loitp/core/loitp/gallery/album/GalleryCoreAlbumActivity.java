package vn.loitp.core.loitp.gallery.album;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.GalleryCorePhotosActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class GalleryCoreAlbumActivity extends BaseFontActivity {
    //private Gson gson = new Gson();
    private ProgressBar progressBar;
    private AlbumAdapter albumAdapter;
    private List<Photoset> photosetList = new ArrayList<>();
    private ArrayList<String> removeAlbumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTransparentStatusNavigationBar();

        removeAlbumList = getIntent().getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, Color.WHITE);

        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);

        albumAdapter = new AlbumAdapter(activity, photosetList, new AlbumAdapter.Callback() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(activity, GalleryCorePhotosActivity.class);
                intent.putExtra(Constants.SK_PHOTOSET_ID, photosetList.get(pos).getId());
                intent.putExtra(Constants.SK_PHOTOSET_SIZE, photosetList.get(pos).getPhotos());
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }

            @Override
            public void onLongClick(int pos) {
                /*photosetList.remove(pos);
                albumAdapter.notifyItemRemoved(pos);
                albumAdapter.notifyItemRangeChanged(pos, photosetList.size());*/
            }
        });
        //recyclerView.setAdapter(albumAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(albumAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        //LUIUtil.setPullLikeIOSVertical(recyclerView);

        photosetsGetList();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_core_album;
    }

    private void photosetsGetList() {
        LUIUtil.setProgressBarVisibility(progressBar, android.view.View.VISIBLE);
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

                LLog.d(TAG, "orginal size: " + photosetList.size());
                LLog.d(TAG, "removeAlbumList size: " + removeAlbumList.size());
                for (int i = removeAlbumList.size() - 1; i >= 0; i--) {
                    for (int j = photosetList.size() - 1; j >= 0; j--) {
                        if (removeAlbumList.get(i).equals(photosetList.get(j).getId())) {
                            photosetList.remove(j);
                        }
                    }
                }
                LLog.d(TAG, "after size: " + photosetList.size());
                Collections.sort(photosetList, new Comparator<Photoset>() {
                    @Override
                    public int compare(Photoset o1, Photoset o2) {
                        return Long.valueOf(o2.getDateUpdate()).compareTo(Long.valueOf(o1.getDateUpdate()));
                    }
                });
                updateAllViews();
                LUIUtil.setProgressBarVisibility(progressBar, android.view.View.GONE);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                LUIUtil.setProgressBarVisibility(progressBar, android.view.View.GONE);
            }
        });
    }

    private void updateAllViews() {
        if (albumAdapter != null) {
            albumAdapter.notifyDataSetChanged();
        }
    }
}
