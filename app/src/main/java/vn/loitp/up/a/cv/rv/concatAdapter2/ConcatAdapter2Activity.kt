package vn.loitp.up.a.cv.rv.concatAdapter2

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getRandomNumber
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AConcatAdapter2Binding
import vn.loitp.up.a.cv.rv.concatAdapter2.model.ContentDetail
import vn.loitp.up.a.cv.rv.concatAdapter2.model.DummyContent
import vn.loitp.up.common.Constants

@LogTag("loitpConcatAdapter2Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ConcatAdapter2Activity : BaseActivityFont() {

    private lateinit var binding: AConcatAdapter2Binding
    private var listDummyContent = ArrayList<DummyContent>()//input data
    private var listContentDetail = ArrayList<ContentDetail>()//transform data

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AConcatAdapter2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()//input data
        transformData()//transform data in order to fit with my recycler view
        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ConcatAdapter2Activity::class.java.simpleName
        }
    }

    private fun setupData() {
        listDummyContent.clear()
        for (i in 0..2) {
            val dummyContent = DummyContent()
            dummyContent.id = i
            dummyContent.title = "Title $i"
            val listContentDetail = ArrayList<ContentDetail>()
            for (j in 0..getRandomNumber(2)) {
                val contentDetail = ContentDetail()
                contentDetail.name = "Name $j"
                if (j % 2 == 0) {
                    contentDetail.img = Constants.URL_IMG_1
                } else {
                    contentDetail.img = Constants.URL_IMG_2
                }
                listContentDetail.add(contentDetail)
            }
            dummyContent.listContentDetail = listContentDetail
            listDummyContent.add(dummyContent)
        }
        logD(">>>setupData listDummyContent ${BaseApplication.gson.toJson(listDummyContent)}")
    }

    private fun transformData() {
        listContentDetail.clear()
        listDummyContent.forEach { dummyContent ->
            //map title
            val contentDetailTitle = ContentDetail()
            contentDetailTitle.parentId = dummyContent.id
            contentDetailTitle.parentTitle = dummyContent.title
            contentDetailTitle.isParentTitle = true
            listContentDetail.add(contentDetailTitle)

            //map item child
            dummyContent.listContentDetail.forEach { cd ->
                val contentDetailChild = ContentDetail()
                contentDetailChild.name = cd.name
                contentDetailChild.img = cd.img
                contentDetailChild.isSelected = cd.isSelected
                //map parent
                contentDetailChild.parentId = dummyContent.id
                contentDetailChild.parentTitle = dummyContent.title
                contentDetailChild.isParentTitle = false

                listContentDetail.add(contentDetailChild)
            }
        }
        logD(">>>transformData listContentDetail ${BaseApplication.gson.toJson(listContentDetail)}")

    }
}
