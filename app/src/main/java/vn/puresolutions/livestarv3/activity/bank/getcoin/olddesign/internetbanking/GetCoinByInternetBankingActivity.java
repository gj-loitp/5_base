package vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.internetbanking;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LChooseView;

public class GetCoinByInternetBankingActivity extends BaseActivity {
    private LActionBar lActionBar;
    private ArrayList<DummyPerson> dummyPersonArrayList = new ArrayList<DummyPerson>();

    private enum ChooseCoinPackage {
        NONE, P500, P1000, P2000
    }

    private ChooseCoinPackage currentChooseCoinPackage = ChooseCoinPackage.NONE;

    private LChooseView lChooseViewPkg500;
    private LChooseView lChooseViewPkg1000;
    private LChooseView lChooseViewPkg2000;

    private TextView tvDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        createDummyData();
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
        lActionBar.setTvTitle(getString(R.string.via_internet_banking));

        lChooseViewPkg500.setTextTvContent("500 xu");
        lChooseViewPkg500.setTextTvInfo("(50.000vnd)");

        lChooseViewPkg1000.setTextTvContent("1000 xu");
        lChooseViewPkg1000.setTextTvInfo("(100.000vnd)");

        lChooseViewPkg2000.setTextTvContent("2000 xu");
        lChooseViewPkg2000.setTextTvInfo("(200.000vnd)");

        lChooseViewPkg500.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P500;
                setChooseBranch(lChooseViewPkg500);
            }
        });
        lChooseViewPkg1000.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P1000;
                setChooseBranch(lChooseViewPkg1000);
            }
        });
        lChooseViewPkg2000.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChooseCoinPackage = ChooseCoinPackage.P2000;
                setChooseBranch(lChooseViewPkg2000);
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertMessage.showSuccess(activity, "Done");
            }
        });

        //init list audiences vertical
        RecyclerView recyclerViewListAudiencesVertical = (RecyclerView) findViewById(R.id.recycler_view_list_audiences_vertical);
        recyclerViewListAudiencesVertical.setHasFixedSize(true);
        recyclerViewListAudiencesVertical.setNestedScrollingEnabled(false);
        //recyclerViewListAudiencesVertical.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerViewListAudiencesVertical.setLayoutManager(new GridLayoutManager(activity, 3));
        BankAdapter bankAdapter = new BankAdapter(activity, dummyPersonArrayList, new BankAdapter.Callback() {
            @Override
            public void onClick(DummyPerson dummyPerson) {
                AlertMessage.showSuccess(activity, "Click vertical dummyPerson " + dummyPerson.getName());
            }
        });
        recyclerViewListAudiencesVertical.setAdapter(bankAdapter);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0, 0);
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
        return R.layout.activity_get_coin_internet_banking;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        lChooseViewPkg500 = (LChooseView) findViewById(R.id.l_choose_view_pkg_500);
        lChooseViewPkg1000 = (LChooseView) findViewById(R.id.l_choose_view_pkg_1000);
        lChooseViewPkg2000 = (LChooseView) findViewById(R.id.l_choose_view_pkg_2000);
        tvDone = (TextView) findViewById(R.id.tv_done);
    }

    private void setChooseBranch(LChooseView lChooseView) {
        lChooseViewPkg500.setChecked(false);
        lChooseViewPkg1000.setChecked(false);
        lChooseViewPkg2000.setChecked(false);

        lChooseView.setChecked(true);
        LLog.d(TAG, "setChooseBranch currentChooseCoinPackage " + currentChooseCoinPackage);
    }

    private void createDummyData() {
        for (int i = 0; i < 50; i++) {
            DummyPerson dummyPerson = new DummyPerson();
            dummyPerson.setName("Idol loitp " + i);
            dummyPerson.setHeart(i);
            dummyPerson.setImage("http://data.whicdn.com/images/237527707/large.png");
            dummyPersonArrayList.add(dummyPerson);
        }
    }
}
