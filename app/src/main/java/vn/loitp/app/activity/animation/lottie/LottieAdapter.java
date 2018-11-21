package vn.loitp.app.activity.animation.lottie;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import loitp.basemaster.R;

public class LottieAdapter extends RecyclerView.Adapter<LottieAdapter.LottieViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private List<String> itemList;

    public class LottieViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rootView;
        public TextView tv;
        public LottieAnimationView lottieAnimationView;

        public LottieViewHolder(View view) {
            super(view);
            rootView = (RelativeLayout) view.findViewById(R.id.root_view);
            tv = (TextView) view.findViewById(R.id.tv);
            lottieAnimationView = (LottieAnimationView) view.findViewById(R.id.animation_view);
        }
    }

    public LottieAdapter(Context context, List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public LottieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottie_view, parent, false);
        return new LottieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LottieViewHolder holder, int position) {
        String s = itemList.get(position);
        holder.tv.setText(s);
        holder.lottieAnimationView.setAnimation("lottie/a.json");
        //holder.lottieAnimationView.playAnimation();
        //holder.lottieAnimationView.loop(true);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.lottieAnimationView.playAnimation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}