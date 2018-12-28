package vn.loitp.core.loitp.uiza;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LDateUtils;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class FrmInformation extends BaseFragment {
    private final String TAG = "TAG" + getClass().getSimpleName();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScrollView sv = (ScrollView) view.findViewById(R.id.sv);
        Bundle bundle = getArguments();
        if (bundle == null) {
            sv.setVisibility(View.GONE);
            return;
        }
        Data data = (Data) bundle.getSerializable(UZCons.ENTITY_DATA);
        LUIUtil.setPullLikeIOSVertical(sv);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvCreatedAt = (TextView) view.findViewById(R.id.tv_created_at);
        TextView tvDuration = (TextView) view.findViewById(R.id.tv_duration);
        TextView tvUpdatedAt = (TextView) view.findViewById(R.id.tv_updated_at);
        TextView tvView = (TextView) view.findViewById(R.id.tv_view);
        TextView tvDescription = (TextView) view.findViewById(R.id.tv_description);
        tvName.setText(data.getName() + "");
        tvCreatedAt.setText("Created At: " + LDateUtils.getDateWithoutTime(data.getCreatedAt()));
        tvUpdatedAt.setText("Created At: " + LDateUtils.getDateWithoutTime(data.getUpdatedAt()));
        tvDuration.setText(LDateUtils.convertDate(data.getDuration(), "hh:mm:ss"));
        tvView.setText("View: " + data.getView());
        tvDescription.setText("Description: " + (data.getDescription() == null ? " - " : data.getDescription()));
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_uiza_information;
    }
}