package vn.loitp.app.activity.demo.firebase.databasesimple;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

//http://www.zoftino.com/firebase-realtime-database-android-example
public class DatabaseSimpleFirebaseActivity extends BaseFontActivity implements View.OnClickListener {
    private final String ROOT_NODE = "loitp";
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        findViewById(R.id.bt_add).setOnClickListener(this);

        mFirebaseDatabase.child(ROOT_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null || dataSnapshot.getValue() == null) {
                    LLog.d(TAG, "onDataChange null => return");
                    return;
                }
                String appTitle = dataSnapshot.getValue().toString();
                LLog.d(TAG, "onDataChange " + appTitle);
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
        mFirebaseDatabase.child(ROOT_NODE).child(System.currentTimeMillis() + "").setValue("dummy msg")
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
}
