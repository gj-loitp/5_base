package com.picker.crop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.R;
import com.annotation.IsFullScreen;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;
import com.core.utilities.LImageUtil;
import com.utils.util.ConvertUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

@LogTag("LGalleryActivity")
@IsFullScreen(false)
//TODO convert kotlin
public class LGalleryActivity extends BaseFontActivity implements View.OnClickListener {
    private final ArrayList<String> thumbsDataList = new ArrayList<>();
    public static final String RETURN_VALUE = "RETURN_VALUE";

    @Override
    protected int setLayoutResourceId() {
        return R.layout.l_activity_l_gallery;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getStoreImage();
        final GalleryAdapter mAdapter = new GalleryAdapter();
        mAdapter.addAllDataList(thumbsDataList);
        final ImageButton btnClose = findViewById(R.id.btn_close);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        final RecyclerView galleryList = findViewById(R.id.gallery_list);
        galleryList.setLayoutManager(gridLayoutManager);
        galleryList.setAdapter(mAdapter);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int i = view.getId();
        if (i == R.id.btn_close) {
            onBackPressed();
        }
    }

    private Cursor imageCursor = null;

    private void getStoreImage() {
        final String[] proj = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};

        imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        if (imageCursor != null && imageCursor.moveToLast()) {
            String title;
            String thumbsID;
            String thumbsImageID;
            String thumbsData;
            String imgSize;

            final int thumbsIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            final int thumbsDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            final int thumbsImageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            final int thumbsSizeCol = imageCursor.getColumnIndex(MediaStore.Images.Media.SIZE);
            int num = 0;
            do {
                thumbsID = imageCursor.getString(thumbsIDCol);
                thumbsData = imageCursor.getString(thumbsDataCol);
                thumbsImageID = imageCursor.getString(thumbsImageIDCol);
                imgSize = imageCursor.getString(thumbsSizeCol);
                num++;

                if (thumbsImageID != null) {
                    //thumbsIDList.add(thumbsID);
                    thumbsDataList.add(thumbsData);
                }
            } while (imageCursor.moveToPrevious());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!imageCursor.isClosed()) {
            imageCursor.close();
        }
    }

    /**
     * GalleryAdapter
     */
    public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final ArrayList<Object> mDataList = new ArrayList<>();

        void addAllDataList(Collection<?> list) {
            this.mDataList.addAll(list);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View itemView = LayoutInflater.from(LGalleryActivity.this).inflate(R.layout.l_item_l_gallery, parent, false);
            return new PhotoItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final PhotoItemHolder listHolder = (PhotoItemHolder) holder;
            final DisplayMetrics metrics = getResources().getDisplayMetrics();
            int CONTENT_PADDING = 5;
            float imgW = (metrics.widthPixels - (ConvertUtils.dp2px(CONTENT_PADDING))) / 3;

            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) imgW);
            listHolder.rlPhoto.setLayoutParams(params);

            final File file = new File(thumbsDataList.get(position));
            LImageUtil.Companion.load(LGalleryActivity.this, file, listHolder.ivPhoto, R.color.colorPrimary, R.color.red, null, null);
            listHolder.ivPhoto.setOnClickListener(v -> {
                /*CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .setBorderLineColor(Color.WHITE)
                        .setBorderLineThickness(5)
                        .setCircleSize(40)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setCircleColor(Color.WHITE)
                        .setBackgroundColor(Color.argb(170, 0, 0, 0))
                        .start(activity);*/

                final Intent intent = new Intent();
                intent.putExtra(RETURN_VALUE, file.getPath());
                setResult(RESULT_OK, intent);
                finish();
                LActivityUtil.tranOut(LGalleryActivity.this);
            });
        }
    }

    public final static class PhotoItemHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlPhoto;
        ImageView ivPhoto;

        PhotoItemHolder(View itemView) {
            super(itemView);
            rlPhoto = itemView.findViewById(R.id.rl_photo);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }
}
