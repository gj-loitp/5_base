package vn.loitp.app.activity.database.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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

    public void clearAllMyBook() {
        realm.beginTransaction();
        realm.clear(MyBook.class);
        realm.commitTransaction();
    }

    public MyBook getMyBook(long id) {
        return realm.where(MyBook.class).equalTo("id", id).findFirst();
    }

    public RealmResults<MyBook> getBooks() {
        return realm.where(MyBook.class).findAll();
    }

    public RealmResults<MyBook> getBooks(MyBook myBook) {
        return realm.where(MyBook.class)
                .equalTo("title", myBook.getTitle())
                .equalTo("id", myBook.getId())
                .findAll();
    }

    public List<MyBook> getMyBookList() {
        return new ArrayList(getBooks());
    }

    public List<MyBook> getMyBookListSortByID() {
        List<MyBook> myBookList = new ArrayList(getBooks());
        sort(myBookList);
        return myBookList;
    }

    private void sort(List<MyBook> myBookList) {
        Collections.sort(myBookList, new Comparator<MyBook>() {
            public int compare(MyBook obj1, MyBook obj2) {
                // ## Ascending order
                //return obj1.firstName.compareToIgnoreCase(obj2.firstName);
                return Long.valueOf(obj1.getId()).compareTo(obj2.getId());

                // ## Descending order
                // return obj2.firstName.compareToIgnoreCase(obj1.firstName);
                // return Integer.valueOf(obj2.empId).compareTo(obj1.empId);
            }
        });
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
