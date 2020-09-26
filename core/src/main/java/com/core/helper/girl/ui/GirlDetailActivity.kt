package com.core.helper.girl.ui

import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.girl.model.GirlPage
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.l_activity_girl_detail.*

@LogTag("GirlActivity")
@IsFullScreen(false)
class GirlDetailActivity : BaseFontActivity() {

    private var girlPage: GirlPage? = null

    companion object {
        const val KEY_GIRL_PAGE = "KEY_GIRL_PAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl_detail)

        getData()
        setupViews()
    }

    private fun getData() {
        val tmpGirlPage = intent.getSerializableExtra(KEY_GIRL_PAGE)
        if (tmpGirlPage is GirlPage) {
            girlPage = tmpGirlPage
        }
    }

    private fun setupViews() {
        LImageUtil.load(context = this, url = girlPage?.src, imageView = ivCover)
    }
}
