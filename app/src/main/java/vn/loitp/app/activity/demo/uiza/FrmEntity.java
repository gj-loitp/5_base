package vn.loitp.app.activity.demo.uiza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class FrmEntity extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private Data metadata;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        metadata = UZD.getInstance().getMetadata();

        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(LSApplication.getInstance().getGson().toJson(metadata));
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.uiza_frm_entity;
    }
}