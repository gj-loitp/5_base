package vn.puresolutions.livestarv3.activity.bank.manager;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.gifthistory.GiftHistory;
import vn.puresolutions.livestar.corev3.api.model.v3.gifthistory.Item;
import vn.puresolutions.livestar.corev3.api.model.v3.gifthistory.SvGiftHistory;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmBankHistoryGift extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private List<Item> itemList = new ArrayList<Item>();
    private GiftAdapter giftAdapter;
    private TextView tvMsg;
    private TextView tvChangeRealMoney;
    private TextView tvTotalCoin;
    private int mCurrentPage = 1;
    private int mTotalPage = 1;
    private int mTotalCoin = 0;
    private RecyclerView recyclerView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(mCurrentPage);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_bank_history_gift, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        tvChangeRealMoney = (TextView) view.findViewById(R.id.tv_change_real_money);
        tvTotalCoin = (TextView) view.findViewById(R.id.tv_total_coin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        //recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        giftAdapter = new GiftAdapter(getActivity(), itemList, new GiftAdapter.GiftCallback() {
            @Override
            public void onClick(Item item) {
                //do nothing
                //AlertMessage.showSuccess(getActivity(), "Click " + item.getGifts().getName());
            }

            @Override
            public void onIsLastItem(int maxSize) {
                LLog.d(TAG, "onIsLastItem maxSize " + maxSize);
                if (mCurrentPage <= mTotalPage && mTotalPage > 1) {
                    mCurrentPage++;
                    LLog.d(TAG, "mCurrentPage " + mCurrentPage + ", mTotalPage: " + mTotalPage);
                    getData(mCurrentPage);
                }
            }
        });
        recyclerView.setAdapter(giftAdapter);
        tvChangeRealMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangeRealMoney();
            }
        });
        return view;
    }

    private void getData(int page) {
        //final Dialog dialog = LDialogUtils.loadingMultiColor(getActivity(), true);
        //LDialogUtils.showDialog(dialog);
        UserProfile userProfile = LPref.getUser(getActivity());
        if (userProfile == null) {
            AlertMessage.showError(getActivity(), R.string.err);
            return;
        }
        final int limit = 50;
        LSService service = RestClient.createService(LSService.class);
        SvGiftHistory svGiftHistory = new SvGiftHistory();
        svGiftHistory.setLimit(limit);
        svGiftHistory.setPage(page);
        svGiftHistory.setType("receive");
        svGiftHistory.setUserId(userProfile.getId());
        subscribe(service.giftHistory(svGiftHistory), new ApiSubscriber<GiftHistory>() {
            @Override
            public void onSuccess(GiftHistory giftHistory) {
                LLog.d(TAG, ">>>onSuccess " + LSApplication.getInstance().getGson().toJson(giftHistory));
                LLog.d(TAG, "giftHistory onSuccess giftHistory.getTotalItems() " + giftHistory.getTotalItems());
                LLog.d(TAG, ">>>giftHistory.getItems() " + giftHistory.getItems().size());
                itemList.addAll(giftHistory.getItems());
                if (itemList.isEmpty()) {
                    tvMsg.setVisibility(View.VISIBLE);
                    tvChangeRealMoney.setVisibility(View.GONE);
                    tvTotalCoin.setVisibility(View.GONE);
                } else {
                    mTotalCoin = giftHistory.getTotalCoins();
                    tvTotalCoin.setText("TỔNG: " + mTotalCoin + " xu");
                    if (giftHistory.getTotalItems() % limit > 1) {
                        mTotalPage = giftHistory.getTotalItems() / limit + 1;
                    } else {
                        mTotalPage = giftHistory.getTotalItems() / limit;
                    }
                    LLog.d(TAG, ">>>>>>>>>mTotalPage: " + mTotalPage);
                    tvMsg.setVisibility(View.GONE);
                    if (giftAdapter != null) {
                        giftAdapter.notifyDataSetChanged();
                    }
                }
                //LDialogUtils.hideDialog(dialog);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
                //LDialogUtils.hideDialog(dialog);
            }
        });
    }

    private void showDialogChangeRealMoney() {
        if (mTotalCoin > 2000) {
            //TODO
            AlertMessage.showError(getActivity(), "TODO");
        } else {
            LDialogUtils.showDlg1Option(getActivity(), R.drawable.wallet, getString(R.string.change_real_coin), getString(R.string.change_real_coin_below_2000), getString(R.string.confirm), new LDialogUtils.Callback1() {
                @Override
                public void onClickButton0() {
                    //do nothing
                }
            });
        }
    }
}

