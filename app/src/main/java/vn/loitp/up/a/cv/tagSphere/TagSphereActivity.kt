package vn.loitp.up.a.cv.tagSphere

import android.graphics.Color
import android.os.Bundle
import android.text.TextPaint
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.tagsphere.OnTagLongPressedListener
import com.loitp.views.tagsphere.OnTagTapListener
import com.loitp.views.tagsphere.item.TagItem
import com.loitp.views.tagsphere.item.TextTagItem
import com.loitp.views.tagsphere.utils.EasingFunction
import vn.loitp.R
import vn.loitp.databinding.ATagSphereBinding

@LogTag("TagSphereActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TagSphereActivity : BaseActivityFont() {

    private lateinit var binding: ATagSphereBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATagSphereBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/magic-goop/tag-sphere"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TagSphereActivity::class.java.simpleName
        }

        binding.tagView.setTextPaint(TextPaint().apply {
            isAntiAlias = true
            textSize = resources.getDimension(R.dimen.txt_medium)
            color = Color.DKGRAY
        })
//        binding.tagView.setRadius(1.5f)
//        binding.tagView.setTouchSensitivity(5)
//        binding.tagView.rotateOnTouch(false)
//        binding.tagView.setEasingFunction {
//            EasingFunction.easeIn(it)
//        }
//        binding.tagView.setOnLongPressedListener(object : OnTagLongPressedListener {
//            override fun onLongPressed(tagItem: TagItem) {
//                showShortInformation("onLongPressed $tagItem")
//            }
//
//        })
//        binding.tagView.setOnTagTapListener(object : OnTagTapListener {
//            override fun onTap(tagItem: TagItem) {
//                showShortInformation("onTap $tagItem")
//            }
//
//        })
        (0..40).map {
            TextTagItem(text = "Loitp $it")
        }.toList().let {
            binding.tagView.addTagList(it)
        }
    }

}
