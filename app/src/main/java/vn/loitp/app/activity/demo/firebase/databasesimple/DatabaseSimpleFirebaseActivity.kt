package vn.loitp.app.activity.demo.firebase.databasesimple

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil.Companion.setPullLikeIOSVertical
import com.google.firebase.database.*
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.activity_menu_firebase_simple.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants.Companion.URL_IMG
import vn.loitp.app.common.Constants.Companion.URL_IMG_1
import java.util.*

@LogTag("DatabaseSimpleFirebaseActivity")
@IsFullScreen(false)
class DatabaseSimpleFirebaseActivity : BaseFontActivity(), View.OnClickListener {
    companion object {
        private const val ROOT_NODE = "loitp"
    }

    private var mFirebaseDatabase: DatabaseReference? = null
    private val userList = ArrayList<User>()
    private var mAdapter: UserAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_firebase_simple
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.reference
        btAdd.setOnClickListener(this)
        setupUI()

        mFirebaseDatabase?.child(ROOT_NODE)?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    logD("onDataChange null => return")
                    userList.clear()
                    mAdapter?.notifyDataSetChanged()
                    return
                }
                userList.clear()
                for (data in dataSnapshot.children) {
                    val user = data.getValue(User::class.java)
                    user?.let {
                        userList.add(it)
                    }
                }
                logD("userList.size: " + userList.size)
                mAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                logE("Failed to read app title value " + error.toException())
            }
        })
    }

    override fun onClick(v: View) {
        if (v == btAdd) {
            addData()
        }
    }

    private fun addData() {
        val user = User()
        user.timestamp = System.currentTimeMillis()
        user.avt = URL_IMG
        user.name = "loitp"
        user.msg = "dummy msg " + user.timestamp
        mFirebaseDatabase?.child(ROOT_NODE)?.child(user.timestamp.toString() + "")?.setValue(user)?.addOnCompleteListener {
            //do sth
        }
    }

    private fun setupUI() {
        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 300
        rv.itemAnimator = animator
        mAdapter = UserAdapter(this, userList, object : UserAdapter.Callback {
            override fun onClick(user: User, position: Int) {
                showShortInformation("onClick To Edit Data: " + user.msg, true)
                user.msg = "Edited Msg " + System.currentTimeMillis()
                user.name = "Edited Name"
                user.avt = URL_IMG_1
                mFirebaseDatabase?.child(ROOT_NODE)?.child(user.timestamp.toString() + "")?.setValue(user)
            }

            override fun onLongClick(user: User, position: Int) {
                showShortInformation("onLongClick " + user.msg, true)
                mFirebaseDatabase?.child(ROOT_NODE)?.child(user.timestamp.toString() + "")?.removeValue()
            }
        })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = mLayoutManager
        mAdapter?.let {
            val scaleAdapter = ScaleInAnimationAdapter(it)
            scaleAdapter.setDuration(1000)
            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            rv.adapter = scaleAdapter
            setPullLikeIOSVertical(rv)
        }
    }
}
