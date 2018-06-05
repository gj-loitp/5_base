package vn.loitp.app.activity.demo.firebase.databasesimple;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;
import vn.loitp.app.activity.demo.firebase.database.models.User;
import vn.loitp.core.utilities.LLog;

//http://www.zoftino.com/firebase-realtime-database-android-example
public class DatabaseSimpleFirebaseActivity extends BaseFontActivity {
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("loitp").child(System.currentTimeMillis() + "").setValue("dummy msg")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    LLog.d(TAG, "onClick isSuccessful");
                                } else {
                                    LLog.d(TAG, "onClick !isSuccessful");
                                }
                            }
                        });
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

}
