package vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.layout.elasticdragdismisslayout.ElasticDragDismissCallback
import kotlinx.android.synthetic.main.activity_elasticdragdismisslayout.*
import vn.loitp.app.R

//https://github.com/Commit451/ElasticDragDismissLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3098
@LayoutId(R.layout.activity_elasticdragdismisslayout)
@LogTag("ElasticDragDismissLayoutActivity")
@IsFullScreen(false)
class ElasticDragDismissLayoutActivity : BaseFontActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        draggable_frame.addListener(object : ElasticDragDismissCallback() {

            override fun onDrag(elasticOffset: Float, elasticOffsetPixels: Float, rawOffset: Float, rawOffsetPixels: Float) {
                textView.text = "onDrag $elasticOffset - $elasticOffsetPixels - $rawOffset - $rawOffsetPixels"
            }

            override fun onDragDismissed() {
                textView.text = "onDragDismissed"

                //if you are targeting 21+ you might want to finish after transition
                finish()
            }
        })
    }

}
