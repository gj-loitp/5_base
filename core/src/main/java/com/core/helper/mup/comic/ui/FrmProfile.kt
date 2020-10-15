package com.core.helper.mup.comic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.l_frm_comic_profile.*

@LogTag("FrmInformation")
class FrmProfile : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_comic_profile, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        LImageUtil.load(context = activity, url = "https://media0.giphy.com/media/qzuGfHYC152dG/giphy.gif", imageView = ivBackground)
        LImageUtil.loadCircle(url = "https://media0.giphy.com/media/qzuGfHYC152dG/giphy.gif", imageView = ivAvatar)
    }


}
