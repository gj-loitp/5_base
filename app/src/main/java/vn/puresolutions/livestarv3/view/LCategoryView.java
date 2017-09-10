package vn.puresolutions.livestarv3.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.IdolCategoryAdapter;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategory;
import vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory.ModelIdolCategoryDetail;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LCategoryView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerViewList;
    private ModelIdolCategory mModelIdolCategory;
    private Activity mActivity;
    private IdolCategoryAdapter idolFollowAdapter;

    public LCategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LCategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_category, this);

        this.recyclerViewList = (RecyclerView) findViewById(R.id.recycler_view_list);
    }

    public void setData(Activity activity, ModelIdolCategory modelIdolCategory) {
        mModelIdolCategory = modelIdolCategory;
        mActivity = activity;
        ArrayList<ModelIdolCategoryDetail> idolCategoryList = mModelIdolCategory.getModelIdolCategoryDetailArrayList();
        if (idolCategoryList != null) {
            idolFollowAdapter = new IdolCategoryAdapter(mActivity, idolCategoryList, new IdolCategoryAdapter.Callback() {
                @Override
                public void onClick(ModelIdolCategoryDetail modelIdolCategoryDetail) {
                    if (callback != null) {
                        callback.onClick(modelIdolCategoryDetail);
                    }
                    for (int i = 0; i < idolCategoryList.size(); i++) {
                        idolCategoryList.get(i).setChecked(false);
                    }
                    modelIdolCategoryDetail.setChecked(true);
                    notifyView();
                }
            });
            recyclerViewList.setHasFixedSize(true);
            recyclerViewList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));

            //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            //linearLayoutManager.setAutoMeasureEnabled(true);

            recyclerViewList.setAdapter(idolFollowAdapter);
            recyclerViewList.setNestedScrollingEnabled(false);
        }
    }

    public interface Callback {
        public void onClick(ModelIdolCategoryDetail modelIdolCategoryDetail);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void notifyView() {
        if (idolFollowAdapter != null) {
            idolFollowAdapter.notifyDataSetChanged();
        }
    }
}