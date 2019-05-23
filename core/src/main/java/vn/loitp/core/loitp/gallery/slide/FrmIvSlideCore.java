package vn.loitp.core.loitp.gallery.slide;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;

import java.io.File;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class FrmIvSlideCore extends Fragment {
    private final String TAG = getClass().getSimpleName();

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
        int position = bundle.getInt(Constants.INSTANCE.getSK_PHOTO_PISITION());
        Photo photo = PhotosDataCore.getInstance().getPhoto(position);

        final AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        final BigImageView bigImageView = (BigImageView) view.findViewById(R.id.biv);
        final TextView tvProgress = (TextView) view.findViewById(R.id.tv_progress);
        LUIUtil.setTextShadow(tvProgress);

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
                if (avLoadingIndicatorView != null) {
                    avLoadingIndicatorView.smoothToShow();
                }
                if (tvProgress != null) {
                    tvProgress.setText("0%");
                }
            }

            @Override
            public void onProgress(int progress) {
                //LLog.d(TAG, "onProgress " + progress);
                if (tvProgress != null) {
                    tvProgress.setVisibility(View.VISIBLE);
                    tvProgress.setText(progress + "%");
                }
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
                if (avLoadingIndicatorView != null) {
                    avLoadingIndicatorView.smoothToHide();
                }
                if (tvProgress != null) {
                    tvProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(Exception error) {
            }
        });
        bigImageView.showImage(Uri.parse(photo.getUrlS()), Uri.parse(photo.getUrlO()));
        bigImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                //LDeviceUtil.vibrate(getActivity());
                ((GalleryCoreSlideActivity) getActivity()).toggleDisplayRlControl();
            }
        });
    }
}
