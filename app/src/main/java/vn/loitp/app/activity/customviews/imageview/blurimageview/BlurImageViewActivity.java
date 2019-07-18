package vn.loitp.app.activity.customviews.imageview.blurimageview;

import android.os.Bundle;
import android.widget.ImageView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;
import com.views.imageview.blurimageview.LBlurImageView;

import loitp.basemaster.R;

public class BlurImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.iv);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv_2);
        LBlurImageView.with(getApplicationContext())
                .load(R.drawable.iv)
                .intensity(20)
                .Async(true)
                .into(imageView);

        LImageUtil.INSTANCE.load(getActivity(), com.core.common.Constants.INSTANCE.getURL_IMG_5(), imageView2);

        /*
        method (load) :- load(int resource), load(Bitmap bitmap)

        method(intesity):- intensity( int value) { Increase Blur and limit of value is in between 0 to 25 }

        Synchronous way to Load :- To make blur in synchronous you need to put false in Async method.

        ASynchronous way to Load:- To make blur in asynchronous (Background) you need to put true in Async method.

        Direct get Blur Bitmap :- To get direct blur bitmap call the following code .
        Bitmap bitmap = LBlurImageView.with(getApplicationContext()).load(R.drawable.mountain).intensity(20).Async(true).getImageBlur();
        */
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
    protected int setLayoutResourceId() {
        return R.layout.activity_blur_imageview;
    }
}
