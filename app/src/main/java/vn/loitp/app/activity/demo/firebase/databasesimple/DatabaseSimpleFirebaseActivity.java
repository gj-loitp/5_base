package vn.loitp.app.activity.demo.firebase.databasesimple;

import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.recyclerview.animator.adapters.ScaleInAnimationAdapter;
import vn.loitp.views.recyclerview.animator.animators.SlideInRightAnimator;

public class DatabaseSimpleFirebaseActivity extends BaseFontActivity implements View.OnClickListener {
    private final String ROOT_NODE = "loitp";
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        findViewById(R.id.bt_add).setOnClickListener(this);

        setupUI();

        mFirebaseDatabase.child(ROOT_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null || dataSnapshot.getValue() == null) {
                    LLog.d(TAG, "onDataChange null => return");
                    userList.clear();
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                userList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    //LLog.d(TAG, "onDataChange: " + LSApplication.getInstance().getGson().toJson(user));
                    /*if (!userList.contains(user)) {
                        userList.add(user);
                    }*/
                    userList.add(user);
                }
                LLog.d(TAG, "userList.size: " + userList.size());
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                LLog.e(TAG, "Failed to read app title value " + error.toException());
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_firebase_simple;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                addData();
                break;
        }
    }

    private void addData() {
        User user = new User();
        user.setTimestamp(System.currentTimeMillis());
        user.setAvt(Constants.INSTANCE.getURL_IMG());
        user.setName("loitp");
        user.setMsg("dummy msg " + user.getTimestamp());
        mFirebaseDatabase.child(ROOT_NODE).child(user.getTimestamp() + "").setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        /*if (task.isSuccessful()) {
                            LLog.d(TAG, "onClick isSuccessful");
                        } else {
                            LLog.d(TAG, "onClick !isSuccessful");
                        }*/
                    }
                });
    }

    private void setupUI() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(300);
        recyclerView.setItemAnimator(animator);
        //recyclerView.getItemAnimator().setAddDuration(1000);

        mAdapter = new UserAdapter(activity, userList, new UserAdapter.Callback() {
            @Override
            public void onClick(User user, int position) {
                LToast.show(activity, "onClick To Edit Data: " + user.getMsg());

                user.setMsg("Edited Msg " + System.currentTimeMillis());
                user.setName("Edited Name");
                user.setAvt(Constants.INSTANCE.getURL_IMG_1());
                mFirebaseDatabase.child(ROOT_NODE).child(user.getTimestamp() + "").setValue(user);
            }

            @Override
            public void onLongClick(User user, int position) {
                LToast.show(activity, "onLongClick " + user.getMsg());
                mFirebaseDatabase.child(ROOT_NODE).child(user.getTimestamp() + "").removeValue();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        //recyclerView.setAdapter(mAdapter);

        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //alphaAdapter.setDuration(1000);
        //alphaAdapter.setInterpolator(new OvershootInterpolator());
        //alphaAdapter.setFirstOnly(true);
        //recyclerView.setAdapter(alphaAdapter);

        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);

        LUIUtil.setPullLikeIOSVertical(recyclerView);
    }
}
