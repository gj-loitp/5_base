package vn.puresolutions.livestar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.tab.PurchaseTabAdapter;
import vn.puresolutions.livestar.adapter.tab.TabAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.AccountService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;

/**
 * @author hoangphu
 * @since 11/27/16
 */
public class PurchaseFragment extends TabLayoutFragment {
    public static final String USER_COIN_CHANGED = "USER_COIN_CHANGED";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                String title = adapter.getTitle(getContext(), position);
                ((PurchaseActivity) getActivity()).setTitle(title);
                if (position == 1 || position == 0) {
                    Log.i("PurchaseFragment", "loadProfile");
                    loadProfile();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadProfile() {
        AccountService service = RestClient.createService(AccountService.class);
        subscribe(service.getProfile(), new ApiSubscriber<User>() {
            @Override
            public void onSuccess(User user) {
                UserInfo.setUserLoggedIn(user);
                Log.d("shit", "loadProfile");
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setMsg(Constants.MSG);
                EventBus.getDefault().post(messageEvent);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    public static class MessageEvent {
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    @Override
    public TabAdapter getAdapter() {
        return new PurchaseTabAdapter();
    }

    public void switchToCoinPurchaseFragment() {
        viewPager.setCurrentItem(0, true);
    }
}
