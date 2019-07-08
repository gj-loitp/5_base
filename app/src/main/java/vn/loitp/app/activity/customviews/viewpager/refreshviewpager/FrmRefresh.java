package vn.loitp.app.activity.customviews.viewpager.refreshviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

public class FrmRefresh extends Fragment {
    public static final String KEY_POSITION = "KEY_POSITION";
    private int mPosition;
    private TextView tv;
    private AVLoadingIndicatorView avl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(KEY_POSITION);
        }
        return inflater.inflate(R.layout.frm_refresh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = (TextView) view.findViewById(R.id.tv);
        avl = (AVLoadingIndicatorView) view.findViewById(R.id.avl);
        if (isVisibleToUser && (!isLoaded)) {
            loadData();
            isLoaded = true;
        }
    }

    private boolean isLoaded, isVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isAdded()) {
            loadData();
            isLoaded = true;
        }
    }

    private void loadData() {
        tv.setVisibility(View.INVISIBLE);
        avl.smoothToShow();
        LToast.showShort(getActivity(), "loadData " + mPosition, R.drawable.bkg_horizontal);
        LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                tv.setVisibility(View.VISIBLE);
                avl.smoothToHide();
            }
        });
    }
}
