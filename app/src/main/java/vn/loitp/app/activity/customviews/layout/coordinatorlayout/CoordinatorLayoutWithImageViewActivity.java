package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.annotation.LayoutId;
import com.core.base.BaseFontActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import vn.loitp.app.R;

@LayoutId(R.layout.activity_coordinator_layout)
public class CoordinatorLayoutWithImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imageView = findViewById(R.id.img_hero);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener((appBarLayout1, verticalOffset) -> {
            Matrix matrix = new Matrix(imageView.getImageMatrix());

            //get image's width and height
            final int dwidth = imageView.getDrawable().getIntrinsicWidth();
            final int dheight = imageView.getDrawable().getIntrinsicHeight();

            //get view's width and height
            final int vwidth = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
            int vheight = imageView.getHeight() - imageView.getPaddingTop() - imageView.getPaddingBottom();

            float scale;
            float dx = 0, dy = 0;
            float parallaxMultiplier = ((CollapsingToolbarLayout.LayoutParams) imageView.getLayoutParams()).getParallaxMultiplier();

            //maintain the image's aspect ratio depending on offset
            if (dwidth * vheight > vwidth * dheight) {
                vheight += (verticalOffset); //calculate view height depending on offset
                scale = (float) vheight / (float) dheight; //calculate scale
                dx = (vwidth - dwidth * scale) * 0.5f; //calculate x value of the center point of scaled drawable
                dy = -verticalOffset * (1 - parallaxMultiplier); //calculate y value by compensating parallaxMultiplier
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * 0.5f;
            }

            int currentWidth = Math.round(scale * dwidth); //calculate current intrinsic width of the drawable

            if (vwidth <= currentWidth) { //compare view width and drawable width to decide, should we scale more or not
                matrix.setScale(scale, scale);
                matrix.postTranslate(Math.round(dx), Math.round(dy));
                imageView.setImageMatrix(matrix);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

}
