package vn.loitp.app.activity.customviews.recyclerview.netview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookViewActivity
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.ConcatAdapterActivity
import vn.loitp.app.activity.customviews.recyclerview.diffutil.DiffUtilActivity
import vn.loitp.app.activity.customviews.recyclerview.footer.RecyclerViewFooterActivity
import vn.loitp.app.activity.customviews.recyclerview.footer2.RecyclerViewFooter2Activity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerVerticalActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.RecyclerViewWithSingletonDataActivity
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.RecyclerViewParallaxYayaActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.RecyclerTabLayoutMenuActivity

@LogTag("NetViewActivity")
@IsFullScreen(false)
class NetViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_net_view
    }

}
