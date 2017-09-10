package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.recycler.LoungeAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Lounge;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.LiveService;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.dialog.BuyLoungeDialog;
import vn.puresolutions.livestar.custom.dialog.ConfirmDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.decoration.LoungeLayoutDecoration;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.RoomCenter;


/**
 * @author hoangphu
 * @since 8/29/16
 */
public class LoungeView extends IRoomView implements BuyLoungeDialog.BuyLoungeDialogListener, NotificationCenter.NotificationCenterListener {

    private static final int COLUMN_NUMBER = 2;

    @BindView(R.id.viewLounges_rclLounges)
    RecyclerView rclLounges;
    @BindView(R.id.viewLounges_tvNoData)
    TextView tvNoData;
    @BindView(R.id.viewLounges_prgLoading)
    ProgressBar prgLoading;

    private LoungeAdapter adapter;
    private BuyLoungeDialog buyLoungeDialog;
    private int selectedPos;
    private int usingMoney;

    public LoungeView(Context context) {
        super(context);
        init(context);
    }

    public LoungeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoungeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_lounges, this);
        ButterKnife.bind(this);

        buyLoungeDialog = new BuyLoungeDialog(context);
        buyLoungeDialog.setDialogListener(this);

        adapter = new LoungeAdapter();
        GridLayoutManager layout = new GridLayoutManager(context, COLUMN_NUMBER);
        rclLounges.addItemDecoration(new LoungeLayoutDecoration(COLUMN_NUMBER, context, R.dimen.action_recycler_item_padding));
        rclLounges.setLayoutManager(layout);
        rclLounges.setHasFixedSize(true);
        rclLounges.setAdapter(adapter);
        adapter.setItemClickListener((view, position) -> {
            if (UserInfo.checkLoginAndShowDialog(context) && UserInfo.isLoggedIn()) {
                selectedPos = position;
                buyLoungeDialog.show();
                buyLoungeDialog.setPrice(adapter.getItem(position).getCost());
            }
        });

        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.addObserver(this, NotificationCenter.BuyLounge);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.removeObserver(this, NotificationCenter.BuyLounge);
    }

    @Override
    public void loadData() {
        prgLoading.setVisibility(VISIBLE);
        RoomService service = RestClient.createService(RoomService.class);
        subscribe(service.getLounges(RoomCenter.getInstance().roomId), new ApiSubscriber<List<Lounge>>() {
            @Override
            public void onSuccess(final List<Lounge> result) {
                if (result.size() > 0) {
                    adapter.setItemsNtf(result);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }

            @Override
            public void onFinally(boolean success) {
                prgLoading.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onOkButtonClicked(int price) {
        usingMoney = price;
        if (usingMoney <= UserInfo.getUserLoggedIn().getMoney()) {
            adapter.setLoadingPos(selectedPos);
            LiveService service = RestClient.createService(LiveService.class);
            subscribe(service.buyLounge(RoomCenter.getInstance().roomId, selectedPos, price, ""), new ApiSubscriber<Void>() {
                @Override
                public void onSuccess(Void result) {
                    User loggedUser = UserInfo.getUserLoggedIn();
                    loggedUser.setMoney(loggedUser.getMoney() - usingMoney);
                    UserInfo.saveUserLogged();
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
                }

                @Override
                public void onFinally(boolean success) {
                    adapter.endLoading();
                }
            });
        } else {
            ConfirmDialog confirmDialog = new ConfirmDialog(getContext());
            confirmDialog.setMessage(R.string.not_enough_buy_gift);
            confirmDialog.setPositiveButton(R.string.purchase_coin, dialog -> {
                Intent intent = new Intent(getContext(), PurchaseActivity.class);
                getContext().startActivity(intent);
            });
            confirmDialog.setNegativeButton(R.string.cancel);
            confirmDialog.show();
        }
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        Lounge lounge = (Lounge) args[0];
        for (int i = 0; i < adapter.getActualItemCount(); i++) {
            if (i == lounge.getIndex()) {
                Lounge data = adapter.getItem(i);
                data.setCost(lounge.getCost());
                data.setUser(lounge.getUser());
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
