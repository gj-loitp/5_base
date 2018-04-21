package vn.loitp.app.activity.database.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;

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
        findViewById(R.id.bt_getcontact_with_id).setOnClickListener(this);

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
            case R.id.bt_getcontact_with_id:
                getContactWithId(2);
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
                //LLog.d(TAG, "onClick " + button.getText().toString());
                updateContact(contact, button);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteContact(contact, button);
                return true;
            }
        });
        ll.addView(button);
    }

    private void addButton() {
        Button button = new Button(activity);
        Contact contact = db.getContact(db.getContactsCount());
        if (contact != null) {
            button.setText(contact.getID() + " - " + contact.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //LLog.d(TAG, "onClick " + button.getText().toString());
                    updateContact(contact, button);
                }
            });
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteContact(contact, button);
                    return true;
                }
            });
            ll.addView(button);
        }
    }

    private void addContact() {
        int size = db.getContactsCount();
        LLog.d(TAG, "size: " + size);
        Contact contact = new Contact();
        contact.setName("name " + (size + 1));
        contact.setPhoneNumber("phone: " + (size + 1));
        db.addContact(contact);
        addButton();
    }

    private void clearAllContact() {
        LLog.d(TAG, "clearAllContact");
        ll.removeAllViews();
        db.clearAllContact();
        getAllContact();
    }

    private void getContactWithId(int id) {
        Contact contact = db.getContact(id);
        if (contact == null) {
            ToastUtils.showShort("Contact with ID=" + id + " not found");
        } else {
            ToastUtils.showShort("Found: " + contact.getID() + " " + contact.getName() + " " + contact.getPhoneNumber());
        }
    }

    private void updateContact(Contact contact, Button button) {
        contact.setName("Updated " + contact.getName());
        int result = db.updateContact(contact);
        LLog.d(TAG, "updateContact result " + result);
        button.setText(contact.getID() + " " + contact.getName());
    }

    private void deleteContact(Contact contact, Button button) {
        int result = db.deleteContact(contact);
        LLog.d(TAG, "deleteContact result " + result);
        ll.removeView(button);
    }
}
