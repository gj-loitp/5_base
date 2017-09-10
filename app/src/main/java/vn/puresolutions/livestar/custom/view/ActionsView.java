package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.recycler.ActionAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Action;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.LiveService;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.dialog.ConfirmDialog;
import vn.puresolutions.livestar.custom.dialog.VoteSuccessDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.decoration.ActionItemDecoration;
import vn.puresolutions.livestar.helper.AnimationViewCreator;
import vn.puresolutions.livestar.helper.AnimationViewHolder;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;

/**
 * Created by Phu Tran on 8/23/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class ActionsView extends IRoomView implements NotificationCenter.NotificationCenterListener {

    @BindView(R.id.viewActions_rclActions)
    RecyclerView rclActions;
    @BindView(R.id.viewActions_prgLoading)
    ProgressBar prgLoading;
    @BindView(R.id.viewActions_tvNoData)
    TextView tvNoData;
    @BindView(R.id.viewActions_vwDivider)
    View vwDivider;
    @BindView(R.id.viewActions_tvUserCoin)
    TextView tvUserCoin;
    @BindView(R.id.viewActions_lnlUserCoin)
    View lnlUserCoin;

    private VoteSuccessDialog voteSuccessDialog;
    private ActionAdapter adapter;
    private int usingMoney;
    private ArrayList<View> sentActionViews = new ArrayList<>();
    private AnimationViewCreator animationCreator;

    public ActionsView(Context context) {
        super(context);
        init(context);
    }

    public ActionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_actions, this);
        ButterKnife.bind(this);

        voteSuccessDialog = new VoteSuccessDialog(context);

        updateUserMoney();

        animationCreator = new AnimationViewCreator(getContext());
        adapter = new ActionAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rclActions.addItemDecoration(new ActionItemDecoration(context, R.dimen.action_recycler_item_padding));
        rclActions.setLayoutManager(layout);
        rclActions.setHasFixedSize(true);
        rclActions.setAdapter(adapter);
        adapter.setVotedListener((action, position, view) -> {
            if (UserInfo.checkLoginAndShowDialog(context) && UserInfo.isLoggedIn()) {
                voteAction(position, view);
            }
        });

        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.addObserver(this, NotificationCenter.userMoneyChanged);
        notificationCenter.addObserver(this, NotificationCenter.ReceivedAction);
        notificationCenter.addObserver(this, NotificationCenter.UserExpChanged);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.removeObserver(this, NotificationCenter.userMoneyChanged);
        notificationCenter.removeObserver(this, NotificationCenter.ReceivedAction);
        notificationCenter.removeObserver(this, NotificationCenter.UserExpChanged);
    }

    @Override
    public void loadData() {
        prgLoading.setVisibility(VISIBLE);
        RoomService service = RestClient.createService(RoomService.class);
        subscribe(service.getActions(RoomCenter.getInstance().roomId), new ApiSubscriber<List<Action>>() {
            @Override
            public void onSuccess(final List<Action> result) {
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

    public void onResume() {
        updateUserMoney();
    }

    @Override
    public void onViewVisible() {
        updateUserMoney();
    }

    private void updateUserMoney() {
        if (UserInfo.isLoggedIn()) {
            lnlUserCoin.setVisibility(VISIBLE);
            vwDivider.setVisibility(VISIBLE);
            AmountUtils.toCoin(tvUserCoin, UserInfo.getUserLoggedIn().getMoney());
        } else {
            lnlUserCoin.setVisibility(GONE);
            vwDivider.setVisibility(GONE);
        }
    }

    private void voteAction(int pos, View view) {
        Action action = adapter.getItem(pos);
        int itemPrice = action.getPrice();
        float discount = action.getDiscount();
        usingMoney = (int) (itemPrice - (itemPrice * discount / 100));

        if (usingMoney <= UserInfo.getUserLoggedIn().getMoney()) {
            sentActionViews.add(view);
            LiveService service = RestClient.createService(LiveService.class);
            subscribe(service.voteAction(RoomCenter.getInstance().roomId, action.getId(), ""), new ApiSubscriber<Void>() {
                @Override
                public void onSuccess(Void result) {
                    User loggedUser = UserInfo.getUserLoggedIn();
                    loggedUser.setMoney(loggedUser.getMoney() - usingMoney);
                    UserInfo.saveUserLogged();
                    updateUserMoney();
                    voteSuccessDialog.show();
                }

                @Override
                public void onFail(Throwable e) {
                    handleException(e);
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
        if (id == NotificationCenter.userMoneyChanged) {
            updateUserMoney();
        } else if (id == NotificationCenter.UserExpChanged && sentActionViews.size() > 0) {
            startExpAnimation(sentActionViews.get(0), (int) args[0]);
            sentActionViews.remove(0);
        } else if (id == NotificationCenter.ReceivedAction) {
            Action action = (Action) args[0];
            for (Action item : adapter.getItems()) {
                if (item.getId() == action.getId() && (action.getPercent() > item.getPercent() || action.getPercent() == 0)) {
                    item.setPrice(action.getPrice());
                    item.setVoted(action.getVoted());
                    item.setPercent(Math.min(action.getPercent(), 100));
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void startExpAnimation(View view, long exp) {
        AnimationViewHolder holder = animationCreator.createGiftExpView(view, exp);
        rootView.addView(holder.getAnimationView(), holder.getParams());
        holder.getAnimationView().bringToFront();
        holder.start();
    }
}
