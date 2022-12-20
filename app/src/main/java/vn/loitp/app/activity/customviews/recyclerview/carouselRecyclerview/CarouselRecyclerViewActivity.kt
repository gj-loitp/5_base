package vn.loitp.app.activity.customviews.recyclerview.carouselRecyclerview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_carousel_recycler_view.*
import vn.loitp.app.R

@LogTag("CarouselRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class CarouselRecyclerViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_carousel_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/sparrow007/CarouselRecyclerview"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = CarouselRecyclerViewActivity::class.java.simpleName
        }

        val list = ArrayList<DataModel>()
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))
        list.add(DataModel(R.drawable.loitp, "Thi is cool"))
        list.add(DataModel(R.drawable.logo, "Thi is cool"))

        val adapter = DataAdapter(list)

        recycler.apply {
            this.adapter = adapter
            set3DItem(true)
            setAlpha(true)
            setInfinite(true)
            setIsScrollingEnabled(true)
        }

        button.setSafeOnClickListener {
            adapter.removeData()
        }
    }
}
