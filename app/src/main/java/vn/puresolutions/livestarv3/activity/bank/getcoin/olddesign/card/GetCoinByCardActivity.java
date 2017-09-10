package vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.card;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LChooseView;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class GetCoinByCardActivity extends BaseActivity {
    private LActionBar lActionBar;

    private enum ChooseBranch {
        NONE, VIETTEL, MOBI, VINA, FPTGATE, ZING, ONECASH, MEGACASH
    }

    private ChooseBranch currentChooseBranch = ChooseBranch.NONE;

    private LChooseView lChooseViewBranchViettel;
    private LChooseView lChooseViewBranchMobi;
    private LChooseView lChooseViewBranchVina;
    private LChooseView lChooseViewBranchFptGate;
    private LChooseView lChooseViewBranchZing;
    private LChooseView lChooseViewBranchOneCash;
    private LChooseView lChooseViewBranchMegaCard;

    private EditText etCardCode;
    private EditText etCardSeri;

    private TextView tvDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        LUIUtil.setSoftInputMode(activity, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        lActionBar.setTvTitle(getString(R.string.via_card));

        lChooseViewBranchViettel.setTextTvContent(getString(R.string.viettel));
        lChooseViewBranchMobi.setTextTvContent(getString(R.string.mobifone));
        lChooseViewBranchVina.setTextTvContent(getString(R.string.vinaphone));
        lChooseViewBranchFptGate.setTextTvContent(getString(R.string.fpt_gate));
        lChooseViewBranchZing.setTextTvContent(getString(R.string.zing));
        lChooseViewBranchOneCash.setTextTvContent(getString(R.string.one_cash));
        lChooseViewBranchMegaCard.setTextTvContent(getString(R.string.mega_cash));

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
        lChooseViewBranchFptGate.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.FPTGATE;
                setChooseBranch(lChooseViewBranchFptGate);
            }
        });
        lChooseViewBranchZing.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.ZING;
                setChooseBranch(lChooseViewBranchZing);
            }
        });
        lChooseViewBranchOneCash.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.ONECASH;
                setChooseBranch(lChooseViewBranchOneCash);
            }
        });
        lChooseViewBranchMegaCard.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseBranch = ChooseBranch.MEGACASH;
                setChooseBranch(lChooseViewBranchMegaCard);
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(activity, "Done");
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
        return R.layout.activity_get_coin_card;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        lChooseViewBranchViettel = (LChooseView) findViewById(R.id.l_choose_view_branch_viettel);
        lChooseViewBranchMobi = (LChooseView) findViewById(R.id.l_choose_view_branch_mobi);
        lChooseViewBranchVina = (LChooseView) findViewById(R.id.l_choose_view_branch_vina);
        lChooseViewBranchFptGate = (LChooseView) findViewById(R.id.l_choose_view_branch_fpt_gate);
        lChooseViewBranchZing = (LChooseView) findViewById(R.id.l_choose_view_branch_zing);
        lChooseViewBranchOneCash = (LChooseView) findViewById(R.id.l_choose_view_branch_one_cash);
        lChooseViewBranchMegaCard = (LChooseView) findViewById(R.id.l_choose_view_branch_mega_card);
        etCardCode = (EditText) findViewById(R.id.et_card_code);
        etCardSeri = (EditText) findViewById(R.id.et_card_seri);
        tvDone = (TextView) findViewById(R.id.tv_done);
    }

    private void setChooseBranch(LChooseView lChooseView) {
        lChooseViewBranchViettel.setChecked(false);
        lChooseViewBranchMobi.setChecked(false);
        lChooseViewBranchVina.setChecked(false);
        lChooseViewBranchFptGate.setChecked(false);
        lChooseViewBranchZing.setChecked(false);
        lChooseViewBranchOneCash.setChecked(false);
        lChooseViewBranchMegaCard.setChecked(false);

        lChooseView.setChecked(true);
        LLog.d(TAG, "setChooseBranch currentChooseBranch " + currentChooseBranch);
    }
}
