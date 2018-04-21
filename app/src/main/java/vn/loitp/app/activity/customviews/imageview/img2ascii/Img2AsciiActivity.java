package vn.loitp.app.activity.customviews.imageview.img2ascii;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.views.imageview.img2ascii.Img2Ascii;
import vn.loitp.views.imageview.touchimageview.lib.LTouchImageView;

public class Img2AsciiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = (TextView) findViewById(R.id.tv_ascii);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.iv);
        // Bitmap image = BitmapFactory.decodeFile(filename);
        int quality = 3; // 1 - 3
        // convert image(bitmap) to ascii(string)
        Img2Ascii image2ascii = new Img2Ascii();
        String ascii = image2ascii.convert(image, quality);

        //textView.setTypeface(monospaceFont);
        textView.setText(ascii);
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
        return R.layout.activity_img2ascii;
    }
}
