package vn.puresolutions.livestar.adapter.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.fragment.PurchaseCoinFragment;
import vn.puresolutions.livestar.fragment.PurchaseVipFragment;
import vn.puresolutions.livestar.fragment.WebViewFragment;

/**
 * Created by Phu Tran on 4/4/2016.
 */
public class PurchaseTabAdapter implements TabAdapter {
    @Override
    public Class<? extends Fragment> getFragmentClass(int position) {
        switch (position) {
            case 0:
                return PurchaseCoinFragment.class;
            case 1:
                return PurchaseVipFragment.class;
            default:
                return WebViewFragment.class;
        }
    }

    @Override
    public String getTitle(Context context, int position) {
        int resourceId;
        switch (position) {
            case 0:
                resourceId = R.string.purchase_coin;
                break;
            case 1:
                resourceId = R.string.purchase_vip;
                break;
            default:
                resourceId = R.string.other_method;
                break;
        }
        return context.getString(resourceId);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Bundle getArgument(int position) {
        if (position == 2) {
            Bundle args = new Bundle();
            /*String url = LSApplication.getInstance().getString(R.string.ls_web_payment_url,
                    Sessions.getInstance(LSApplication.getInstance()).get(SessionsKey.TOKEN_KEY, ""));*/
            String url = LSApplication.getInstance().getString(R.string.ls_web_payment_url,
                    UserInfo.getToken());
            args.putString(WebViewFragment.BUNDLE_KEY_URL, url);
            return args;
        }
        return null;
    }
}