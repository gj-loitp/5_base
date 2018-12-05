package vn.loitp.app.activity.demo.uiza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.restapi.uiza.model.v3.videoondeman.listallentity.ResultListEntity;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class FrmEntity extends BaseFragment {
    private final String TAG = "TAG" + getClass().getSimpleName();
    private Data metadata;
    private boolean isMetadataHome;
    private AVLoadingIndicatorView avl;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metadata = UZD.getInstance().getMetadata();
        if (metadata.getName().equals(UZCons.HOME)) {
            isMetadataHome = true;
        } else {
            isMetadataHome = false;
        }
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
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
}