package vn.loitp.app.activity.animation.flyschool;

import android.os.Bundle;
import android.widget.Button;

import com.core.base.BaseFontActivity;
import com.views.animation.flyschool.ImgObject;
import com.views.animation.flyschool.PATHS;
import com.views.animation.flyschool.ShapeFlyer;

import vn.loitp.app.R;

//https://github.com/cipherthinkers/shapeflyer?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5370
public class FlySchoolActivity extends BaseFontActivity {
    private ShapeFlyer mShapeFlyer;
    private Button btPlay1;
    private Button btPlay2;
    private Button btPlay3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShapeFlyer = findViewById(R.id.floating_container);
        btPlay1 = findViewById(R.id.bt_play_1);
        btPlay2 = findViewById(R.id.bt_play_2);
        btPlay3 = findViewById(R.id.bt_play_3);
        mShapeFlyer.addPath(PATHS.S_INVERTED_BOTTOM_RIGHT);
        //mShapeFlyer.addPath(PATHS.S_BOTTOM_LEFT);

        btPlay1.setOnClickListener(v -> {
            ImgObject imgObject = new ImgObject();
            imgObject.setAvatar("https://kenh14cdn.com/2016/photo-1-1472659093342.jpg");
            imgObject.setUrl("https://kenh14cdn.com/2016/photo-1-1472659093342.jpg");
            play1(imgObject);
        });
        btPlay2.setOnClickListener(v -> {
            ImgObject imgObject = new ImgObject();
            imgObject.setAvatar("https://kenh14cdn.com/2016/photo-9-1472659093718.jpg");
            imgObject.setUrl("https://kenh14cdn.com/2016/photo-9-1472659093718.jpg");
            play2(imgObject);
        });
        btPlay3.setOnClickListener(v -> play3());
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
        return R.layout.activity_fly_school;
    }

    @Override
    protected void onDestroy() {
        if (mShapeFlyer != null) {
            mShapeFlyer.release();
        }
        super.onDestroy();
    }

    private void play1(ImgObject imgObject) {
        //FlyBluePrint linearBluePrint = new FlyBluePrint(new FPoint(0, 0), FlyPath.getSimpleLinePath(new FPoint(1, 1)));
        //mShapeFlyer.startAnimation(R.drawable.logo, linearBluePrint);
        //mShapeFlyer.startAnimation(R.drawable.l_heart_icon);

        if (imgObject == null) {
            return;
        }
        mShapeFlyer.startAnimation(imgObject, 0);
    }

    private void play2(ImgObject imgObject) {
        if (imgObject == null) {
            return;
        }
        mShapeFlyer.startAnimation(imgObject, R.drawable.logo);
    }

    private void play3() {
        mShapeFlyer.startAnimation(R.drawable.logo);
    }
}
