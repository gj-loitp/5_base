package vn.loitp.up.a.cv.rv.carouselRv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvCarouselBinding

@LogTag("CarouselRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class CarouselRecyclerViewActivity : BaseActivityFont() {
    private lateinit var binding: ARvCarouselBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvCarouselBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/sparrow007/CarouselRecyclerview"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CarouselRecyclerViewActivity::class.java.simpleName
        }

        val list = ArrayList<DataModel>()
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))

        val adapter = DataAdapter(list)

        binding.recycler.apply {
            this.adapter = adapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
            setIsScrollingEnabled(true)
        }

        binding.button.setSafeOnClickListener {
            adapter.removeData()
        }
    }
}
