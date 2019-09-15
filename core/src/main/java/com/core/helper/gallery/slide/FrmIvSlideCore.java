package com.core.helper.gallery.slide;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.R;
import com.core.common.Constants;
import com.core.helper.gallery.photos.PhotosDataCore;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.restapi.flickr.model.photosetgetphotos.Photo;
import com.views.progressloadingview.avl.LAVLoadingIndicatorView;

import java.io.File;

public class FrmIvSlideCore extends Fragment {
    private final String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.l_item_photo_slide_iv_core, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.d(TAG, "onViewCreated bundle == null -> return");
            return;
        }
        int position = bundle.getInt(Constants.INSTANCE.getSK_PHOTO_PISITION());
        final Photo photo = PhotosDataCore.getInstance().getPhoto(position);

        final LAVLoadingIndicatorView LAVLoadingIndicatorView = view.findViewById(R.id.avi);
        final BigImageView bigImageView = view.findViewById(R.id.biv);
        final TextView tvProgress = view.findViewById(R.id.tv_progress);
        LUIUtil.INSTANCE.setTextShadow(tvProgress);

        bigImageView.setImageViewFactory(new GlideImageViewFactory());
        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {
            }

            @Override
            public void onCacheMiss(int imageType, File image) {
            }

            @Override
            public void onStart() {
                if (LAVLoadingIndicatorView != null) {
                    LAVLoadingIndicatorView.smoothToShow();
                }
                tvProgress.setText("0%");
            }

            @Override
            public void onProgress(int progress) {
                //LLog.d(TAG, "onProgress " + progress);
                tvProgress.setVisibility(View.VISIBLE);
                tvProgress.setText(progress + "%");
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onSuccess(File image) {
                LLog.d(TAG, "onSuccess");
                /*SubsamplingScaleImageView ssiv = bigImageView.getSSIV();
                if (ssiv != null) {
                    ssiv.setZoomEnabled(true);
                }*/
                if (LAVLoadingIndicatorView != null) {
                    LAVLoadingIndicatorView.smoothToHide();
                }
                tvProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFail(Exception error) {
            }
        });
        bigImageView.showImage(Uri.parse(photo.getUrlS()), Uri.parse(photo.getUrlO()));
        bigImageView.setOnClickListener(view1 -> {
            if (getActivity() == null) {
                return;
            }
            //LDeviceUtil.vibrate(getActivity());
            ((GalleryCoreSlideActivity) getActivity()).toggleDisplayRlControl();
        });
    }
}
