package vn.loitp.app.activity.demo.uiza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.restapi.uiza.model.v3.videoondeman.listallentity.ResultListEntity;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.LToast;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class FrmEntity extends BaseFragment {
    private final String TAG = "TAG" + getClass().getSimpleName();
    private Data metadata;
    private boolean isMetadataHome;
    private AVLoadingIndicatorView avl;
    private List<Data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EntityAdapter entityAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metadata = UZD.getInstance().getMetadata();
        if (metadata == null) {
            return;
        }
        if (metadata.getName().equals(UZCons.HOME)) {
            isMetadataHome = true;
        } else {
            isMetadataHome = false;
        }
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        entityAdapter = new EntityAdapter(getActivity(), dataList, new EntityAdapter.Callback() {
            @Override
            public void onClick(Data data, int position) {
                LToast.show(getActivity(), "Click " + data.getEntityName());
            }

            @Override
            public void onLongClick(Data data, int position) {
            }

            @Override
            public void onLoadMore() {
                LLog.d(TAG, "onLoadMore");
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(entityAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);
        getListEntity();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.uiza_frm_entity;
    }

    private void getListEntity() {
        if (metadata == null) {
            return;
        }
        if (isMetadataHome) {
            listAllEntity();
        } else {
            listAllEntityMetadata();
        }
    }

    private void listAllEntity() {
        UZService service = UZRestClient.createService(UZService.class);
        String metadataId = "";
        int limit = 50;
        int page = 0;
        String orderBy = "createdAt";
        String orderType = "DESC";
        subscribe(service.getListAllEntity(metadataId, limit, page, orderBy, orderType, "success"), new ApiSubscriber<ResultListEntity>() {
            @Override
            public void onSuccess(ResultListEntity result) {
                LLog.d(TAG, "getListAllEntity onSuccess: " + LSApplication.getInstance().getGson().toJson(result));
                if (result == null || result.getData() == null || result.getData().isEmpty()) {

                } else {
                    dataList.addAll(result.getData());
                    refreshAllViews();
                }
                avl.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getListAllEntity onFail " + e.getMessage());
                avl.smoothToHide();
            }
        });
    }

    private void listAllEntityMetadata() {
        UZService service = UZRestClient.createService(UZService.class);
        int limit = 50;
        int page = 0;
        String orderBy = "createdAt";
        String orderType = "DESC";
        subscribe(service.getListAllEntity(metadata.getId(), limit, page, orderBy, orderType, "success"), new ApiSubscriber<ResultListEntity>() {
            @Override
            public void onSuccess(ResultListEntity result) {
                LLog.d(TAG, "listAllEntityMetadata onSuccess: " + LSApplication.getInstance().getGson().toJson(result));
                avl.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "listAllEntityMetadata onFail " + e.getMessage());
                avl.smoothToHide();
            }
        });
    }

    private void refreshAllViews() {
        LLog.d(TAG, "refreshAllViews size: " + dataList.size());
        if (entityAdapter != null) {
            entityAdapter.notifyDataSetChanged();
        }
    }
}