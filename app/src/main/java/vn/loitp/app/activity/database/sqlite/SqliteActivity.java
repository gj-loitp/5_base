package vn.loitp.app.activity.database.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;

public class SqliteActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseHandler db;
    private List<Contact> contactList = new ArrayList<>();
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll = (LinearLayout) findViewById(R.id.ll);

        db = new DatabaseHandler(this);

        findViewById(R.id.bt_add).setOnClickListener(this);
        findViewById(R.id.bt_clear_all).setOnClickListener(this);

        getAllContact();
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
            case R.id.bt_clear_all:
                clearAllContact();
                break;
        }
    }

    private void getAllContact() {
        contactList = db.getAllContacts();
        for (Contact contact : contactList) {
            addButton(contact);
        }
    }

    private void addButton(Contact contact) {
        Button button = new Button(activity);
        button.setText(contact.getID() + " " + contact.getName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LLog.d(TAG, "onClick " + button.getText().toString());
            }
        });
        ll.addView(button);
    }

    private void addContact() {
        int size = db.getContactsCount();
        LLog.d(TAG, "size: " + size);
        Contact contact = new Contact();
        contact.setName("Loitp " + size);
        contact.setID(size);
        contact.setPhoneNumber("" + size);
        db.addContact(contact);
        addButton(contact);
    }

    private void clearAllContact() {
        LLog.d(TAG, "clearAllContact");
        ll.removeAllViews();
        db.clearAllContact();
        getAllContact();
    }
}
