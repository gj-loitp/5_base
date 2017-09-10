package vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.bank.CoinTransaction;
import vn.puresolutions.livestar.corev3.api.model.v3.bank.MobilePackage;
import vn.puresolutions.livestar.corev3.api.model.v3.bank.PackageCoinItem;
import vn.puresolutions.livestar.corev3.api.model.v3.bank.PackagePayment;
import vn.puresolutions.livestar.corev3.api.model.v3.bank.TransactionStatus;
import vn.puresolutions.livestar.corev3.api.restclient.RestClient;
import vn.puresolutions.livestar.corev3.api.service.LSService;
import vn.puresolutions.livestar.corev3.data.service.CoinTransactionDBService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestarv3.activity.bank.getcoin.IABHelper;
import vn.puresolutions.livestarv3.view.LChooseView;
import vn.puresolutions.livestarv3.base.BaseFragment;
import vn.puresolutions.livestarv3.utilities.aib.Purchase;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LDialogUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmCoinNative extends BaseFragment {
    private IABHelper iabHelper;
    private CoinTransactionDBService service;
    ArrayList<PackagePayment> lstCoinItem;
    private final String TAG = getClass().getSimpleName();
    private LChooseView lChooseView50;
    private LChooseView lChooseView100;
    private LChooseView lChooseView200;

    private enum ChoosePackage {
        NONE, P_50, P_100, P_200
    }

    private ChoosePackage currentChoosePackage = ChoosePackage.NONE;

    private IABHelper.OnBuyProductCompletedListener onBuyProductCompletedListener = new IABHelper.OnBuyProductCompletedListener() {
        @Override
        public void onSuccess(String productId) {
            Log.d(TAG,"onSuccess");
            iabHelper.consumeCurrentProduct(onConsumeProductListener);
            // save to database
            Purchase purchase = iabHelper.getPurchaseProduct();
            CoinTransaction transaction = new CoinTransaction();
            transaction.setReceiptId(purchase.getOrderId());
            transaction.setStatus(TransactionStatus.UNVERIFIED);
            service.insert(transaction);
            submitCoin(purchase);
            iabHelper.consumeCurrentProduct(onConsumeProductListener);
        }

        @Override
        public void onFail(String errorMessage) {
            Log.d(TAG,"onFail");
            AlertMessage.showError(getActivity(), errorMessage);
        }
    };

    private IABHelper.OnConsumeProductListener onConsumeProductListener = new IABHelper.OnConsumeProductListener() {
        @Override
        public void onConsumeCompleted(boolean isSuccess) {
            if (isSuccess) {
                // update user info
               /* CoinPackage coinPackage = adapter.getItem(selectedPackage).getCoinPackage();
                int currentMoney = UserInfo.getUserLoggedIn().getMoney() + coinPackage.getQuantity();
                UserInfo.getUserLoggedIn().setMoney(currentMoney);
                UserInfo.saveUserLogged();
                coinInfoView.setCoinValue(currentMoney);

                Intent intent = new Intent(PurchaseFragment.USER_COIN_CHANGED);
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                // prepare data again
                iabHelper.queryInventory(onPrepareInventoryListener);*/

                AlertMessage.showSuccess(getContext(), R.string.purchase_coin_success);
            } else {
                AlertMessage.showError(getContext(), R.string.PURCHASE_COIN_FAILED);
            }
        }
    };

    private IABHelper.OnPrepareInventoryListener onPrepareInventoryListener = new IABHelper.OnPrepareInventoryListener() {
        @Override
        public void onPrepareCompleted(boolean isSuccess) {
            if (isSuccess) {
               /* if (adapter.getItems().size() > 0) {
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
                }*/
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_coin_native, container, false);
        lChooseView50 = (LChooseView) view.findViewById(R.id.l_choose_view_50);
        lChooseView100 = (LChooseView) view.findViewById(R.id.l_choose_view_100);
        lChooseView200 = (LChooseView) view.findViewById(R.id.l_choose_view_200);
        //
        //iabHelper = new IABHelper(getActivity());
        iabHelper = ((GetCoinPagerActivity) getContext()).getIABHelper();
        iabHelper.prepareInventory(onPrepareInventoryListener);
        lstCoinItem = new ArrayList<>();
        //TODO fix hardcode
       /* lChooseView50.setTextTvContent("50 xu");
        lChooseView50.setTextTvInfo("(50.000vnd)");
        lChooseView100.setTextTvContent("100 xu");
        lChooseView100.setTextTvInfo("(100.000vnd)");
        lChooseView200.setTextTvContent("200 xu");
        lChooseView200.setTextTvInfo("(200.000vnd)");*/
        initCoinPackage();
        lChooseView50.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChoosePackage = ChoosePackage.P_50;
                setChooseBranch(lChooseView50, lstCoinItem.get(0));
                //iabHelper.buyProduct(lstCoinItem.get(0).getIapCode(), onBuyProductCompletedListener);
            }
        });

        lChooseView100.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChoosePackage = ChoosePackage.P_100;
                setChooseBranch(lChooseView100, lstCoinItem.get(1));
            }
        });
        lChooseView200.setOnItemClick(new LChooseView.Callback() {
            @Override
            public void onClick() {
                currentChoosePackage = ChoosePackage.P_200;
                setChooseBranch(lChooseView200, lstCoinItem.get(2));
            }
        });
        return view;
    }

    private void initCoinPackage() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPackageCoin(), new ApiSubscriber<MobilePackage>() {
            @Override
            public void onSuccess(MobilePackage mobilePackage) {
                lstCoinItem =mobilePackage.getIap().getPackages();
                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                String price50 = formatter.format(lstCoinItem.get(0).getPrice());
                String price100 = formatter.format(lstCoinItem.get(1).getPrice());
                String price200 = formatter.format(lstCoinItem.get(2).getPrice());
                lChooseView50.setTextTvContent(String.valueOf(lstCoinItem.get(0).getCoin()) + " xu");
                lChooseView50.setTextTvInfo(price50 + " vnd");
                lChooseView100.setTextTvContent(String.valueOf(lstCoinItem.get(1).getCoin()) + " xu");
                lChooseView100.setTextTvInfo(price100 + " vnd");
                lChooseView200.setTextTvContent(String.valueOf(lstCoinItem.get(2).getCoin()) + " xu");
                lChooseView200.setTextTvInfo(price200 + " vnd");
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    private void setChooseBranch(LChooseView lChooseView, PackagePayment packagePayment) {
        lChooseView50.setChecked(false);
        lChooseView100.setChecked(false);
        lChooseView200.setChecked(false);

        lChooseView.setChecked(true);

        String packageName = "Gói " + lChooseView.getTextTvContent();
        String packagePrice = lChooseView.getTextTvInfo().replace("(", "").replace(")", "");
        LDialogUtils.showDialogBuyPackage(getActivity(), packageName, packagePrice, new LDialogUtils.CallbackDialogBuyPackage() {
            @Override
            public void onCancel() {
                LLog.d(TAG, "onCancel");
            }

            @Override
            public void onOkay() {
                LLog.d(TAG, "onOkay");
                iabHelper.buyProduct(packagePayment.getCode(), onBuyProductCompletedListener);
            }
        });
    }

    private void submitCoin(Purchase purchase) {

    }
}

