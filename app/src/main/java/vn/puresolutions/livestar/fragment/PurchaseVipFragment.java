package vn.puresolutions.livestar.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.recycler.VipAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.VipPackage;
import vn.puresolutions.livestar.core.api.model.VipPackageDetails;
import vn.puresolutions.livestar.core.api.model.VipPackageResponse;
import vn.puresolutions.livestar.core.api.model.param.BuyVipParam;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.PurchaseService;
import vn.puresolutions.livestar.custom.dialog.ConfirmDialog;
import vn.puresolutions.livestar.custom.dialog.ConfirmPurchaseDialog;
import vn.puresolutions.livestar.custom.dialog.ProgressDialog;
import vn.puresolutions.livestar.custom.dialog.VipBenefitDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.custom.view.CoinInfoView;
import vn.puresolutions.livestar.custom.view.GridCenterView;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;

/**
 * Created by Phu Tran on 4/9/2016.
 */
public class PurchaseVipFragment extends BaseFragment implements GridCenterView.OnItemClickListener, ConfirmPurchaseDialog.ConfirmPurchaseDialogListener, VipBenefitDialog.OnBuyPackageListener {
    private static final String VIP_PACKAGE_EXTRA = "VIP_PACKAGE_EXTRA";
    private static final String VIP_DETAILS_EXTRA = "VIP_DETAILS_EXTRA";
    private static final String GROUPED_VIP_PACKAGE_EXTRA = "GROUPED_VIP_PACKAGE_EXTRA";

    @BindView(R.id.purchaseVipFragment_contentView)
    GridCenterView gridCenterView;
    @BindView(R.id.purchaseVipFragment_vwRoot)
    View root;
    @BindView(R.id.purchaseVipFragment_pgbLoading)
    ProgressBar progressBar;
    @BindView(R.id.purchaseVipFragment_vwCoinInfo)
    CoinInfoView coinInfoView;

    private ConfirmPurchaseDialog confirmDialog;
    private VipBenefitDialog benefitDialog;
    private VipAdapter adapter;
    private ProgressDialog progressDialog;
    private VipPackage selectPackage;
    private HashMap<Long, VipPackageDetails> details = new HashMap<>();
    private HashMap<Long, List<VipPackage>> packages = new HashMap<>();
    private List<VipPackage> groupedPackage = new ArrayList<>();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            coinInfoView.setCoinValue(UserInfo.getUserLoggedIn().getMoney());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_purchase_vip, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.buying));

        confirmDialog = new ConfirmPurchaseDialog(getContext());
        confirmDialog.setListener(this);

        benefitDialog = new VipBenefitDialog(getContext());
        benefitDialog.setListener(this);

        adapter = new VipAdapter();
        gridCenterView.setAdapter(adapter);
        gridCenterView.setOnItemClickListener(this);
        coinInfoView.setType(CoinInfoView.Type.COIN);
        User author = UserInfo.getUserLoggedIn();
        if (author != null) {
            coinInfoView.setCoinValue(author.getMoney());
        }

        // call to server
        if (savedInstanceState == null) {
            progressBar.setVisibility(View.VISIBLE);
            PurchaseService service = RestClient.createService(PurchaseService.class);
            subscribe(service.getVipPackage(UserInfo.getUserLoggedIn().isMBFUser() ? 1 : 0), new ApiSubscriber<List<VipPackageResponse>>() {

                @Override
                public void onSuccess(List<VipPackageResponse> result) {
                    details.clear();
                    packages.clear();
                    for (VipPackageResponse response : result) {
                        List<VipPackage> data = response.getPackages();
                        if (data != null && data.size() > 0) {
                            // compile related package
                            ArrayList<VipPackage> clone = new ArrayList<>(data);
                            clone.remove(0);
                            packages.put(data.get(0).getId(), clone);

                            // save grouped package
                            groupedPackage.add(data.get(0));

                            // prepare vip package details
                            for (VipPackage vipPackage : data) {
                                details.put(vipPackage.getId(), response.getDetails());
                            }
                        }
                    }

                    refreshItem();
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }

                @Override
                public void onFinally(boolean success) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            packages = (HashMap<Long, List<VipPackage>>) savedInstanceState
                    .getSerializable(VIP_PACKAGE_EXTRA);
            groupedPackage = (ArrayList<VipPackage>) savedInstanceState
                    .getSerializable(GROUPED_VIP_PACKAGE_EXTRA);
            details = (HashMap<Long, VipPackageDetails>) savedInstanceState
                    .getSerializable(VIP_DETAILS_EXTRA);

            refreshItem();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(GROUPED_VIP_PACKAGE_EXTRA, (ArrayList<VipPackage>) adapter.getItems());
        outState.putSerializable(VIP_PACKAGE_EXTRA, packages);
        outState.putSerializable(VIP_DETAILS_EXTRA, adapter.getDetails());
    }

    @Override
    public void onResume() {
        super.onResume();
        coinInfoView.setCoinValue(UserInfo.getUserLoggedIn().getMoney());
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(PurchaseFragment.USER_COIN_CHANGED));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PurchaseFragment.MessageEvent event) {
        if (event == null) {
            return;
        }
        if (coinInfoView != null && event.getMsg().equals(Constants.MSG)) {
            coinInfoView.setCoinValue(UserInfo.getUserLoggedIn().getMoney());
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    @OnClick(R.id.purchaseVipFragment_btnVipInfo)
    void showBenefit() {
        benefitDialog.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        // check user login
        User author = UserInfo.getUserLoggedIn();
        if (author == null) {
            ((BaseActivity) getActivity()).forceSignIn();
            return;
        }

        selectPackage = adapter.getItem(position);
        if (selectPackage.getPrice() > author.getMoney() && !author.isMBFUser()) {
            ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
            confirmDialog.setMessage(R.string.not_enough_buy_vip);
            confirmDialog.setPositiveButton(R.string.purchase_coin,
                    dialog -> ((PurchaseActivity) getActivity()).switchToCoinPurchaseFragment());
            confirmDialog.setNegativeButton(R.string.cancel);
            confirmDialog.show();
            return;
        }

        VipPackageDetails detail = adapter.getDetails().get(selectPackage.getId());
        confirmDialog.show();
        confirmDialog.setDataSource(selectPackage, detail);
        confirmDialog.setTitle(getString(R.string.buy_vip_confirm_title));
        if (author.isMBFUser()) {
            String message = String.format(getString(R.string.buy_vip_instruction_message),
                    selectPackage.getCode());
            confirmDialog.setMessage(message);
        } else {
            confirmDialog.setMessage(getString(R.string.buy_vip_confirm_message));
        }
    }

    private void refreshItem() {
        adapter.setDetails(details);
        adapter.setItemsNtf(groupedPackage);
        benefitDialog.setData(packages, details, groupedPackage);
        root.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOkButtonClicked() {
        if (UserInfo.getUserLoggedIn().isMBFUser()) {
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:9387"));
            sendIntent.putExtra("sms_body", selectPackage.getCode());
            getContext().startActivity(sendIntent);
        } else {
            progressDialog.show();
            BuyVipParam param = new BuyVipParam(selectPackage.getId());
            PurchaseService service = RestClient.createService(PurchaseService.class);
            subscribe(service.buyVip(param), new ApiSubscriber<Void>() {
                @Override
                public void onSuccess(Void result) {
                    User author = UserInfo.getUserLoggedIn();

                    int currentMoney;
                    if (author.isMBFUser()) {
                        currentMoney = (int) (author.getMoney() + selectPackage.getDiscount());
                    } else {
                        currentMoney = (int) (author.getMoney() - (selectPackage.getPrice() - selectPackage.getDiscount()));
                    }
                    UserInfo.getUserLoggedIn().setMoney(currentMoney);
                    UserInfo.saveUserLogged();
                    coinInfoView.setCoinValue(currentMoney);

                    Intent intent = new Intent(PurchaseFragment.USER_COIN_CHANGED);
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                    AlertMessage.showSuccess(getContext(), R.string.purchase_vip_success);
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }

                @Override
                public void onFinally(boolean success) {
                    super.onFinally(success);
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onBuyPackage(VipPackage vipPackage) {
        // check user login
        User author = UserInfo.getUserLoggedIn();
        if (author == null) {
            ((BaseActivity) getActivity()).forceSignIn();
            return;
        }

        selectPackage = vipPackage;
        if (vipPackage.getPrice() > author.getMoney() && !author.isMBFUser()) {
            ConfirmDialog confirmDialog = new ConfirmDialog(getActivity());
            confirmDialog.setMessage(R.string.not_enough_buy_vip);
            confirmDialog.setPositiveButton(R.string.purchase_coin,
                    dialog -> ((PurchaseActivity) getActivity()).switchToCoinPurchaseFragment());
            confirmDialog.setNegativeButton(R.string.cancel);
            confirmDialog.show();
            return;
        }

        VipPackageDetails detail = adapter.getDetails().get(vipPackage.getId());
        confirmDialog.show();
        confirmDialog.setDataSource(vipPackage, detail);
        confirmDialog.setTitle(getString(R.string.buy_vip_confirm_title));
        if (author.isMBFUser()) {
            String message = String.format(getString(R.string.buy_vip_instruction_message),
                    vipPackage.getCode());
            confirmDialog.setMessage(message);
        } else {
            confirmDialog.setMessage(getString(R.string.buy_vip_confirm_message));
        }
    }

}
