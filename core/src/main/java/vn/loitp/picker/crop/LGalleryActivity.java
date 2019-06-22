package vn.loitp.picker.crop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ConvertUtils;

public class LGalleryActivity extends BaseFontActivity implements View.OnClickListener {
    public static final int PICK_FROM_CAMERA = 0;
    public static final int PICK_FROM_ALBUM = 1;
    public static final int CROP_FROM_IMAGE = 2;
    public static final int WRITE_EXTERNAL_STORAGE_CODE = 66;
    private ArrayList<String> thumbsDataList = new ArrayList<>();
    private RecyclerView galleryList;
    private GalleryAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private ImageButton btnClose;
    public static final int CONTENT_PADDING = 5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStoreImage();
        mAdapter = new GalleryAdapter(this);
        mAdapter.addAllDataList(thumbsDataList);
        btnClose = findViewById(R.id.btn_close);
        gridLayoutManager = new GridLayoutManager(activity, 3);
        galleryList = findViewById(R.id.gallery_list);
        galleryList.setLayoutManager(gridLayoutManager);
        galleryList.setAdapter(mAdapter);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int i = view.getId();
        if (i == R.id.btn_close) {
            LLog.d(TAG, "onClick btn_close");
            finish();
            LActivityUtil.tranOut(activity);
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

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_l_gallery;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        LLog.d(TAG, "onActivityResult");
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                //setResult(CROP_FROM_IMAGE, data);

//                Intent intent = new Intent();
//                intent.putExtra(CROP_FROM_IMAGE, data);
                setResult(RESULT_OK, data);
                break;
            }
//            case PICK_FROM_CAMERA:
//            {
//                LogUtil.i("Gallery PICK_FROM_CAMERA");
//                Uri carameUri = data.getData();
//
//                if (carameUri != null) {
//                    LogUtil.i("carameUri :: " + carameUri);
//                    CropImage.activity(carameUri)
//                            .setGuidelines(CropImageView.Guidelines.OFF)
//                            .setBorderLineColor(Color.WHITE)
//                            .setBorderLineThickness(5)
//                            .setCircleSize(40)
//                            .setCircleColor(Color.WHITE)
//                            .setBackgroundColor(Color.argb(170, 0, 0, 0))
//                            .start(this);
//                }
//                return;
//            }
        }
        finish();
        LActivityUtil.tranOut(activity);
    }

    /**
     * GalleryAdapter
     */
    public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private ArrayList<Object> mDataList = new ArrayList<>();

        GalleryAdapter(Context context) {
            this.context = context;
        }

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
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_l_gallery, parent, false);
            return new PhotoItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final PhotoItemHolder listHolder = (PhotoItemHolder) holder;
            final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float imgW = (metrics.widthPixels - (ConvertUtils.dp2px(CONTENT_PADDING))) / 3;

            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) imgW);
            listHolder.rlPhoto.setLayoutParams(params);

            final File file = new File(thumbsDataList.get(position));
            final Uri imageUri = Uri.fromFile(file);
            Glide.with(context).load(imageUri).into(listHolder.ivPhoto);
            listHolder.ivPhoto.setOnClickListener(v -> {
                /*Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image*//*");
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, PICK_FROM_CAMERA);*/

                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.OFF)
                        .setBorderLineColor(Color.WHITE)
                        .setBorderLineThickness(5)
                        .setCircleSize(40)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setCircleColor(Color.WHITE)
                        .setBackgroundColor(Color.argb(170, 0, 0, 0))
                        .start((Activity) context);
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
