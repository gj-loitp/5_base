package vn.loitp.up.a.anim.animationView

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.play
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationViewBinding
import java.util.*

@LogTag("AnimationViewActivity")
@IsFullScreen(false)
class AnimationViewActivity : BaseActivityFont() {
    private lateinit var binding: AAnimationViewBinding

    private var listAnim: List<Techniques> = ArrayList()
    private var listString: Array<String?>? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = AnimationViewActivity::class.java.simpleName
        }
        setupAnimList()
        binding.btSelectAnim.setOnClickListener {
            showDialogSelectAnim()
        }
    }

    private fun setupAnimList() {
        listAnim = ArrayList(EnumSet.allOf(Techniques::class.java))
        listString = arrayOfNulls(listAnim.size)
        listString?.let {
            for (i in listAnim.indices) {
                it[i] = listAnim[i].toString()
            }
        }
    }

    private fun showDialogSelectAnim() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose:")
        builder.setItems(listString) { _, position ->
            if (binding.tvGuide.visibility != View.VISIBLE) {
                binding.tvGuide.visibility = View.VISIBLE
            }
            setDelay(
                mls = 500,
                runnable = {
                    binding.tvAnim.play(
                        techniques = listAnim[position],
                        duration = 1_000
                    )
                }
            )
        }
        val dialog = builder.create()
        dialog.show()
    }
}
