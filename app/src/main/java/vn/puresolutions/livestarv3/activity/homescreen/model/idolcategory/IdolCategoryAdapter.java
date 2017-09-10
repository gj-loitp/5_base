package vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.old.ScreenUtils;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;

public class IdolCategoryAdapter extends RecyclerView.Adapter<IdolCategoryAdapter.IdolCategoryDetailHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList;
    private Activity activity;
    private Callback callback;
    private int widthViewHolder;

    public interface Callback {
        public void onClick(ModelIdolCategoryDetail modelIdolCategoryDetail);
    }

    public IdolCategoryAdapter(Activity activity, ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList, Callback callback) {
        this.modelIdolCategoryDetailArrayList = modelIdolCategoryDetailArrayList;
        this.activity = activity;
        this.callback = callback;

        int screenWidthInDp = (int) ScreenUtils.convertPixelsToDp((float) (ScreenUtils.getWidthScreen(activity) / 4.5), activity);
        widthViewHolder = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidthInDp, activity.getResources().getDisplayMetrics());//200dp
    }

    @Override
    public IdolCategoryDetailHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_idol_category_detail, null);
        int width = (int) (viewGroup.getMeasuredWidth() / 4.5);
        v.setMinimumWidth(width);
        return new IdolCategoryDetailHolder(v);
    }

    @Override
    public void onBindViewHolder(IdolCategoryDetailHolder holder, int position) {
        ModelIdolCategoryDetail modelIdolCategoryDetail = modelIdolCategoryDetailArrayList.get(position);
        holder.tvDescription.setText(modelIdolCategoryDetail.getName());

        if (modelIdolCategoryDetail.isChecked()) {
            switch (modelIdolCategoryDetail.getType()) {
                case ModelIdolCategoryDetail.TYPE_ALL:
                    holder.ivIcon.setImageResource(R.drawable.idol_tap);
                    break;
                case ModelIdolCategoryDetail.TYPE_DJ:
                    holder.ivIcon.setImageResource(R.drawable.dj);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_BOY:
                    holder.ivIcon.setImageResource(R.drawable.hot_boy_tap);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_GIRL:
                    holder.ivIcon.setImageResource(R.drawable.hot_girl_tap);
                    break;
                case ModelIdolCategoryDetail.TYPE_NGHESY:
                    holder.ivIcon.setImageResource(R.drawable.idol_tap);
                    break;
                case ModelIdolCategoryDetail.TYPE_CARTOON:
                    holder.ivIcon.setImageResource(R.drawable.cartoon_tap);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_IDOL:
                    holder.ivIcon.setImageResource(R.drawable.hot_idol_tap);
                    break;
                default:
                    break;
            }
        } else {
            switch (modelIdolCategoryDetail.getType()) {
                case ModelIdolCategoryDetail.TYPE_ALL:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_all);
                    break;
                case ModelIdolCategoryDetail.TYPE_DJ:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_dj);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_BOY:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_hotboy);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_GIRL:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_hotgirl);
                    break;
                case ModelIdolCategoryDetail.TYPE_NGHESY:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_all);
                    break;
                case ModelIdolCategoryDetail.TYPE_CARTOON:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_cartoon);
                    break;
                case ModelIdolCategoryDetail.TYPE_HOT_IDOL:
                    holder.ivIcon.setImageResource(R.drawable.bt_category_hot_idol);
                    break;
                default:
                    break;
            }
        }
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    LAnimationUtil.play(holder.llMain, Techniques.Pulse);
                    callback.onClick(modelIdolCategoryDetail);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != modelIdolCategoryDetailArrayList ? modelIdolCategoryDetailArrayList.size() : 0);
    }

    public class IdolCategoryDetailHolder extends RecyclerView.ViewHolder {
        protected LinearLayout llMain;
        protected ImageView ivIcon;
        protected TextView tvDescription;

        public IdolCategoryDetailHolder(View view) {
            super(view);
            this.llMain = (LinearLayout) view.findViewById(R.id.ll_main);
            this.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            this.tvDescription = (TextView) view.findViewById(R.id.tv_description);
        }
    }
}