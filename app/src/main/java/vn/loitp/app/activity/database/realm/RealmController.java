package vn.loitp.app.activity.database.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.clear(MyBook.class);
        realm.commitTransaction();
    }

    public RealmResults<MyBook> getBooks() {
        return realm.where(MyBook.class).findAll();
    }

    //query a single item with the given id
    public MyBook getMyBook(String id) {
        return realm.where(MyBook.class).equalTo("id", id).findFirst();
    }

    public boolean hasMyBooks() {
        return !realm.allObjects(MyBook.class).isEmpty();
    }

    //query example
    public RealmResults<MyBook> queryedMyBooks() {
        return realm.where(MyBook.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}
