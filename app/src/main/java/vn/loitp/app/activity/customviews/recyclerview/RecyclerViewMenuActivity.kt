package vn.loitp.app.activity.customviews.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.antonioleiva.diffutilkotlin.DiffUtilActivity
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookViewActivity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerVerticalActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.RecyclerViewWithSingletonDataActivity
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity
import vn.loitp.app.activity.customviews.recyclerview.overflyingrecyclerview.OverFlyingRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.ParallaxYayandroidRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclerbanner.RecyclerBannerActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class RecyclerViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_parallax_recyclerview).setOnClickListener(this)
        findViewById<View>(R.id.bt_normal_recyclerview).setOnClickListener(this)
        findViewById<View>(R.id.bt_normal_recyclerview_with_spansize).setOnClickListener(this)
        findViewById<View>(R.id.bt_parallax_recyclerview_yayandroid).setOnClickListener(this)
        findViewById<View>(R.id.bt_normal_recyclerview_with_singleton_data).setOnClickListener(this)
        findViewById<View>(R.id.bt_recyclerview_banner).setOnClickListener(this)
        findViewById<View>(R.id.bt_overflying_recyclerview).setOnClickListener(this)
        findViewById<View>(R.id.bt_gallery_layout_manager).setOnClickListener(this)
        findViewById<View>(R.id.bt_gallery_layout_manager_vertical).setOnClickListener(this)
        findViewById<View>(R.id.bt_bookview).setOnClickListener(this)
        findViewById<View>(R.id.bt_diff_util).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_recyclerview
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_parallax_recyclerview -> intent = Intent(activity, ParallaxRecyclerViewActivity::class.java)
            R.id.bt_normal_recyclerview -> intent = Intent(activity, RecyclerViewActivity::class.java)
            R.id.bt_normal_recyclerview_with_spansize -> intent = Intent(activity, RecyclerViewWithSpanSizeActivity::class.java)
            R.id.bt_parallax_recyclerview_yayandroid -> intent = Intent(activity, ParallaxYayandroidRecyclerViewActivity::class.java)
            R.id.bt_normal_recyclerview_with_singleton_data -> intent = Intent(activity, RecyclerViewWithSingletonDataActivity::class.java)
            R.id.bt_recyclerview_banner -> intent = Intent(activity, RecyclerBannerActivity::class.java)
            R.id.bt_overflying_recyclerview -> intent = Intent(activity, OverFlyingRecyclerViewActivity::class.java)
            R.id.bt_gallery_layout_manager -> intent = Intent(activity, GalleryLayoutManagerHorizontalActivity::class.java)
            R.id.bt_gallery_layout_manager_vertical -> intent = Intent(activity, GalleryLayoutManagerVerticalActivity::class.java)
            R.id.bt_bookview -> intent = Intent(activity, BookViewActivity::class.java)
            R.id.bt_diff_util -> intent = Intent(activity, DiffUtilActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
