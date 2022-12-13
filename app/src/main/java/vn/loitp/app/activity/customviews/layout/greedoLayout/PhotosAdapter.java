package vn.loitp.app.activity.customviews.layout.greedoLayout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator.SizeCalculatorDelegate;
import com.loitpcore.core.utilities.LImageUtil;

import vn.loitp.app.R;

/**
 * Created by Julian Villella on 16-02-24.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> implements SizeCalculatorDelegate {
    private static final int IMAGE_COUNT = 500; // number of images adapter will show

    private final int[] mImageResIds = Constants.IMAGES;
    private final double[] mImageAspectRatios = new double[Constants.IMAGES.length];

    private final Context mContext;

    @Override
    public double aspectRatioForIndex(int index) {
        // Precaution, have better handling for this in greedo-layout
        if (index >= getItemCount()) return 1.0;
        return mImageAspectRatios[getLoopedIndex(index)];
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;

        public PhotoViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }
    }

    public PhotosAdapter(Context context) {
        mContext = context;
        calculateImageAspectRatios();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return new PhotoViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        LImageUtil.Companion.load(
                mContext,
                mImageResIds[getLoopedIndex(position)],
                holder.mImageView,
                R.color.transparent,
                R.color.red,
                null,
                null
        );
    }

    @Override
    public int getItemCount() {
        return IMAGE_COUNT;
    }

    private void calculateImageAspectRatios() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        for (int i = 0; i < mImageResIds.length; i++) {
            BitmapFactory.decodeResource(mContext.getResources(), mImageResIds[i], options);
            mImageAspectRatios[i] = options.outWidth / (double) options.outHeight;
        }
    }

    // Index gets wrapped around <code>Constants.IMAGES.length</code> so we can loop content.
    private int getLoopedIndex(int index) {
        return index % Constants.IMAGES.length; // wrap around
    }
}
