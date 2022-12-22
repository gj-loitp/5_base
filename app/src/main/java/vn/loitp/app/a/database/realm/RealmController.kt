package vn.loitp.app.a.database.realm

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class RealmController {
    val realm: Realm = Realm.getDefaultInstance()

    val myBookList: RealmResults<MyBook>
        get() = realm.where(MyBook::class.java).findAll()

    val myBookListSortByID: List<MyBook>
        get() {
            val myBookList = ArrayList(myBookList)
            sort(myBookList)
            return myBookList
        }

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

    fun getMyBook(myBook: MyBook): RealmResults<MyBook> {
        return realm.where(MyBook::class.java)
            .equalTo(FIELD_TITLE, myBook.title)
            .equalTo(FIELD_ID, myBook.id)
            .findAll()
    }

    private fun sort(myBookList: List<MyBook>) {
        Collections.sort(myBookList) { obj1, obj2 ->
            // ## Ascending order
            // return obj1.firstName.compareToIgnoreCase(obj2.firstName);
            java.lang.Long.valueOf(obj1.id).compareTo(obj2.id)

            // ## Descending order
            // return obj2.firstName.compareToIgnoreCase(obj1.firstName);
            // return Integer.valueOf(obj2.empId).compareTo(obj1.empId);
        }
    }

    @Suppress("unused")
    fun hasMyBook(): Boolean {
        return !realm.allObjects(MyBook::class.java).isEmpty()
    }

    @Suppress("unused")
    fun hasMyBooks(): RealmResults<MyBook> {
        return realm.where(MyBook::class.java)
            .contains(FIELD_AUTHOR, "Author 0")
            .or()
            .contains(FIELD_TITLE, "Realm")
            .findAll()
    }

    companion object {
        const val FIELD_ID = "id"
        const val FIELD_TITLE = "title"
        const val FIELD_AUTHOR = "author"
        private var ins: RealmController? = null

        fun getInstance(activity: Activity): RealmController {
            if (ins == null) {
                ins = RealmController()
            }
            return ins!!
        }

        fun with(fragment: Fragment): RealmController {
            if (ins == null) {
                ins = RealmController()
            }
            return ins!!
        }

        fun with(activity: Activity): RealmController {
            if (ins == null) {
                ins = RealmController()
            }
            return ins!!
        }

        fun with(application: Application): RealmController {
            if (ins == null) {
                ins = RealmController()
            }
            return ins!!
        }
    }
}
