package vn.loitp.app.activity.customviews.viewpager.refreshviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;

public class FrmRefresh extends Fragment {
    public static final String KEY_POSITION = "KEY_POSITION";
    private int mPosition;
    private TextView tv;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(KEY_POSITION);
        }
        return inflater.inflate(R.layout.frm_refresh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv = view.findViewById(R.id.tv);
        avLoadingIndicatorView = view.findViewById(R.id.avl);
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
        avLoadingIndicatorView.smoothToShow();
        LToast.showShort(getActivity(), "loadData " + mPosition, R.drawable.l_bkg_horizontal);
        LUIUtil.INSTANCE.setDelay(1000, new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.VISIBLE);
                avLoadingIndicatorView.smoothToHide();
            }
        });
    }
}
