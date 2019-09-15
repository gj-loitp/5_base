package vn.loitp.app.activity.demo.firebase.databasesimple;

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;
import com.core.utilities.LLog;
import com.views.imageview.circle.LCircleImageView;

import java.util.List;

import loitp.basemaster.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final String TAG = getClass().getSimpleName();

    public interface Callback {
        public void onClick(User user, int position);

        public void onLongClick(User user, int position);
    }

    private Context context;
    private Callback callback;
    private List<User> userList;

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMsg, tvTime;
        public LCircleImageView avt;
        public LinearLayout rootView;

        public UserViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
            tvMsg = (TextView) view.findViewById(R.id.tv_msg);
            avt = (LCircleImageView) view.findViewById(R.id.avt);
            rootView = (LinearLayout) view.findViewById(R.id.root_view);
        }
    }

    public UserAdapter(Context context, List<User> userList, Callback callback) {
        this.context = context;
        this.userList = userList;
        this.callback = callback;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvMsg.setText(user.getMsg());
        holder.tvTime.setText(user.getTimestamp() + "");

        LImageUtil.INSTANCE.load(context, user.getAvt(), holder.avt);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLog.INSTANCE.d(TAG, "onClick");
                if (callback != null) {
                    callback.onClick(user, position);
                }
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LLog.INSTANCE.d(TAG, "onLongClick");
                if (callback != null) {
                    callback.onLongClick(user, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        }
        return userList.size();
    }
}