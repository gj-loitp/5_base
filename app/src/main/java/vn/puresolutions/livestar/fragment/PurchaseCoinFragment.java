package vn.puresolutions.livestar.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.recycler.CoinAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.common.CoinPackageExtension;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestar.core.api.model.CoinTransaction;
import vn.puresolutions.livestar.core.api.model.ReceiptData;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.model.def.TransactionStatus;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.PurchaseService;
import vn.puresolutions.livestar.core.data.service.CoinTransactionDBService;
import vn.puresolutions.livestar.custom.dialog.AlertDialog;
import vn.puresolutions.livestar.custom.dialog.ConfirmPurchaseDialog;
import vn.puresolutions.livestar.custom.dialog.ProgressDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.custom.view.CoinInfoView;
import vn.puresolutions.livestar.custom.view.GridCenterView;
import vn.puresolutions.livestar.helper.IABHelper;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.aib.Purchase;

/**
 * Created by Phu Tran on 3/30/2016.
 */
public class PurchaseCoinFragment extends BaseFragment implements GridCenterView.OnItemClickListener, ConfirmPurchaseDialog.ConfirmPurchaseDialogListener {
    private static final String COIN_PACKAGE_EXTRA = "COIN_PACKAGE_EXTRA";
    private static final String COIN_PACKAGE_EXTENSION_EXTRA = "COIN_PACKAGE_EXTENSION_EXTRA";

    @BindView(R.id.purchaseCoinFragment_contentView)
    GridCenterView gridCenterView;
    @BindView(R.id.purchaseCoinFragment_vwRoot)
    View root;
    @BindView(R.id.purchaseCoinFragment_pgbLoading)
    ProgressBar progressBar;
    @BindView(R.id.purchaseCoinFragment_vwCoinInfo)
    CoinInfoView coinInfoView;

    private ConfirmPurchaseDialog confirmDialog;
    private CoinAdapter adapter;
    private int selectedPackage;
    private IABHelper iabHelper;
    private CoinTransactionDBService service;
    private ProgressDialog progressDialog;
    private List<CoinPackage> coinPackages;
    private AlertDialog alertDialog;

    private IABHelper.OnBuyProductCompletedListener onBuyProductCompletedListener = new IABHelper.OnBuyProductCompletedListener() {
        @Override
        public void onSuccess(String productId) {
            // save to database
            Purchase purchase = iabHelper.getPurchaseProduct();
            CoinTransaction transaction = new CoinTransaction();
            transaction.setReceiptId(purchase.getOrderId());
            transaction.setStatus(TransactionStatus.UNVERIFIED);
            service.insert(transaction);

            submitCoin(purchase);
        }

        @Override
        public void onFail(String errorMessage) {
            AlertMessage.showError(getActivity(), errorMessage);
        }
    };

    private IABHelper.OnPrepareInventoryListener onPrepareInventoryListener = new IABHelper.OnPrepareInventoryListener() {
        @Override
        public void onPrepareCompleted(boolean isSuccess) {
            if (isSuccess) {
                if (adapter.getItems().size() > 0) {
                    prepareCoinPackage();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    PurchaseService service = RestClient.createService(PurchaseService.class);
                    subscribe(service.getCoinPackage(), new ApiSubscriber<List<CoinPackage>>() {

                        @Override
                        public void onSuccess(List<CoinPackage> result) {
                            coinPackages = result;
                            prepareCoinPackage();
                        }

                        @Override
                        public void onFail(Throwable e) {
                            dismissLoading();
                        }
                    });
                }
            }
        }
    };

    private IABHelper.OnConsumeProductListener onConsumeProductListener = new IABHelper.OnConsumeProductListener() {
        @Override
        public void onConsumeCompleted(boolean isSuccess) {
            if (isSuccess) {
                // update user info
                CoinPackage coinPackage = adapter.getItem(selectedPackage).getCoinPackage();
                int currentMoney = UserInfo.getUserLoggedIn().getMoney() + coinPackage.getQuantity();
                UserInfo.getUserLoggedIn().setMoney(currentMoney);
                UserInfo.saveUserLogged();
                coinInfoView.setCoinValue(currentMoney);

                Intent intent = new Intent(PurchaseFragment.USER_COIN_CHANGED);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                // prepare data again
                iabHelper.queryInventory(onPrepareInventoryListener);

                AlertMessage.showSuccess(getContext(), R.string.purchase_coin_success);
            } else {
                AlertMessage.showError(getContext(), R.string.PURCHASE_COIN_FAILED);
            }
        }
    };

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
        return inflater.inflate(R.layout.fragment_purchase_coin, container, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        service = new CoinTransactionDBService(getContext());
        alertDialog = new AlertDialog(getContext());
        alertDialog.setMessage(R.string.warning_when_user_use_other_account);
        alertDialog.setButton(R.string.ok);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.verifying));
        confirmDialog = new ConfirmPurchaseDialog(getContext());
        confirmDialog.setListener(this);
        coinInfoView.setType(CoinInfoView.Type.COIN);
        coinInfoView.setCoinValue(UserInfo.getUserLoggedIn().getMoney());

        adapter = new CoinAdapter();
        gridCenterView.setAdapter(adapter);
        gridCenterView.setOnItemClickListener(this);

        if (savedInstanceState != null) {
            coinPackages = (ArrayList<CoinPackage>) savedInstanceState
                    .getSerializable(COIN_PACKAGE_EXTRA);
            List<CoinPackageExtension> data = (ArrayList<CoinPackageExtension>) savedInstanceState
                    .getSerializable(COIN_PACKAGE_EXTENSION_EXTRA);
            adapter.setItems(data);
        }

        iabHelper = ((PurchaseActivity) getContext()).getIABHelper();
        iabHelper.prepareInventory(onPrepareInventoryListener);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(COIN_PACKAGE_EXTRA, (ArrayList<CoinPackage>) coinPackages);
        outState.putSerializable(COIN_PACKAGE_EXTENSION_EXTRA, (ArrayList<CoinPackageExtension>) adapter.getItems());
    }

    @Override
    public void onItemClick(View view, int position) {
        // check user login
        User author = UserInfo.getUserLoggedIn();
        if (author == null) {
            ((BaseActivity) getActivity()).forceSignIn();
            return;
        }

        selectedPackage = position;
        CoinPackage coinPackage = adapter.getItem(position).getCoinPackage();
        Purchase purchase = adapter.getItem(position).getPurchase();
        if (purchase == null) {
            confirmDialog.show();
            confirmDialog.setDataSource(coinPackage);
            confirmDialog.setTitle(getString(R.string.buy_vip_confirm_title));
        } else {
            long buyerId = Long.parseLong(purchase.getDeveloperPayload());
            if (buyerId != author.getId()) {
                // buy package by another account
                alertDialog.show();
            } else {
                // resubmit
                iabHelper.setPurchaseProduct(purchase);
                submitCoin(purchase);
            }
        }
    }

    private void submitCoin(Purchase purchase) {
        progressDialog.show();

        // call to server
        ReceiptData data = new ReceiptData();
        data.setOrderId(purchase.getOrderId());
        data.setPackageName(purchase.getPackageName());
        data.setProductId(purchase.getSku());
        data.setPurchaseState(purchase.getPurchaseState());
        data.setPurchaseTime(new Date(purchase.getPurchaseTime()));
        data.setPurchaseToken(purchase.getToken());
        data.setDeveloperPayload(purchase.getDeveloperPayload());

        PurchaseService purchaseService = RestClient.createService(PurchaseService.class);
        Subscription subscription = purchaseService.verifyReceipt(data)
                .map(transaction -> {
                    if (transaction.getStatus() == TransactionStatus.VERIFIED) {
                        // save transaction with status is success
                        service.updateStatus(data.getOrderId(), TransactionStatus.VERIFIED);
                    }
                    return transaction;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<CoinTransaction>() {

                    @Override
                    public void onSuccess(CoinTransaction transaction) {
                        if (transaction.getStatus() == TransactionStatus.VERIFIED) {
                            iabHelper.consumeCurrentProduct(onConsumeProductListener);
                        } else {
                            iabHelper.queryInventory(onPrepareInventoryListener);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        iabHelper.queryInventory(onPrepareInventoryListener);
                    }
                });
        compositeSubscription.add(subscription);
    }

    private void prepareCoinPackage() {
        Subscription subscription = Observable.create((Observable.OnSubscribe<List<CoinPackageExtension>>) subscriber -> {
            try {
                List<CoinPackageExtension> items = new ArrayList<>();
                if (coinPackages != null && coinPackages.size() > 0) {
                    for (CoinPackage coinPackage : coinPackages) {
                        CoinPackageExtension extension;
                        Purchase purchase = iabHelper.getNonConsumePurchase().get(coinPackage.getCode());
                        if (purchase != null) {
                            long buyerId = Long.parseLong(purchase.getDeveloperPayload());
                            CoinTransaction transaction = service.getById(purchase.getOrderId());
                            if (buyerId == UserInfo.getUserLoggedIn().getId() && transaction != null &&
                                    transaction.getStatus() == TransactionStatus.VERIFIED) {
                                iabHelper.consumeProduct(purchase);
                                extension = new CoinPackageExtension(coinPackage, null);
                            } else {
                                purchase.setStatus(Purchase.PurchaseStatus.ERROR);
                                extension = new CoinPackageExtension(coinPackage, purchase);
                            }
                        } else {
                            extension = new CoinPackageExtension(coinPackage, null);
                        }
                        items.add(extension);
                    }
                }
                subscriber.onNext(items);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        }).subscribe(new ApiSubscriber<List<CoinPackageExtension>>() {

            @Override
            public void onSuccess(List<CoinPackageExtension> result) {
                adapter.setItemsNtf(result);
                dismissLoading();
            }

            @Override
            public void onFail(Throwable e) {
                // DO NOTHING
            }

            @Override
            public void onFinally(boolean success) {
                super.onFinally(success);
                dismissLoading();
            }
        });

        compositeSubscription.add(subscription);
    }

    protected void dismissLoading() {
        progressBar.setVisibility(View.GONE);
        progressDialog.dismiss();
        root.setVisibility(View.VISIBLE);
    }

    @Override
    public void onOkButtonClicked() {
        CoinPackageExtension coinPackage = adapter.getItem(selectedPackage);
        iabHelper.buyProduct(coinPackage.getCoinPackage().getCode(),
                onBuyProductCompletedListener);
    }
}
