package com.views.imageview.blurimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

//https://github.com/sparrow007/BlurImage
public class LBlurImageView {

    private static final float BITMAP_SCALE = 0.3f;
    private static final float BLUR_RADIUS = 7f;
    private Bitmap image;

    private Context context;
    private float intensity = 08f;
    private float MAX_RADIUS = 25;

    private float MIN_RADIUS = 0;
    private boolean async = false;

    LBlurImageView(Context context) {
        this.context = context;
    }

    /*
    *  This method is creating a blur bitmap , this method use renderscript which efficient
     *  and here we use scriptIntrinsicBlur for performing blurring
    * */
    private Bitmap blur() {

        if (image == null) {
            return image;
        }

        int width = Math.round(image.getWidth());
        int height = Math.round(image.getHeight());

        Bitmap input = Bitmap.createScaledBitmap(image, width, height, false);

        Bitmap output = Bitmap.createBitmap(input);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));


        Allocation inputallocation = Allocation.createFromBitmap(rs, input);
        Allocation outputallocation = Allocation.createFromBitmap(rs, output);
        intrinsicBlur.setRadius(intensity);
        intrinsicBlur.setInput(inputallocation);
        intrinsicBlur.forEach(outputallocation);

        outputallocation.copyTo(output);

        return output;
    }

    @NonNull
    public static LBlurImageView with(Context context) {
        return new LBlurImageView(context);
    }

    /*
    * Here we get bitmap on which we apply the blur process
    * */

    public LBlurImageView load(Bitmap bitmap) {
        this.image = bitmap;
        return this;
    }

    public LBlurImageView load(int res) {
        image = BitmapFactory.decodeResource(context.getResources(), res);
        return this;
    }

    public LBlurImageView intensity(float intensity) {
        if (intensity < MAX_RADIUS && intensity > 0)
            this.intensity = intensity;
        else
            this.intensity = MAX_RADIUS;
        return this;
    }

    public LBlurImageView Async(boolean async) {
        this.async = async;
        return this;
    }

    public void into(ImageView imageView) {

        if (async) {
            new AsyncBlurImage(imageView).execute();
        } else {
            try {
                imageView.setImageBitmap(blur());
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }

    }

    public Bitmap getImageBlur() {

        return blur();
    }

    private class AsyncBlurImage extends AsyncTask<Void, Void, Bitmap> {
        private WeakReference<ImageView> weakReference;

        public AsyncBlurImage(ImageView image) {
            this.weakReference = new WeakReference<ImageView>(image);
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return blur();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView imageView = weakReference.get();
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }

        }
    }


}