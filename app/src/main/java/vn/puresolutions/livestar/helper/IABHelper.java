package vn.puresolutions.livestar.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Map;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestarv3.utilities.aib.IabHelper;
import vn.puresolutions.livestarv3.utilities.aib.IabResult;
import vn.puresolutions.livestarv3.utilities.aib.Inventory;
import vn.puresolutions.livestarv3.utilities.aib.Purchase;


/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 9/20/2015
 */
public class IABHelper {

    enum SetupIABStatus {
        NONE,
        INITIALIZING,
        FAILED,
        COMPLETED
    }

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwjLlvxP1Y+0nq9AxB4c/dKgzwJW6cep3tLWA95SPcoXci1L56C5oAYh3ch2NbES13GoedZPxRFqWw8HBu2hYhGaUEn4Hlmmyrk9FoVz7aKlXzwGydm5E3e4JySVKgxBnxeqhzWTHImOoq/+aekSvPKeH/mdpPYGOoCnTQByrnlUI4s4AZWj4Ilz5TuszXloVcw680i/7AXc6Nf6HpOBSvJOwa/ixhK3RyY2CSSU62yz80I49LR8zXKl3eWguDda7QcHxSpIJgoSrrni3z8eZzZ5oCCm01dspp5k8+7te65NUTrJ4Emb5QhMSupk7NSr+aXnb499BHZbzekOurTdYmwIDAQAB";
    private static final int ACTIVITY_REQUEST_CODE_IAB = 0x1111;
    private static final String TAG = IABHelper.class.getName();

    private Context context;
    private IabHelper iabHelper;
    private Map<String, Purchase> nonConsumePurchasesMap;
    private SetupIABStatus setupIABStatus = SetupIABStatus.NONE;
    private OnBuyProductCompletedListener onBuyProductCompletedListener;
    private OnPrepareInventoryListener onPrepareInventoryListener;
    private OnConsumeProductListener onConsumeProductListener;
    private Purchase currentProduct;

    private IabHelper.OnIabSetupFinishedListener onIabSetupFinishedListener = new IabHelper.OnIabSetupFinishedListener() {
        @Override
        public void onIabSetupFinished(IabResult result) {
            if (result.isSuccess()) {
                iabHelper.queryInventoryAsync(queryInventoryFinishedListener);
                Log.i(TAG, "Setup IAB success");
            } else {
                setupIABStatus = SetupIABStatus.FAILED;
                Log.e(TAG, "Setup IAB failed!");
            }
        }
    };

    private IabHelper.OnIabSetupFinishedListener onIabSetupForLoadProductsFinishedListener = new IabHelper.OnIabSetupFinishedListener() {
        @Override
        public void onIabSetupFinished(IabResult result) {
            if (result.isSuccess()) {
                iabHelper.queryInventoryAsync(queryProductsFinishedListener);
                Log.i(TAG, "Setup IAB success");
            } else {
                setupIABStatus = SetupIABStatus.FAILED;
                Log.e(TAG, "Setup IAB failed!");
            }
        }
    };

    private IabHelper.QueryInventoryFinishedListener queryInventoryFinishedListener
            = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (result.isFailure()) {
                setupIABStatus = SetupIABStatus.FAILED;
                Log.d(TAG, "Failed to query inventory" + result);
                return;
            }

            Log.i(TAG, "Query inventory was successfully.");

            setupIABStatus = SetupIABStatus.COMPLETED;
            nonConsumePurchasesMap = inv.getPurchaseMap();

            if (onPrepareInventoryListener != null) {
                onPrepareInventoryListener.onPrepareCompleted(result.isSuccess());
            }
        }
    };

    private IabHelper.QueryInventoryFinishedListener queryProductsFinishedListener
            = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (result.isFailure()) {
                setupIABStatus = SetupIABStatus.FAILED;
                Log.d(TAG, "Failed to query inventory" + result);
                return;
            }
            Log.i(TAG, "Query inventory was successfully.");
            setupIABStatus = SetupIABStatus.COMPLETED;
        }
    };

    private IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener =
            new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase info) {
                    switch (result.getResponse()) {
                        case IabHelper.BILLING_RESPONSE_RESULT_OK:
                            Log.i(TAG, "Buy product success");
                            currentProduct = info;
                            if (onBuyProductCompletedListener != null) {
                                onBuyProductCompletedListener.onSuccess(currentProduct.getSku());
                            }
                            break;

                        case IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED:
                        case IabHelper.IABHELPER_USER_CANCELLED:
                            // do not thing
                            break;

                        default:
                            if (onBuyProductCompletedListener != null) {
                                onBuyProductCompletedListener
                                        .onFail(context.getString(R.string.buyProductFailed, result.getResponse()));
                            }
                            break;
                    }

                }
            };

    private IabHelper.OnConsumeFinishedListener consumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        @Override
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (result.isSuccess()) {
                Log.i(TAG, "Consume success");
                if (nonConsumePurchasesMap != null && nonConsumePurchasesMap.containsKey(purchase.getSku())) {
                    nonConsumePurchasesMap.remove(purchase.getSku());
                }
                currentProduct = null;
            } else {
                Log.e(TAG, "Consume failed: " + purchase);
            }

            if (onConsumeProductListener != null) {
                onConsumeProductListener.onConsumeCompleted(result.isSuccess());
            }
        }
    };

    public IABHelper(Context context) {
        this.context = context;
    }

    public void onCreate() {
        iabHelper = new IabHelper(context, PUBLIC_KEY);
        iabHelper.startSetup(onIabSetupFinishedListener);
    }

    public void prepareInventory(OnPrepareInventoryListener listener) {
        this.onPrepareInventoryListener = listener;
        iabHelper = new IabHelper(context, PUBLIC_KEY);
        iabHelper.startSetup(onIabSetupFinishedListener);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        iabHelper.handleActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        if (iabHelper != null) {
            iabHelper.dispose();
            iabHelper = null;
        }
    }

    public void buyProduct(String productId, OnBuyProductCompletedListener onBuyProductCompletedListener) {
        this.onBuyProductCompletedListener = onBuyProductCompletedListener;
        if (setupIABStatus == SetupIABStatus.FAILED) {
            onBuyProductCompletedListener.onFail(context.getString(R.string.setupIABFailed));
        } else if (currentProduct != null && currentProduct.getSku().equalsIgnoreCase(productId)) {
            onBuyProductCompletedListener.onSuccess(productId);
        } else if (nonConsumePurchasesMap != null && nonConsumePurchasesMap.containsKey(productId)) {
            currentProduct = nonConsumePurchasesMap.get(productId);
            onBuyProductCompletedListener.onSuccess(productId);
        } else {
            iabHelper.launchPurchaseFlow((Activity) context,
                    productId,
                    ACTIVITY_REQUEST_CODE_IAB,
                    onIabPurchaseFinishedListener,
                    String.valueOf(UserInfo.getUserLoggedIn().getId()));
        }
    }

    public void consumeCurrentProduct(OnConsumeProductListener listener) {
        onConsumeProductListener = listener;
        if (currentProduct != null) {
            consumeProduct(currentProduct);
        }
    }

    public void consumeProduct(Purchase purchase) {
        iabHelper.consumeAsync(purchase, consumeFinishedListener);
    }

    public Purchase getPurchaseProduct() {
        return currentProduct;
    }

    public void setPurchaseProduct(Purchase purchase) {
        this.currentProduct = purchase;
    }

    public Map<String, Purchase> getNonConsumePurchase() {
        return nonConsumePurchasesMap;
    }

    public void queryInventory(OnPrepareInventoryListener listener) {
        this.onPrepareInventoryListener = listener;
        iabHelper.queryInventoryAsync(queryInventoryFinishedListener);
    }

    public interface OnBuyProductCompletedListener {
        void onSuccess(String productId);

        void onFail(String errorMessage);
    }

    public interface OnPrepareInventoryListener {
        void onPrepareCompleted(boolean isSuccess);
    }

    public interface OnConsumeProductListener {
        void onConsumeCompleted(boolean isSuccess);
    }
}
