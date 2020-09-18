package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_recycler_view__parallax_yaya.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.FakeData.Companion.instance

@LayoutId(R.layout.activity_recycler_view__parallax_yaya)
@LogTag("RecyclerViewParallaxYayaActivity")
class RecyclerViewParallaxYayaActivity : BaseFontActivity() {

    private var yayaParallaxAdapter: YayaParallaxAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_2.jpg")
        instance.stringList.add("http://yayandroid.com/data/github_library/parallax_listview/test_image_1.jpg")

        yayaParallaxAdapter = YayaParallaxAdapter(context = this,
                callback = object : YayaParallaxAdapter.Callback {
                    override fun onClick(pos: Int) {
                        showShort("onClick $pos")
                    }

                    override fun onLongClick(pos: Int) {
                        instance.stringList.removeAt(pos)
                        yayaParallaxAdapter?.let {
                            it.notifyItemRemoved(pos)
                            it.notifyItemRangeChanged(pos, instance.stringList.size)
                        }
                    }
                })
        recyclerView.adapter = yayaParallaxAdapter
        //LUIUtil.setPullLikeIOSVertical(recyclerView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
