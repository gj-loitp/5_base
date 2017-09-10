package vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.internetbanking;

/**
 * Created by loitp
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.DummyPerson;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BaseHolder> {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<DummyPerson> dummyPersonArrayList;
    private Context context;
    private final int NUMBER_DISPLAY = 3;
    private int width;

    public interface Callback {
        public void onClick(DummyPerson dummyPerson);
    }

    private Callback callback;

    public BankAdapter(Context context, ArrayList<DummyPerson> dummyPersonArrayList, Callback callback) {
        this.dummyPersonArrayList = dummyPersonArrayList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_bank, null);
        width = viewGroup.getMeasuredWidth() / NUMBER_DISPLAY;
        //LLog.d(TAG, "width " + width);
        return new BankHolder(v);
    }

    @Override
    public void onBindViewHolder(BaseHolder baseHolder, int position) {
        if (baseHolder == null) {
            LLog.d(TAG, "onBindViewHolder baseHolder == null");
            return;
        }
        DummyPerson dummyPerson = dummyPersonArrayList.get(position);
        if (baseHolder instanceof BankHolder) {
            BankHolder bankHolder = (BankHolder) baseHolder;
            LImageUtils.loadImage(bankHolder.ivBank, dummyPerson.getImage());
            //bankHolder.simpleDraweeView.getLayoutParams().width = width;

            bankHolder.ivCheck.setVisibility(View.VISIBLE);
            bankHolder.viewGroupMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClick(dummyPerson);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != dummyPersonArrayList ? dummyPersonArrayList.size() : 0);
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        public BaseHolder(View itemView) {
            super(itemView);
        }
    }

    public class BankHolder extends BaseHolder {
        private SimpleDraweeView ivBank;
        private ImageView ivCheck;
        private RelativeLayout viewGroupMain;

        public BankHolder(View view) {
            super(view);
            this.ivBank = (SimpleDraweeView) view.findViewById(R.id.iv_bank);
            this.ivCheck = (ImageView) view.findViewById(R.id.iv_check);
            this.viewGroupMain = (RelativeLayout) view.findViewById(R.id.view_group_main);
        }
    }
}