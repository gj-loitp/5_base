package vn.loitp.app.activity.database.realm

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RealmController(application: Application) {
    val realm: Realm = Realm.getDefaultInstance()

    val myBookList: RealmResults<MyBook>
        get() = realm.where(MyBook::class.java).findAll()

    val myBookListSortByID: List<MyBook>
        get() {
            val myBookList = ArrayList(myBookList)
            sort(myBookList)
            return myBookList
        }

    //Refresh the realm istance
    fun refresh() {
        realm.refresh()
    }

    fun clearAllMyBook() {
        realm.beginTransaction()
        realm.clear(MyBook::class.java)
        realm.commitTransaction()
    }

    fun getMyBook(id: Long): MyBook {
        return realm.where(MyBook::class.java).equalTo(FIELD_ID, id).findFirst()
    }

    fun getBooks(myBook: MyBook): RealmResults<MyBook> {
        return realm.where(MyBook::class.java)
                .equalTo("title", myBook.title)
                .equalTo(FIELD_ID, myBook.id)
                .findAll()
    }

    private fun sort(myBookList: List<MyBook>) {
        Collections.sort(myBookList) { obj1, obj2 ->
            // ## Ascending order
            //return obj1.firstName.compareToIgnoreCase(obj2.firstName);
            java.lang.Long.valueOf(obj1.id).compareTo(obj2.id)

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName);
            // return Integer.valueOf(obj2.empId).compareTo(obj1.empId);
        }
    }

    fun hasMyBooks(): Boolean {
        return !realm.allObjects(MyBook::class.java).isEmpty()
    }

    //query example
    fun queryedMyBooks(): RealmResults<MyBook> {
        return realm.where(MyBook::class.java)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll()
    }

    companion object {
        const val FIELD_ID = "id"
        private var ins: RealmController? = null

        fun getInstance(): RealmController {
            return ins!!
        }

        fun with(fragment: Fragment): RealmController {
            if (ins == null) {
                ins = RealmController(fragment.activity!!.application)
            }
            return ins!!
        }

        fun with(activity: Activity): RealmController {
            if (ins == null) {
                ins = RealmController(activity.application)
            }
            return ins!!
        }

        fun with(application: Application): RealmController {
            if (ins == null) {
                ins = RealmController(application)
            }
            return ins!!
        }
    }
}
