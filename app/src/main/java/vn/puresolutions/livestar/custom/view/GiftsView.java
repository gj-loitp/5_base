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

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.activity.PurchaseActivity;
import vn.puresolutions.livestar.adapter.recycler.GiftAdapter;
import vn.puresolutions.livestarv3.app.UserInfo;
import vn.puresolutions.livestar.core.api.model.Gift;
import vn.puresolutions.livestar.core.api.model.User;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.LiveService;
import vn.puresolutions.livestar.core.api.service.RoomService;
import vn.puresolutions.livestar.custom.dialog.ConfirmDialog;
import vn.puresolutions.livestar.custom.dialog.GiftQuantityDialog;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.decoration.base.ListHorizontalLayoutDecoration;
import vn.puresolutions.livestar.helper.AnimationViewCreator;
import vn.puresolutions.livestar.helper.AnimationViewHolder;
import vn.puresolutions.livestar.helper.NotificationCenter;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.old.AmountUtils;

/**
 * Created by Phu Tran on 8/17/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class GiftsView extends IRoomView implements NotificationCenter.NotificationCenterListener {

    @BindView(R.id.viewGifts_rclGifts)
    RecyclerView rclGifts;
    @BindView(R.id.viewGifts_tvTitleUserCoin)
    TextView tvTitleUserCoin;
    @BindView(R.id.viewGifts_tvUserCoin)
    TextView tvUserCoin;
    @BindView(R.id.viewGifts_prgLoading)
    ProgressBar prgLoading;
    @BindView(R.id.viewGifts_tvNormalGift)
    TextView tvNormalGift;
    @BindView(R.id.viewGifts_tvNoData)
    TextView tvNoData;

    private AnimationViewCreator animationCreator;
    private ArrayList<View> sentGiftView = new ArrayList<>();
    private GiftAdapter adapter;
    private int usingMoney;
    private int selectedPos;
    private View selectedView;
    private GiftQuantityDialog giftQuantityDialog;

    public GiftsView(Context context) {
        super(context);
        init(context);
    }

    public GiftsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_gifts, this);
        ButterKnife.bind(this);

        updateUserMoney();

        giftQuantityDialog = new GiftQuantityDialog(getContext());
        giftQuantityDialog.setDialogListener(this::sendGift);
        animationCreator = new AnimationViewCreator(getContext());
        adapter = new GiftAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rclGifts.addItemDecoration(new ListHorizontalLayoutDecoration(context, R.dimen.on_air_recycler_item_padding));
        rclGifts.setLayoutManager(layout);
        rclGifts.setHasFixedSize(true);
        rclGifts.setAdapter(adapter);
        adapter.setItemClickListener((view, position) -> {
            if (UserInfo.checkLoginAndShowDialog(context) && UserInfo.isLoggedIn() && !adapter.isLoading()) {
                selectedView = view;
                selectedPos = position;
                sendGift(1);
            }
        });
        adapter.setItemLongClickListener((view, position) -> {
            if (UserInfo.checkLoginAndShowDialog(context) && UserInfo.isLoggedIn() &&  !adapter.isLoading()) {
                selectedView = view;
                selectedPos = position;
                giftQuantityDialog.show();
            }
        });

        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.addObserver(this, NotificationCenter.userMoneyChanged);
        notificationCenter.addObserver(this, NotificationCenter.UserExpChanged);
    }

    public void onDestroy() {
        NotificationCenter notificationCenter = NotificationCenter.getInstance();
        notificationCenter.removeObserver(this, NotificationCenter.userMoneyChanged);
        notificationCenter.removeObserver(this, NotificationCenter.UserExpChanged);
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
            tvTitleUserCoin.setVisibility(VISIBLE);
            tvUserCoin.setVisibility(VISIBLE);
            AmountUtils.toCoin(tvUserCoin, UserInfo.getUserLoggedIn().getMoney(), true);
        } else {
            tvTitleUserCoin.setVisibility(GONE);
            tvUserCoin.setVisibility(GONE);
        }
    }

    @Override
    public void loadData() {
        prgLoading.setVisibility(VISIBLE);
        RoomService service = RestClient.createService(RoomService.class);
        subscribe(service.getGifts(RoomCenter.getInstance().roomId), new ApiSubscriber<List<Gift>>() {
            @Override
            public void onSuccess(final List<Gift> result) {
                if (result.size() > 0) {
                    adapter.setItemsNtf(result);
                    tvNormalGift.setVisibility(VISIBLE);
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

    private void sendGift(int quantity) {
        Gift gift = adapter.getItem(selectedPos);
        int itemPrice = gift.getPrice();
        float discount = gift.getDiscount();
        usingMoney = (int) (itemPrice - (itemPrice * discount / 100)) * quantity;

        if (usingMoney <= UserInfo.getUserLoggedIn().getMoney()) {
            adapter.setLoadingPos(selectedPos);
            sentGiftView.add(selectedView);
            triggerAsync(gift.getId(), quantity,UserInfo.getUserLoggedIn().getMoney());
            startGiftItemAnimation(selectedView);
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

    private void triggerAsync(int giftId, int quantity, int usercoin) {
        LiveService service = RestClient.createService(LiveService.class);
        subscribe(service.sendGift(RoomCenter.getInstance().roomId, giftId, quantity, ""), new ApiSubscriber<Void>() {
            @Override
            public void onSuccess(Void result) {
                User loggedUser = UserInfo.getUserLoggedIn();
                //loggedUser.setMoney(loggedUser.getMoney() - usingMoney);
                loggedUser.setMoney(usercoin - usingMoney);
                UserInfo.saveUserLogged();
                updateUserMoney();
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
    }

    @Override
    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.userMoneyChanged) {
            updateUserMoney();
        } else if (id == NotificationCenter.UserExpChanged && sentGiftView.size() > 0) {
            startExpAnimation(sentGiftView.get(0), (int) args[0]);
            sentGiftView.remove(0);
        }
    }

    /**
     * Animation
     */
    private void startGiftItemAnimation(View view) {
        startAnimation(view, null, false);
    }

    private void startExpAnimation(View view, long exp) {
        startAnimation(view, exp, true);
    }

    private void startAnimation(View view, Long exp, boolean isExpAnimation) {
        SimpleDraweeView giftImage = (SimpleDraweeView) view.findViewById(R.id.giftItem_imgGift);
        AnimationViewHolder holder;
        if (isExpAnimation) {
            holder = animationCreator.createGiftExpView(giftImage, exp);
        } else {
            String imageUrl = adapter.getItem(selectedPos).getImage();
            holder = animationCreator.createGiftItemView(giftImage, imageUrl);
        }
        rootView.addView(holder.getAnimationView(), holder.getParams());
        holder.getAnimationView().bringToFront();
        holder.start();
    }
}
