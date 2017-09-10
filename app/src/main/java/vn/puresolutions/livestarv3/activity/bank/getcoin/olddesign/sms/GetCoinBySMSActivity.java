package vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.sms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LChooseView;

public class GetCoinBySMSActivity extends BaseActivity {
    private LActionBar lActionBar;

    private enum ChooseBranch {
        NONE, VIETTEL, MOBI, VINA
    }

    private ChooseBranch currentChooseBranch = ChooseBranch.NONE;

    private LChooseView lChooseViewBranchViettel;
    private LChooseView lChooseViewBranchMobi;
    private LChooseView lChooseViewBranchVina;

    private enum ChooseCoinPackage {
        NONE, P50, P200, P500
    }

    private ChooseCoinPackage currentChooseCoinPackage = ChooseCoinPackage.NONE;
    private LChooseView lChooseViewPkg50;
    private LChooseView lChooseViewPkg200;
    private LChooseView lChooseViewPkg500;

    private TextView tvSendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();

        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lActionBar.setTvTitle(getString(R.string.via_sms));

        lChooseViewBranchViettel.setTextTvContent(getString(R.string.viettel));
        lChooseViewBranchMobi.setTextTvContent(getString(R.string.mobifone));
        lChooseViewBranchVina.setTextTvContent(getString(R.string.vinaphone));

        lChooseViewBranchViettel.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.VIETTEL;
                setChooseBranch(lChooseViewBranchViettel);
            }
        });
        lChooseViewBranchMobi.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.MOBI;
                setChooseBranch(lChooseViewBranchMobi);
            }
        });
        lChooseViewBranchVina.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.VINA;
                setChooseBranch(lChooseViewBranchVina);
            }
        });

        lChooseViewPkg50.setTextTvContent("50 xu");
        lChooseViewPkg50.setTextTvInfo("50000vnd");

        lChooseViewPkg200.setTextTvContent("200 xu");
        lChooseViewPkg200.setTextTvInfo("200000vnd");

        lChooseViewPkg500.setTextTvContent("500 xu");
        lChooseViewPkg500.setTextTvInfo("500000vnd");

        lChooseViewPkg50.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P50;
                setChooseCoinPackage(lChooseViewPkg50);
            }
        });
        lChooseViewPkg200.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P200;
                setChooseCoinPackage(lChooseViewPkg200);
            }
        });
        lChooseViewPkg500.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P500;
                setChooseCoinPackage(lChooseViewPkg500);
            }
        });
        tvSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(activity, "send");
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_get_coin_sms;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        lChooseViewBranchViettel = (LChooseView) findViewById(R.id.l_choose_view_branch_viettel);
        lChooseViewBranchMobi = (LChooseView) findViewById(R.id.l_choose_view_branch_mobi);
        lChooseViewBranchVina = (LChooseView) findViewById(R.id.l_choose_view_branch_vina);
        lChooseViewPkg50 = (LChooseView) findViewById(R.id.l_choose_view_pkg_50);
        lChooseViewPkg200 = (LChooseView) findViewById(R.id.l_choose_view_pkg_200);
        lChooseViewPkg500 = (LChooseView) findViewById(R.id.l_choose_view_pkg_500);
        tvSendSms = (TextView) findViewById(R.id.tv_send_sms);
    }

    private void setChooseBranch(LChooseView chooseBranch) {
        lChooseViewBranchViettel.setChecked(false);
        lChooseViewBranchMobi.setChecked(false);
        lChooseViewBranchVina.setChecked(false);

        chooseBranch.setChecked(true);
        updateCode();
    }

    private void setChooseCoinPackage(LChooseView chooseCoinPackage) {
        lChooseViewPkg50.setChecked(false);
        lChooseViewPkg200.setChecked(false);
        lChooseViewPkg500.setChecked(false);

        chooseCoinPackage.setChecked(true);
        updateCode();
    }

    private void updateCode() {
        if (currentChooseBranch != ChooseBranch.NONE && currentChooseCoinPackage != ChooseCoinPackage.NONE) {
            LLog.d(TAG, "setChooseBranch " + currentChooseBranch + ", setChooseCoinPackage: " + currentChooseCoinPackage);
        }
    }
}
