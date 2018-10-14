package vn.loitp.core.loitp.gallery.slide;

/**
 * Created by www.muathu@gmail.com on 12/24/2017.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.gallery.photos.PhotosDataCore;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;
import vn.loitp.views.imageview.bigimageview.LBigImageView;

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
        int position = bundle.getInt(Constants.SK_PHOTO_PISITION);
        Photo photo = PhotosDataCore.getInstance().getPhoto(position);

        LBigImageView bigImageView = (LBigImageView) view.findViewById(R.id.biv);

        //bigImageView.setColorProgressBar(Color.WHITE);
        bigImageView.setOptimizeDisplay(false);
        //bigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_INSIDE);

        bigImageView.load(photo.getUrlS(), photo.getUrlO());
    }
}
