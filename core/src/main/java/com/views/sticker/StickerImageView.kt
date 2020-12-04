package com.views.sticker;

/**
 * Created by www.muathu@gmail.com on 10/21/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

//TODO convert kotlin
public class StickerImageView extends StickerView {

    private String ownerId;
    private ImageView ivMain;

    public StickerImageView(Context context) {
        super(context);
    }

    public StickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOwnerId(String owner_id) {
        this.ownerId = owner_id;
    }

    public String getOwnerId() {
        return this.ownerId;
    }

    @Override
    public View getMainView() {
        if (this.ivMain == null) {
            this.ivMain = new ImageView(getContext());
            this.ivMain.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        return ivMain;
    }

    public void setImageBitmap(Bitmap bmp) {
        this.ivMain.setImageBitmap(bmp);
    }

    public void setImageResource(int res_id) {
        this.ivMain.setImageResource(res_id);
    }

    public void setImageDrawable(Drawable drawable) {
        this.ivMain.setImageDrawable(drawable);
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable) this.ivMain.getDrawable()).getBitmap();
    }

}
