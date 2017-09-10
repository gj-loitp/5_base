package vn.puresolutions.livestar.custom.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.adapter.recycler.GiftQuantityAdapter;
import vn.puresolutions.livestar.core.api.model.GiftQuantity;
import vn.puresolutions.livestar.core.utilities.Sessions;
import vn.puresolutions.livestar.core.utilities.SessionsKey;


public class GiftQuantityDialog extends IRoomDialog {

    public interface GiftQuantityDialogListener {
        void onOkButtonClicked(int quantity);
    }

    @BindView(R.id.dialogGiftQuantity_cbRemember)
    CheckBox cbRemember;
    @BindView(R.id.dialogGiftQuantity_lvQuantity)
    RecyclerView rclQuantity;

    private GiftQuantityDialogListener dialogListener;
    private Sessions sessions;
    private GiftQuantityAdapter adapter;

    public GiftQuantityDialog(Context context) {
        super(context);
    }

    public void setDialogListener(GiftQuantityDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.selected_gift_quantity);

        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rclQuantity = (RecyclerView) findViewById(R.id.dialogGiftQuantity_lvQuantity);
        rclQuantity.setLayoutManager(layout);

        adapter = new GiftQuantityAdapter();
        adapter.setItemClickListener((view, position) -> adapter.setSelectedPos(position));
        rclQuantity.setAdapter(adapter);
        createItem();

        sessions = Sessions.getInstance(getContext());
        adapter.setSelectedPos(sessions.get(SessionsKey.GIFT_QUANTITY_POSITION_KEY, 0));
    }

    private void createItem() {
        int[] quantity = new int[]{
                1,
                10,
                30,
                66,
                188,
                520,
                1314
        };

        Integer[] resource = new Integer[]{
                0,
                0,
                0,
                R.drawable.ic_heart_no_background,
                R.drawable.ic_vip,
                R.drawable.ic_ilu,
                R.drawable.ic_two_heart
        };

        List<GiftQuantity> items = new ArrayList<>();
        for (int i = 0; i < quantity.length; i++) {
            GiftQuantity item = new GiftQuantity(quantity[i], resource[i]);
            items.add(item);
        }
        adapter.setItemsNtf(items);
    }

    @Override
    void onOkButtonClick() {
        super.onOkButtonClick();
        int selectedPos = adapter.getSelectedPos();
        if (dialogListener != null) {
            dialogListener.onOkButtonClicked(adapter.getItem(selectedPos).getQuantity());
        }

        if (cbRemember.isChecked()) {
            // save to session
            sessions.put(SessionsKey.GIFT_QUANTITY_POSITION_KEY, selectedPos);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_gift_quantity;
    }
}
