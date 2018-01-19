package vn.loitp.app.activity.database.sqlite;

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
import vn.loitp.app.activity.database.realm.MyBook;
import vn.loitp.app.activity.database.realm.RealmController;
import vn.loitp.app.activity.demo.ebookwithrealm.EbookWithRealmActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;

public class SqliteActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHandler(this);

        findViewById(R.id.bt_add).setOnClickListener(this);

        /*db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        List<Contact> contacts = db.getAllContacts();
        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            LLog.d(TAG, log);
        }*/
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
        return R.layout.activity_sqlite;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                addContact();
                break;
        }
    }

    private void addContact() {
        int size = db.getContactsCount();
        //LLog.d(TAG, "size: " + size);
        Contact contact = new Contact();
        contact.setName("Loitp " + size);
        contact.setPhoneNumber("" + size);
        db.addContact(contact);
    }
}
