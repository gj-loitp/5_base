package vn.loitp.app.activity.database.realm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import loitp.basemaster.R;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LDeviceUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;

public class RealmActivity extends BaseActivity implements View.OnClickListener {
    private Realm mRealm;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(activity).getRealm();
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.bt_realm).setOnClickListener(this);
        findViewById(R.id.bt_add).setOnClickListener(this);

        // refresh the realm instance
        RealmController.with(this).refresh();

        //getAll();
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_realm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_realm:
                Intent intent = new Intent(activity, EbookWithRealmActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
                break;
            case R.id.bt_add:
                add();
                break;
        }
    }

    private void add() {
        LLog.d(TAG, "add " + RealmController.getInstance().getBooks().size());
        mRealm.beginTransaction();

        MyBook myBook = new MyBook();
        myBook.setId(RealmController.getInstance().getBooks().size() + System.currentTimeMillis());
        myBook.setTitle("Demo Book " + LDeviceUtil.getRandomNumber(Integer.MAX_VALUE));

        //logMyBook(myBook);

        mRealm.copyToRealm(myBook);
        mRealm.commitTransaction();

        getAll();
    }

    private void logMyBook(MyBook myBook) {
        LLog.d(TAG, "_______________________________________");
        LLog.d(TAG, ">>> " + myBook.getId() + " - " + myBook.getTitle());
    }

    private void logMyBook(List<MyBook> myBookList) {
        LLog.d(TAG, "_______________________________________");
        for (MyBook mb : myBookList) {
            LLog.d(TAG, ">>> " + mb.getId() + " - " + mb.getTitle());
        }
    }

    private void printUI(List<MyBook> myBookList) {
        String s = "";
        for (MyBook mb : myBookList) {
            s += mb.getId() + " - " + mb.getTitle() + "\n";
        }
        tv.setText(s);
    }

    private void getAll() {
        List<MyBook> myBookList = RealmController.getInstance().getMyBookList();
        LLog.d(TAG, "getAll: " + myBookList.size());
        //logMyBook(myBookList);
        printUI(myBookList);
    }
}
