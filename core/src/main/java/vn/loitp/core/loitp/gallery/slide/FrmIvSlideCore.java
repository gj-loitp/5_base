package vn.loitp.core.loitp.gallery.slide;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

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
            return;
        }
        int position = bundle.getInt(Constants.SK_PHOTO_PISITION);
        Photo photo = PhotosDataCore.getInstance().getPhoto(position);
        LLog.d(TAG, position + " -> " + photo.getUrlO());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        LImageUtil.load(getActivity(), photo.getUrlO(), imageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                LLog.d(TAG, "onLoadFailed");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                LLog.d(TAG, "onResourceReady");
                return false;
            }
        });
    }
}
