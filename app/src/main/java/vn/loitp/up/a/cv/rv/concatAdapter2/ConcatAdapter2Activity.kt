package vn.loitp.up.a.cv.rv.concatAdapter2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getRandomNumber
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AConcatAdapter2Binding
import vn.loitp.up.a.cv.rv.concatAdapter2.adt.ContentAdapter
import vn.loitp.up.a.cv.rv.concatAdapter2.adt.TitleAdapter
import vn.loitp.up.a.cv.rv.concatAdapter2.model.ContentDetail
import vn.loitp.up.a.cv.rv.concatAdapter2.model.DummyContent
import vn.loitp.up.common.Constants

@LogTag("loitpConcatAdapter2Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ConcatAdapter2Activity : BaseActivityFont() {

    private lateinit var binding: AConcatAdapter2Binding
    private var listDummyContent = ArrayList<DummyContent>()//input data
    private val concatAdapter = ConcatAdapter()

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AConcatAdapter2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupViews()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ConcatAdapter2Activity::class.java.simpleName
        }

        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = concatAdapter

        listDummyContent.forEach { dummyContent ->
            //adapter title
            val titleAdapter = TitleAdapter()
            titleAdapter.setData(dummyContent.title)
            concatAdapter.addAdapter(titleAdapter)

            //adapter content
            val contentAdapter = ContentAdapter()
            contentAdapter.setData(dummyContent.listContentDetail)
            contentAdapter.onClickRootListener = { cd, pos ->
                cd.isSelected = !(cd.isSelected ?: false)
                contentAdapter.notifyItemChanged(pos)
            }
            concatAdapter.addAdapter(contentAdapter)
        }
        concatAdapter.notifyDataSetChanged()
    }

    private fun setupData() {
        listDummyContent.clear()
        for (i in 0..5) {
            val dummyContent = DummyContent()
            dummyContent.id = i
            dummyContent.title = "Title $i"
            val listContentDetail = ArrayList<ContentDetail>()
            for (j in 0..getRandomNumber(15)) {
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
}
