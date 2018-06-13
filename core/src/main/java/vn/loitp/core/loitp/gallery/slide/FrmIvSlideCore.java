package vn.loitp.core.loitp.gallery.slide;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

public class FrmIvSlideCore extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivBkg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_photo_slide_iv_core, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.d(TAG, "onViewCreated bundle == null -> return");
            return;
        }
        int position = bundle.getInt(Constants.SK_PHOTO_PISITION);
        Photo photo = PhotosDataCore.getInstance().getPhoto(position);
        //LLog.d(TAG, position + " -> getUrlS " + photo.getUrlS());

        ivBkg = (ImageView) view.findViewById(R.id.iv_bkg);
        updateBkg(PhotosDataCore.getInstance().getPhoto(position).getUrlS());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, Color.WHITE);
        LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE);
        int sizeW = LScreenUtil.getScreenWidth();
        int sizeH = sizeW * photo.getHeightO() / photo.getWidthO();

        //LLog.d(TAG, ">>>getUrlO " + photo.getUrlO());
        //LLog.d(TAG, ">>>getFlickrLink640 " + photo.getFlickrLink640());
        //LLog.d(TAG, ">>>getFlickrLink1024 " + photo.getFlickrLink1024());

        LImageUtil.load(getContext(), photo.getFlickrLink1024(), imageView, progressBar, sizeW, sizeH);
    }

    public void updateBkg(String bkg) {
        LImageUtil.load(getActivity(), bkg, ivBkg, 8, 6);
    }
}
