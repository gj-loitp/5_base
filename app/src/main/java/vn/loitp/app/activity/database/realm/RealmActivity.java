package vn.loitp.app.activity.database.realm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import loitp.basemaster.R;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;

public class RealmActivity extends BaseActivity implements View.OnClickListener {
    private Realm mRealm;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(getApplication()).getRealm();
        ll = (LinearLayout) findViewById(R.id.ll);
        findViewById(R.id.bt_realm).setOnClickListener(this);
        findViewById(R.id.bt_add).setOnClickListener(this);

        // refresh the realm instance
        RealmController.with(getApplication()).refresh();

        getAll();
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
        myBook.setTitle((RealmController.getInstance().getBooks().size() + 1) + " title");

        //logMyBook(myBook);

        mRealm.copyToRealm(myBook);
        mRealm.commitTransaction();

        addButton(myBook);
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

    private void addButton(MyBook myBook) {
        Button button = new Button(activity);
        button.setText(myBook.getTitle() + " - " + myBook.getId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMyBook(myBook, button);
            }
        });
        ll.addView(button);
    }

    private void printUI(List<MyBook> myBookList) {
        for (MyBook mb : myBookList) {
            addButton(mb);
        }
    }

    private void getAll() {
        List<MyBook> myBookList = RealmController.getInstance().getMyBookList();
        LLog.d(TAG, "getAll: " + myBookList.size());
        //logMyBook(myBookList);
        printUI(myBookList);
    }

    private void clickMyBook(MyBook myBook, Button button) {
        logMyBook(myBook);
        remove(myBook, button);
    }

    private void remove(MyBook myBook, Button button) {
        mRealm.beginTransaction();
        RealmResults<MyBook> myBookRealmResults = RealmController.getInstance().removeMyBook(myBook);
        if (!myBookRealmResults.isEmpty()) {
            for (int i = myBookRealmResults.size() - 1; i >= 0; i--) {
                myBookRealmResults.get(i).removeFromRealm();
            }
        }
        mRealm.commitTransaction();
        ll.removeView(button);
    }
}
