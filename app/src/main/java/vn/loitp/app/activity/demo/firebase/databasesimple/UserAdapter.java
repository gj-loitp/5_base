package vn.loitp.app.activity.demo.firebase.databasesimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.utilities.LImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.loitp.app.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final String logTag = getClass().getSimpleName();

    public interface Callback {
        void onClick(User user, int position);

        void onLongClick(User user, int position);
    }

    private Context context;
    private Callback callback;
    private List<User> userList;

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvMsg, tvTime;
        public CircleImageView avt;
        public LinearLayout rootView;

        public UserViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvTime = view.findViewById(R.id.tvTime);
            tvMsg = view.findViewById(R.id.tvMsg);
            avt = view.findViewById(R.id.avt);
            rootView = view.findViewById(R.id.rootView);
        }
    }

    public UserAdapter(Context context, List<User> userList, Callback callback) {
        this.context = context;
        this.userList = userList;
        this.callback = callback;
    }

    @NotNull
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

        LImageUtil.Companion.load(context, user.getAvt(), holder.avt);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick(user, position);
                }
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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