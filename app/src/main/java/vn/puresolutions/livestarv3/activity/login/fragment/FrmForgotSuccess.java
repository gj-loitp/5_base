package vn.puresolutions.livestarv3.activity.login.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrmForgotSuccess extends BaseFragment {

    @BindView(R.id.btnFrmForgotSuccess_BackLogin)
    Button btnBackLogin;

    public FrmForgotSuccess() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frm_forgot_success, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btnFrmForgotSuccess_BackLogin)
    void backLoginScreen() {
        /*getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flLoginScreen_Container, new FrmLogin())
                .commit();*/
        //getActivity().getSupportFragmentManager().popBackStack();
    }
}
