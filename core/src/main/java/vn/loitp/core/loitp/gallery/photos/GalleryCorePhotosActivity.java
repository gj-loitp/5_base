package vn.loitp.core.loitp.gallery.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.slide.GalleryCoreSlideActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.FlickrConst;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.restapi.flickr.service.FlickrService;
import vn.loitp.restapi.restclient.RestClient;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class GalleryCorePhotosActivity extends BaseFontActivity {
    private TextView tvTitle;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 100;

    private boolean isLoading;
    private PhotosAdapter photosAdapter;
    private FloatingActionButton btPage;
    private String photosetID;

    private int bkgRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        setTransparentStatusNavigationBar();
        PhotosDataCore.getInstance().clearData();
        btPage = findViewById(R.id.bt_page);
        tvTitle = findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle);

        avLoadingIndicatorView = findViewById(R.id.av);
        //ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        //LImageUtil.load(activity, Constants.URL_IMG_2, ivBkg);

        photosetID = getIntent().getStringExtra(Constants.getSK_PHOTOSET_ID());
        final String photosSize = getIntent().getStringExtra(Constants.getSK_PHOTOSET_SIZE());
        LLog.d(TAG, "photosetID " + photosetID);
        LLog.d(TAG, "photosSize " + photosSize);

        bkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), Constants.getNOT_FOUND());
        LLog.d(TAG, "bkgRootView " + bkgRootView);
        if (bkgRootView == Constants.getNOT_FOUND()) {
            getRootView().setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        } else {
            getRootView().setBackgroundResource(bkgRootView);
        }

        int totalPhotos;
        try {
            totalPhotos = Integer.parseInt(photosSize);
        } catch (final Exception e) {
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        if (totalPhotos == 0) {
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        if (totalPhotos % PER_PAGE_SIZE == 0) {
            totalPage = totalPhotos / PER_PAGE_SIZE;
        } else {
            totalPage = totalPhotos / PER_PAGE_SIZE + 1;
        }

        currentPage = totalPage;
        //LLog.d(TAG, "total page " + totalPage);
        //LLog.d(TAG, "currentPage " + currentPage);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        final SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);

        final int column = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(activity, column));
        recyclerView.setHasFixedSize(true);
        photosAdapter = new PhotosAdapter(activity, column, new PhotosAdapter.Callback() {
            @Override
            public void onClick(final Photo photo, final int pos) {
                //LLog.d(TAG, "onClick " + photo.getWidthO() + "x" + photo.getHeightO());
                final Intent intent = new Intent(activity, GalleryCoreSlideActivity.class);
                intent.putExtra(Constants.INSTANCE.getSK_PHOTO_ID(), photo.getId());
                intent.putExtra(Constants.getBKG_ROOT_VIEW(), bkgRootView);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }

            @Override
            public void onLongClick(final Photo photo, final int pos) {
                LSocialUtil.share(activity, photo.getUrlO());
            }
        });
        //recyclerView.setAdapter(albumAdapter);
        final ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(photosAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        photosetsGetPhotos(photosetID);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        currentPage--;
                        LLog.d(TAG, "last item ->>> ");
                        photosetsGetPhotos(photosetID);
                    }
                }
            }
        });

        btPage.setOnClickListener(v -> {
            //LLog.d(TAG, "onClick " + currentPage + "/" + totalPage);
            showListPage();
        });
    }

    private void showListPage() {
        int size = totalPage;
        final String arr[] = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Page " + (totalPage - i);
        }
        LDialogUtil.showDialogList(activity, "Select page", arr, position -> {
            currentPage = totalPage - position;
            LLog.d(TAG, "showDialogList onClick position " + position + ", -> currentPage: " + currentPage);
            PhotosDataCore.getInstance().clearData();
            updateAllViews();
            photosetsGetPhotos(photosetID);
        });
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
        return R.layout.activity_gallery_core_photos;
    }

    private void photosetsGetPhotos(@NonNull final String photosetID) {
        if (isLoading) {
            LLog.d(TAG, "isLoading true -> return");
            return;
        }
        LLog.d(TAG, "is calling photosetsGetPhotos " + currentPage + "/" + totalPage);
        isLoading = true;
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        if (currentPage <= 0) {
            LLog.d(TAG, "currentPage <= 0 -> return");
            currentPage = 0;
            avLoadingIndicatorView.smoothToHide();
            isLoading = false;
            return;
        }
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        compositeDisposable.add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetPhotos -> {
                    //LLog.d(TAG, "photosetsGetPhotos " + currentPage + "/" + totalPage);
                    final String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                    tvTitle.setText(s);
                    final List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                    PhotosDataCore.getInstance().addPhoto(photoList);
                    updateAllViews();
                    avLoadingIndicatorView.smoothToHide();
                    btPage.setVisibility(View.VISIBLE);
                    isLoading = false;
                }, e -> {
                    LLog.e(TAG, "onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                    isLoading = true;
                }));
    }

    private void updateAllViews() {
        if (photosAdapter != null) {
            photosAdapter.notifyDataSetChanged();
        }
    }
}
